package org.javalobby.validate;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class IntegerValidator extends AbstractValidator {
	
	private int min, max;
	
    public IntegerValidator(JDialog parent, JTextField c, String message, int min, int max) {
        super(parent, c, message);
        this.min = min;
        this.max = max;
    }	

	public IntegerValidator(JFrame parent, JTextField c, String message, int min, int max) {
		super(parent, c, message);
		this.min = min;
		this.max = max;
	}
	
	@Override
	protected boolean validationCriteria(JComponent c) {
		boolean status = true;
		JTextField txtField = (JTextField) c;
		
		try {
			int b = Integer.parseInt(txtField.getText());
			if (b < min || b > max) {
				status = false;
			}
		}catch(NumberFormatException e){
			status = false;
		}
		return status;
	}

}
