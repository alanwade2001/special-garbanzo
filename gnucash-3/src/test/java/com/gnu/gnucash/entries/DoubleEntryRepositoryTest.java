package com.gnu.gnucash.entries;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gnu.gnucash.accounts.Account;
import com.gnu.gnucash.accounts.AccountRepository;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
class DoubleEntryRepositoryTest {
	
	@Autowired
	private EntryRepository entryRepository;
	
	@Autowired
	private DoubleEntryRepository doubleEntryRepository;
	
	@Autowired
	private AccountRepository accountRepository;


	@Test
	void test() {
		DoubleEntry transaction = new DoubleEntry();
		transaction.setPostedAt(Calendar.getInstance());
		
		doubleEntryRepository.save(transaction);
		
		assertNotNull(transaction.getId());
	}
	
	@Test
	void test_02() {
		DoubleEntry transaction = new DoubleEntry();
		transaction.setPostedAt(Calendar.getInstance());
		
		doubleEntryRepository.save(transaction);
		assertNotNull(transaction.getId());
		
		Account mainAcc = accountRepository.save(new Account("mainAcc", ""));
		Account split1Acc = accountRepository.save(new Account("split1Acc", ""));
		
		Entry main = new Entry();
		main.setAmount(BigDecimal.TEN);
		main.setDescription("main");
		main.setTransaction(transaction);
		main.setType(EntryType.Credit);
		main.setAccount(mainAcc);
		
		entryRepository.save(main);
		
		Entry split1 = new Entry();
		split1.setAmount(BigDecimal.TEN);
		split1.setDescription("split1");
		split1.setTransaction(transaction);
		split1.setType(EntryType.Debit);
		split1.setAccount(split1Acc);
		
		entryRepository.save(split1);
		
		transaction.setMain(main);
		transaction.getSplit().add(split1);
		DoubleEntry result = doubleEntryRepository.save(transaction);
		
		assertNotNull(result);
		assertEquals( main.getDescription(), result.getMain().getDescription());
		assertEquals(1, result.getSplit().size());
		assertEquals(split1.getDescription(), result.getSplit().get(0).getDescription());
		
	}

}
