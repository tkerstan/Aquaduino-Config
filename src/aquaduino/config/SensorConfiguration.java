package aquaduino.config;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SensorConfiguration {
	public static final int SENSORS = 8;
	
	private String[] sensorNames = {"None", "Digital Input", "Temperature", "Atlas pH", "Atlas EC", "Atlas ORP"};
	private String[] pinPortNames = { "Not assigned", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54"};
	private String[] serialPortNames = { "Not assigned", "Serial 0", "Serial 1", "Serial 2", "Serial 3"};
	private JComboBox sensorCombo[];
	private JComboBox sensorPortCombo[];
	
	public void initSensorPanel(JFrame parent, JPanel sensorPanel){
		JLabel lblSensor[] = new JLabel[SENSORS];
		JPanel sensorLblPanel = new JPanel();
		JPanel sensorComboPanel = new JPanel();
		JPanel sensorPortComboPanel = new JPanel();
		
		sensorCombo = new JComboBox[SENSORS];
		sensorPortCombo = new JComboBox[SENSORS];
		
		sensorPanel.setLayout(new GridLayout(0, 3, 0, 0));
		sensorLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		sensorComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		sensorPortComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
	
		sensorPanel.add(sensorLblPanel);
		sensorPanel.add(sensorComboPanel);
		sensorPanel.add(sensorPortComboPanel);
		
		for (int i = 0; i < SENSORS; i++){
			lblSensor[i] = new JLabel("Sensor "+ String.valueOf(i));
			sensorCombo[i] = new JComboBox();
			sensorCombo[i].setModel(new DefaultComboBoxModel(sensorNames));
			sensorPortCombo[i] = new JComboBox();
			sensorPortCombo[i].setModel(new DefaultComboBoxModel(new String[] {"N/A"}));
			sensorLblPanel.add(lblSensor[i]);
			sensorComboPanel.add(sensorCombo[i]);	
			sensorPortComboPanel.add(sensorPortCombo[i]);
			
			sensorCombo[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (int i = 0; i < SENSORS; i++){
						if (arg0.getSource() == sensorCombo[i]){
							if (sensorCombo[i].getSelectedItem().toString().compareTo(sensorNames[3]) == 0 ||
								sensorCombo[i].getSelectedItem().toString().compareTo(sensorNames[4]) == 0 ||
								sensorCombo[i].getSelectedItem().toString().compareTo(sensorNames[5]) == 0){
								sensorPortCombo[i].setModel(new DefaultComboBoxModel(serialPortNames));
							} else if (sensorCombo[i].getSelectedItem().toString().compareTo(sensorNames[0]) == 0) {
								sensorPortCombo[i].setModel(new DefaultComboBoxModel(new String[] {"N/A"}));
							} else {
								sensorPortCombo[i].setModel(new DefaultComboBoxModel(pinPortNames));
							}
						}
					}
				}
			});	
		}
	}

}
