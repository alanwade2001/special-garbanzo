package io.gnucash.cloud.transactions;

import java.util.List;

import org.apache.http.client.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RestController()
@RequestMapping(name = "/transactions")
public class TransactionsController {

	private final TransactionsControllerService service;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<Transaction> getTransactions() throws HttpResponseException {
		try
		{
			return service.getTransactions();
		}
		catch(Exception x)
		{
			throw new HttpResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), x.getMessage());
		}
	}

}
