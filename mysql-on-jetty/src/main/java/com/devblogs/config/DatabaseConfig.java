package com.devblogs.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.devblogs.dao")
public class DatabaseConfig {
	@Bean
	@Qualifier("dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //dataSource.setUrl("jdbc:mysql://localhost:3306/phpmyadmin");
        dataSource.setUrl("jdbc:mysql://localhost:3306/phpmyadmin?useSSL=false");
        dataSource.setUsername("phpmyadmin");
        dataSource.setPassword("vsim_22");
        return dataSource;
    }
	
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dataSource") DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}
}
