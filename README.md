# PayrollCodeSmells
1) Criado o package com.victor.uttils e a classe ShowDialogMessage com o metodo showMessage
Antes era utilizado JOptionPane.showMessageDialog(null, <message>, <title>, JOptionPane.INFORMATION_MESSAGE),
Agora basta utilizar ShowDialogMessage.showMessage(<title>, <message>)
