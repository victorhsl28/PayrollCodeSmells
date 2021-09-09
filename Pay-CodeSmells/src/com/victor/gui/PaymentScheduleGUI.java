package com.victor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class PaymentScheduleGUI implements ActionListener {
	
	private JLabel result;
	private JFrame frame;
	private JPanel panel;
	private JFormattedTextField stringField;
	private JButton createButton;
	
	public PaymentScheduleGUI() {
		frame = new JFrame("Create Payment Schedule");
		panel = new JPanel();
		
		result = new JLabel("");
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 50, 20, 50));
		panel.setLayout(new GridLayout(0, 1));
		
		panel.add(result);
		
		JLabel stringLabel = new JLabel("Payment Schedule:");		
		stringField = new JFormattedTextField();
		panel.add(stringLabel);
		panel.add(stringField);
		
		createButton = new JButton("Create payment schedule");
		createButton.addActionListener(this);
		panel.add(createButton);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	private void createPaymentSchedule() {
		String text = stringField.getText();
		if(text.isBlank()) {
			result.setText("The string field cannot de blank!");
			return;
		}
		
		try {
			if(text.length() <= 3 || text.length() >= 2) {
				String[] args = text.split(" ");
				if(args[0].equalsIgnoreCase("mensal")) {
					if(args[1].equalsIgnoreCase("$")) {
						Main.paymentSchedules.add("mensal $");
						showConcludeMessage("mensal $");
					} else {
						int week = Integer.valueOf(args[1]);
						Main.paymentSchedules.add("mensal " + week);
						showConcludeMessage("mensal " + week);
					}
				} else if(args[0].equalsIgnoreCase("semanal")){
					int week = Integer.valueOf(args[1]);
					String dia = args[2];
					if(dia.equalsIgnoreCase("domingo") || dia.equalsIgnoreCase("segunda") || dia.equalsIgnoreCase("terca") || dia.equalsIgnoreCase("quarta") || 
					dia.equalsIgnoreCase("quinta") || dia.equalsIgnoreCase("sexta") || dia.equalsIgnoreCase("sabado")) {
						Main.paymentSchedules.add("semanal " + week + " " + dia);
						showConcludeMessage("semanal " + week + " " + dia);
					} else {
						result.setText("The day is not valid!");
					}
				} else {
					result.setText("This string is not valid!");
				}
						
			} else {
				result.setText("This string is not valid!");
			}
		} catch (Exception e) {
			result.setText("This string is not valid!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == createButton) {
			createPaymentSchedule();
		}
	}
	
	void showConcludeMessage(String paymentSchedule) {
		ShowDialogMessage.showMessage("Success!", "Payment schedule " + paymentSchedule + " has been created!", true, frame);
	}

}
