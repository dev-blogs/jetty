package com.devblogs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.devblogs.model.Provider;

@Repository
public class ProviderDaoImpl implements ProviderDao {
	
	private static final String SQL_INSERT = 
			"INSERT INTO employees (id, name, position) VALUES (employee_sequence.nextval, :name, :position)";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public int saveProvider(Provider provider) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", provider.getName());
		return jdbcTemplate.update(SQL_INSERT, params);
	}
	
	public String findAllByNameUsingPrepareStatement(String name) {
		StringBuilder builder = new StringBuilder();
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phpmyadmin?useSSL=false", "phpmyadmin", "vsim_22")) {
			PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM providers WHERE name = ?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				builder.append(rs.getInt("id"));
				builder.append(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	public String findAllByNameUsingStatement(String name) {
		StringBuilder builder = new StringBuilder();
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phpmyadmin?useSSL=false", "phpmyadmin", "vsim_22")) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name FROM providers WHERE name = '" + name + "'");
			while (rs.next()) {
				builder.append(rs.getInt("id"));
				builder.append(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	public String findAllByName(String name) {
		List<String> providers = jdbcTemplate.query("SELECT id, name FROM providers WHERE name = '" + name + "'",
				Collections.singletonMap("name", name),
				(rs, rowNum) -> rs.getLong("id") + " | " + rs.getString("name")
		);
		StringBuilder builder = new StringBuilder();
		for (String s : providers) {
			builder.append(s + "<br>");
		}
		return builder.toString();
	}
	
	public Provider findProviderByName(String name) {
		List<Provider> providers = jdbcTemplate.query("SELECT id, name FROM providers WHERE name = '" + name + "'",
				Collections.singletonMap("name", name),
				(rs, rowNum) -> new Provider(rs.getLong("id"), rs.getString("name"))
		);
		Provider pvr = null;
		if (providers != null && providers.size() > 0) {
			pvr = providers.get(0);
		}
		return pvr;
	}
}