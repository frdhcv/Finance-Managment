package org.example.financemanagment.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinanceManager {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "";
    private static final double MONTHLY_BUDGET = 40000.0;

    public static void main(String[] args) {

        insertDataFromFile("C:/Users/aydan/OneDrive/Desktop/test.csv");


        calculateStatistics();
    }


    private static void insertDataFromFile(String filePath) {
        String sql = "INSERT INTO employee(id, name, address, salary, kredit) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             BufferedReader lineReader = new BufferedReader(new FileReader(filePath))) {

            connection.setAutoCommit(false);

            String lineText;
            lineReader.readLine(); // Header row, skip it

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String address = data[2];
                int salary = Integer.parseInt(data[3]);
                long kredit = Long.parseLong(data[4]);

                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, address);
                statement.setInt(4, salary);
                statement.setLong(5, kredit);

                statement.addBatch();
            }

            statement.executeBatch();
            connection.commit();
            System.out.println("Data has been inserted successfully.");

        } catch (SQLException | NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }


    private static void calculateStatistics() {
        String selectSql = "SELECT salary, kredit FROM employee";
        String insertSql = "INSERT INTO statistics(total_records, average_salary, average_kredit, monthly_budget, salary_percentage, kredit_percentage) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             ResultSet resultSet = selectStatement.executeQuery();
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

            int totalSalary = 0;
            long totalKredit = 0;
            int count = 0;

            while (resultSet.next()) {
                int salary = resultSet.getInt("salary");
                long kredit = resultSet.getLong("kredit");

                totalSalary += salary;
                totalKredit += kredit;
                count++;
            }

            if (count > 0) {
                double averageSalary = (double) totalSalary / count;
                double averageKredit = (double) totalKredit / count;

                double salaryPercentage = (averageSalary / MONTHLY_BUDGET) * 100;
                double kreditPercentage = (averageKredit / MONTHLY_BUDGET) * 100;

                insertStatement.setInt(1, count);
                insertStatement.setDouble(2, averageSalary);
                insertStatement.setDouble(3, averageKredit);
                insertStatement.setDouble(4, MONTHLY_BUDGET);
                insertStatement.setDouble(5, salaryPercentage);
                insertStatement.setDouble(6, kreditPercentage);
                insertStatement.executeUpdate();

                System.out.println("Statistics have been calculated and stored in the database.");
            } else {
                System.out.println("No records found in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
