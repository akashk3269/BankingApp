package com.neosoftbank.operation;

import com.neosoftbank.pojo.Customer;

public interface CustomerOperation {
	
	void login();
	Customer viewAccount();
	void viewAllTransactions();
	void transferMoney();
	void viewLastFiveTransactions();
	void checkBalance();
	void logout();
	void selectCustomerOperation();

}
