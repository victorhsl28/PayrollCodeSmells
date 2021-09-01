package com.victor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.victor.actions.Action;
import com.victor.actions.Action.Event;
import com.victor.classes.Address;
import com.victor.classes.Syndicate;
import com.victor.employees.Comissioned;
import com.victor.employees.Employee;
import com.victor.employees.Employee.PaymentMethod;
import com.victor.employees.Hourly;
import com.victor.employees.Salaried;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class ChangeInfoGUI implements ActionListener {
	
	private JFrame frame;
	private JPanel panel;
	private JFormattedTextField idField;
	private JFormattedTextField newIdField;
	private JTextField nameField;
	private JTextField adressCityField;
	private JTextField adressStateField;
	private JTextField adressCountryField;
	private ButtonGroup salaryTypeGroup;
	private JRadioButton salaryTypeButtonHourly;
	private JRadioButton salaryTypeButtonSalaried;
	private JRadioButton salaryTypeButtonComissioned;
	private JFormattedTextField salaryField;
	private JFormattedTextField comissionedField;
	private ButtonGroup paymentMethodGroup;
	private JRadioButton paymentMethodButtonMail;
	private JRadioButton paymentMethodButtonHand;
	private JRadioButton paymentMethodButtonDeposit;
	private ButtonGroup syndicateGroup;
	private JRadioButton syndicateYesButton;
	private JRadioButton syndicateNoButton;
	private JFormattedTextField syndicateIdField;
	private JFormattedTextField syndicateTaxField;
	private JFormattedTextField paymentScheduleField;
	private JButton concludeButton;
	private JLabel result;
	private JButton searchButton;
	
	public ChangeInfoGUI() {
		frame = new JFrame("Change employee info");
		panel = new JPanel();
		
		result = new JLabel("");
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 50, 20, 50));
		panel.setLayout(new GridLayout(0, 1));
		
		panel.add(result);
		
		JLabel idText = new JLabel("ID:");
		idField = new JFormattedTextField();
		panel.add(idText);
		panel.add(idField);
		
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		panel.add(searchButton);
		
		JLabel newIdText = new JLabel("New ID:");
		newIdField = new JFormattedTextField();
		panel.add(newIdText);
		panel.add(newIdField);
		
		JLabel nameText = new JLabel("Name:");
		nameField = new JTextField(20);
		panel.add(nameText);
		panel.add(nameField);
		
		JLabel adressCity = new JLabel("City:");
		adressCityField = new JTextField(20);
		panel.add(adressCity);
		panel.add(adressCityField);
		
		JLabel adressState = new JLabel("State:");
		adressStateField = new JTextField(20);
		panel.add(adressState);
		panel.add(adressStateField);
		
		JLabel adressCountry = new JLabel("Country:");
		adressCountryField = new JTextField(20);
		panel.add(adressCountry);
		panel.add(adressCountryField);
		
		JLabel salaryType = new JLabel("Salary type:");
		panel.add(salaryType);
		
		salaryTypeGroup = new ButtonGroup();
		
		salaryTypeButtonHourly = new JRadioButton("Hourly");
		salaryTypeGroup.add(salaryTypeButtonHourly);
		salaryTypeButtonSalaried = new JRadioButton("Salaried");
		salaryTypeGroup.add(salaryTypeButtonSalaried);
		salaryTypeButtonComissioned = new JRadioButton("Comissioned");
		salaryTypeGroup.add(salaryTypeButtonComissioned);
		
		panel.add(salaryTypeButtonHourly);
		panel.add(salaryTypeButtonSalaried);
		panel.add(salaryTypeButtonComissioned);
		
		JLabel salaryLabel = new JLabel("Salary");
		salaryField = new JFormattedTextField();
		panel.add(salaryLabel);
		panel.add(salaryField);
		
		JLabel taxLabel = new JLabel("Tax (if comissioned)");
		comissionedField = new JFormattedTextField();
		panel.add(taxLabel);
		panel.add(comissionedField);
		
		JLabel paymentMethodLabel = new JLabel("Payment method:");
		panel.add(paymentMethodLabel);
		
		paymentMethodGroup = new ButtonGroup();
		
		paymentMethodButtonMail = new JRadioButton("Mail");
		paymentMethodGroup.add(paymentMethodButtonMail);
		paymentMethodButtonHand = new JRadioButton("Hand check");
		paymentMethodGroup.add(paymentMethodButtonHand);
		paymentMethodButtonDeposit = new JRadioButton("Bank account deposit");
		paymentMethodGroup.add(paymentMethodButtonDeposit);
		
		panel.add(paymentMethodButtonMail);
		panel.add(paymentMethodButtonHand);
		panel.add(paymentMethodButtonDeposit);
		
		JLabel syndicateLabel = new JLabel("Is on the syndicate:");
		panel.add(syndicateLabel);
		
		syndicateGroup = new ButtonGroup();
		syndicateYesButton = new JRadioButton("Yes");
		syndicateGroup.add(syndicateYesButton);
		syndicateNoButton = new JRadioButton("No");
		syndicateGroup.add(syndicateNoButton);
		
		panel.add(syndicateYesButton);
		panel.add(syndicateNoButton);
		
		JLabel syndicateId = new JLabel("Syndicate ID");
		syndicateIdField = new JFormattedTextField();
		panel.add(syndicateId);
		panel.add(syndicateIdField);
		
		JLabel syndicateTax = new JLabel("Syndicate tax");
		syndicateTaxField = new JFormattedTextField();
		panel.add(syndicateTax);
		panel.add(syndicateTaxField);
		
		JLabel paymentScheduleLabel = new JLabel("Payment schedule");
		paymentScheduleField = new JFormattedTextField();
		panel.add(paymentScheduleLabel);
		panel.add(paymentScheduleField);
		
		
		concludeButton = new JButton("Save changes");
		concludeButton.addActionListener(this);
		panel.add(concludeButton);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	private void searchEmployee() {
		if(idField.getText().isBlank()) {
			result.setText("The ID field cannot de blank!");
			return;
		}
		
		try {
			UUID id = UUID.fromString(idField.getText());
			if(Main.employees.containsKey(id)) {
				Employee employee = Main.employees.get(id);
				newIdField.setText(String.valueOf(employee.getUUID()));
				nameField.setText(employee.getName());
				adressCityField.setText(employee.getAdress().getCity());
				adressStateField.setText(employee.getAdress().getState());
				adressCountryField.setText(employee.getAdress().getCountry());
				if(employee instanceof Hourly) {
					salaryTypeButtonHourly.setSelected(true);
					salaryField.setText(String.valueOf(((Hourly)employee).getSalary()));
				} else if(employee instanceof Salaried) {
					salaryTypeButtonSalaried.setSelected(true);
					salaryField.setText(String.valueOf(((Salaried)employee).getSalary()));
				} else if(employee instanceof Comissioned) {
					salaryTypeButtonComissioned.setSelected(true);
					salaryField.setText(String.valueOf(((Comissioned)employee).getSalary()));
					comissionedField.setText(String.valueOf(((Comissioned)employee).getComissionedTax()));
				}
				
				if(employee.getPaymentMethod() == PaymentMethod.MAIL_CHECK) {
					paymentMethodButtonMail.setSelected(true);
				} else if(employee.getPaymentMethod() == PaymentMethod.HAND_CHECK) {
					paymentMethodButtonHand.setSelected(true);
				} else if(employee.getPaymentMethod() == PaymentMethod.DEPOSIT_BANK_ACCOUNT) {
					paymentMethodButtonDeposit.setSelected(true);
				}
				
				if(!employee.getSyndicateUUID().toString().equalsIgnoreCase(Main.nullUUID)) {
					syndicateYesButton.setSelected(true);
					if(Main.syndicate.containsKey(employee.getSyndicateUUID())) {
						syndicateIdField.setText(String.valueOf(employee.getSyndicateUUID()));
						syndicateTaxField.setText(String.valueOf(Main.syndicate.get(employee.getSyndicateUUID()).getSyndicateTax()));
					}
				} else {
					syndicateNoButton.setSelected(true);
					syndicateIdField.setText(new UUID(0, 0).toString());
					syndicateTaxField.setText("0");
				}
				
				paymentScheduleField.setText(employee.getPaymentSchedule());
				
			} else {
				result.setText("ID not founded!");
			}
		} catch (Exception e) {
			result.setText("The ID field cannot de blank!");
		}
	}
	
	private void changeEmployeeInfo() {
		if(newIdField.getText().isBlank()) {
			result.setText("The ID field cannot de blank!");
			return;
		}
		
		if(nameField.getText().isBlank()) {
			result.setText("The name field cannot de blank!");
			return;
		}
		
		if(adressCityField.getText().isBlank()) {
			result.setText("The city field cannot de blank!");
			return;
		}
		
		if(adressStateField.getText().isBlank()) {
			result.setText("The state field cannot de blank!");
			return;
		}
		
		if(adressCountryField.getText().isBlank()) {
			result.setText("The country field cannot de blank!");
			return;
		}
		
		if(salaryTypeGroup.getSelection() == null) {
			result.setText("The salary type is not selected!");
			return;
		}
		
		if(salaryField.getText().isBlank()) {
			result.setText("The salary field cannot de blank!");
			return;
		}
		
		if(paymentMethodGroup.getSelection() == null) {
			result.setText("The payment method is not selected!");
			return;
		}
		
		if(syndicateGroup.getSelection() == null) {
			result.setText("The syndicate option is not selected!");
			return;
		}
		
		if(syndicateYesButton.isSelected() && syndicateIdField.getText().isBlank()) {
			result.setText("The syndicate ID field cannot de blank!");
			return;
		}
		
		if(syndicateYesButton.isSelected() && syndicateTaxField.getText().isBlank()) {
			result.setText("The syndicate tax field cannot de blank!");
			return;
		}
		
		if(paymentMethodGroup.getSelection() == salaryTypeButtonComissioned && comissionedField.getText().isEmpty()) {
			result.setText("The tax field cannot de blank!");
			return;
		}
		
		if(paymentScheduleField.getText().isBlank()) {
			result.setText("The payment schedule field cannot de blank!");
			return;
		}
		
		try {
			UUID oldID = UUID.fromString(idField.getText());
			UUID newID = UUID.fromString(newIdField.getText());
			
			PaymentMethod paymentMethod = null;
			if(paymentMethodButtonMail.isSelected()) {
				paymentMethod = PaymentMethod.MAIL_CHECK;
			} else if(paymentMethodButtonDeposit.isSelected()) {
				paymentMethod = PaymentMethod.DEPOSIT_BANK_ACCOUNT;
			} else if(paymentMethodButtonHand.isSelected()) {
				paymentMethod = PaymentMethod.HAND_CHECK;
			}
			
			if(Main.employees.containsKey(oldID)) {
				
				Employee oldEmployee = Main.employees.get(oldID);
				Syndicate oldSyndicate = Main.syndicate.get(oldEmployee.getSyndicateUUID());
				
				UUID oldSyndicateID = Main.employees.get(oldID).getSyndicateUUID();
				UUID newSyndicateID = UUID.fromString(syndicateIdField.getText());
				UUID syndicateId = new UUID(0, 0);
				
				if(syndicateYesButton.isSelected()) {
					if(!(newSyndicateID.toString().equalsIgnoreCase(oldSyndicateID.toString())) && Main.syndicate.containsKey(newSyndicateID)) {
						result.setText("The syndicate ID is already in use!");
						return;
					}
					if(Main.syndicate.containsKey(oldSyndicateID) && (!oldSyndicateID.toString().equalsIgnoreCase(Main.nullUUID))) {
						Main.syndicate.remove(oldSyndicateID);
						syndicateId = newSyndicateID;
					} else {					
						syndicateId = UUID.randomUUID();
						if(Main.syndicate.containsKey(syndicateId) || syndicateId.toString().equalsIgnoreCase(Main.nullUUID)) {
							while(Main.syndicate.containsKey(syndicateId))
								syndicateId = UUID.randomUUID();
						}
					}
				} else if(syndicateNoButton.isSelected()) {
					syndicateId = new UUID(0, 0);
					if(Main.syndicate.containsKey(oldSyndicateID)) {
						Main.syndicate.remove(oldSyndicateID);
					}
				}
				
				if(newID.toString().equalsIgnoreCase(Main.nullUUID)) {
					result.setText("This ID is already in use!");
					return;
				}
				
				if(Main.employees.containsKey(newID)) {	
					if(newID.toString().equalsIgnoreCase(oldID.toString())) {
						if(Main.paymentSchedules.contains(paymentScheduleField.getText())) {
							changeInfo(newID, oldID, oldEmployee, oldSyndicate, paymentMethod, paymentScheduleField.getText(), syndicateId);
						} else {
							result.setText("Payment Schedule not founded!");
						}
					} else {
						result.setText("This ID is already in use!");
					}
				} else {
					if(Main.paymentSchedules.contains(paymentScheduleField.getText())) {
						changeInfo(newID, oldID, oldEmployee, oldSyndicate, paymentMethod, paymentScheduleField.getText(), syndicateId);
					} else {
						result.setText("Payment Schedule not founded!");
					}
				}
			} else {
				result.setText("ID not founded!");
			}
		} catch (Exception e) {
			result.setText("There is an incorrect field!");
		}
	}
	
	private void changeInfo(UUID newID, UUID oldID, Employee oldEmployee, Syndicate oldSyndicate, PaymentMethod paymentMethod, String paymentSchedule, UUID syndicateId) {
		if(salaryTypeButtonHourly.isSelected()) {
			Main.employees.remove(oldID);
			Main.employees.put(newID, new Hourly(newID, nameField.getText(), new Address(adressCityField.getText(), adressStateField.getText(), adressCountryField.getText()), Double.valueOf(salaryField.getText()), paymentMethod, paymentSchedule, syndicateId));
		} else if(salaryTypeButtonSalaried.isSelected()) {
			Main.employees.remove(oldID);
			Main.employees.put(newID, new Salaried(newID, nameField.getText(), new Address(adressCityField.getText(), adressStateField.getText(), adressCountryField.getText()), Double.valueOf(salaryField.getText()), paymentMethod, paymentSchedule, syndicateId));
		} else if(salaryTypeButtonComissioned.isSelected()) {
			if(!comissionedField.getText().isEmpty()) {
				Main.employees.remove(oldID);
				Main.employees.put(newID, new Comissioned(newID, nameField.getText(), new Address(adressCityField.getText(), adressStateField.getText(), adressCountryField.getText()), Double.valueOf(salaryField.getText()), paymentMethod, paymentSchedule, syndicateId, Double.valueOf(comissionedField.getText())));
			} else {
				result.setText("The tax field cannot de blank!");
				return;
			}
		} else {
			result.setText("Error while creating new employee!");
		}
		if(syndicateYesButton.isSelected()) {
			if(syndicateTaxField.getText().equalsIgnoreCase("0")) {
				Main.syndicate.put(syndicateId, new Syndicate(syndicateId, Double.valueOf(salaryField.getText()) * 0.1));
			} else {
				Main.syndicate.put(syndicateId, new Syndicate(syndicateId, Double.valueOf(syndicateTaxField.getText())));
			}
		}
		if(!oldEmployee.getSyndicateUUID().toString().equalsIgnoreCase(Main.nullUUID)) {
			Main.lastAction = new Action(Main.employees.get(newID), oldEmployee, oldSyndicate, null, Event.CHANGE_EMPLOYEE_INFO);
		} else {
			Main.lastAction = new Action(Main.employees.get(newID), oldEmployee, null, null, Event.CHANGE_EMPLOYEE_INFO);
		}
		ShowDialogMessage.showMessage("Success!", "Employee " + oldID + " info has been saved!");
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchButton) {
			searchEmployee();
		}
		
		if(e.getSource() == concludeButton) {
			changeEmployeeInfo();
		}
	}

}