package com.gnu.gnucash.entries;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class DoubleEntryResource extends ResourceSupport{

	private Calendar postedAt;
	private EntryResource main;
	private List<EntryResource> split;
	
	public Calendar getPostedAt() {
		return postedAt;
	}
	
	public void setPostedAt(Calendar postedAt) {
		this.postedAt = postedAt;
	}
	
	
	
	public EntryResource getMain() {
		return main;
	}

	public void setMain(EntryResource main) {
		this.main = main;
	}

	public List<EntryResource> getSplit() {
		if(split == null )
			split = new ArrayList<DoubleEntryResource.EntryResource>();
		
		return split;
	}

	public void setSplit(List<EntryResource> split) {
		this.split = split;
	}



	public static class EntryResource {
		private BigDecimal amount;
		private Long accountId;
		private EntryType type;
		private String description;
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public Long getAccountId() {
			return accountId;
		}
		public void setAccountId(Long accountId) {
			this.accountId = accountId;
		}
		public EntryType getType() {
			return type;
		}
		public void setType(EntryType type) {
			this.type = type;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		
	}
}
