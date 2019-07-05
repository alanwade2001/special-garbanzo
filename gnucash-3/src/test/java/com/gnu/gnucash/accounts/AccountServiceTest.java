package com.gnu.gnucash.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
class AccountServiceTest {

	@Autowired
	private AccountRepository repository;

	private AccountService service;

	@BeforeEach
	void setup() {
		service = new AccountService(repository);
	}

	@Test
	void testNewAccountAccountEntity() {
		Account acc = new Account("test01", "description");
		Account saved = service.newAccount(acc);

		assertNotNull(saved.getId());
		assertEquals(acc.getName(), saved.getName());
	}

	@Test
	void testNewAccountNullName() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Account acc = new Account(null, "description");
			service.newAccount(acc);
		});
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
	void testDeleteAccountDoesNotExist() {
		Assertions.assertThrows(RuntimeException.class, ()->{
			
			service.deleteAccount(1000L);
			
		});	
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
		assertEquals(0, repository.getOne(parent.getId()).getChildren().size());
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
		Account child = repository.save(new Account("child", ""));

		Account parent = new Account("parent", "");

		parent = service.newAccount(parent);

		assertNotNull(parent.getId());

		service.moveAccount(child.getId(), parent.getId());
	}

	@Test
	void testGetOne() {
		Account acc = new Account("getOne", "description");
		repository.save(acc);

		Account got = service.getOne(acc.getId());

		assertNotNull(got.getId());
		assertEquals("getOne", got.getName());
	}

	@Test
	void testNewAccountExistingName() {
		Account acc = new Account("account_exists", "");
		repository.save(acc);

		Assertions.assertThrows(RuntimeException.class, () -> {
			Account newAcc = new Account("account_exists", "");
			service.newAccount(newAcc);
		});
	}
	
	@Test
	void testSaveAccount() {
		Account acc = new Account("save_account", "");
		repository.save(acc);
		
		acc.setName("name change");
		service.saveAccount(acc);
		
		Account savedAcc = repository.getOne(acc.getId());
		assertEquals(acc.getName(), savedAcc.getName());

	}
	
	

}
