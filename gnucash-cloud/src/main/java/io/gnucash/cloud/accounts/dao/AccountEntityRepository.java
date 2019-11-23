package io.gnucash.cloud.accounts.dao;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountEntityRepository extends DatastoreRepository<AccountEntity, Long> {

}
