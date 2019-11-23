package io.gnucash.cloud.transactions;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry {
	
	private String id;
	private String description;
	private String accountName;
	private BigDecimal amount;
	private String entryType;
}
