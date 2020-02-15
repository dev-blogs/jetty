package com.devblogs.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import com.devblogs.dao.EmployeeDao;
import com.devblogs.model.Employee;

@Component("testServlet")
public class TestServlet implements HttpRequestHandler {
	@Autowired
	private EmployeeDao employeeDao;

	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		Employee emp = employeeDao.findEmployeeByName("vasya");
		
		response.getWriter().println("<h1>" + emp.getPosition() + "</h2>");
		response.getWriter().println("session=" + request.getSession(true).getId());
	}
}