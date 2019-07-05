package com.gnu.gnucash.entries;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gnu.gnucash.accounts.Account;
import com.gnu.gnucash.entries.DoubleEntryResource.EntryResource;

@RestController
@RequestMapping("/doubleEntries")
@ExposesResourceFor(value = DoubleEntry.class)
public class DoubleEntryController {

	private final EntityLinks entityLinks;
	
	private final EntryService service;


	public DoubleEntryController(EntityLinks entityLinks, EntryService service) {
		this.entityLinks = entityLinks;
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Resources<DoubleEntryResource>> getDoubleEntries() {
		
		List<DoubleEntry> doubleEntries = service.findAll();
		
		DoubleEntryResourceAssembler assembler = new DoubleEntryResourceAssembler();		
		List<DoubleEntryResource> resourceList = assembler.toResources(doubleEntries);
		Resources<DoubleEntryResource> resources = new Resources<>(resourceList);
		
		return ResponseEntity.ok(resources);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<DoubleEntryResource> getDoubleEntry(@PathVariable("Id") Long id )
	{
		DoubleEntry entity = service.getOne(id);
		DoubleEntryResourceAssembler assembler = new DoubleEntryResourceAssembler();
		DoubleEntryResource resource = assembler.toResource(entity);
		
		return ResponseEntity.ok(resource);
	}
	
	@PostMapping
	public ResponseEntity<DoubleEntryResource> newDoubleEntry( DoubleEntryResource resource )
	{
		DoubleEntry entity = new DoubleEntry();
		entity.setPostedAt(resource.getPostedAt());
		entity.setMain(map(resource.getMain()));
		entity.setSplit(map(resource.getSplit()));
		
		entity = service.newTransaction(entity);
		
		DoubleEntryResourceAssembler assembler = new DoubleEntryResourceAssembler();
		DoubleEntryResource converted = assembler.toResource(entity);
		
		Link link = entityLinks.linkToSingleResource(DoubleEntry.class, entity.getId());
		converted.add(link);
		
		final URI uri = URI.create(link.getHref()); 
		
		
        return ResponseEntity.created(uri).body(converted);
	}
	
	public Entry map( EntryResource resource )
	{
		Entry entry = new Entry();
		entry.setAccount(new Account());
		entry.getAccount().setId(resource.getAccountId());
		entry.setAmount(resource.getAmount());
		entry.setDescription(resource.getDescription());
		entry.setType(resource.getType());
		
		return entry;
	}
	
	public List<Entry> map(List<EntryResource> resources ) 
	{
		return resources.stream().map(r->map(r)).collect(Collectors.toList());
	}
}
