package com.devblogs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.devblogs.model.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
	
	private static final String SQL_INSERT = 
			"INSERT INTO employees (id, name, position) VALUES (employee_sequence.nextval, :name, :position)";
	
	private static final String SQL_SELECT = 
			"SELECT id, name, position FROM employees WHERE name = :name";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public int saveEmployee(Employee emp) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", emp.getName());
		params.addValue("position", emp.getPosition());
		return jdbcTemplate.update(SQL_INSERT, params);
	}

	public Employee findEmployeeByName(String name) {
		List<Employee> employees = jdbcTemplate.query(SQL_SELECT, Collections.singletonMap("name", name), new RowMapper<Employee>() {
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Employee(rs.getLong("id"), rs.getString("name"), rs.getString("position"));
			}
		});
		Employee emp = null;
		if (employees != null && employees.size() > 0) {
			emp = employees.get(0);
		}
		return emp;
	}

}