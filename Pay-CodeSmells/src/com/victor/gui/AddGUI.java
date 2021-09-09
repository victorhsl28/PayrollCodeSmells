package com.victor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.victor.actions.AddEmployeeAction;
import com.victor.classes.Address;
import com.victor.classes.Syndicate;
import com.victor.employees.Comissioned;
import com.victor.employees.Employee.PaymentMethod;
import com.victor.employees.Hourly;
import com.victor.employees.Salaried;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class AddGUI implements ActionListener {
	
	private JFrame frame;
	private JPanel panel;
	private JTextField nameField;
	private JTextField adressCityField;
	private JTextField adressStateField;
	private JTextField adressCountryField;
	private ButtonGroup salaryTypeGroup;
	private JRadioButton salaryTypeButtonHourly;
	private JRadioButton salaryTypeButtonSalaried;
	private JRadioButton salaryTypeButtonComissioned;
	private JFormattedTextField salaryField;
	private JFormattedTextField comissionField;
	private ButtonGroup paymentMethodGroup;
	private JRadioButton paymentMethodButtonMail;
	private JRadioButton paymentMethodButtonHand;
	private JRadioButton paymentMethodButtonDeposit;
	private ButtonGroup syndicateGroup;
	private JRadioButton syndicateYesButton;
	private JRadioButton syndicateNoButton;
	private JButton concludeButton;
	private JLabel result;
	
	public AddGUI() {
		frame = new JFrame("Add new employee");
		panel = new JPanel();
		
		result = new JLabel("");
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 50, 20, 50));
		panel.setLayout(new GridLayout(0, 1));
		
		panel.add(result);
		
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
		
		JLabel taxLabel = new JLabel("Tax (if comisisoned)");
		comissionField = new JFormattedTextField();
		panel.add(taxLabel);
		panel.add(comissionField);
		
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
		
		
		concludeButton = new JButton("Add employee");
		concludeButton.addActionListener(this);
		panel.add(concludeButton);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	private void addEmployee() {
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
		
		if(paymentMethodGroup.getSelection() == salaryTypeButtonComissioned && comissionField.getText().isEmpty()) {
			result.setText("The tax field cannot de blank!");
			return;
		}
		
		UUID uuid = UUID.randomUUID();
		if(Main.employees.containsKey(uuid) || uuid.toString().equalsIgnoreCase(new UUID(0, 0).toString())) {
			while(Main.employees.containsKey(uuid))
				uuid = UUID.randomUUID();
		}
		
		PaymentMethod paymentMethod = null;
		if(paymentMethodButtonMail.isSelected()) {
			paymentMethod = PaymentMethod.MAIL_CHECK;
		} else if(paymentMethodButtonDeposit.isSelected()) {
			paymentMethod = PaymentMethod.DEPOSIT_BANK_ACCOUNT;
		} else if(paymentMethodButtonHand.isSelected()) {
			paymentMethod = PaymentMethod.HAND_CHECK;
		}
		
		UUID syndicateUUID = new UUID(0, 0);
		if(syndicateYesButton.isSelected()) {
			syndicateUUID = UUID.randomUUID();
			if(Main.syndicate.containsKey(syndicateUUID) || syndicateUUID.toString().equalsIgnoreCase(Main.nullUUID)) {
				while(Main.syndicate.containsKey(syndicateUUID))
					syndicateUUID = UUID.randomUUID();
			}
		}
		
		try {
			if(salaryTypeButtonHourly.isSelected()) {
				Main.employees.put(uuid, new Hourly(uuid, nameField.getText(), new Address(adressCityField.getText(), adressStateField.getText(), adressCountryField.getText()), Double.valueOf(salaryField.getText()), paymentMethod, "semanal 1 sexta", syndicateUUID));
			} else if(salaryTypeButtonSalaried.isSelected()) {
				Main.employees.put(uuid, new Salaried(uuid, nameField.getText(), new Address(adressCityField.getText(), adressStateField.getText(), adressCountryField.getText()), Double.valueOf(salaryField.getText()), paymentMethod, "mensal $", syndicateUUID));
			} else if(salaryTypeButtonComissioned.isSelected()) {
				Main.employees.put(uuid, new Comissioned(uuid, nameField.getText(), new Address(adressCityField.getText(), adressStateField.getText(), adressCountryField.getText()), Double.valueOf(salaryField.getText()), paymentMethod, "semanal 2 sexta", syndicateUUID, Double.valueOf(comissionField.getText())));
			} else {
				result.setText("Error while creating new employee!");
			}
			if(!uuid.toString().equalsIgnoreCase(Main.nullUUID)) {
				Main.syndicate.put(syndicateUUID, new Syndicate(syndicateUUID, Double.valueOf(salaryField.getText()) * 0.1));
			}
			Main.control.setAction(new AddEmployeeAction(Main.employees.get(uuid)));
			ShowDialogMessage.showMessage("Success!", "Employee " + nameField.getText() + " has been created with the ID: " + uuid.toString() + "!", true, frame);
		} catch (Exception e) {
			result.setText("There is an incorrect field!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == concludeButton) {
			addEmployee();
		}
	}

}
