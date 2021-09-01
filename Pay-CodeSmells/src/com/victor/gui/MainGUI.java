package com.victor.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.victor.actions.Redo;
import com.victor.actions.Undo;
import com.victor.main.Main;

public class MainGUI implements ActionListener {
	
	private JFrame frame;
	private JPanel panel;
	private JLabel functionsLabel;
	private JButton ListEmployeeButton;
	private JButton addEmployeeButton;
	private JButton removeEmployeeButton;
	private JButton createTimecardButton;
	private JButton createSellResultButton;
	private JButton createServiceTaxButton;
	private JButton changeInfoButton;
	private JButton rollButton;
	private JButton undoButton;
	private JButton redoButton;
	private JButton createPaymentScheduleButton;
	
	public MainGUI() {
		frame = new JFrame("Payroll");
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		panel.setLayout(new GridLayout(0, 1));
		
		functionsLabel = new JLabel("Please choose an option:");
		panel.add(functionsLabel);
		
		ListEmployeeButton = new JButton("List employees");
		ListEmployeeButton.addActionListener(this);
		
		addEmployeeButton = new JButton("Add employee");
		addEmployeeButton.addActionListener(this);
		
		removeEmployeeButton = new JButton("Remove employee");
		removeEmployeeButton.addActionListener(this);
		
		createTimecardButton = new JButton("Create timecard");
		createTimecardButton.addActionListener(this);
		
		createSellResultButton = new JButton("Create sell result");
		createSellResultButton.addActionListener(this);
		
		createServiceTaxButton = new JButton("Create service tax");
		createServiceTaxButton.addActionListener(this);
		
		changeInfoButton = new JButton("Change employee info");
		changeInfoButton.addActionListener(this);
		
		rollButton = new JButton("Roll");
		rollButton.addActionListener(this);
		
		undoButton = new JButton("Undo last action");
		undoButton.addActionListener(this);
		
		redoButton = new JButton("Redo last action");
		redoButton.addActionListener(this);
		
		createPaymentScheduleButton = new JButton("Create payment schedule");
		createPaymentScheduleButton.addActionListener(this);
		
		panel.add(ListEmployeeButton);
		panel.add(rollButton);
		panel.add(addEmployeeButton);
		panel.add(removeEmployeeButton);
		panel.add(createTimecardButton);
		panel.add(createSellResultButton);
		panel.add(createServiceTaxButton);
		panel.add(changeInfoButton);
		panel.add(undoButton);
		panel.add(redoButton);
		panel.add(createPaymentScheduleButton);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ListEmployeeButton) {
			new ListGUI();
		}
		
		if(e.getSource() == addEmployeeButton) {
			new AddGUI();
		}
		
		if(e.getSource() == removeEmployeeButton) {
			new RemoveGUI();
		}
		
		if(e.getSource() == createTimecardButton) {
			new TimecardGUI();
		}
		
		if(e.getSource() == createSellResultButton) {
			new SellResultGUI();
		}
		
		if(e.getSource() == createServiceTaxButton) {
			new SyndicateGUI();
		}
		
		if(e.getSource() == changeInfoButton) {
			new ChangeInfoGUI();
		}
		
		if(e.getSource() == rollButton) {
			Main.roll();
		}
		
		if(e.getSource() == undoButton) {
			Undo.undo();
		}
		
		if(e.getSource() == redoButton) {
			Redo.redo();
		}
		
		if(e.getSource() == createPaymentScheduleButton) {
			new PaymentScheduleGUI();
		}
	}

}
