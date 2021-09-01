# PayrollCodeSmells
1) Criado o package com.victor.uttils e a classe ShowDialogMessage com o metodo showMessage
 <br />Antes era utilizado <br />
	`JOptionPane.showMessageDialog(null, <message>, <title>, JOptionPane.INFORMATION_MESSAGE);`
 <br />Agora basta utilizar <br />
	`ShowDialogMessage.showMessage(<title>, <message>)`

2) Na classe TimecardGUI, criei dois métodos para evitar código repetido.
 <br />Antes<br />
    ```java
   if(employee.getTimecards().isEmpty()) {
		employee.getTimecards().add(new TimeCard());
		JOptionPane.showMessageDialog(null, "Timecard for employee " + id + " has been created!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		Main.lastAction = new Action(employee, null, null, null, Event.CREATE_TIMECARD);
		WindowEvent closingEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
        } else if(!employee.getTimecards().get(employee.getTimecards().size() - 1).isCompleted()) {
		result.setText("The last employee timecard is not completed!");
	} else {
	        employee.getTimecards().add(new TimeCard());
		Main.lastAction = new Action(employee, null, null, null, Event.CREATE_TIMECARD);
		JOptionPane.showMessageDialog(null, "Timecard for employee " + id + " has been created!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		WindowEvent closingEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
		employee.getTimecards().get(employee.getTimecards().size() - 1).print_info();
	}
    ```
 <br />Depois<br />
    ```
   if(employee.getTimecards().isEmpty()) {
	        createTimeCard(employee, frame);
        } else if(!employee.getTimecards().get(employee.getTimecards().size() - 1).isCompleted()) {
                result.setText("The last employee timecard is not completed!");
        } else {
                createTimeCard(employee, frame);
        }```
	
  <br />Com os métodos<br />
  
        private static void createTimeCard(Hourly employee, JFrame frame) {
		employee.getTimecards().add(new TimeCard());
		ShowDialogMessage.showMessage("Success!", "Timecard for employee \" + id + \" has been created!");
		Main.lastAction = new Action(employee, null, null, null, Event.CREATE_TIMECARD);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		employee.getTimecards().get(employee.getTimecards().size() - 1).print_info();
	}
	
    private static void updateTimeCard(Hourly employee, JFrame frame, UUID id) {
		employee.getTimecards().get(employee.getTimecards().size() - 1).closeTimecard();
		Main.lastAction = new Action(employee, null, null, null, Event.CREATE_TIMECARD);
		ShowDialogMessage.showMessage("Success!", "Timecard for employee " + id + " has been updated!");
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        employee.getTimecards().get(employee.getTimecards().size() - 1).print_info();
	}
3)
