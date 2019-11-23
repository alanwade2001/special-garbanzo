package io.gnucash.cloud.transactions;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;

import io.gnucash.cloud.accounts.Account;
import io.gnucash.cloud.transactions.dao.TransactionEntity;
import io.gnucash.cloud.transactions.dao.TransactionEntityService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
public class TransactionsControllerService {

	private final TransactionEntityService service;

	public List<Transaction> getTransactions() {
		Iterable<TransactionEntity> entities = service.getTransactions();
		
		ModelMapper mapper = new ModelMapper();
		@SuppressWarnings("serial")
		Type listType = new TypeToken<List<Transaction>>() {}.getType();
		
		List<Transaction> transactions = mapper.map(entities, listType);
		
		
		return transactions;
	}

}
