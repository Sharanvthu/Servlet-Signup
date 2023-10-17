package com.sharan.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		
		String umobile=request.getParameter("contact");
		String upsw=request.getParameter("pass");
		RequestDispatcher dispatcher=null;
		Connection con=null;
		try{
			String s="INSERT INTO users(UNAME,UEMAIL,UMOBILE,PASSWORD) values (?,?,?,?)";
			Class.forName("oracle.jdbc.driver.OracleDriver");//load the driver
			con=DriverManager.getConnection
					("jdbc:oracle:thin:@localhost:1521:ORCLE123","SCOTT","tiger");// Get the connection

			PreparedStatement st=con.prepareStatement(s);

			st.setString(1, uname);
			st.setString(2, uemail);
			st.setString(3,umobile );
			st.setString(4, upsw);
			int rowcount=st.executeUpdate();
			dispatcher=request.getRequestDispatcher("registration.jsp");
			if(rowcount>0)
				request.setAttribute("status", "success");
			else{
				request.setAttribute("status", "fail");

			}
			dispatcher.forward(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

}
