package com.neosoftbank.operation;

import com.neosoftbank.pojo.Customer;

public interface AdminOperation {
	
	public Customer accountOpen();
	public void amountDeposit();
	public void amountWithdraw();
	public void deleteAccount();
	public void checkBalance();
	public void selectAdminOperation();
	public void printAllCustomers();
	public Customer getCustomerByAccountNo();

}
