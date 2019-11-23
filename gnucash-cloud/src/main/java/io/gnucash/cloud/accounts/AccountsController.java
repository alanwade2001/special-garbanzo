package io.gnucash.cloud.accounts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountsController {

	private final AccountsControllerService service;
	
	public ResponseEntity<List<Account>> getAccounts() {
		List<Account> accounts = service.getAccounts();
		
		return ResponseEntity.ok(accounts);
	}

}
