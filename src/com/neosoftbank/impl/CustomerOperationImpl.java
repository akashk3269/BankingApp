package com.neosoftbank.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import com.neosoftbank.MyTransaction;
import com.neosoftbank.NeosoftBank;
import com.neosoftbank.exception.InsufficientBalanceException;
import com.neosoftbank.exception.UserNotFoundException;
import com.neosoftbank.operation.CommonOperation;
import com.neosoftbank.operation.CustomerOperation;
import com.neosoftbank.pojo.Customer;
import com.neosoftbank.pojo.Transaction;

public class CustomerOperationImpl implements CommonOperation, CustomerOperation {
	Scanner sc=new Scanner(System.in);
	Customer c=null;
	
	@Override
	public void login() {
		System.out.println("Enter your credentials");
		System.out.println("Enter account no");
		int accNo=Integer.parseInt(sc.nextLine());
		System.out.println("Enter username");
		String username=sc.nextLine();
		for(int i=0;i<NeosoftBank.customerList.size();i++) {
			Customer c1=NeosoftBank.customerList.get(i);
			if(accNo==c1.getAccNo()&&username.equalsIgnoreCase(c1.getName())) {
				c=c1;	
			}
		}
		if(c!=null) {
				System.out.println("You have been login successfully");
				selectCustomerOperation();
			}else {
				System.out.println("Enter valid credentials");
				throw new UserNotFoundException("user not found");
			}
		}
	

	@Override
	public Customer viewAccount() {
		return c;
	}

	@Override
	public void viewAllTransactions() {
		
		for(Transaction tr:c.getTr()) {
			System.out.println("Transaction Date :"+tr.getDate()+" Transaction Type :"+tr.getType()+" Transaction Amount "+tr.getAmount()+" Your Balance "+tr.getBalance());
		}
	}

	@Override
	public void transferMoney() {
		
		Customer customer=getCustomerByAccountNo();
		if(customer==null) {
			try {
				throw new UserNotFoundException("user does not exist");
			}catch(UserNotFoundException e) {
				System.out.println(e);
			}
		}
		else {
			BigDecimal bal=customer.getBalance();
			BigDecimal cbal=c.getBalance();
			
			System.out.println("Enter the amount");
			String amt=sc.nextLine();
			BigDecimal amount=new BigDecimal(amt);
			if(c.getBalance().compareTo(amount)==1) {
				bal=bal.add(amount);
				customer.setBalance(bal);
				cbal=cbal.subtract(amount);
				c.setBalance(cbal);
				Transaction transaction1=new Transaction(new Date(),MyTransaction.WITHDRAW.toString(),amount.doubleValue(),cbal.doubleValue());
				c.getTr().add(transaction1);
				Transaction transaction2=new Transaction(new Date(),MyTransaction.DEPOSIT.toString(),amount.doubleValue(),bal.doubleValue());
				customer.getTr().add(transaction2);
				System.out.println("money has been transfered succesfully");
			}else {
				throw new InsufficientBalanceException("Not enough balance");
			}
		}
	}

	@Override
	public void viewLastFiveTransactions() {

		List<Transaction> trList=new ArrayList<>();
		for(Transaction tr:c.getTr()) {
			trList.add(tr);
		}
		if(trList.size()>5) {
			Collections.reverse(trList);
			System.out.println(trList.subList(trList.size()-5, trList.size()));
		}else {
			Collections.reverse(trList);
			System.out.println(trList);
		}
		
	}

	@Override
	public void checkBalance() {
		// TODO Auto-generated method stub
		System.out.println("Your balance is "+c.getBalance());

	}

	@Override
	public void logout() {
		System.out.println("THANKS FOR VISITING OUR BANK. VISIT AGAIN!\n");
	    NeosoftBank.app();
	}

	@Override
	public void selectCustomerOperation() {
		// TODO Auto-generated method stub
		int choice=0;
		while(true) {
			System.out.println("1.VIEW ACCOUNT DETAILS");
			System.out.println("2.VIEW ALL TRANSACTION");
			System.out.println("3.TRANSFER MONEY");
			System.out.println("4.VIEW LAST 5 TRANSACTION");
			System.out.println("5.CHECK BALANCE");
			System.out.println("6.LOG OUT");
			System.out.println("Enter the choice");
			choice=Integer.parseInt(sc.nextLine());
			switch(choice)
			{
			case 1:
				System.out.println("Your Account Details");
				Customer c1 = viewAccount();
				System.out.println("Account No: "+c1.getAccNo());
				System.out.println("Name: "+c1.getName());
				System.out.println("Age: "+c1.getAge());
				System.out.println("Balance: "+c1.getBalance());
				break;
			case 2:
				System.out.println("Your Transactions");
				viewAllTransactions();
				break;
				
			case 3:
				System.out.println("Transfer money");
				System.out.println("-------------------------------");
				try {
					transferMoney();
				}catch(InsufficientBalanceException e) {
					System.out.println(e);
				}
				break;
			case 4:
				viewLastFiveTransactions();
				break;
			case 5:
				checkBalance();
				break;
			case 6:
				logout();
				break;
			default:
				System.out.println("Wrong choice");
				break;
				
			}
			
		}
		
	}

	public Customer getCustomerByAccountNo() {
		Customer searchedCustomer=null;
		System.out.println("Enter account no");
		int accNo=Integer.parseInt(sc.nextLine());
		for(int i=0;i<NeosoftBank.customerList.size();i++) {
			Customer c1=NeosoftBank.customerList.get(i);
			if(c1.getAccNo()==accNo) {
				searchedCustomer=c1;
				return searchedCustomer;
			}
		}
		return searchedCustomer;
	}
}
