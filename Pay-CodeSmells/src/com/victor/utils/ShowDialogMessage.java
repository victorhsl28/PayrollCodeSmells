package com.victor.utils;

import javax.swing.JOptionPane;

public class ShowDialogMessage {
	
	public static void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

}
