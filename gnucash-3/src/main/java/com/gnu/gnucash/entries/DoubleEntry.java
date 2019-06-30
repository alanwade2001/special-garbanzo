package com.gnu.gnucash.entries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DoubleEntry {

	@Id @GeneratedValue
	private Long id;
	
	@OneToOne
	private Entry main;
	
	@OneToMany
	private List<Entry> split;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar postedAt;
	
	public DoubleEntry() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Entry getMain() {
		return main;
	}

	public void setMain(Entry main) {
		this.main = main;
	}

	public List<Entry> getSplit() {
		if( split ==null) {
			split = new ArrayList<>();
		}
		
		return split;
	}

	public void setSplit(List<Entry> split) {
		this.split = split;
	}

	public Calendar getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Calendar postedAt) {
		this.postedAt = postedAt;
	}
	
	

}
