package com.gnu.gnucash.accounts;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/accounts", consumes="application/*")
@ExposesResourceFor(value = Account.class)
public class AccountController {
	
	private final EntityLinks entityLinks;
	
	private final AccountService service;


	public AccountController(EntityLinks entityLinks, AccountService service) {
		this.entityLinks = entityLinks;
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<Resources<AccountResource>> getAccounts() {
		
		List<Account> accounts = service.findAll();
		
		AccountResourceAssembler assembler = new AccountResourceAssembler();		
		List<AccountResource> resourceList = assembler.toResources(accounts);
		Resources<AccountResource> resources = new Resources<>(resourceList);
		
		return ResponseEntity.ok(resources);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<AccountResource> getAccount(@PathVariable("id") Long id )
	{
		Account entity = service.getOne(id);
		AccountResourceAssembler assembler = new AccountResourceAssembler();
		AccountResource resource = assembler.toResource(entity);
		
		return ResponseEntity.ok(resource);
	}
	
	@PostMapping
	public ResponseEntity<AccountResource> newAccount( @RequestBody AccountResource resource )
	{
		Account entity = new Account();
		entity.setDescription(resource.getDescription());
		entity.setName(resource.getName());
		
		entity = service.newAccount(entity);
		
		AccountResourceAssembler assembler = new AccountResourceAssembler();
		AccountResource converted = assembler.toResource(entity);
		
		Link link = entityLinks.linkToSingleResource(Account.class, entity.getId());
		
		final URI uri = URI.create(link.getHref()); 
		
		
        return ResponseEntity.created(uri).body(converted);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id )
	{
		service.deleteAccount(id);
		
		return ResponseEntity.ok().build();
	}
	
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<AccountResource> putAccount(@PathVariable("id") Long id, @RequestBody AccountResource resource )
	{
		Account entity = new Account();
		entity.setDescription(resource.getDescription());
		entity.setName(resource.getName());
		entity.setId(id);
		
		Account savedAccount = service.saveAccount(entity);
		
		AccountResourceAssembler assembler = new AccountResourceAssembler();
		AccountResource savedResource = assembler.toResource(savedAccount);
		
		return ResponseEntity.ok(savedResource);
	}
}
