package com.example.dashboard.service;

import java.util.List;

import com.example.dashboard.model.Customer;

public interface IDashboardService {

	public List<Customer> getCustomers();
	
	public Customer getCustomerIndo(Integer id);
	
}
