package com.hospitalmanagement;
import java.sql.*;

public class Doctors {
	
	    private Connection connection;

	    public Doctors(Connection connection) {
	        this.connection = connection;
	    }

	    public void viewDoctor() {

	        try {
	            String query = "SELECT * FROM doctors";
	            PreparedStatement ps = connection.prepareStatement(query);
	            ResultSet rs = ps.executeQuery();

	            System.out.println("| ID | Name | Department |");

	            while (rs.next()) {
	                System.out.printf("| %d | %s | %s |\n",
	                        rs.getInt("id"),
	                        rs.getString("name"),
	                        rs.getString("dept"));
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public boolean getDoctorId(int id) {
	        try {
	            String query = "SELECT * FROM doctors WHERE id=?";
	            PreparedStatement ps = connection.prepareStatement(query);
	            ps.setInt(1, id);
	            ResultSet rs = ps.executeQuery();
	            return rs.next();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	}



