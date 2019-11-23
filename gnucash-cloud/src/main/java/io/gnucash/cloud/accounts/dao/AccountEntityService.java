package io.gnucash.cloud.accounts.dao;

import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class AccountEntityService {
	private final AccountEntityRepository repo;
	
	public Iterable<AccountEntity> getAccounts() 
	{
		return repo.findAll();
	}

	public AccountEntity createAccount(AccountEntity entity) {
		return repo.save(entity);
	}

	public AccountEntity getAccount(Long lid) {
		return repo.findById(lid).get();
	}

}
