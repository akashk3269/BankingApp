package com.neosoftbank.exception;

public class InsufficientBalanceException extends RuntimeException {
	
	public InsufficientBalanceException(String message)
	{
		System.out.println(message);
	}
}
