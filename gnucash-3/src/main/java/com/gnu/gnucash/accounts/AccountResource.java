package com.gnu.gnucash.accounts;

import org.springframework.hateoas.ResourceSupport;


public class AccountResource extends ResourceSupport {

	private String name;
	private String description;
	
	
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
	
	

	

}
