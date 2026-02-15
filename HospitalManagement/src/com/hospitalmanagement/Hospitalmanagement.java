package com.hospitalmanagement;
import java.sql.*;
import java.util.Scanner;

public class Hospitalmanagement {
	
	    private static final String URL = "jdbc:mysql://localhost:3306/hospitalm";
	    private static final String USERNAME = "root";
	    private static final String PASSWORD = "root123";

	    public static void main(String[] args) {

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Scanner sc = new Scanner(System.in);
	            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

	            System.out.println("Connected to Database");

	            Patient patient = new Patient(connection, sc);
	            Doctors doctor = new Doctors(connection);

	            while (true) {
	                System.out.println("\nWelcome To ABC Hospital Management");
	                System.out.println("1. Add Patient");
	                System.out.println("2. View Patient");
	                System.out.println("3. View Doctors");
	                System.out.println("4. Book Appointment");
	                System.out.println("5. Exit");

	                System.out.print("Enter your choice: ");
	                int choice = sc.nextInt();

	                switch (choice) {
	                    case 1:
	                        patient.addPatient();
	                        break;

	                    case 2:
	                        patient.viewPatient();
	                        break;

	                    case 3:
	                        doctor.viewDoctor();
	                        break;

	                    case 4:
	                        bookAppointment(patient, doctor, connection, sc);
	                        break;

	                    case 5:
	                        System.out.println("Thank you!");
	                        System.exit(0);

	                    default:
	                        System.out.println("Invalid choice");
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static void bookAppointment(Patient patient, Doctors doctors,
	                                       Connection connection, Scanner sc) {

	        System.out.print("Enter Patient ID: ");
	        int patientId = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter Patient Name: ");
	        String patientName = sc.nextLine();

	        System.out.print("Enter Doctor ID: ");
	        int doctorId = sc.nextInt();

	        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
	        String appointmentDate = sc.next();

	        if (patient.getPatientId(patientId) && doctors.getDoctorId(doctorId)) {

	            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {

	                String query = "INSERT INTO appointments " +
	                        "(patient_id, patient_name, doctor_id, appointment_date) VALUES (?,?,?,?)";

	                try {
	                    PreparedStatement ps = connection.prepareStatement(query);
	                    ps.setInt(1, patientId);
	                    ps.setString(2, patientName);
	                    ps.setInt(3, doctorId);
	                    ps.setString(4, appointmentDate);

	                    ps.executeUpdate();
	                    System.out.println("Appointment Booked Successfully");

	                } catch (Exception e) {
	                    e.printStackTrace();
	                }

	            } else {
	                System.out.println("Doctor not available on this date");
	            }

	        } else {
	            System.out.println("Invalid Patient ID or Doctor ID");
	        }
	    }

	    public static boolean checkDoctorAvailability(int doctorId,
	                                                  String appointmentDate,
	                                                  Connection connection) {

	        try {
	            String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
	            PreparedStatement ps = connection.prepareStatement(query);
	            ps.setInt(1, doctorId);
	            ps.setString(2, appointmentDate);

	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getInt(1) == 0;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	}


