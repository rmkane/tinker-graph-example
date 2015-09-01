package com.github.tinkerpop.graph;

public class Person {
	private long uniqueId;
	private String name;

	public Person(long uniqueId, String name) {
		super();
		this.uniqueId = uniqueId;
		this.name = name;
	}

	public long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(long uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [uniqueId=" + uniqueId + ", name=" + name + "]";
	}
}