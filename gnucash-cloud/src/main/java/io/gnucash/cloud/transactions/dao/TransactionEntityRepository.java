package io.gnucash.cloud.transactions.dao;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionEntityRepository extends DatastoreRepository<TransactionEntity, Long> {

}
