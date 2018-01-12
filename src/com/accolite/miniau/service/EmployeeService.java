package com.accolite.miniau.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.accolite.miniau.POJO.Employee;

@WebService
@SOAPBinding(style=Style.RPC)
public interface EmployeeService {

	@WebMethod public ArrayList<Employee> getAllEmployees() throws SQLException;
	@WebMethod public Employee getEmployeeById(int id) throws SQLException;
	@WebMethod public void addEmployee(String name,String phone,String email,String address,String gender) throws SQLException;
	@WebMethod public void updateEmployee(int id, String name,String phone,String email,String address,String gender) throws SQLException;
	@WebMethod public void deleteEmployee(int id) throws SQLException;
}
