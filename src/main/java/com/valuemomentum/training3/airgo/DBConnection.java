package com.valuemomentum.training3.airgo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection 
{
	 static Connection con;
	 public static Connection getConnection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/airgo","root","Passw0rd");
			return con;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
}