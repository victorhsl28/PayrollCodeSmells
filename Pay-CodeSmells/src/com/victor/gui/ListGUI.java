package com.victor.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.victor.employees.Comissioned;
import com.victor.employees.Employee;
import com.victor.employees.Hourly;
import com.victor.employees.Salaried;
import com.victor.main.Main;

public class ListGUI {
	
	private JFrame frame;
	private JTable table;
	private JScrollPane scroll;
	
	public ListGUI() {
		frame = new JFrame("Employees");
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		String[][] data = new String[Main.employees.size()][10];
		int i = 0;
		for(Employee employee : Main.employees.values()) {
			data[i][0] = employee.getUUID().toString();
			data[i][1] = employee.getName();
			data[i][2] = employee.getAdress().getCity();
			data[i][3] = employee.getAdress().getState();
			data[i][4] = employee.getAdress().getCountry();
			if(employee instanceof Hourly) {
				data[i][5] = "Hourly";
			} else if(employee instanceof Salaried) {
				data[i][5] = "Salaried";
			} else if(employee instanceof Comissioned) {
				data[i][5] = "Comissioned";
			}
			data[i][6] = String.valueOf(employee.getSalary());
			data[i][7] = employee.getPaymentMethod().toString();
			data[i][8] = employee.getSyndicateUUID().toString();
			data[i][9] = employee.getPaymentSchedule();
			i++;
		}
		String[] columnNames = { "ID", "Name", "City", "State", "Country", "Employee Type", "Salary/Tax", "Payment Method", "Syndicate ID", "Payment Schedule" };
		table = new JTable(data, columnNames);
		table.setBounds(50, 50, 200, 300);
		scroll = new JScrollPane(table);
		frame.add(scroll);
		frame.setVisible(true);
	}
}
