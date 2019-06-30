package com.gnu.gnucash.entries;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gnu.gnucash.accounts.Account;
import com.gnu.gnucash.accounts.AccountRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
class EntryServiceTest {
	
	@Autowired
	private EntryRepository entryRepository;
	
	@Autowired
	private DoubleEntryRepository doubleEntryRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	private EntryService service;
	
	@BeforeEach
	public void setup() {
		service = new EntryService(doubleEntryRepository, entryRepository);
	}

	@Test	
	void testNewTransaction() {
		Account main = accountRepository.save(new Account("main",""));
		Account split = accountRepository.save(new Account("split", "split1"));
		
		
		DoubleEntry transaction = createNew(main,split, BigDecimal.TEN);
		
		DoubleEntry de = service.newTransaction(transaction);
		
		assertNotNull(de);
		assertNotNull(de.getId());
		assertNotNull(de.getMain());
		assertNotNull(de.getSplit());
		assertNotNull(de.getPostedAt());
		
		assertEquals(1, de.getSplit().size());
		
		assertEquals(0, de.getMain().getAmount().compareTo(BigDecimal.TEN));
		assertEquals(0, de.getSplit().get(0).getAmount().compareTo(BigDecimal.TEN));
		
		
	}
	
	@Test	
	void testNewTransactionOnlyCredits() {
		Account main = accountRepository.save(new Account("main",""));
		Account split = accountRepository.save(new Account("split", "split1"));
		
		
		DoubleEntry transaction = createNew(main,split, BigDecimal.TEN);
		//change split to be a credit too
		transaction.getSplit().get(0).setType(EntryType.Credit);
		
		
		Assertions.assertThrows(RuntimeException.class, () ->{
			
			service.newTransaction(transaction);
			
		}); 
			
		
				
	}

	@Test
	void testGetOne() {
		Account main = accountRepository.save(new Account("main",""));
		Account split = accountRepository.save(new Account("split", "split1"));
		
		DoubleEntry de = createNew(main, split, BigDecimal.TEN);
		de = service.newTransaction(de);
		
		Long id = de.getId();
		
		DoubleEntry found = service.getOne(id);
		
		assertNotNull(found);
		assertNotNull(found.getId());
		assertNotNull(found.getMain());
		assertNotNull(found.getSplit());
		assertNotNull(found.getPostedAt());
		
		assertEquals(1, found.getSplit().size());
		
		assertEquals(0, found.getMain().getAmount().compareTo(BigDecimal.TEN));
		assertEquals(0, found.getSplit().get(0).getAmount().compareTo(BigDecimal.TEN));
	}

	@Test
	void testFindAllByAccountId() {
		Account main = accountRepository.save(new Account("main",""));
		Account split = accountRepository.save(new Account("split", "split1"));
		
		DoubleEntry de1 = createNew(main, split, BigDecimal.TEN);
		DoubleEntry de2 = createNew(main, split, BigDecimal.TEN);
		DoubleEntry de3 = createNew(main, split, BigDecimal.TEN);
		
		service.newTransaction(de1);
		service.newTransaction(de2);
		service.newTransaction(de3);
		
		List<Entry> mainEntries = service.findAllByAccountId(main.getId());
		assertNotNull(mainEntries);
		assertEquals(3, mainEntries.size());
		
		List<Entry> splitEntries = service.findAllByAccountId(split.getId());
		assertNotNull(splitEntries);
		assertEquals(3, splitEntries.size());
	}
	
	
	private DoubleEntry createNew(Account main, Account split, BigDecimal amount)
	{
		
		
		DoubleEntry transaction = new DoubleEntry();
		transaction.setPostedAt(Calendar.getInstance());
		
		transaction.setMain(new Entry());
		transaction.getMain().setAccount(main);
		transaction.getMain().setAmount(amount);
		transaction.getMain().setType(EntryType.Credit);
		
		transaction.getSplit().add(new Entry());
		transaction.getSplit().get(0).setAccount(split);
		transaction.getSplit().get(0).setAmount(amount);
		transaction.getSplit().get(0).setType(EntryType.Debit);
		
		return transaction;
	}

}
