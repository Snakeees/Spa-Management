package com.spa.screens;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Reports extends JPanel {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Reports() {
        initComponents();
        UIManager.put("Button.select", new Color(250, 105, 192));
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        setBackground(new Color(255, 220, 241));

        // Common padding
        int padding = 10; // Adjust the padding as needed
        gbc.insets = new Insets(padding, padding, padding, padding); // Top, left, bottom, right padding

        // Scatter plot for monthly incomes
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1; // Set this to 1 to allow horizontal spacing to take effect
        int[] dimen = {450,300};
        add(new ScatterPlot(getMonthlyIncomes(), dimen, FetchMonthNames()), gbc);

        // Scatter plot for monthly visits
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(new ScatterPlot(getMonthlyVisits(), dimen, FetchMonthNames()), gbc);

        // Labels
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0; // Set this back to 0 for the labels
        JLabel incomeLabel = new JLabel("Monthly Incomes", SwingConstants.CENTER);
        incomeLabel.setFont(new Font("Play", Font.BOLD, 20));
        add(incomeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel visitLabel = new JLabel("Monthly Visits", SwingConstants.CENTER);
        visitLabel.setFont(new Font("Play", Font.BOLD, 20));
        add(visitLabel, gbc);
    }

    public int[] getMonthlyIncomes() {
        Database db = new Database();
        LocalDate startDate = LocalDate.of(2023, 8, 1); // Starting from August 2023
        LocalDate today = LocalDate.now();
        LocalDate endDate = LocalDate.now(); // Until current date

        ArrayList<Integer> monthlyIncomes = new ArrayList<>();

        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            YearMonth yearMonth = YearMonth.from(startDate);
            LocalDate firstDayOfMonth = yearMonth.atDay(1);
            LocalDate lastDayOfMonth = startDate.getMonth().equals(today.getMonth()) && startDate.getYear() == today.getYear() ? today : yearMonth.atEndOfMonth();

            try {
                Date startOfMonth = dateFormat.parse(dateFormat.format(java.sql.Date.valueOf(firstDayOfMonth)));
                Date endOfMonth = dateFormat.parse(dateFormat.format(java.sql.Date.valueOf(lastDayOfMonth)));

                ResultSet rs = db.executeQuery("select sum(s.Cost) as monthIncome from Service s, Appointment a where a.ServiceID=s.ID and a.AppointmentDate>=? and a.AppointmentDate<=?", dateFormat.format(startOfMonth), dateFormat.format(endOfMonth));
                if (rs.next()) {
                    monthlyIncomes.add(rs.getInt("monthIncome"));
                } else {
                    monthlyIncomes.add(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                monthlyIncomes.add(0);
            }
            startDate = startDate.plusMonths(1);
        }
        return monthlyIncomes.stream().mapToInt(i -> i).toArray();
    }

    public int[] getMonthlyVisits() {
        Database db = new Database();
        LocalDate startDate = LocalDate.of(2023, 8, 1); // Starting from August 2023
        LocalDate today = LocalDate.now();
        LocalDate endDate = LocalDate.now(); // Until current date

        ArrayList<Integer> monthlyVisits = new ArrayList<>();

        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            YearMonth yearMonth = YearMonth.from(startDate);
            LocalDate firstDayOfMonth = yearMonth.atDay(1);
            LocalDate lastDayOfMonth = startDate.getMonth().equals(today.getMonth()) && startDate.getYear() == today.getYear() ? today : yearMonth.atEndOfMonth();

            try {
                Date startOfMonth = dateFormat.parse(dateFormat.format(java.sql.Date.valueOf(firstDayOfMonth)));
                Date endOfMonth = dateFormat.parse(dateFormat.format(java.sql.Date.valueOf(lastDayOfMonth)));

                ResultSet rs = db.executeQuery("select count(*) as visits from Appointment where AppointmentDate>=? and AppointmentDate<=?", dateFormat.format(startOfMonth), dateFormat.format(endOfMonth));
                if (rs.next()) {
                    monthlyVisits.add(rs.getInt("visits"));
                } else {
                    monthlyVisits.add(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                monthlyVisits.add(0);
            }
            startDate = startDate.plusMonths(1);
        }
        return monthlyVisits.stream().mapToInt(i -> i).toArray();
    }

    public String[] FetchMonthNames() {
        LocalDate startDate = LocalDate.of(2023, 8, 1); // Starting from August 2023
        LocalDate endDate = LocalDate.now(); // Until current month

        ArrayList<String> monthNames = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM"); // Abbreviated month name

        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            String monthName = startDate.format(formatter);
            monthNames.add(monthName);

            // Move to the next month
            startDate = startDate.plusMonths(1);
        }

        // Convert ArrayList to array
        return monthNames.toArray(new String[0]);
    }

}
