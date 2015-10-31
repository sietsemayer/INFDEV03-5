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
import org.hro.infdev03_5.entity.Users;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sietse
 */
public class CharactersJpaController implements Serializable {

    public CharactersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Characters characters) throws PreexistingEntityException, Exception {
        if (characters.getUsersList() == null) {
            characters.setUsersList(new ArrayList<Users>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : characters.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUserName());
                attachedUsersList.add(usersListUsersToAttach);
            }
            characters.setUsersList(attachedUsersList);
            em.persist(characters);
            for (Users usersListUsers : characters.getUsersList()) {
                usersListUsers.getCharactersList().add(characters);
                usersListUsers = em.merge(usersListUsers);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCharacters(characters.getName()) != null) {
                throw new PreexistingEntityException("Characters " + characters + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Characters characters) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Characters persistentCharacters = em.find(Characters.class, characters.getName());
            List<Users> usersListOld = persistentCharacters.getUsersList();
            List<Users> usersListNew = characters.getUsersList();
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUserName());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            characters.setUsersList(usersListNew);
            characters = em.merge(characters);
            for (Users usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.getCharactersList().remove(characters);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    usersListNewUsers.getCharactersList().add(characters);
                    usersListNewUsers = em.merge(usersListNewUsers);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = characters.getName();
                if (findCharacters(id) == null) {
                    throw new NonexistentEntityException("The characters with id " + id + " no longer exists.");
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
            Characters characters;
            try {
                characters = em.getReference(Characters.class, id);
                characters.getName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The characters with id " + id + " no longer exists.", enfe);
            }
            List<Users> usersList = characters.getUsersList();
            for (Users usersListUsers : usersList) {
                usersListUsers.getCharactersList().remove(characters);
                usersListUsers = em.merge(usersListUsers);
            }
            em.remove(characters);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Characters> findCharactersEntities() {
        return findCharactersEntities(true, -1, -1);
    }

    public List<Characters> findCharactersEntities(int maxResults, int firstResult) {
        return findCharactersEntities(false, maxResults, firstResult);
    }

    private List<Characters> findCharactersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Characters.class));
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

    public Characters findCharacters(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Characters.class, id);
        } finally {
            em.close();
        }
    }

    public int getCharactersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Characters> rt = cq.from(Characters.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
