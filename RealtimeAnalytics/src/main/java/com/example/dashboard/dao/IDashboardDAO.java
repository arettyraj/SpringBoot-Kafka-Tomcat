package com.example.dashboard.dao;

import java.util.List;

import com.example.dashboard.model.Customer;

public interface IDashboardDAO {
	public List<Customer> getCustomers();

	public Customer getCustomerInfo(Integer id);
}
