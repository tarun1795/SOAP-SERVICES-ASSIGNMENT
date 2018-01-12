package com.accolite.miniau.publisher;

import javax.xml.ws.Endpoint;

import com.accolite.miniau.service.EmployeeServiceImpl;

public class Publisher {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8125/ws/employee", new EmployeeServiceImpl());
	}
}
