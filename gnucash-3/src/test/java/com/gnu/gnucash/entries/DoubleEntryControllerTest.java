package com.gnu.gnucash.entries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.ControllerEntityLinks;
import org.springframework.hateoas.mvc.ControllerLinkBuilderFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gnu.gnucash.accounts.Account;
import com.gnu.gnucash.entries.DoubleEntryResource.EntryResource;

@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class DoubleEntryControllerTest {
	
	@Mock
	private EntryService service;
	
	@Mock
	private EntityLinks links;
	
	private DoubleEntryController controller;
	
	@BeforeEach
	public void setup() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		ControllerLinkBuilderFactory f = new ControllerLinkBuilderFactory();
		ControllerEntityLinks entityLinks = new ControllerEntityLinks(Arrays.asList(DoubleEntryController.class), f);
		controller = new DoubleEntryController(entityLinks, service);
	}

	@Test
	void testGetDoubleEntries() {
		Mockito.doReturn(new ArrayList<>()).when(service).findAll();
		
		ResponseEntity<Resources<DoubleEntryResource>> result = controller.getDoubleEntries();
		
		assertEquals(0, result.getBody().getContent().size());
	}
	
	@Test
	void testGetDoubleEntries_1() {
		
		DoubleEntry one = createDoubleEntry();
		
		Mockito.doReturn(Arrays.asList(one)).when(service).findAll();
		
		ResponseEntity<Resources<DoubleEntryResource>> result = controller.getDoubleEntries();
		
		assertNotNull(result);
		
		assertEquals(1, result.getBody().getContent().size());
		
		DoubleEntryResource[] content = result.getBody().getContent().toArray(new DoubleEntryResource[0]);
		
		assertEquals(one.getPostedAt(), content[0].getPostedAt());
	}

	@Test
	void testGetDoubleEntry() {
		DoubleEntry one = createDoubleEntry();
		
		Mockito.doReturn(one).when(service).getOne(Mockito.anyLong());
		
		ResponseEntity<DoubleEntryResource> found = controller.getDoubleEntry(1L);
		
		assertEquals(one.getPostedAt(), found.getBody().getPostedAt());
	}

	@Test
	void testNewDoubleEntry() {
		DoubleEntry savedOne = createDoubleEntry();
		savedOne.setId(100L);
		Mockito.doReturn(savedOne).when(service).newTransaction(Mockito.any());
		
		DoubleEntryResource newResource = createDoubleEntryResource();		
		ResponseEntity<DoubleEntryResource> result = controller.newDoubleEntry(newResource);
	
		assertNotNull(result);
		assertNotNull(result.getBody().getId());
	
	}

	@Test
	void testMapEntryResource() {
		EntryResource resource = new EntryResource();
		resource.setAccountId(1L);
		resource.setAmount(BigDecimal.TEN);
		resource.setDescription("entry description");
		resource.setType(EntryType.Credit);
		
		Entry entry = controller.map(resource);
		
		assertNotNull(entry);
		assertNotNull(entry.getAccount());
		assertEquals(resource.getAccountId(), entry.getAccount().getId());
		assertEquals(resource.getAmount(), entry.getAmount());
		assertEquals(resource.getDescription(), entry.getDescription());
		assertEquals(resource.getType(), entry.getType());
	}

	@Test
	void testMapListOfEntryResource() {
		EntryResource resource1 = new EntryResource();
		resource1.setAccountId(1L);
		resource1.setAmount(BigDecimal.TEN);
		resource1.setDescription("entry description one");
		resource1.setType(EntryType.Credit);
		
		EntryResource resource2 = new EntryResource();
		resource2.setAccountId(2L);
		resource2.setAmount(BigDecimal.ONE);
		resource2.setDescription("entry description two");
		resource2.setType(EntryType.Debit);
		
		List<Entry> result = controller.map(Arrays.asList(resource1, resource2));
		
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(1L, (long)result.get(0).getAccount().getId());
		assertEquals(2L, (long)result.get(1).getAccount().getId());
		
		assertEquals(BigDecimal.TEN, result.get(0).getAmount());
		assertEquals(BigDecimal.ONE, result.get(1).getAmount());
		
		assertEquals("entry description one", result.get(0).getDescription());
		assertEquals("entry description two", result.get(1).getDescription());
		
	}
	
	
	public static DoubleEntry createDoubleEntry() {
		DoubleEntry one = new DoubleEntry();
		one.setPostedAt( Calendar.getInstance());
		Entry main = new Entry();
		
		main.setAccount( new Account(1L, "main", "main account"));
		main.setAmount(BigDecimal.TEN);
		main.setDescription("main descr");
		main.setTransaction(one);
		main.setType(EntryType.Credit);
		one.setMain(main);

		Entry split = new Entry();
		split.setAccount(new Account(2L, "split", "split description"));
		split.setDescription("split descr");
		split.setTransaction(one);
		split.setType(EntryType.Debit);
		one.getSplit().add(split);

		return one;
	}
	
	public static DoubleEntryResource createDoubleEntryResource() {
		DoubleEntryResource newResource = new DoubleEntryResource();
		newResource.setPostedAt( Calendar.getInstance());
		newResource.setMain( new DoubleEntryResource.EntryResource());
		newResource.getMain().setAccountId(1L);
		newResource.getMain().setAmount(BigDecimal.TEN);
		newResource.getMain().setDescription("main description");
		newResource.getMain().setType(EntryType.Credit);
		
		newResource.getSplit().add(new DoubleEntryResource.EntryResource());
		newResource.getSplit().get(0).setAccountId(2L);
		newResource.getSplit().get(0).setAmount(BigDecimal.TEN);
		newResource.getSplit().get(0).setDescription("split description");
		newResource.getSplit().get(0).setType(EntryType.Debit);
		
		return newResource;
	}

}
