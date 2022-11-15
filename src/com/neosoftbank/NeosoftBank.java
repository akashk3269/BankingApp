package com.neosoftbank;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.neosoftbank.exception.UserNotFoundException;
import com.neosoftbank.impl.AdminOperationImpl;
import com.neosoftbank.impl.CustomerOperationImpl;
import com.neosoftbank.pojo.Customer;


public class NeosoftBank {
	
	public static List<Customer> customerList=new ArrayList<>();
	
	public static void app() {
		AdminOperationImpl admin=null;
		CustomerOperationImpl customer=null;
		int choice=0;
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Welcome to Neosoft Bank");
		while(true) {
			System.out.println("1.Login as Admin");
			System.out.println("2.Login as Customer");
			System.out.println("3.EXIT");
			System.out.println("Enter the choice");
			choice=sc.nextInt();
			switch(choice) {
			
			case 1:
				admin=new AdminOperationImpl();   
				try {
					admin.login();
				}catch(UserNotFoundException e) {
					System.out.println(e);
				}
				break;   
			case 2:
				customer=new CustomerOperationImpl();
				try {
					customer.login();
				}catch(UserNotFoundException e) {
					System.out.println(e);
				}
				break;
			case 3:
				System.exit(0);
				break;
			default:
				System.out.println("Wrong Choice");
			}
			
		}
	}
	
	
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		app();
	}
}
