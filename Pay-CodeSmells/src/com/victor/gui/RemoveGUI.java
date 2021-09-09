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

import com.victor.actions.RemoveEmployeeAction;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class RemoveGUI implements ActionListener {
	
	private JLabel result;
	private JFrame frame;
	private JPanel panel;
	private JFormattedTextField idField;
	private JButton removeButton;
	
	public RemoveGUI() {
		frame = new JFrame("Remove employee");
		panel = new JPanel();
		
		result = new JLabel("");
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 50, 20, 50));
		panel.setLayout(new GridLayout(0, 1));
		
		panel.add(result);
		
		JLabel removeLabel = new JLabel("Employee ID:");		
		idField = new JFormattedTextField();
		panel.add(removeLabel);
		panel.add(idField);
		
		removeButton = new JButton("Remove employee");
		removeButton.addActionListener(this);
		panel.add(removeButton);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	private void removeEmployee() {
		if(idField.getText().isBlank()) {
			result.setText("The ID field cannot de blank!");
			return;
		}
		
		try {
			UUID id = UUID.fromString(idField.getText());
			if(Main.employees.containsKey(id)) {
				if(!Main.employees.get(id).getSyndicateUUID().toString().equalsIgnoreCase(Main.nullUUID)) {
					Main.syndicate.remove(Main.employees.get(id).getSyndicateUUID());
				}
				if(!Main.employees.get(id).getSyndicateUUID().toString().equalsIgnoreCase(Main.nullUUID)) {
					Main.control.setAction(new RemoveEmployeeAction(Main.employees.get(id), Main.syndicate.get(Main.employees.get(id).getSyndicateUUID())));
				} else {
					Main.control.setAction(new RemoveEmployeeAction(Main.employees.get(id), null));
				}
				Main.employees.remove(id);
				ShowDialogMessage.showMessage("Success!", "Employee " + id + " has been removed!", true, frame);
			} else {
				result.setText("ID not founded!");
			}
		} catch (Exception e) {
			result.setText("This ID is not valid!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == removeButton) {
			removeEmployee();
		}
	}

}
