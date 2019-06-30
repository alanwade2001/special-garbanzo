package com.gnu.gnucash.entries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
class EntryRepositoryTest {
	
	@Autowired
	private EntryRepository entryRepository;
	
	@Autowired
	private DoubleEntryRepository doubleEntryRepository;

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
