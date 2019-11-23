package io.gnucash.cloud.accounts;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class AccountsControllerService {

	public AccountsControllerService() {
	}

	public List<Account> getAccounts() {
		Account a1 = new Account("1", "income", "income description");
		Account a2 = new Account("2", "expenses", "expenses description");
		
		List<Account> accs =  Arrays.asList(a1,a2);
		
		log.info(accs.toString());
		
		return accs;
	}

}
