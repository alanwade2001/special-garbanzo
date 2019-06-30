package com.gnu.gnucash.accounts;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountResource> {

	public AccountResourceAssembler() {
		super(AccountController.class, AccountResource.class);
	}

	@Override
	public AccountResource toResource(Account entity) {
		AccountResource resource = createResourceWithId(entity.getId(), entity);
		resource.setName(entity.getName());
		resource.setDescription(entity.getDescription());
		
		return resource;
	}

	@Override
	public List<AccountResource> toResources(Iterable<? extends Account> entities) {
		return StreamSupport.stream(entities.spliterator(), false)
				.map(x->toResource(x))
				.collect(Collectors.toList());
	}
}
