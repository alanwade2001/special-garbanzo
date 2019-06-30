package com.gnu.gnucash.accounts;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
//@Table(name = "ACCOUNTS", schema = "CASH")
public class Account{
	
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;
	
	@ManyToOne
	private Account parent;
	
	@OneToMany
	private List<Account> children;

	public Account() {
	}

	public Account(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Account(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Account getParent() {
		return parent;
	}

	public void setParent(Account parent) {
		this.parent = parent;
	}

	public List<Account> getChildren() {
		if( children == null )
		{
			children = new ArrayList<>();
		}
		
		return children;
	}

	public void setChildren(List<Account> children) {
		this.children = children;
	}
	
	

}
