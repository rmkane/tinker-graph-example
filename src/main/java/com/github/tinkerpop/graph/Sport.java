package com.github.tinkerpop.graph;

import java.util.ArrayList;
import java.util.List;

public class Sport {
	private String name;
	private List<Person> peopleWhoPlayThisSport;

	public Sport(String name) {
		this.name = name;
		this.peopleWhoPlayThisSport = new ArrayList<Person>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected List<Person> getPeopleWhoPlayThisSport() {
		return peopleWhoPlayThisSport;
	}

	protected void setPeopleWhoPlayThisSport(List<Person> peopleWhoPlayThisSport) {
		this.peopleWhoPlayThisSport = peopleWhoPlayThisSport;
	}

	@Override
	public String toString() {
		return "Sport [name=" + name + ", peopleWhoPlayThisSport="
				+ peopleWhoPlayThisSport + "]";
	}
	
	
}