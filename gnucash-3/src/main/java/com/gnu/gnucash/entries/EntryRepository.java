package com.gnu.gnucash.entries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {

	List<Entry> findAllByAccountId(Long id);

}
