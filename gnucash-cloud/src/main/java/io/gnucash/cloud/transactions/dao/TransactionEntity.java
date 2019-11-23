package io.gnucash.cloud.transactions.dao;

import java.util.List;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity(name = "Transactions")
public class TransactionEntity {

	@Id
	private Long id;
	
	private List<EntryEntity> entries;
}
