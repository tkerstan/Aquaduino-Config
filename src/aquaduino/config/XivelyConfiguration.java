package aquaduino.config;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class XivelyConfiguration {
	
	public static final int XIVELY_FEED_NAME_LENGTH = 21;
	public static final int XIVELY_API_KEY_LENGTH = 51;
	public static final int XIVELY_CHANNEL_NAME_LENGTH = 20;
	
	private JTextField xivelyAPIKeyTxtField;
	private JTextField xivelyFeedIdTxtField;
	private JCheckBox xivelyCBox;
		
	public void initXivelyPanel(JFrame parent, JPanel xivelyPanel){
		xivelyPanel.setLayout(new GridLayout(0, 2, 0, 0));
		JPanel xivelyLblPanel = new JPanel();
		xivelyPanel.add(xivelyLblPanel);
		xivelyLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblXively_1 = new JLabel("Xively");
		xivelyLblPanel.add(lblXively_1);
		
		JLabel lblXivelyAPIKey = new JLabel("API Key");
		xivelyLblPanel.add(lblXivelyAPIKey);
		
		JLabel lblFeedId = new JLabel("Feed ID");
		xivelyLblPanel.add(lblFeedId);
		
		JPanel panel = new JPanel();
		xivelyPanel.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		xivelyCBox = new JCheckBox("");
		xivelyCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (xivelyCBox.getSelectedObjects() != null)
				{
					xivelyAPIKeyTxtField.setEnabled(true);
					xivelyFeedIdTxtField.setEnabled(true);
				}
				else
				{
					xivelyAPIKeyTxtField.setEnabled(false);
					xivelyFeedIdTxtField.setEnabled(false);					
				}
			}
		});
		panel.add(xivelyCBox);
		
		xivelyAPIKeyTxtField = new JTextField();
		xivelyAPIKeyTxtField.setEnabled(false);
		panel.add(xivelyAPIKeyTxtField);
		xivelyAPIKeyTxtField.setColumns(10);
		
		xivelyFeedIdTxtField = new JTextField();
		xivelyFeedIdTxtField.setEnabled(false);
		panel.add(xivelyFeedIdTxtField);
		xivelyFeedIdTxtField.setColumns(10);

	}

}
