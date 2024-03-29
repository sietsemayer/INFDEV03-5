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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Sietse
 */
@Entity 
@Table(name = "characters")
@NamedQueries({ @NamedQuery(name = "Characters.findAll", query = "SELECT c FROM Characters c"),
		@NamedQuery(name = "Characters.findByName", query = "SELECT c FROM Characters c WHERE c.name = :name"),
		@NamedQuery(name = "Characters.findByClass1", query = "SELECT c FROM Characters c WHERE c.class1 = :class1"),
		@NamedQuery(name = "Characters.findByRace", query = "SELECT c FROM Characters c WHERE c.race = :race"),
		@NamedQuery(name = "Characters.findByLevel", query = "SELECT c FROM Characters c WHERE c.level = :level") })
public class Characters implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@Basic(optional = false)
	@Column(name = "class")
	private String class1;
	@Basic(optional = false)
	@Column(name = "race")
	private String race;
	@Basic(optional = false)
	@Column(name = "level")
	private int level;

	@ManyToMany(targetEntity = User.class)
//	@JoinTable(name = "owns")
	private Set<User> userSet;
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "owns")
//	private List<User> usersList;

	public Characters() {
	}

	public Characters(String name) {
		this.name = name;
	}

	public Characters(String name, String class1, String race, int level) {
		this.name = name;
		this.class1 = class1;
		this.race = race;
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClass1() {
		return class1;
	}

	public void setClass1(String class1) {
		this.class1 = class1;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (name != null ? name.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Characters)) {
			return false;
		}
		Characters other = (Characters) object;
		if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jpatutorial.Characters[ name=" + name + " ]";
	}

}
