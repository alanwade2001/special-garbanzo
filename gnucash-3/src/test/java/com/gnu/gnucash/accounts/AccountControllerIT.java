package com.gnu.gnucash.accounts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIT {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	AccountRepository repository;
	
	@BeforeEach
	public void beforeEach() {
		Account ae = new Account("income","description");
		repository.save(ae);
	}
	
	@Test
    @DisplayName("Integration test which will get the actual output of text service")
    public void contextLoads() throws Exception {
		mockMvc.perform(get("/accounts")).andExpect(status().isOk());
	}

}
