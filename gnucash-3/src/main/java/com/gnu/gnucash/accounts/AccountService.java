package com.gnu.gnucash.accounts;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

	private final AccountRepository repository;

	public AccountService(AccountRepository repository) {
		super();
		this.repository = repository;
	}

	public Account newAccount(Account account) {
		return repository.save(account);
	}

	public Account newAccount(Long parentId, Account account) {
		Account parent = repository.getOne(parentId);
		account.setParent(parent);
		repository.save(account);

		parent.getChildren().add(account);
		repository.save(parent);

		return account;
	}

	public void deleteAccount(long accountId) {
		Optional<Account> result = repository.findById(accountId);

		if (result.isPresent() == true) {
			
			Account account = result.get();

			// remove from parent if exists
			if (account.getParent() != null) {
				account.getParent().getChildren().remove(account);
				repository.save(account.getParent());
			}
			
			// remove from children
			if( account.getChildren().size() > 0 ) {
				for (Account child : account.getChildren()) {
					child.setParent(null);
				}
				
				repository.saveAll(account.getChildren());
			}			
		}

		repository.deleteById(accountId);
	}

	public Account moveAccount(Long accountId, Long newParentId) {
		Account account = repository.getOne(accountId);
		Account newParent = repository.getOne(newParentId);
		Account oldParent = account.getParent();

		account.setParent(newParent);
		repository.save(account);
		
		newParent.getChildren().add(account);
		repository.save(newParent);
		
		// check if there is an old parent
		if( oldParent != null )
		{
			oldParent.getChildren().remove(account);
			repository.save(oldParent);
		}

		return account;
	}
	
	public Account getOne( Long id )
	{
		return repository.getOne(id);
	}
	
	public List<Account> findAll() {
		return repository.findAll();
	}
}
