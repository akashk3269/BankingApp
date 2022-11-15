package com.neosoftbank.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	
	private String name;
	private long accNo;
	private int age;
	private  BigDecimal balance;
	private List<Transaction> tr=new ArrayList<>();
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(String name, long accNo, int age, BigDecimal balance, List<Transaction> tr) {
		super();
		this.name = name;
		this.accNo = accNo;
		this.age = age;
		this.balance = balance;
		this.tr = tr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAccNo() {
		return accNo;
	}
	public void setAccNo(long accNo) {
		this.accNo = accNo;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public List<Transaction> getTr() {
		return tr;
	}
	public void setTr(List<Transaction> tr) {
		this.tr = tr;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", accNo=" + accNo + ", age=" + age + ", balance=" + balance + ", tr=" + tr
				+ "]";
	}
}
