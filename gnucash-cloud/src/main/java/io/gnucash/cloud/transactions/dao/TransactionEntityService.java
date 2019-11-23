package io.gnucash.cloud.transactions.dao;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
public class TransactionEntityService {
	
	private final TransactionEntityRepository repo;
	
	public Iterable<TransactionEntity> getTransactions() {
		return repo.findAll();
	}

}
