package aquaduino.config;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ActuatorConfiguration {
	
	public static final int ACTUATORS = 24;
	
	
	private String[] actuatorNames = {"None", "DigitalOutput"};
	private String[] actuatorOnValues = {"0", "1"};
	private String[] pinPortNames = { "Not assigned", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54"};
	
	private JComboBox actuatorCombo[];
	private JComboBox actuatorPortCombo[];
	private JComboBox actuatorOnValueCombo[];
	
	public void initActuatorsPanel(JFrame parent, JPanel actuatorPanel){
		JPanel actuatorLblPanel = new JPanel();
		JLabel lblActuator[] = new JLabel[ACTUATORS];
		actuatorCombo = new JComboBox[ACTUATORS];
		actuatorPortCombo = new JComboBox[ACTUATORS];
		actuatorOnValueCombo = new JComboBox[ACTUATORS];
		JPanel actuatorComboPanel = new JPanel();
		JPanel actuatorComboPanel2 = new JPanel();
		JPanel actuatorComboPanel3 = new JPanel();
		
		actuatorPanel.setLayout(new GridLayout(0, 4, 0, 0));
		actuatorLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		actuatorComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		actuatorComboPanel2.setLayout(new GridLayout(0, 1, 0, 0));
		actuatorComboPanel3.setLayout(new GridLayout(0, 1, 0, 0));
		
		actuatorPanel.add(actuatorLblPanel);
		actuatorPanel.add(actuatorComboPanel);
		actuatorPanel.add(actuatorComboPanel2);
		actuatorPanel.add(actuatorComboPanel3);
		
		actuatorCombo = new JComboBox[ACTUATORS];
		for (int i = 0; i < ACTUATORS; i++){
			lblActuator[i] = new JLabel("Actuator "+ String.valueOf(i));
			actuatorCombo[i] = new JComboBox();
			actuatorPortCombo[i] = new JComboBox();
			actuatorOnValueCombo[i] = new JComboBox();
			actuatorCombo[i].setModel(new DefaultComboBoxModel(actuatorNames));
			actuatorPortCombo[i].setModel(new DefaultComboBoxModel(new String[] {"N/A"}));
			actuatorOnValueCombo[i].setModel(new DefaultComboBoxModel(actuatorOnValues));
			actuatorLblPanel.add(lblActuator[i]);
			actuatorComboPanel.add(actuatorCombo[i]);
			actuatorComboPanel2.add(actuatorPortCombo[i]);
			actuatorComboPanel3.add(actuatorOnValueCombo[i]);
			
			actuatorCombo[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (int i = 0; i < ACTUATORS; i++){
						if (arg0.getSource() == actuatorCombo[i]){
							if (actuatorCombo[i].getSelectedItem().toString().compareTo(actuatorNames[1]) == 0){
								actuatorPortCombo[i].setModel(new DefaultComboBoxModel(pinPortNames));
							} else {
								actuatorPortCombo[i].setModel(new DefaultComboBoxModel(new String[] {"N/A"}));
							}
						}
					}
				}
			});
		}
		
	}
	
	public void writeConfig(){
		try{
			FileOutputStream f = new FileOutputStream("Network.cfg");
			f.close();
		} catch (Exception e){
			
		}
	}
}
