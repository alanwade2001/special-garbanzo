package io.gnucash.cloud.accounts;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;

import io.gnucash.cloud.accounts.dao.AccountEntity;
import io.gnucash.cloud.accounts.dao.AccountEntityService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class AccountsControllerService {
	
	private final AccountEntityService service;

	public List<Account> getAccounts() {
		Iterable<AccountEntity> acceIter = service.getAccounts();
		
		ModelMapper mapper = new ModelMapper();
		Type listType = new TypeToken<List<Account>>() {}.getType();
		
		List<Account> accs = mapper.map(acceIter, listType);
		
		return accs;
	}

}
