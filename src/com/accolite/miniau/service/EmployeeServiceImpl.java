package com.accolite.miniau.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.accolite.miniau.DatabaseConnection.DatabaseConnection;
import com.accolite.miniau.POJO.Employee;
import com.accolite.miniau.service.EmployeeService;

@WebService(endpointInterface="com.accolite.miniau.service.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService{

	PreparedStatement stmt;
	DatabaseConnection con;
	
	private void prepareStatment(String sql)
	{
		try {
			stmt = con.getCon().prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public EmployeeServiceImpl() {
		
		con = DatabaseConnection.getConnection();
	}
	
	
	@Override
	public ArrayList<Employee> getAllEmployees() throws SQLException {
		 String sql = "SELECT * FROM EMPLOYEE";
		 prepareStatment(sql);
		 ResultSet rs = stmt.executeQuery();
		 ArrayList<Employee> list = new ArrayList<>();
		 while(rs.next())
		 {
			 Employee employee = new Employee(rs.getInt("ID"),rs.getString("NAME"),rs.getString("EMAIL"),
						rs.getString("PHONE"),rs.getString("ADDRESS"),rs.getString("GENDER").charAt(0));
			 list.add(employee);
		 }
		 return list;
	}

	
	@Override
	public Employee getEmployeeById(@WebParam(name="id")int id) throws SQLException {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID=?";
		prepareStatment(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		Employee employee = new Employee(rs.getInt("ID"),rs.getString("NAME"),rs.getString("EMAIL"),
				rs.getString("PHONE"),rs.getString("ADDRESS"),rs.getString("GENDER").charAt(0));
		return employee;
	}

	@Override
	public void addEmployee(@WebParam(name="name")String name,@WebParam(name="phone")String phone,@WebParam(name="email")String email,@WebParam(name="address")String address,@WebParam(name="gender")String gender) throws SQLException {
		
		Employee employee = new Employee(0, name, email, phone, address, gender.charAt(0));
		String sql = "INSERT INTO EMPLOYEE(NAME,EMAIL,PHONE,ADDRESS,GENDER) VALUES(?,?,?,?,?)";
		prepareStatment(sql);
		stmt.setString(1, employee.getName());
		stmt.setString(2, employee.getEmail());
		stmt.setString(3, employee.getPhone());
		stmt.setString(4, employee.getAddress());
		stmt.setString(5, employee.getGender()+"");
		stmt.executeUpdate();
	}

	@Override
	public void updateEmployee(@WebParam(name="id")int id,@WebParam(name="name")String name,@WebParam(name="phone")String phone,@WebParam(name="email")String email,@WebParam(name="address")String address,@WebParam(name="gender")String gender) throws SQLException {
		
		Employee employee = new Employee(id, name, email, phone, address, gender.charAt(0));
		Employee oldEmployee = getEmployeeById(id);
		if(employee.getName() == null) 
			employee.setName(oldEmployee.getName());
		if(employee.getPhone() == null)
			employee.setPhone(oldEmployee.getPhone());
		if(employee.getAddress() == null)
			employee.setAddress(oldEmployee.getAddress());
		if(employee.getEmail() == null)
			employee.setEmail(oldEmployee.getEmail());
		if(employee.getGender() == '\u0000')
			employee.setGender(oldEmployee.getGender());
		String sql = "UPDATE EMPLOYEE SET NAME=?,PHONE=?,ADDRESS=?,EMAIL=?,GENDER=? WHERE ID=?";
		prepareStatment(sql);
		stmt.setString(1, employee.getName());
		stmt.setString(2, employee.getPhone());
		stmt.setString(3, employee.getAddress());
		stmt.setString(4, employee.getEmail());
		stmt.setString(5, employee.getGender()+"");
		stmt.setInt(6, id);
		stmt.executeUpdate();
	}

	@Override
	public void deleteEmployee(@WebParam(name="id")int id) throws SQLException {
		
		String sql = "DELETE FROM EMPLOYEE WHERE ID=?";
		prepareStatment(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
	}

}