package io.gnucash.cloud.accounts;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountsController {

	private final AccountsControllerService service;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<Account> getAccounts() {
		List<Account> accounts = service.getAccounts();
				
		return accounts;
	}
	
	
	@PostMapping
	public ResponseEntity<?> createAccount(UriComponentsBuilder builder, @RequestBody Account account) {
		Account created = service.createAccount(account);
		
		URI location = builder.path("/accounts/").path(created.getId()).build().toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(path = "/{id}")
	public Account getAccount(@PathVariable(name = "id", required = true) String id) {
		Account account = service.getAccount(id);
		
		return account;
	}

}
