package com.gnu.gnucash.entries;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.gnu.gnucash.accounts.Account;

@Entity
public class Entry {

	@Id @GeneratedValue
	private Long id;
	
	private String description;
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	private EntryType type;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private DoubleEntry transaction;
	
	public Entry() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public EntryType getType() {
		return type;
	}

	public void setType(EntryType type) {
		this.type = type;
	}

	public DoubleEntry getTransaction() {
		return transaction;
	}
	
	public void setTransaction(DoubleEntry transaction) {
		this.transaction = transaction;
	}
	
	public Account getAccount() {
		return account;
	}
	
	
	public void setAccount(Account account) {
		this.account = account;
	}

}
