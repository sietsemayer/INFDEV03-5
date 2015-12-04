/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hro.infdev03_5.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Sietse
 */
@Entity
@Table(name = "users")
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
		@NamedQuery(name = "User.findByBalance", query = "SELECT u FROM User u WHERE u.balance = :balance"),
		@NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
		@NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
		@NamedQuery(name = "User.findByIban", query = "SELECT u FROM User u WHERE u.iban = :iban"),
		@NamedQuery(name = "User.findByCharacterSlots", query = "SELECT u FROM User u WHERE u.characterSlots = :characterSlots"),
		@NamedQuery(name = "User.findByLastPayment", query = "SELECT u FROM User u WHERE u.lastPayment = :lastPayment"),
		@NamedQuery(name = "User.findByMonthsPayed", query = "SELECT u FROM User u WHERE u.monthsPayed = :monthsPayed"),
		@NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
		@NamedQuery(name = "User.findByBanned", query = "SELECT u FROM User u WHERE u.banned = :banned") })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "user_name")
	private String userName;
	@Basic(optional = false)
	@Column(name = "balance")
	private int balance;
	@Basic(optional = false)
	@Column(name = "first_name")
	private String firstName;
	@Basic(optional = false)
	@Column(name = "last_name")
	private String lastName;
	@Basic(optional = false)
	@Column(name = "iban")
	private String iban;
	@Basic(optional = false)
	@Column(name = "character_slots")
	private int characterSlots;
	@Basic(optional = false)
	@Column(name = "last_payment")
	@Temporal(TemporalType.DATE)
	private Date lastPayment;
	@Basic(optional = false)
	@Column(name = "months_payed")
	private int monthsPayed;
	@Basic(optional = false)
	@Column(name = "password")
	private char[] password;
	@Basic(optional = false)
	@Column(name = "banned")
	private boolean banned;

	@ManyToMany(targetEntity = Characters.class)
//	@JoinTable(name = "owns")
//	@OrderBy("level DESC")
	private Set<Characters> CharactersSet;

	@ManyToMany(targetEntity = Servers.class)
//	@JoinTable(name = "stores")
	private Set<Servers> ServersSet;

	public User() {
	}

	public User(String userName) {
		this.userName = userName;
	}

	public User(String userName, int balance, String firstName, String lastName, String iban, int characterSlots,
			Date lastPayment, int monthsPayed, char[] password, boolean banned) {
		this.userName = userName;
		this.balance = balance;
		this.firstName = firstName;
		this.lastName = lastName;
		this.iban = iban;
		this.characterSlots = characterSlots;
		this.lastPayment = lastPayment;
		this.monthsPayed = monthsPayed;
		this.password = password;
		this.banned = banned;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public int getCharacterSlots() {
		return characterSlots;
	}

	public void setCharacterSlots(int characterSlots) {
		this.characterSlots = characterSlots;
	}

	public Date getLastPayment() {
		return lastPayment;
	}

	public void setLastPayment(Date lastPayment) {
		this.lastPayment = lastPayment;
	}

	public int getMonthsPayed() {
		return monthsPayed;
	}

	public void setMonthsPayed(int monthsPayed) {
		this.monthsPayed = monthsPayed;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public boolean getBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public void setCharactersSet(Set<Characters> charactersSet) {
		CharactersSet = charactersSet;
	}

	public void setServersSet(Set<Servers> serversSet) {
		ServersSet = serversSet;
	}

	@Override
	public String toString() {
		return "jpatutorial.Users[ userName=" + userName + " ]";
	}

}
