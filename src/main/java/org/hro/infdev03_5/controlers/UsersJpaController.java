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

import org.hro.infdev03_5.controlers.exceptions.NonexistentEntityException;
import org.hro.infdev03_5.controlers.exceptions.PreexistingEntityException;
import org.hro.infdev03_5.entity.Characters;
import org.hro.infdev03_5.entity.Servers;
import org.hro.infdev03_5.entity.Users;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sietse
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) throws PreexistingEntityException, Exception {
        if (users.getServersList() == null) {
            users.setServersList(new ArrayList<Servers>());
        }
        if (users.getCharactersList() == null) {
            users.setCharactersList(new ArrayList<Characters>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Servers> attachedServersList = new ArrayList<Servers>();
            for (Servers serversListServersToAttach : users.getServersList()) {
                serversListServersToAttach = em.getReference(serversListServersToAttach.getClass(), serversListServersToAttach.getAdress());
                attachedServersList.add(serversListServersToAttach);
            }
            users.setServersList(attachedServersList);
            List<Characters> attachedCharactersList = new ArrayList<Characters>();
            for (Characters charactersListCharactersToAttach : users.getCharactersList()) {
                charactersListCharactersToAttach = em.getReference(charactersListCharactersToAttach.getClass(), charactersListCharactersToAttach.getName());
                attachedCharactersList.add(charactersListCharactersToAttach);
            }
            users.setCharactersList(attachedCharactersList);
            em.persist(users);
            for (Servers serversListServers : users.getServersList()) {
                serversListServers.getUsersList().add(users);
                serversListServers = em.merge(serversListServers);
            }
            for (Characters charactersListCharacters : users.getCharactersList()) {
                charactersListCharacters.getUsersList().add(users);
                charactersListCharacters = em.merge(charactersListCharacters);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsers(users.getUserName()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getUserName());
            List<Servers> serversListOld = persistentUsers.getServersList();
            List<Servers> serversListNew = users.getServersList();
            List<Characters> charactersListOld = persistentUsers.getCharactersList();
            List<Characters> charactersListNew = users.getCharactersList();
            List<Servers> attachedServersListNew = new ArrayList<Servers>();
            for (Servers serversListNewServersToAttach : serversListNew) {
                serversListNewServersToAttach = em.getReference(serversListNewServersToAttach.getClass(), serversListNewServersToAttach.getAdress());
                attachedServersListNew.add(serversListNewServersToAttach);
            }
            serversListNew = attachedServersListNew;
            users.setServersList(serversListNew);
            List<Characters> attachedCharactersListNew = new ArrayList<Characters>();
            for (Characters charactersListNewCharactersToAttach : charactersListNew) {
                charactersListNewCharactersToAttach = em.getReference(charactersListNewCharactersToAttach.getClass(), charactersListNewCharactersToAttach.getName());
                attachedCharactersListNew.add(charactersListNewCharactersToAttach);
            }
            charactersListNew = attachedCharactersListNew;
            users.setCharactersList(charactersListNew);
            users = em.merge(users);
            for (Servers serversListOldServers : serversListOld) {
                if (!serversListNew.contains(serversListOldServers)) {
                    serversListOldServers.getUsersList().remove(users);
                    serversListOldServers = em.merge(serversListOldServers);
                }
            }
            for (Servers serversListNewServers : serversListNew) {
                if (!serversListOld.contains(serversListNewServers)) {
                    serversListNewServers.getUsersList().add(users);
                    serversListNewServers = em.merge(serversListNewServers);
                }
            }
            for (Characters charactersListOldCharacters : charactersListOld) {
                if (!charactersListNew.contains(charactersListOldCharacters)) {
                    charactersListOldCharacters.getUsersList().remove(users);
                    charactersListOldCharacters = em.merge(charactersListOldCharacters);
                }
            }
            for (Characters charactersListNewCharacters : charactersListNew) {
                if (!charactersListOld.contains(charactersListNewCharacters)) {
                    charactersListNewCharacters.getUsersList().add(users);
                    charactersListNewCharacters = em.merge(charactersListNewCharacters);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = users.getUserName();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getUserName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<Servers> serversList = users.getServersList();
            for (Servers serversListServers : serversList) {
                serversListServers.getUsersList().remove(users);
                serversListServers = em.merge(serversListServers);
            }
            List<Characters> charactersList = users.getCharactersList();
            for (Characters charactersListCharacters : charactersList) {
                charactersListCharacters.getUsersList().remove(users);
                charactersListCharacters = em.merge(charactersListCharacters);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
