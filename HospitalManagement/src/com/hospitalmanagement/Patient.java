package com.hospitalmanagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Patient {
	

	    private Connection connection;
	    private Scanner sc;

	    public Patient(Connection connection, Scanner sc) {
	        this.connection = connection;
	        this.sc = sc;
	    }

	    public void addPatient() {

	        System.out.print("Enter Patient Name: ");
	        String name = sc.next();

	        System.out.print("Enter Patient Age: ");
	        int age = sc.nextInt();

	        System.out.print("Enter Patient Gender: ");
	        String gender = sc.next();

	        try {
	            String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
	            PreparedStatement ps = connection.prepareStatement(query);

	            ps.setString(1, name);
	            ps.setInt(2, age);
	            ps.setString(3, gender);

	            ps.executeUpdate();
	            System.out.println("Patient Added Successfully");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public void viewPatient() {

	        try {
	            String query = "SELECT * FROM patients";
	            PreparedStatement ps = connection.prepareStatement(query);
	            ResultSet rs = ps.executeQuery();

	            System.out.println("| ID | Name | Age | Gender |");

	            while (rs.next()) {
	                System.out.printf("| %d | %s | %d | %s |\n",
	                        rs.getInt("id"),
	                        rs.getString("name"),
	                        rs.getInt("age"),
	                        rs.getString("gender"));
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public boolean getPatientId(int id) {
	        try {
	            String query = "SELECT * FROM patients WHERE id=?";
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



