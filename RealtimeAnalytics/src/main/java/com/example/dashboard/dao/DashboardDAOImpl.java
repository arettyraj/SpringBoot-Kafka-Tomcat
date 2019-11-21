package com.example.dashboard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dashboard.model.Customer;

@Repository
public class DashboardDAOImpl implements IDashboardDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Customer> getCustomers() {
		String SQL = "select * from customers";
		List<Customer> customers = new ArrayList<Customer>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

		for (Map<String, Object> row : rows) {
			Customer customer = new Customer();
			customer.setId((Integer) row.get("id"));
			customer.setFirstName((String) row.get("firstName"));
			customer.setLastName((String) row.get("lastName"));
			customer.setCity((String) row.get("city"));
			customer.setState((String) row.get("state"));
			customer.setCountry((String) row.get("country"));

			customers.add(customer);
		}

		return customers;
	}

	public Customer getCustomerInfo(Integer id) {
		return null;

	}
}
