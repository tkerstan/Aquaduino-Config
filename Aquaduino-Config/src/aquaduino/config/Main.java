package aquaduino.config;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.nio.channels.GatheringByteChannel;

import javax.swing.JTabbedPane;

public class Main {
	
	static final int ACTUATORS = 24;
	static final int CONTROLLERS = 8;
	static final int SENSORS = 8;
	
	public class Config{
		public int nrOfActuators = ACTUATORS;
		public int nrOfControllers = CONTROLLERS;
		public int nrOfSensors = SENSORS;
		public int actuators[] = new int[ACTUATORS];
		public int actuatorPorts[] = new int[ACTUATORS];
		public int controllers[] = new int [CONTROLLERS];
		public int controllerPorts[] = new int[ACTUATORS];
		public int sensors[] = new int[SENSORS];
		public int sensorPorts[] = new int[SENSORS];
		public byte macAddress[] = { 0,0,0,0,0,0 };
		public boolean dhcp = true;
		public byte ipAddress[] = { 0,0,0,0 };
		public byte netmask[] = { 0,0,0,0 };
		public byte defaultGW[] = { 0,0,0,0 };
		public boolean ntp = false;
		public byte ntpAddress[] = {0,0,0,0};
		public int syncInterval;
		public int gmtOffset;
	}
	
	Config configuration;
	
	private JFrame frame;
	private JComboBox actuatorCombo[];
	private JComboBox actuatorPortCombo[];
	private JComboBox controllerCombo[];
	private JComboBox sensorCombo[];
	private JComboBox sensorPortCombo[];
	private JTextField macTxtField;
	private JTextField ipTxtField;
	private JTextField netmaskTxtField;
	private JTextField defaultGWTxtField;
	private JSlider gmtSlider;
	private JCheckBox ntpCBox;
	private JCheckBox dhcpCBox;
	private JLabel lblGMTVal;
	private JTextField ntpServerTxtField;
	private JTextField ntpSyncIntvlTxtField;
	private JTextField xivelyAPIKeyTxtField;
	private JTextField xivelyFeedIdTxtField;
	private JCheckBox xivelyCBox;
	private JFileChooser fileChooser;
	
	private String[] actuatorNames = {"None", "DigitalOutput"};
	private String[] controllerNames = {"None", "Level", "Temperature", "ClockTimer"};
	private String[] sensorNames = {"None", "Level", "Temperature", "Atlas pH"};
	
	private String[] pinPortNames = { "Not assigned", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54"};
	private String[] serialPortNames = {"Not assigned", "Serial 0", "Serial 1", "Serial 2", "Serial 3"};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}
	
