package edu.usm.cos420.example1.domain;

public class Customer {
	Long id;
	String name;
	String address;
	String town;
	String state;
	int zipcode;
	
	public Customer(Long id, String name, String address, String town, String state, int zipcode)
	{
		this.id = id;
		this.name = name;
		this.address = address;
		this.town = town;
		this.state = state;
		this.zipcode = zipcode;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	
	public String toString()
	{
		return " Customer " + this.id;
	}
	

}
