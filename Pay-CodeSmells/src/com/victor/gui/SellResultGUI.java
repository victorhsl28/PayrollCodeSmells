package com.victor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.victor.actions.Action;
import com.victor.actions.Action.Event;
import com.victor.classes.SellResult;
import com.victor.employees.Comissioned;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class SellResultGUI implements ActionListener {
	
	private JLabel result;
	private JFrame frame;
	private JPanel panel;
	private JFormattedTextField idField;
	private JFormattedTextField valueField;
	private JButton createButton;
	
	public SellResultGUI() {
		frame = new JFrame("Create Sell Result");
		panel = new JPanel();
		
		result = new JLabel("");
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 70, 20, 70));
		panel.setLayout(new GridLayout(0, 1));
		
		panel.add(result);
		
		JLabel removeLabel = new JLabel("Employee ID:");		
		idField = new JFormattedTextField();
		panel.add(removeLabel);
		panel.add(idField);
		
		JLabel valueLabel = new JLabel("Enter sell value:");
		valueField = new JFormattedTextField();
		panel.add(valueLabel);
		panel.add(valueField);
		
		createButton = new JButton("Create result");
		createButton.addActionListener(this);
		panel.add(createButton);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	private void createResult() {
		if(idField.getText().isBlank()) {
			result.setText("The ID field cannot de blank!");
			return;
		}
		
		if(valueField.getText().isBlank()) {
			result.setText("The value field cannot de blank!");
			return;
		}
		
		try {
			UUID id = UUID.fromString(idField.getText());
			int value = Integer.valueOf(valueField.getText());
			if(Main.employees.containsKey(id)) {
				if(Main.employees.get(id) instanceof Comissioned) {
					Comissioned employee = (Comissioned) Main.employees.get(id);
					employee.getSellResults().add(new SellResult(value));
					Main.lastAction = new Action(employee, null, null, null, Event.CREATE_SELL_RESULT);
					ShowDialogMessage.showMessage("Success!", "Sell result for employee " + id + " has been created!");
					Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					employee.getSellResults().get(employee.getSellResults().size() - 1).print_info();
				} else {
					result.setText("This employee is not comissioned!");
				}
			} else {
				result.setText("ID not founded!");
			}
		} catch (Exception e) {
			result.setText("The ID or value is not valid!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == createButton) {
			createResult();
		}
	}

}
