/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hro.infdev03_5.controlers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hro.infdev03_5.entity.Users;
import org.hro.infdev03_5.entity.Servers;
import org.hro.infdev03_5.controlers.exceptions.NonexistentEntityException;
import org.hro.infdev03_5.controlers.exceptions.PreexistingEntityException;

/**
 *
 * @author Sietse
 */
public class ServersJpaController implements Serializable {

	public ServersJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Servers servers) throws PreexistingEntityException, Exception {
		if (servers.getUsersList() == null) {
			servers.setUsersList(new ArrayList<Users>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Users> attachedUsersList = new ArrayList<Users>();
			for (Users usersListUsersToAttach : servers.getUsersList()) {
				usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(),
						usersListUsersToAttach.getUserName());
				attachedUsersList.add(usersListUsersToAttach);
			}
			servers.setUsersList(attachedUsersList);
			em.persist(servers);
			for (Users usersListUsers : servers.getUsersList()) {
				usersListUsers.getServersList().add(servers);
				usersListUsers = em.merge(usersListUsers);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findServers(servers.getAdress()) != null) {
				throw new PreexistingEntityException("Servers " + servers + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Servers servers) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Servers persistentServers = em.find(Servers.class, servers.getAdress());
			List<Users> usersListOld = persistentServers.getUsersList();
			List<Users> usersListNew = servers.getUsersList();
			List<Users> attachedUsersListNew = new ArrayList<Users>();
			for (Users usersListNewUsersToAttach : usersListNew) {
				usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(),
						usersListNewUsersToAttach.getUserName());
				attachedUsersListNew.add(usersListNewUsersToAttach);
			}
			usersListNew = attachedUsersListNew;
			servers.setUsersList(usersListNew);
			servers = em.merge(servers);
			for (Users usersListOldUsers : usersListOld) {
				if (!usersListNew.contains(usersListOldUsers)) {
					usersListOldUsers.getServersList().remove(servers);
					usersListOldUsers = em.merge(usersListOldUsers);
				}
			}
			for (Users usersListNewUsers : usersListNew) {
				if (!usersListOld.contains(usersListNewUsers)) {
					usersListNewUsers.getServersList().add(servers);
					usersListNewUsers = em.merge(usersListNewUsers);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = servers.getAdress();
				if (findServers(id) == null) {
					throw new NonexistentEntityException("The servers with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Servers servers;
			try {
				servers = em.getReference(Servers.class, id);
				servers.getAdress();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The servers with id " + id + " no longer exists.", enfe);
			}
			List<Users> usersList = servers.getUsersList();
			for (Users usersListUsers : usersList) {
				usersListUsers.getServersList().remove(servers);
				usersListUsers = em.merge(usersListUsers);
			}
			em.remove(servers);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Servers> findServersEntities() {
		return findServersEntities(true, -1, -1);
	}

	public List<Servers> findServersEntities(int maxResults, int firstResult) {
		return findServersEntities(false, maxResults, firstResult);
	}

	private List<Servers> findServersEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Servers.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Servers findServers(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Servers.class, id);
		} finally {
			em.close();
		}
	}

	public int getServersCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Servers> rt = cq.from(Servers.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
