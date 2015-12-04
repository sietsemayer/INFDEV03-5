/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hro.infdev03_5.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;

/**
 *
 * @author Sietse
 */
@Entity
@Table(name = "servers")
@NamedQueries({ @NamedQuery(name = "Servers.findAll", query = "SELECT s FROM Servers s"),
		@NamedQuery(name = "Servers.findAllSorted", query = "SELECT s FROM Servers s ORDER BY s.name ASC"),
		@NamedQuery(name = "Servers.findByAdress", query = "SELECT s FROM Servers s WHERE s.adress = :adress"),
		@NamedQuery(name = "Servers.findByName", query = "SELECT s FROM Servers s WHERE s.name = :name"),
		@NamedQuery(name = "Servers.findByLocation", query = "SELECT s FROM Servers s WHERE s.location = :location"),
		@NamedQuery(name = "Servers.findByMaxUsers", query = "SELECT s FROM Servers s WHERE s.maxUsers = :maxUsers"),
		@NamedQuery(name = "Servers.findByConnectedUsers", query = "SELECT s FROM Servers s WHERE s.connectedUsers = :connectedUsers") })

public class Servers implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@Basic(optional = false)
	@Column(name = "adress")
	private Integer adress;
	@Basic(optional = false)
	@Column(name = "location")
	private String location;
	@Basic(optional = false)
	@Column(name = "max_users")
	private int maxUsers;
	@Basic(optional = false)
	@Column(name = "connected_users")
	private int connectedUsers;
	
	@ManyToMany(targetEntity=User.class)
//	@JoinTable(name = "stores")
	private Set<User> userSet;

	public Servers() {
	}

	public Servers(Integer adress) {
		this.adress = adress;
	}

	public Servers(Integer adress, String name, String location, int maxUsers, int connectedUsers) {
		this.adress = adress;
		this.name = name;
		this.location = location;
		this.maxUsers = maxUsers;
		this.connectedUsers = connectedUsers;
	}

	public Integer getAdress() {
		return adress;
	}

	public void setAdress(Integer adress) {
		this.adress = adress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(int maxUsers) {
		this.maxUsers = maxUsers;
	}

	public int getConnectedUsers() {
		return connectedUsers;
	}

	public void setConnectedUsers(int connectedUsers) {
		this.connectedUsers = connectedUsers;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}


	@Override
	public String toString() {
		return "jpatutorial.Servers[ adress=" + adress + " ]";
	}

}
