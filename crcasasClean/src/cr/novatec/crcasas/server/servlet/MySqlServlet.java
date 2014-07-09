package cr.novatec.crcasas.server.servlet;


import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import cr.novatec.crcasas.server.resources.Sql;



@SuppressWarnings("serial")
public class MySqlServlet extends HttpServlet {
	
	

	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		  

		    PrintWriter out = resp.getWriter();
           out.println(
	                "Thanks for using crCasas.com");
		  }

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			

			String cursorStr = req.getParameter("cursor");
			String table = req.getParameter("table");

			
			Sql mySql = new Sql();
			
			try {
				mySql.execStm(table, cursorStr);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mySql.close();
			

		    PrintWriter out = resp.getWriter();
          out.println("Well done! ");
		    
		}
		
}
