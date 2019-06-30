package com.gnu.gnucash.entries;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class EntryService {

	private DoubleEntryRepository doubleEntryRepository;
	private EntryRepository entryRepository;
	
	
	public EntryService(DoubleEntryRepository doubleEntryRepository, EntryRepository entryRepository) {
		super();
		this.doubleEntryRepository = doubleEntryRepository;
		this.entryRepository = entryRepository;
	}
	
	public DoubleEntry newTransaction( DoubleEntry t)
	{
		// check if it is balanced
		if( isBalanced(t) == false )
		{
			throw new RuntimeException("Double Entry not balanced");
		}
		
		Entry main = t.getMain();
		List<Entry> split = t.getSplit();
		
		t.setMain(null);
		t.setSplit(null);
		
		doubleEntryRepository.save(t);
		main.setTransaction(t);		
		entryRepository.save(main);
		
		split.stream().forEach(x->x.setTransaction(t));
		entryRepository.saveAll(split);
		
		t.setMain(main);
		t.setSplit(split);
		
		return doubleEntryRepository.save(t);
	}
	
	public DoubleEntry getOne(Long id) {
		return doubleEntryRepository.getOne(id);
	}

	public List<Entry> findAllByAccountId(Long id) {
		List<Entry> entries = entryRepository.findAllByAccountId( id );
		return entries;
	}
	
	public boolean isBalanced( DoubleEntry t) {
		if( isDebitCredit(t) == false )
			return false;
		
		BigDecimal mainAmount = t.getMain().getAmount();
		BigDecimal splitAmountTotal = sumSplitEntries(t.getSplit());
		
		return mainAmount.compareTo(splitAmountTotal) == 0;
	}
	
	public boolean isDebitCredit( DoubleEntry t) {
		EntryType mainType = t.getMain().getType();
		
		// ensure that none of the split are the same type
		for (Entry e : t.getSplit()) {
			if( e.getType().equals(mainType))
				return false;
		}
		
		return true;
	}
	
	public BigDecimal sumSplitEntries( List<Entry> entries)
	{
		BigDecimal total = BigDecimal.ZERO;
		for (Entry entry : entries) {
			total = entry.getAmount();
		}
		
		return total;
	}

	public List<DoubleEntry> findAll() {
		return doubleEntryRepository.findAll();
	}
}
