package com.gnu.gnucash.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.ControllerEntityLinks;
import org.springframework.hateoas.mvc.ControllerLinkBuilderFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

	@Mock
	private AccountService service;
	
	@Mock
	private EntityLinks links;
	
	private AccountController controller;
	
	
	@BeforeEach
	public void setup() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		ControllerLinkBuilderFactory f = new ControllerLinkBuilderFactory();
		ControllerEntityLinks entityLinks = new ControllerEntityLinks(Arrays.asList(AccountController.class), f);
		controller = new AccountController(entityLinks, service);
	}
	
	@Test
	void testGetAccounts() {
		
		Mockito.doReturn(new ArrayList<>()).when(service).findAll();
		
		ResponseEntity<Resources<AccountResource>> result = controller.getAccounts();
		
		assertEquals(0, result.getBody().getContent().size());
	}
	
	@Test
	void testGetAccounts_1() {
		
		Account one = new Account(1L, "one", "one description");
		
		Mockito.doReturn(Arrays.asList(one)).when(service).findAll();
		
		ResponseEntity<Resources<AccountResource>> result = controller.getAccounts();
		
		assertEquals(1, result.getBody().getContent().size());
		
		AccountResource[] content = result.getBody().getContent().toArray(new AccountResource[0]);
		
		assertEquals(one.getName(), content[0].getName());
	}
	
	@Test
	void testNewAccount( ) {
		Mockito.doAnswer(new Answer<Account>() {

			@Override
			public Account answer(InvocationOnMock invocation) throws Throwable {
				Account entity = invocation.getArgument(0);
				entity.setId(1L);
				return entity;
			}
		}).when(service).newAccount(Mockito.any());
		
		
		AccountResource resource = new AccountResource();
		resource.setName("newAccount");
		resource.setDescription("newDescription");
		
		ResponseEntity<AccountResource> response = controller.newAccount(resource);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		assertEquals("http://localhost/accounts/1", response.getHeaders().getLocation().toString());
	}
	
	@Test
	void testDeleteAccount() {
		ResponseEntity<?> result = controller.deleteAccount(1L);
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void testPutAccount() {
		AccountResource resource = new AccountResource();
		resource.setName("putAccount");
		
		ResponseEntity<AccountResource> response = controller.putAccount(1L, resource);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
}
