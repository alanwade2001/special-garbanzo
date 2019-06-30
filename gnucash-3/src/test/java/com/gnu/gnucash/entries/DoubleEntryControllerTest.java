package com.gnu.gnucash.entries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityLinks;

import com.gnu.gnucash.accounts.AccountController;
import com.gnu.gnucash.accounts.AccountService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class DoubleEntryControllerTest {
	
	@Mock
	private EntryService service;
	
	@Mock
	private EntityLinks links;
	
	private DoubleEntryController controller;

	@Test
	void testGetAccounts() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDoubleEntry() {
		fail("Not yet implemented");
	}

	@Test
	void testNewAccount() {
		fail("Not yet implemented");
	}

	@Test
	void testMapEntryResource() {
		fail("Not yet implemented");
	}

	@Test
	void testMapListOfEntryResource() {
		fail("Not yet implemented");
	}

}
