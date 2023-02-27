/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csc229labfiles.googlefirestoretest;

/**
 *
 * @author adam_solomon_home
 */
public class Person {

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", age=" + age + ", location=" + location + '}';
    }
	
	private String name;
	
	private String age;
	
	private String location;
	
	public Person() {
		super();
	}
	
	public Person(String name, String age, String location) {
		super();
		this.name = name;
		this.age = age;
		this.location = location;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	

}
