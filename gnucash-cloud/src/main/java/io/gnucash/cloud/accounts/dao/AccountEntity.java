package io.gnucash.cloud.accounts.dao;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "Accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

	@Id
	private Long id;
	
	private String name;

	private String description;
}
