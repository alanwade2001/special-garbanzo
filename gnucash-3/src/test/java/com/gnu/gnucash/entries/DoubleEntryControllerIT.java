package com.gnu.gnucash.entries;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.gnu.gnucash.accounts.Account;
import com.gnu.gnucash.accounts.AccountRepository;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DoubleEntryControllerIT {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	DoubleEntryRepository repository;
	
	@Autowired
	AccountRepository accountRepos;
	
	
	Account incomeAccount;
	
	Account expenseAccount;
	
	@BeforeEach
	public void beforeEach() {
		incomeAccount = new Account("income","description");
		accountRepos.save(incomeAccount);
		
		expenseAccount = new Account("expense","descr 2");
		accountRepos.save(expenseAccount);
		
		DoubleEntry one = DoubleEntryControllerTest.createDoubleEntry();
		one.getMain().setAccount(incomeAccount);
		one.getSplit().get(0).setAccount(expenseAccount);
	}
	
	@Test
    @DisplayName("Integration test which will get the actual output of text service")
    public void contextLoads() throws Exception {
		mockMvc.perform(get("/doubleEntries")).andExpect(status().isOk());		
	
	}

}
