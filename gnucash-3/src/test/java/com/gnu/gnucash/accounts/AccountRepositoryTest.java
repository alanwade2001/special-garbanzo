package com.gnu.gnucash.accounts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
class AccountRepositoryTest {
	
	@Autowired
	private AccountRepository repository;

	@Test
	void saveAccountEntity() {
		Account account = new Account("test01", "test01 description");
		
		Account saved = repository.save(account);
		
		assertNotNull(saved.getId());
		assertEquals(account.getName(), saved.getName());
		assertEquals(account.getDescription(), saved.getDescription());
	}
	
	@Test
	void saveAccountEntityWithParent() {
		Account parent = new Account("test02-parent", "test02 description");
		
		Account savedparent = repository.save(parent);
		
		assertNotNull(savedparent.getId());
		assertEquals(parent.getName(), savedparent.getName());
		assertEquals(parent.getDescription(), savedparent.getDescription());
		
		Account child = new Account("test02-childt", "child description");
		child.setParent(savedparent);
		
		Account savedChild = repository.save(child);
		assertEquals(child.getName(), savedChild.getName());
		assertEquals(child.getDescription(), savedChild.getDescription());
		
		assertNotNull(savedChild.getId());
		assertNotNull(savedChild.getParent());
		assertEquals(savedparent.getId(), savedChild.getParent().getId());
		
	}
	
	@Test
	void saveAccountEntityWithChildren() {
		Account child = new Account("test02-child", "test02 description");
		
		child = repository.save(child);
		
		assertNotNull(child.getId());
		
		Account parent = new Account("test02-parent", "parent description");
		parent.getChildren().add(child);
		
		Account saved = repository.save(parent);
		
		assertNotNull(saved.getId());
		assertNull(saved.getParent());
		assertEquals(1, saved.getChildren().size());
		assertEquals(child.getId(), saved.getChildren().get(0).getId());
	}
	
	@Test
	void saveAccountEntityWithParentAndChildren() {
		Account parent = new Account("parent", "");
		Account account = new Account("account", "");
		Account child = new Account("child", "");
		
		parent = repository.save(parent);
		child = repository.save(child);
		
		assertNotNull(parent.getId());
		assertNotNull(child.getId());
		
		account.setParent(parent);
		account.getChildren().add(child);
		
		repository.save(account);
		assertNotNull(account.getId());
		assertNotNull(account.getParent());
		assertEquals(1, account.getChildren().size());
	}
	
	@Test
	void deleteAccountEntity() {
		Account account = new Account("test01", "test01 description");
		
		Account saved = repository.save(account);
		
		assertNotNull(saved.getId());	
	
		repository.delete(saved);
		
		Optional<Account> found = repository.findById(saved.getId());
		
		assertFalse(found.isPresent());
	}
	
	@Test
	void addChild() {
		Account child = new Account("test02-child", "test02 description");
		
		child = repository.save(child);
		
		assertNotNull(child.getId());
		
		Account parent = new Account("test02-parent", "parent description");
		
		Account saved = repository.save(parent);
		assertEquals(0, saved.getChildren().size());
		
		parent.getChildren().add(child);
		
		saved = repository.save(parent);
		
		assertNotNull(saved.getId());
		assertNull(saved.getParent());
		assertEquals(1, saved.getChildren().size());
		assertEquals(child.getId(), saved.getChildren().get(0).getId());
	}
	
	@Test
	void moveChild() {
		Account parent1 = new Account("p1", "");
		Account parent2 = new Account("p2", "");
		Account child = new Account("c1", "");
		
		repository.saveAll(Arrays.asList(parent1, parent2, child));
		
		parent1.getChildren().add(child);
		repository.save(parent1);
		
		assertEquals(1, parent1.getChildren().size());
		assertEquals(0, parent2.getChildren().size());
		
		parent1.getChildren().remove(child);
		parent2.getChildren().add(child);
		
		repository.saveAll(Arrays.asList(parent1, parent2));
	
		assertEquals(0, parent1.getChildren().size());
		assertEquals(1, parent2.getChildren().size());
		
	}
	
	@Test
	void deleteChild() {
Account child = new Account("test02-child", "test02 description");
		
		child = repository.save(child);
		
		assertNotNull(child.getId());
		
		Account parent = new Account("test02-parent", "parent description");
		parent.getChildren().add(child);
		
		Account saved = repository.save(parent);
		
		assertNotNull(saved.getId());
		assertNull(saved.getParent());
		assertEquals(1, saved.getChildren().size());
		assertEquals(child.getId(), saved.getChildren().get(0).getId());
		
		// delete the child
		repository.delete(child);
		parent.getChildren().remove(child);
		repository.save(parent);
		
		parent = repository.getOne(parent.getId());
		
		assertEquals(0, parent.getChildren().size());
	}

}
