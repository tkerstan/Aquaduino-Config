package org.javalobby.validate;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class IPValidator extends AbstractValidator {
	
	private int min, max;
	
    public IPValidator(JDialog parent, JTextField c, String message, int min, int max) {
        super(parent, c, message);
        this.min = min;
        this.max = max;
    }	

	public IPValidator(JFrame parent, JTextField c, String message, int min, int max) {
		super(parent, c, message);
		this.min = min;
		this.max = max;
	}
	
	@Override
	protected boolean validationCriteria(JComponent c) {
		boolean status = true;
		JTextField txtField = (JTextField) c;
		String[] ip = txtField.getText().split("\\.");
		
		if ((!txtField.getText().endsWith(".")) && (ip.length == 4)){
			try {
				for (int i = 0; i < 4; i++){
					int b = Integer.parseInt(ip[i]);
					if ( !(b >= this.min && b <= this.max) ){
						status = false;
						break;
					}
				}
			}catch(NumberFormatException e){
				status = false;
			}
		} else {
			status = false;
		}
		return status;
	}

}
