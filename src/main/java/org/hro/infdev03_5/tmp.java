package org.hro.infdev03_5;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hro.infdev03_5.entity.Characters;

public class tmp {
//	int level = Integer.parseInt(selectedLevel);
//	String queryParameter =  charactername.getModelObject();
//	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
//	EntityManager entityManager = entityManagerFactory.createEntityManager();
//	Query queryFindUsername = entityManager.createNamedQuery("Characters.findByName");
//	queryFindUsername.setParameter("name", queryParameter);
//	@SuppressWarnings("unchecked")
//	List<Characters> ResultsetUserList = queryFindUsername.getResultList();
//	if(ResultsetUserList.isEmpty()){
//		System.out.println("Character DOES NOT EXISTS!! USER IS BEING CREATED!!");
//		entityManager.getTransaction().begin();
//		Characters characters = new Characters();
//		characters.setName(charactername.getModelObject());
//		characters.setLevel(Integer.parseInt(selectedLevel));
//		characters.setRace(selectedRace);
//		characters.setClass1("selectedClass");
//		entityManager.persist(characters);
//		entityManager.getTransaction().commit();
//		entityManager.close();
//		entityManagerFactory.close();
//		info("Character " + charactername.getModelObject() + " was created successfully ");
//	} else {
//		System.out.println("USER EXISTS!! END OF STORY!!");
//		info("You already hava a character with this name.");
//		entityManager.close();
//		entityManagerFactory.close();
//	}

}
