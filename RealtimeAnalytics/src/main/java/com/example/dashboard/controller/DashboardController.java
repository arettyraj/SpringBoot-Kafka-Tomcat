package com.example.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dashboard.model.Customer;
import com.example.dashboard.sender.KafkaSender;
import com.example.dashboard.service.IDashboardService;

@RestController
public class DashboardController {
	
	@Autowired
	private IDashboardService dashboardService;
	
	@GetMapping("/api/v1/customers")
	private @ResponseBody ResponseEntity<List<Customer>> getCustomers(){
		return ResponseEntity.ok(dashboardService.getCustomers());
	}
	
	@Autowired
    KafkaSender kafkaSender;

    @PostMapping("/kafka/{topicName}")
    public String sendToTopic(@PathVariable String topicName, @RequestBody String message) {
        kafkaSender.send(topicName, message);
        return "Message sent";
    }

}