	/**
	 * 
	 */
	private void initNetworkPanel(JPanel networkPanel){
		networkPanel.setLayout(new GridLayout(0, 2, 0, 0));
		JPanel networkLblPanel = new JPanel();
		networkPanel.add(networkLblPanel);
		networkLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblMacAddress = new JLabel("MAC Address");
		networkLblPanel.add(lblMacAddress);
		
		JLabel lblDhcp = new JLabel("DHCP");
		networkLblPanel.add(lblDhcp);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		networkLblPanel.add(lblIpAddress);
		
		JLabel lblNetmask = new JLabel("Netmask");
		networkLblPanel.add(lblNetmask);
		
		JLabel lblDefaultGw = new JLabel("Default GW");
		networkLblPanel.add(lblDefaultGw);
		
		JPanel networkComboPanel = new JPanel();
		networkPanel.add(networkComboPanel);
		networkComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		macTxtField = new JTextField();
		networkComboPanel.add(macTxtField);
		macTxtField.setColumns(12);
		
		dhcpCBox = new JCheckBox("");
		dhcpCBox.setSelected(true);
		dhcpCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dhcpCBox.getSelectedObjects() == null)
				{
					ipTxtField.setEnabled(true);
					netmaskTxtField.setEnabled(true);
					defaultGWTxtField.setEnabled(true);
				} else
				{
					ipTxtField.setEnabled(false);
					netmaskTxtField.setEnabled(false);
					defaultGWTxtField.setEnabled(false);					
				}
			}
		});
		networkComboPanel.add(dhcpCBox);
		
		ipTxtField = new JTextField();
		ipTxtField.setEnabled(false);
		networkComboPanel.add(ipTxtField);
		ipTxtField.setColumns(15);
		
		netmaskTxtField = new JTextField();
		netmaskTxtField.setEnabled(false);
		networkComboPanel.add(netmaskTxtField);
		netmaskTxtField.setColumns(15);
		
		defaultGWTxtField = new JTextField();
		defaultGWTxtField.setEnabled(false);
		networkComboPanel.add(defaultGWTxtField);
		defaultGWTxtField.setColumns(15);		
	}
	
	/**
	 * 
	 */
	private void initNTPPanel(JPanel ntpPanel){
		ntpPanel.setLayout(new GridLayout(0, 2, 0, 0));
		JPanel ntpLblPanel = new JPanel();
		ntpPanel.add(ntpLblPanel);
		ntpLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNtp = new JLabel("NTP");
		ntpLblPanel.add(lblNtp);
		
		JLabel lblNtpServer = new JLabel("NTP Server");
		ntpLblPanel.add(lblNtpServer);
		
		JLabel lblSyncInterval = new JLabel("Sync Interval");
		ntpLblPanel.add(lblSyncInterval);
		
		JLabel lblGmt = new JLabel("GMT");
		ntpLblPanel.add(lblGmt);
		
		JLabel lblSlider = new JLabel("");
		ntpLblPanel.add(lblSlider);
		
		JPanel ntpComboPanel = new JPanel();
		ntpPanel.add(ntpComboPanel);
		ntpComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		ntpCBox = new JCheckBox("");
		ntpCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ntpCBox.getSelectedObjects() != null){
					ntpServerTxtField.setEnabled(true);
					ntpSyncIntvlTxtField.setEnabled(true);
					gmtSlider.setEnabled(true);
				} else {
					ntpServerTxtField.setEnabled(false);
					ntpSyncIntvlTxtField.setEnabled(false);
					gmtSlider.setEnabled(false);
				}
			}
		});
		ntpComboPanel.add(ntpCBox);
		
		ntpServerTxtField = new JTextField();
		ntpServerTxtField.setEnabled(false);
		ntpComboPanel.add(ntpServerTxtField);
		ntpServerTxtField.setColumns(10);
		
		ntpSyncIntvlTxtField = new JTextField();
		ntpSyncIntvlTxtField.setEnabled(false);
		ntpComboPanel.add(ntpSyncIntvlTxtField);
		ntpSyncIntvlTxtField.setColumns(10);
		
		gmtSlider = new JSlider();
		gmtSlider.setEnabled(false);
		gmtSlider.setSnapToTicks(true);
		gmtSlider.setMinorTickSpacing(1);
		gmtSlider.setMajorTickSpacing(1);
		gmtSlider.setValue(0);
		gmtSlider.setMinimum(-12);
		gmtSlider.setMaximum(12);
		ntpComboPanel.add(gmtSlider);
		
		lblGMTVal = new JLabel("0");
		lblGMTVal.setHorizontalAlignment(SwingConstants.CENTER);
		ntpComboPanel.add(lblGMTVal);				
		
		gmtSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblGMTVal.setText(String.valueOf(gmtSlider.getValue()));
			}
		});			
	}
	
	/**
	 * 
	 */
	private void initActuatorsPanel(JPanel actuatorPanel){
		JPanel actuatorLblPanel = new JPanel();
		JLabel lblActuator[] = new JLabel[ACTUATORS];
		actuatorCombo = new JComboBox[ACTUATORS];
		actuatorPortCombo = new JComboBox[ACTUATORS];
		JPanel actuatorComboPanel = new JPanel();
		JPanel actuatorComboPanel2 = new JPanel();
		
		actuatorPanel.setLayout(new GridLayout(0, 3, 0, 0));
		actuatorLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		actuatorComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		actuatorComboPanel2.setLayout(new GridLayout(0, 1, 0, 0));
		
		actuatorPanel.add(actuatorLblPanel);		
		actuatorPanel.add(actuatorComboPanel);
		actuatorPanel.add(actuatorComboPanel2);
		
		actuatorCombo = new JComboBox[ACTUATORS];
		for (int i = 0; i < ACTUATORS; i++){
			lblActuator[i] = new JLabel("Actuator "+ String.valueOf(i));
			actuatorCombo[i] = new JComboBox();
			actuatorPortCombo[i] = new JComboBox();
			actuatorCombo[i].setModel(new DefaultComboBoxModel(actuatorNames));
			actuatorPortCombo[i].setModel(new DefaultComboBoxModel(new String[] {"N/A"}));
			actuatorLblPanel.add(lblActuator[i]);
			actuatorComboPanel.add(actuatorCombo[i]);
			actuatorComboPanel2.add(actuatorPortCombo[i]);
			
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
	
	/**
	 * 
	 */
	private void initControllersPanel(JPanel controllerPanel){
		JLabel lblController[] = new JLabel[CONTROLLERS];
		JPanel controllerLblPanel = new JPanel();
		JPanel controllerComboPanel = new JPanel();
		
		controllerPanel.setLayout(new GridLayout(0, 2, 0, 0));
		controllerLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		controllerComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		controllerPanel.add(controllerLblPanel);
		controllerPanel.add(controllerComboPanel);	
		
		controllerCombo = new JComboBox[CONTROLLERS];
		for (int i = 0; i < CONTROLLERS; i++){
			lblController[i] = new JLabel("Controller " + String.valueOf(i));
			controllerCombo[i] = new JComboBox();
			controllerCombo[i].setModel(new DefaultComboBoxModel(controllerNames));
			controllerComboPanel.add(controllerCombo[i]);
			controllerLblPanel.add(lblController[i]);
		}		

	}
	
	/**
	 * 
	 */
	private void initSensorPanel(JPanel sensorPanel){
		sensorPanel.setLayout(new GridLayout(0, 3, 0, 0));
		JLabel lblSensor[] = new JLabel[SENSORS];
		JPanel sensorLblPanel = new JPanel();
		JPanel sensorComboPanel = new JPanel();
		JPanel sensorPortComboPanel = new JPanel();
		
		sensorCombo = new JComboBox[SENSORS];
		sensorPortCombo = new JComboBox[SENSORS];
		
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
							if (sensorCombo[i].getSelectedItem().toString().compareTo(sensorNames[3]) == 0){
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
	
	private void initXivelyPanel(JPanel xivelyPanel){
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		configuration = new Config();
		frame.setBounds(100, 100, 640, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fileChooser = new JFileChooser();
		
		JButton btnLoadConfig = new JButton("Load Config");
		btnLoadConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(frame.getContentPane()) == JFileChooser.APPROVE_OPTION)
				{
					loadConfig(fileChooser.getSelectedFile().getName());
				}
				
			}
		});
		btnLoadConfig.setBounds(410, 449, 110, 28);
		frame.getContentPane().add(btnLoadConfig);
		
		JButton btnSaveConfig = new JButton("Save Config");
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showSaveDialog(frame.getContentPane()) == JFileChooser.APPROVE_OPTION){
					if (fileChooser.getSelectedFile().exists()){
						if (JOptionPane.showConfirmDialog(frame,"Overwrite " + fileChooser.getSelectedFile() + "?","Overwrite File?", JOptionPane.YES_NO_OPTION) == 1)
							return;
					}
					saveConfig(fileChooser.getSelectedFile().getName());
				}
			}
		});
		btnSaveConfig.setBounds(524, 449, 110, 28);
		frame.getContentPane().add(btnSaveConfig);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(16, 6, 608, 450);
		frame.getContentPane().add(tabbedPane);
				
		JPanel networkPanel = new JPanel();
		JPanel ntpPanel = new JPanel();
		JPanel actuatorPanel = new JPanel();
		JPanel controllerPanel = new JPanel();
		JPanel sensorPanel = new JPanel();
		JPanel xivelyPanel = new JPanel();

		JPanel networkTopPanel = new JPanel();
		JPanel ntpTopPanel = new JPanel();
		JPanel xivelyTopPanel = new JPanel();

		networkTopPanel.setBounds(10, 6, 283, 159);
		ntpTopPanel.setBounds(10, 6, 283, 159);
		xivelyTopPanel.setBounds(10, 6, 283, 159);
		networkTopPanel.add(networkPanel);
		ntpTopPanel.add(ntpPanel);
		xivelyTopPanel.add(xivelyPanel);
		
		tabbedPane.addTab("Network", null, networkTopPanel, null);
		tabbedPane.addTab("NTP", null, ntpTopPanel, null);
		tabbedPane.addTab("Actuators", null, actuatorPanel, null);
		tabbedPane.addTab("Controllers", null, controllerPanel, null);
		tabbedPane.addTab("Sensors", null, sensorPanel, null);
		tabbedPane.addTab("Xively", null, xivelyTopPanel, null);
				
		initNetworkPanel(networkPanel);		
		initNTPPanel(ntpPanel);	
		initActuatorsPanel(actuatorPanel);
		initControllersPanel(controllerPanel);
		initSensorPanel(sensorPanel);
		initXivelyPanel(xivelyPanel);
	}
	
	private void saveConfig(String fileName){
		
		if (macTxtField.getText().matches("[0-9a-fA-F]{12}")){
			try {
				for (int i = 0; i < 6; i++){
					configuration.macAddress[i] = (byte) Integer.parseInt(macTxtField.getText().substring(i*2,i*2+2),16);
					System.out.println(configuration.macAddress[i]);
					
				}
			} catch (Exception e){
				JOptionPane.showMessageDialog(frame, "Invalid MAC Format");
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Invalid MAC Format");
		}
		if (dhcpCBox.getSelectedObjects() == null) {
			ipTxtField.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
			netmaskTxtField.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
			defaultGWTxtField.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		}
		
		if (ntpCBox.getSelectedObjects() != null){
			ntpServerTxtField.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		}
	}
	
	private void loadConfig(String fileName){
	}

}
