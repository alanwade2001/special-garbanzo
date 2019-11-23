package io.gnucash.cloud.accounts;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<List<Account>> getAccounts() {
		List<Account> accounts = service.getAccounts();
				
		return ResponseEntity.ok(accounts);
	}
	
	
	@PostMapping
	public ResponseEntity<?> createAccount(UriComponentsBuilder builder, @RequestBody Account account) {
		Account created = service.createAccount(account);
		
		URI location = builder.path(created.getId()).build().toUri();
		return ResponseEntity.created(location).build();
	}

}
