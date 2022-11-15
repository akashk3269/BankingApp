package com.neosoftbank.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.neosoftbank.MyTransaction;
import com.neosoftbank.NeosoftBank;
import com.neosoftbank.exception.InsufficientBalanceException;
import com.neosoftbank.exception.UserNotFoundException;
import com.neosoftbank.exception.YoungerAgeException;
import com.neosoftbank.operation.AdminOperation;
import com.neosoftbank.operation.CommonOperation;
import com.neosoftbank.pojo.Customer;
import com.neosoftbank.pojo.Transaction;

public class AdminOperationImpl implements AdminOperation, CommonOperation {
	
	Scanner sc=new Scanner(System.in);
	static int accNo;
	
	public void login()  {
		System.out.println("Enter your credentials");
		System.out.println("Enter username");
		String username=sc.nextLine();
		System.out.println("Enter password");
		String password=sc.nextLine();
		if(username.equals("admin")&&password.equals("admin")) {
			System.out.println("You have been login successfully");
			selectAdminOperation();
		}else {
			System.out.println("Enter valid username and password");
			throw new UserNotFoundException("user not found");
		}
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
       System.out.println("THANKS FOR VISITING OUR BANK. VISIT AGAIN!\n");
       NeosoftBank.app();

	}

	@Override
	public Customer accountOpen()  {
		// TODO Auto-generated method stub
		Customer c=new Customer();
		    accNo++;
			System.out.println("Enter name");
			String name=sc.nextLine();
			System.out.println("Enter age");
			int age=Integer.parseInt(sc.nextLine());
				if(age>=18) {
					BigDecimal bal=new BigDecimal("500.0");
					c.setAccNo(accNo);
					c.setAge(age);
					c.setName(name);
					c.setBalance(bal);
					if(c!=null) {
						NeosoftBank.customerList.add(c);
						return c;
					}
				}else {
					throw new YoungerAgeException("age should be greater than 18");
				}
			return c;
	}

	@Override
	public void amountDeposit() {
		Customer customer=getCustomerByAccountNo();
		if(customer==null) {
			try {
				throw new UserNotFoundException("user does not exist");
			}catch(UserNotFoundException e) {
				System.out.println(e);
			}
		}
		else {
			System.out.println("Enter amount to deposit");
			String amt=sc.nextLine();
			BigDecimal amount=new BigDecimal(amt);
			BigDecimal bal=customer.getBalance();
			bal=bal.add(amount);
			customer.setBalance(bal);
			Transaction transaction=new Transaction(new Date(),MyTransaction.DEPOSIT.toString(),amount.doubleValue(),bal.doubleValue());
			customer.getTr().add(transaction);
			System.out.println("Amount has been deposited successfully");
			System.out.println("PRESS 5 to check balance");
		}
	}

	@Override
	public void amountWithdraw() {
		Customer customer=getCustomerByAccountNo();
		if(customer==null) {
			try {
				throw new UserNotFoundException("user does not exist");
			}catch(UserNotFoundException e) {
				System.out.println(e);
			}
		}
		else {
			BigDecimal balance=customer.getBalance();
			System.out.println("Enter amount to withdraw");
			String amt=sc.nextLine();
			BigDecimal amount=new BigDecimal(amt);
			   if (balance.compareTo(amount)==1 ) {  
		            balance = balance.subtract(amount);
		            customer.setBalance(balance);
		    		Transaction transaction=new Transaction(new Date(),MyTransaction.WITHDRAW.toString(),amount.doubleValue(),balance.doubleValue());
		    		customer.getTr().add(transaction);
		            System.out.println("Balance after withdrawal: " + customer.getBalance());  
		        } else {  
		            System.out.println("Your balance is less than " + amt + "\tTransaction failed...!!" );  
		            throw new InsufficientBalanceException("Not enough balance");
		        } 
		}
	}

	@Override
	public void deleteAccount() {
		Customer c=getCustomerByAccountNo();
		boolean b=NeosoftBank.customerList.remove(c);
		if(b) {
			System.out.println("Account has been deleted succesfully");
		}else {
			System.out.println("Fail to delete account");
		}
	}

	@Override
	public void checkBalance() {
		Customer c=getCustomerByAccountNo();
		System.out.println("Your account balance is "+c.getBalance());
	}
	
	@Override
	public void printAllCustomers() {
		for(Customer c:NeosoftBank.customerList)
			System.out.println(c.getAccNo()+" "+c.getName()+" "+c.getAge()+" "+c.getBalance());
	}

	@Override
	public void selectAdminOperation() {
		int choice=0;
		while(true) {
			System.out.println("1.Create new Account");
			System.out.println("2.Deposit Amount");
			System.out.println("3.Withdraw Amount");
			System.out.println("4.Delete Account");
			System.out.println("5.Check Balance");
			System.out.println("6.Print All Customers");
			System.out.println("7.Log out");
			System.out.println("Enter the choice");
			choice=Integer.parseInt(sc.nextLine());
			switch(choice) {
			   case 1:
				   System.out.println("CREATE YOUR ACCOUNT HERE");
				   System.out.println("-----------------------------------------");
				  try {
					   Customer c1=accountOpen();
					   System.out.println(c1.getName()+" your Account is Successfully opened ");
				  }catch(YoungerAgeException e) {
					  System.out.println(e);
				  }
				   break;
			   case 2:
				   System.out.println("DEPOSIT AMOUNT HERE");
				   System.out.println("---------------------------------------");
				   amountDeposit();
				   break;
			   case 3:
				   System.out.println("WITHDRAW AMOUNT HERE");
				   System.out.println("----------------------------------------");
				   try {
					   amountWithdraw();
				   }catch(InsufficientBalanceException e) {
					   System.out.println(e);
				   }
				   break;
			   case 4:
				   System.out.println("Delete Account");
				   deleteAccount();
				   break;
			   case 5:
				   System.out.println("Account Balance");
				   System.out.println("---------------------------------------");
				   checkBalance();
				   break;
			   case 6:
				   System.out.println("Customer Details");
				   System.out.println("----------------------------------------");
				   printAllCustomers();
				   break;
			   case 7:
				   logout();
				   break;
			   default:
				   System.out.println("Wrong choice");
				   break;
			}
			
		}
	}


	@Override
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
