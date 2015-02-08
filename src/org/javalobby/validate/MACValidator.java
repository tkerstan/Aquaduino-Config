package org.javalobby.validate;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class MACValidator extends AbstractValidator {
	
    public MACValidator(JDialog parent, JTextField c, String message) {
        super(parent, c, message);
    }	

	public MACValidator(JFrame parent, JTextField c, String message) {
		super(parent, c, message);
	}
	
	@Override
	protected boolean validationCriteria(JComponent c) {
		boolean status = true;
		JTextField txtField = (JTextField) c;
		
		status = txtField.getText().matches("[0-9a-fA-F]{12}");
		
		return status;
	}

}
