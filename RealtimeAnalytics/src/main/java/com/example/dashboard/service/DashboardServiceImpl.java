package com.example.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dashboard.dao.IDashboardDAO;
import com.example.dashboard.model.Customer;

@Service
public class DashboardServiceImpl implements IDashboardService {

	@Autowired
	private IDashboardDAO dashboardDAO;
	
	@Override
	public List<Customer> getCustomers() {
		return dashboardDAO.getCustomers();
	}

	@Override
	public Customer getCustomerIndo(Integer id) {
		return null;
	}

}
