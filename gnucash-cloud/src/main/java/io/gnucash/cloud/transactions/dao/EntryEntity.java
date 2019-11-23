package io.gnucash.cloud.transactions.dao;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EntryEntity {
	private Long id;
	private String description;
	private String accountName;
	private BigDecimal amount;
	private String entryType;
}
