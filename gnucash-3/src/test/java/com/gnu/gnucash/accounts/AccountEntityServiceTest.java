package com.gnu.gnucash.accounts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
class AccountEntityServiceTest {
	
	@Autowired
	private AccountRepository repository;
		
	private AccountService service;
	
	@BeforeEach
	void setup()
	{
		service = new AccountService(repository);
	}

	@Test
	void testNewAccountAccountEntity() {
		Account acc = new Account("test01", "description");
		Account saved = service.newAccount(acc);
		
		assertNotNull(saved.getId());
		assertEquals(acc.getName(), saved.getName());
	}

	@Test()
	void testNewAccountLongAccountEntity() {
		Account income = repository.save(new Account("income", ""));
		Account newAccount = new Account("newAccount", "");
		
		newAccount = service.newAccount(income.getId(), newAccount);
		
		assertNotNull(newAccount.getId());
		assertNotNull(newAccount.getParent());
		assertEquals(1, newAccount.getParent().getChildren().size());
	}

	@Test
	void testDeleteAccount() {
		Account deleteAccount = new Account("deleteAccount", "");
		
		deleteAccount = service.newAccount(deleteAccount);
		
		assertNotNull(deleteAccount.getId());
		
		service.deleteAccount(deleteAccount.getId());
		
		Optional<Account> found = repository.findById(deleteAccount.getId());
		
		assertFalse(found.isPresent());
	}
	
	@Test
	void testDeleteAccountWithParent() {
		Account parent = repository.save(new Account("deleteAccountParent", ""));
		
		Account deleteAccount = new Account("deleteAccount", "");
		
		deleteAccount = service.newAccount(parent.getId(), deleteAccount);
		
		assertNotNull(deleteAccount.getId());
		
		service.deleteAccount(deleteAccount.getId());
		
		Optional<Account> found = repository.findById(deleteAccount.getId());
		
		assertFalse(found.isPresent());
		
		// check that the parent does not have a child
		assertEquals( 0, repository.getOne(parent.getId()).getChildren().size());
	}
	
	@Test
	void testDeleteAccountWithChild() {
		Account child = repository.save(new Account("deleteAccountWithChildChild", ""));
		
		Account deleteAccountWithChild = new Account("deleteAccountWithChild", "");
		
		deleteAccountWithChild = service.newAccount(deleteAccountWithChild);
		
		assertNotNull(deleteAccountWithChild.getId());
		
		service.moveAccount(child.getId(), deleteAccountWithChild.getId());
		
		assertEquals(1, repository.getOne(deleteAccountWithChild.getId()).getChildren().size());
	
		service.deleteAccount(deleteAccountWithChild.getId());
		
		assertFalse(repository.findById(deleteAccountWithChild.getId()).isPresent());
		assertNull(repository.getOne(child.getId()).getParent());
	}

	@Test
	void testMoveAccount() {
		Account child = repository.save(new Account("deleteAccountWithChildChild", ""));
		
		Account deleteAccountWithChild = new Account("deleteAccountWithChild", "");
		
		deleteAccountWithChild = service.newAccount(deleteAccountWithChild);
		
		assertNotNull(deleteAccountWithChild.getId());
		
		service.moveAccount(child.getId(), deleteAccountWithChild.getId());
	}
	
	@Test
	void testGetOne() {
		Account acc = new Account("getOne", "description");
		repository.save(acc);
		
		Account got = service.getOne(acc.getId());
		
		assertNotNull(got.getId());
		assertEquals("getOne", got.getName());
	}

}
