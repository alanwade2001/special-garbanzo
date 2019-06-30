package com.gnu.gnucash.entries;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.gnu.gnucash.entries.DoubleEntryResource.EntryResource;

public class DoubleEntryResourceAssembler extends ResourceAssemblerSupport<DoubleEntry, DoubleEntryResource> {

	
	
	public DoubleEntryResourceAssembler() {
		super(DoubleEntryController.class, DoubleEntryResource.class);
	}

	@Override
	public DoubleEntryResource toResource(DoubleEntry entity) {
		DoubleEntryResource resource = new DoubleEntryResource();
		resource.setPostedAt(entity.getPostedAt());
		
		resource.setMain(toResource(entity.getMain()));
		resource.setSplit(toResources(entity.getSplit()));
		
		return resource;
	}
	
	public EntryResource toResource(Entry entry)
	{
		EntryResource resource = new EntryResource();
		
		resource.setAccountId(entry.getAccount().getId());
		resource.setAmount(entry.getAmount());
		resource.setDescription(entry.getDescription());
		resource.setType(entry.getType());
		
		return resource;
	}
	
	public List<EntryResource> toResources(List<Entry> entries)
	{
		return entries.stream().map(e->toResource(e)).collect(Collectors.toList());
	}

}
