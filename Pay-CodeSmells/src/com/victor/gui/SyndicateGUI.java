package com.victor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.victor.actions.CreateServiceTaxAction;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class SyndicateGUI implements ActionListener {
	
	private JLabel result;
	private JFrame frame;
	private JPanel panel;
	private JFormattedTextField idField;
	private JFormattedTextField taxField;
	private JButton createButton;
	
	public SyndicateGUI() {
		frame = new JFrame("Create service tax");
		panel = new JPanel();
		
		result = new JLabel("");
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 70, 20, 70));
		panel.setLayout(new GridLayout(0, 1));
		
		panel.add(result);
		
		JLabel removeLabel = new JLabel("Syndicate ID:");		
		idField = new JFormattedTextField();
		panel.add(removeLabel);
		panel.add(idField);
		
		JLabel valueLabel = new JLabel("Service tax:");
		taxField = new JFormattedTextField();
		panel.add(valueLabel);
		panel.add(taxField);
		
		createButton = new JButton("Create tax service");
		createButton.addActionListener(this);
		panel.add(createButton);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	private void createServiceTax() {
		if(idField.getText().isBlank()) {
			result.setText("The ID field cannot de blank!");
			return;
		}
		
		if(taxField.getText().isBlank()) {
			result.setText("The tax field cannot de blank!");
			return;
		}
		
		try {
			UUID id = UUID.fromString(idField.getText());
			double tax = Double.valueOf(taxField.getText());
			
			if(Main.syndicate.containsKey(id)) {
				Main.syndicate.get(id).getExtraTaxes().add(tax);
				ShowDialogMessage.showMessage("Success!", "Service tax for syndicate employee " + id + " has been created!", true, frame);
				Main.syndicate.get(id).print_info();
				Main.control.setAction(new CreateServiceTaxAction(Main.syndicate.get(id)));
			} else {
				result.setText("ID not founded!");
			}
		} catch (Exception e) {
			result.setText("The ID or tax is not valid!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == createButton) {
			createServiceTax();
		}
	}

}
