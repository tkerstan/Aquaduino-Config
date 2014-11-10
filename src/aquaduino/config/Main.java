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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTabbedPane;

public class Main {
	
	static final int ACTUATORS = 24;
	static final int CONTROLLERS = 8;
	static final int SENSORS = 8;
	static final int XIVELY_FEED_NAME_LENGTH = 21;
	static final int XIVELY_API_KEY_LENGTH = 51;
	static final int XIVELY_CHANNEL_NAME_LENGTH = 20;
	static final int AQUADUINO_STRING_LENGTH = 20;
	
	public class Config{
		public byte aquaduinoStringLength = AQUADUINO_STRING_LENGTH;
		public byte xivelyFeedNameLength = XIVELY_FEED_NAME_LENGTH;
		public byte xivelyApiKeyLength = XIVELY_API_KEY_LENGTH;
		public byte xivelyChannelNameLength = XIVELY_CHANNEL_NAME_LENGTH;
		public byte nrOfActuators = ACTUATORS;
		public byte nrOfControllers = CONTROLLERS;
		public byte nrOfSensors = SENSORS;
		public byte[][] actuatorName = new byte[ACTUATORS][AQUADUINO_STRING_LENGTH];
		public byte actuators[] = new byte[ACTUATORS];
		public byte actuatorPorts[] = new byte[ACTUATORS];
		public byte actuatorOnValues[] = new byte[ACTUATORS];
		public byte[][] controllerName = new byte[CONTROLLERS][AQUADUINO_STRING_LENGTH];
		public byte controllers[] = new byte[CONTROLLERS];
		public byte[][] sensorName = new byte[SENSORS][AQUADUINO_STRING_LENGTH];
		public byte sensors[] = new byte[SENSORS];
		public byte sensorPorts[] = new byte[SENSORS];
		public byte[][] sensorXivelyChannel = new byte[SENSORS][XIVELY_CHANNEL_NAME_LENGTH];
		public byte macAddress[] = { 0,0,0,0,0,0 };
		public byte dhcp = 1;
		public byte ipAddress[] = { 0,0,0,0 };
		public byte netmask[] = { 0,0,0,0 };
		public byte defaultGW[] = { 0,0,0,0 };
		public byte ntp = 0;
		public byte ntpAddress[] = {0,0,0,0};
		public byte syncInterval;
		public byte gmtOffset;
		public byte xively = 0;
		public byte[] xivelyAPIKey = new byte[XIVELY_API_KEY_LENGTH];
		public byte[] xivelyFeedName = new byte[XIVELY_FEED_NAME_LENGTH];
	}
	
	private JFrame frame;
	private JTextField actuatorNameTxtField[];
	private JComboBox actuatorCombo[];
	private JComboBox actuatorPortCombo[];
	private JComboBox actuatorOnValueCombo[];
	private JTextField controllerNameTxtField[];
	private JComboBox controllerCombo[];
	private JTextField sensorNameTxtField[];
	private JComboBox sensorCombo[];
	private JComboBox sensorPortCombo[];
	private JTextField sensorXivelyChannelTxtField[];
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
	private String[] actuatorOnValues = {"0", "1"};
	private String[] controllerNames = {"None", "Level", "Temperature", "ClockTimer"};
	private String[] sensorNames = {"None", "Digital Input", "Temperature", "Atlas pH", "Atlas EC", "Atlas ORP"};
	
	private String[] pinPortNames = { "Not assigned", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54"};
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
		JPanel actuatorNamePanel = new JPanel();
		actuatorNameTxtField = new JTextField[ACTUATORS];
		actuatorCombo = new JComboBox[ACTUATORS];
		actuatorPortCombo = new JComboBox[ACTUATORS];
		actuatorOnValueCombo = new JComboBox[ACTUATORS];
		JPanel actuatorComboPanel = new JPanel();
		JPanel actuatorComboPanel2 = new JPanel();
		JPanel actuatorComboPanel3 = new JPanel();
		
		actuatorPanel.setLayout(new GridLayout(0, 5, 0, 0));
		actuatorLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		actuatorNamePanel.setLayout(new GridLayout(0, 1, 0, 0));
		actuatorComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		actuatorComboPanel2.setLayout(new GridLayout(0, 1, 0, 0));
		actuatorComboPanel3.setLayout(new GridLayout(0, 1, 0, 0));
		
		actuatorPanel.add(actuatorLblPanel);
		actuatorPanel.add(actuatorNamePanel);
		actuatorPanel.add(actuatorComboPanel);
		actuatorPanel.add(actuatorComboPanel2);
		actuatorPanel.add(actuatorComboPanel3);
		
		actuatorCombo = new JComboBox[ACTUATORS];
		for (int i = 0; i < ACTUATORS; i++){
			lblActuator[i] = new JLabel("Actuator "+ String.valueOf(i));
			actuatorNameTxtField[i] = new JTextField(AQUADUINO_STRING_LENGTH);
			actuatorNameTxtField[i].setText("Name " + String.valueOf(i));
			actuatorCombo[i] = new JComboBox();
			actuatorPortCombo[i] = new JComboBox();
			actuatorOnValueCombo[i] = new JComboBox();
			actuatorCombo[i].setModel(new DefaultComboBoxModel(actuatorNames));
			actuatorPortCombo[i].setModel(new DefaultComboBoxModel(new String[] {"N/A"}));
			actuatorOnValueCombo[i].setModel(new DefaultComboBoxModel(actuatorOnValues));
			actuatorLblPanel.add(lblActuator[i]);
			actuatorNamePanel.add(actuatorNameTxtField[i]);
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
	
	/**
	 * 
	 */
	private void initControllersPanel(JPanel controllerPanel){
		JLabel lblController[] = new JLabel[CONTROLLERS];
		JPanel controllerLblPanel = new JPanel();
		JPanel controllerNamePanel = new JPanel();
		JPanel controllerComboPanel = new JPanel();
		controllerNameTxtField = new JTextField[CONTROLLERS];
		
		controllerPanel.setLayout(new GridLayout(0, 3, 0, 0));
		controllerNamePanel.setLayout(new GridLayout(0, 1, 0, 0));
		controllerLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		controllerComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		controllerPanel.add(controllerLblPanel);
		controllerPanel.add(controllerNamePanel);
		controllerPanel.add(controllerComboPanel);	
		
		controllerCombo = new JComboBox[CONTROLLERS];
		for (int i = 0; i < CONTROLLERS; i++){
			lblController[i] = new JLabel("Controller " + String.valueOf(i));
			controllerNameTxtField[i] = new JTextField(AQUADUINO_STRING_LENGTH);
			controllerNameTxtField[i].setText("Name "+String.valueOf(i));
			controllerCombo[i] = new JComboBox();
			controllerCombo[i].setModel(new DefaultComboBoxModel(controllerNames));
			controllerComboPanel.add(controllerCombo[i]);
			controllerLblPanel.add(lblController[i]);
			controllerNamePanel.add(controllerNameTxtField[i]);
		}		

	}
	
	/**
	 * 
	 */
	private void initSensorPanel(JPanel sensorPanel){
		JLabel lblSensor[] = new JLabel[SENSORS];
		JPanel sensorLblPanel = new JPanel();
		JPanel sensorComboPanel = new JPanel();
		JPanel sensorPortComboPanel = new JPanel();
		JPanel sensorNamePanel = new JPanel();
		JPanel sensorXivelyChannelPanel = new JPanel();
		sensorNameTxtField = new JTextField[SENSORS];
		sensorXivelyChannelTxtField = new JTextField[SENSORS];
		
		sensorCombo = new JComboBox[SENSORS];
		sensorPortCombo = new JComboBox[SENSORS];
		
		sensorPanel.setLayout(new GridLayout(0, 5, 0, 0));
		sensorLblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		sensorNamePanel.setLayout(new GridLayout(0, 1, 0, 0));
		sensorComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		sensorPortComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		sensorXivelyChannelPanel.setLayout(new GridLayout(0, 1, 0, 0));
	
		sensorPanel.add(sensorLblPanel);
		sensorPanel.add(sensorNamePanel);
		sensorPanel.add(sensorComboPanel);
		sensorPanel.add(sensorPortComboPanel);
		sensorPanel.add(sensorXivelyChannelPanel);
		
		for (int i = 0; i < SENSORS; i++){
			lblSensor[i] = new JLabel("Sensor "+ String.valueOf(i));
			sensorNameTxtField[i] = new JTextField(AQUADUINO_STRING_LENGTH);
			sensorNameTxtField[i].setText("Name "+String.valueOf(i));
			sensorXivelyChannelTxtField[i] = new JTextField(XIVELY_CHANNEL_NAME_LENGTH);
			sensorXivelyChannelTxtField[i].setText("Channel "+String.valueOf(i));
			sensorCombo[i] = new JComboBox();
			sensorCombo[i].setModel(new DefaultComboBoxModel(sensorNames));
			sensorPortCombo[i] = new JComboBox();
			sensorPortCombo[i].setModel(new DefaultComboBoxModel(new String[] {"N/A"}));
			sensorLblPanel.add(lblSensor[i]);
			sensorNamePanel.add(sensorNameTxtField[i]);
			sensorComboPanel.add(sensorCombo[i]);	
			sensorPortComboPanel.add(sensorPortCombo[i]);
			sensorXivelyChannelPanel.add(sensorXivelyChannelTxtField[i]);
			
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
		frame.setBounds(100, 100, 660, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fileChooser = new JFileChooser();
		
		JButton btnLoadConfig = new JButton("Load Config");
		btnLoadConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(frame.getContentPane()) == JFileChooser.APPROVE_OPTION)
				{
					loadConfig(fileChooser.getSelectedFile().getAbsolutePath());
				}
				
			}
		});
		btnLoadConfig.setBounds(384, 559, 110, 28);
		frame.getContentPane().add(btnLoadConfig);
		
		JButton btnSaveConfig = new JButton("Save Config");
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showSaveDialog(frame.getContentPane()) == JFileChooser.APPROVE_OPTION){
					if (fileChooser.getSelectedFile().exists()){
						if (JOptionPane.showConfirmDialog(frame,"Overwrite " + fileChooser.getSelectedFile() + "?","Overwrite File?", JOptionPane.YES_NO_OPTION) == 1)
							return;
					}
					saveConfig(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btnSaveConfig.setBounds(498, 559, 110, 28);
		frame.getContentPane().add(btnSaveConfig);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(16, 6, 608, 550);
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
	
	private boolean txtFieldToIp(JTextField txtField, byte[] ip){
		String[] ipElements = txtField.getText().split("\\.");
		if (ipElements.length != 4)
			return false;
		for (int i = 0; i < ipElements.length; i++){
			int tmp = Integer.parseInt(ipElements[i]);
			if (tmp >= 0 && tmp < 256){
				ip[i] = (byte) tmp;
			}
			else
			{
				return false;
			}
		}
		return true;
	}
	
	private void printConfig(Config c){
		System.out.println("String Length: " + c.aquaduinoStringLength);
		System.out.println("Xively Feed Name Length " + c.xivelyFeedNameLength);
		System.out.println("Xively API Key Length " + c.xivelyApiKeyLength);
		System.out.println("Xively Channel Name Length " + c.xivelyChannelNameLength);
		System.out.println("# of Actuators " + c.nrOfActuators);
		System.out.println("# of Controllers " + c.nrOfControllers);
		System.out.println("# of Sensors " + c.nrOfSensors);
		
		System.out.print("MAC Address: ");
		for (int i = 0; i < c.macAddress.length; i++){
				System.out.printf("%x",c.macAddress[i]);
		}
		System.out.println();
		System.out.println("DHCP: "+c.dhcp);
		System.out.println("IP Address: "+c.ipAddress[0]+"."+c.ipAddress[1]+"."+c.ipAddress[2]+"."+c.ipAddress[3]);
		System.out.println("Netmask: "+c.netmask[0]+"."+c.netmask[1]+"."+c.netmask[2]+"."+c.netmask[3]);
		System.out.println("Default GW: "+c.defaultGW[0]+"."+c.defaultGW[1]+"."+c.defaultGW[2]+"."+c.defaultGW[3]);
		System.out.println("NTP: "+c.ntp);
		System.out.println("NTP Server: "+c.ntpAddress[0]+"."+c.ntpAddress[1]+"."+c.ntpAddress[2]+"."+c.ntpAddress[3]);
		System.out.println("Sync Interval: "+c.syncInterval);
		System.out.println("GMT Offset: "+c.gmtOffset);
		System.out.println("Xively: "+c.xively);
		System.out.print("Xively API Key: ");
		for (int i = 0; i < c.xivelyApiKeyLength; i++){
			System.out.print(i+"("+(char)c.xivelyAPIKey[i]+")");
		}
		System.out.println();
		System.out.print("Xively Feed ID: ");
		for (int i = 0; i < c.xivelyFeedNameLength; i++){
			System.out.print(i+"("+(char)c.xivelyFeedName[i]+")");
		}
		System.out.println();
		
		for (int i = 0; i < c.nrOfActuators; i++){
			System.out.print("Actuator " + i + " = "+c.actuators[i]+": ");
			for (int j = 0; j < c.aquaduinoStringLength; j++){
				System.out.print(j+"("+(char)c.actuatorName[i][j]+")");
			}
			System.out.print(" @ Port: ");
			System.out.println(c.actuatorPorts[i]);
			System.out.println(" On @ ");
			System.out.println(c.actuatorOnValues[i]);
		}
		
		for (int i = 0; i < c.nrOfControllers; i++){
			System.out.print("Controller " + i + " = "+c.controllers[i]+": ");
			for (int j = 0; j < c.aquaduinoStringLength; j++){
				System.out.print(j+"("+(char)c.controllerName[i][j]+")");
			}
			System.out.println();
		}
		
		for (int i = 0; i < c.nrOfSensors; i++){
			System.out.print("Sensor " + i + " = " +c.sensors[i]+": ");
			for (int j = 0; j < c.aquaduinoStringLength; j++){
				System.out.print(j+"("+(char)c.sensorName[i][j]+")");
			}
			System.out.print(" @ Port: ");
			System.out.print(c.sensorPorts[i]);
			System.out.print(" @ Channel: ");
			for (int j = 0; j < c.xivelyChannelNameLength; j++){
				System.out.print(j+"("+(char)c.sensorXivelyChannel[i][j]+")");
			}
			System.out.println();
		}
		
	}
	
	private boolean readConfigFromFile(String fileName, Config c){
		try{
			FileInputStream f = new FileInputStream(fileName);
			c.aquaduinoStringLength = (byte) f.read();
			c.xivelyFeedNameLength = (byte) f.read();
			c.xivelyApiKeyLength = (byte) f.read();
			c.xivelyChannelNameLength = (byte) f.read();
			c.nrOfActuators = (byte) f.read();
			c.nrOfControllers = (byte) f.read();
			c.nrOfSensors = (byte) f.read();
			for (int i=0; i<c.nrOfActuators; i++){
				f.read(c.actuatorName[i]);
				actuatorNameTxtField[i].setText(new String(c.actuatorName[i]).trim());
				c.actuators[i] = (byte) f.read();
				actuatorCombo[i].setSelectedIndex(c.actuators[i]);
				c.actuatorPorts[i] = (byte) f.read();
				actuatorPortCombo[i].setSelectedItem(Byte.toString(c.actuatorPorts[i]));
				c.actuatorOnValues[i] = (byte) f.read();
				actuatorOnValueCombo[i].setSelectedIndex(c.actuatorOnValues[i]);
			}
			for (int i=0; i<c.nrOfControllers; i++){
				f.read(c.controllerName[i]);
				controllerNameTxtField[i].setText(new String(c.controllerName[i]).trim());
				c.controllers[i] = (byte) f.read();
				controllerCombo[i].setSelectedIndex(c.controllers[i]);
			}
			for (int i=0; i<c.nrOfSensors; i++){
				f.read(c.sensorName[i]);
				sensorNameTxtField[i].setText(new String(c.sensorName[i]).trim());
				c.sensors[i] = (byte) f.read();
				sensorCombo[i].setSelectedIndex(c.sensors[i]);
				c.sensorPorts[i] = (byte) f.read();
				sensorPortCombo[i].setSelectedIndex(c.sensorPorts[i]);
				f.read(c.sensorXivelyChannel[i]);
				sensorXivelyChannelTxtField[i].setText(new String(c.sensorXivelyChannel[i]).trim());
			}
			String tmp = "";
			f.read(c.macAddress);
			for (int i = 0; i < 6; i++){
				tmp += Integer.toHexString((c.macAddress[i] & 0xf0) >> 4);
				tmp += Integer.toHexString(c.macAddress[i] & 0x0f);
			}
			macTxtField.setText(tmp);
			c.dhcp = (byte) f.read();
			if (c.dhcp == 1){
				dhcpCBox.setSelected(true);
				ipTxtField.setEnabled(false);
				netmaskTxtField.setEnabled(false);
				defaultGWTxtField.setEnabled(false);
			} else {
				dhcpCBox.setSelected(false);
				ipTxtField.setEnabled(true);
				netmaskTxtField.setEnabled(true);
				defaultGWTxtField.setEnabled(true);				
			}
			f.read(c.ipAddress);
			tmp = "";
			for (int i = 0; i < 4; i++){
				tmp += ((int) c.ipAddress[i]) & 0xff;
				if (i < 3)
					tmp+=".";
			}
			ipTxtField.setText(tmp);
			f.read(c.netmask);
			tmp = "";
			for (int i = 0; i < 4; i++){
				tmp += ((int) c.netmask[i]) & 0xff;
				if (i < 3)
					tmp+=".";
			}
			netmaskTxtField.setText(tmp);			
			f.read(c.defaultGW);
			tmp = "";
			for (int i = 0; i < 4; i++){
				tmp += ((int) c.defaultGW[i]) & 0xff;
				if (i < 3)
					tmp+=".";
			}
			defaultGWTxtField.setText(tmp);
			c.ntp = (byte) f.read();
			if (c.ntp == 1){
				ntpCBox.setSelected(true);
				ntpServerTxtField.setEnabled(true);
				ntpSyncIntvlTxtField.setEnabled(true);
			} else {
				ntpCBox.setSelected(false);
				ntpServerTxtField.setEnabled(false);
				ntpSyncIntvlTxtField.setEnabled(false);				
			}
			f.read(c.ntpAddress);
			tmp = "";
			for (int i = 0; i < 4; i++){
				tmp += ((int) c.ntpAddress[i]) & 0xff;
				if (i < 3)
					tmp+=".";
			}
			ntpServerTxtField.setText(tmp);			
			c.syncInterval = (byte) f.read();
			ntpSyncIntvlTxtField.setText(Byte.toString(c.syncInterval));
			c.gmtOffset = (byte) f.read();
			gmtSlider.setValue(c.gmtOffset);
			c.xively = (byte) f.read();
			if (c.xively == 1){
				xivelyCBox.setSelected(true);
				xivelyAPIKeyTxtField.setEnabled(true);
				xivelyFeedIdTxtField.setEnabled(true);
			} else {
				xivelyCBox.setSelected(false);
				xivelyAPIKeyTxtField.setEnabled(false);
				xivelyFeedIdTxtField.setEnabled(false);				
			}
			f.read(c.xivelyAPIKey);
			xivelyAPIKeyTxtField.setText(new String(c.xivelyAPIKey).trim());
			f.read(c.xivelyFeedName);
			xivelyFeedIdTxtField.setText(new String(c.xivelyFeedName).trim());
			f.close();
			
		}catch (IOException e){
			return false;
		}
		return true;
	}

	
	private boolean writeConfigToFile(String fileName, Config c){
		try{
			System.out.println("Writing file "+fileName);
			FileOutputStream f = new FileOutputStream(fileName);
			f.write(c.aquaduinoStringLength);
			f.write(c.xivelyFeedNameLength);
			f.write(c.xivelyApiKeyLength);
			f.write(c.xivelyChannelNameLength);
			f.write(c.nrOfActuators);
			f.write(c.nrOfControllers);
			f.write(c.nrOfSensors);
			for (int i=0; i<c.nrOfActuators; i++){
				f.write(c.actuatorName[i]);
				f.write(c.actuators[i]);
				f.write(c.actuatorPorts[i]);
				f.write(c.actuatorOnValues[i]);
			}
			for (int i=0; i<c.nrOfControllers; i++){
				f.write(c.controllerName[i]);
				f.write(c.controllers[i]);
			}
			for (int i=0; i<c.nrOfSensors; i++){
				f.write(c.sensorName[i]);
				f.write(c.sensors[i]);
				f.write(c.sensorPorts[i]);
				f.write(c.sensorXivelyChannel[i]);
			}
			f.write(c.macAddress);
			f.write(c.dhcp);
			f.write(c.ipAddress);
			f.write(c.netmask);
			f.write(c.defaultGW);
			f.write(c.ntp);
			f.write(c.ntpAddress);
			f.write(c.syncInterval);
			f.write(c.gmtOffset);
			f.write(c.xively);
			f.write(c.xivelyAPIKey);
			f.write(c.xivelyFeedName);
			f.close();
			
		}catch (IOException e){
			return false;
		}
		return true;
	}
	
	private boolean checkConfig(Config c){
		if (macTxtField.getText().matches("[0-9a-fA-F]{12}")){
			for (int i = 0; i < 6; i++){
				c.macAddress[i] = (byte) Integer.parseInt(macTxtField.getText().substring(i*2,i*2+2),16);
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Invalid MAC Format");
			return false;
		}
		
		c.dhcp = 1;
		
		if (dhcpCBox.getSelectedObjects() == null) {
			c.dhcp = 0;
			if (ipTxtField.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
				if (!txtFieldToIp(ipTxtField, c.ipAddress)){
					JOptionPane.showMessageDialog(frame, "Invalid IP");
					return false;
				}
			}else{
				JOptionPane.showMessageDialog(frame, "Invalid IP");
				return false;
			}
			
			if (netmaskTxtField.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
				if (!txtFieldToIp(netmaskTxtField, c.netmask)){
					JOptionPane.showMessageDialog(frame, "Invalid Netmask");
					return false;
				}
			}else{
				JOptionPane.showMessageDialog(frame, "Invalid Netmask");
				return false;
			}
			
			if (defaultGWTxtField.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
				if (!txtFieldToIp(defaultGWTxtField, c.defaultGW)){
					JOptionPane.showMessageDialog(frame, "Invalid Gateway");
					return false;
				}
			}else{
				JOptionPane.showMessageDialog(frame, "Invalid Gateway");
				return false;
			}
		}
		
		c.ntp = 0;
		
		if (ntpCBox.getSelectedObjects() != null){
			c.ntp = 1;
			if (ntpServerTxtField.getText().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
				if (!txtFieldToIp(ntpServerTxtField, c.ntpAddress)){
					JOptionPane.showMessageDialog(frame, "Invalid NTP Server");
					return false;
				}
			}else{
				JOptionPane.showMessageDialog(frame, "Invalid NTP Server");
				return false;
			}
			try {
			c.syncInterval = Byte.parseByte(ntpSyncIntvlTxtField.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "Invalid NTP Syncinterval");
				return false;
			}
			c.gmtOffset = (byte) gmtSlider.getValue();
		}
		
		c.xively = 0;
		
		if (xivelyCBox.getSelectedObjects() != null){
			c.xively = 1;
			if (xivelyAPIKeyTxtField.getText().length() <= XIVELY_API_KEY_LENGTH){
				for (int j = 0; j < XIVELY_API_KEY_LENGTH; j++){
					if (j < xivelyAPIKeyTxtField.getText().length()){
						c.xivelyAPIKey[j] = (byte) xivelyAPIKeyTxtField.getText().charAt(j);
					}
					else {
						c.xivelyAPIKey[j] = 0;
					}
				}
			}else{
				JOptionPane.showMessageDialog(frame, "Xively API Key too long! " + xivelyAPIKeyTxtField.getText().length() + "(" + xivelyAPIKeyTxtField.getText() + ")");
				return false;
			}
			if(xivelyFeedIdTxtField.getText().length() <= XIVELY_FEED_NAME_LENGTH){
				for (int j = 0; j < XIVELY_FEED_NAME_LENGTH; j++){
					if (j < xivelyFeedIdTxtField.getText().length()){
						c.xivelyFeedName[j] = (byte) xivelyFeedIdTxtField.getText().charAt(j);
					} else {
						c.xivelyFeedName[j] = 0;
					}
				}
			}else{
				JOptionPane.showMessageDialog(frame, "Xively Feed ID too long! " + xivelyFeedIdTxtField.getText().length());
				return false;
			}
		}
		
		for (int i = 0; i < ACTUATORS; i++){
			if (actuatorNameTxtField[i].getText().length() <= AQUADUINO_STRING_LENGTH){
				for (int j = 0; j < AQUADUINO_STRING_LENGTH; j++){
					if (j < actuatorNameTxtField[i].getText().length()){
						c.actuatorName[i][j] = (byte) actuatorNameTxtField[i].getText().charAt(j);
						System.out.print(Byte.toString(c.actuatorName[i][j]));
					} else {
						c.actuatorName[i][j] = 0;
					}
				}
				System.out.println();
			} else {
				JOptionPane.showMessageDialog(frame, "Actuator Name " + i + " too long!");
				return false;
			}
			c.actuators[i] = (byte) actuatorCombo[i].getSelectedIndex();
			try{
				c.actuatorPorts[i] = (byte) Byte.parseByte(actuatorPortCombo[i].getSelectedItem().toString());
			} catch (Exception e){
				c.actuatorPorts[i] = 0;
			}
			c.actuatorOnValues[i] = (byte) actuatorOnValueCombo[i].getSelectedIndex();
		}
		
		for (int i = 0; i < CONTROLLERS; i++){
			if (controllerNameTxtField[i].getText().length() <= AQUADUINO_STRING_LENGTH){
				for (int j = 0; j < AQUADUINO_STRING_LENGTH; j++){
					if (j < controllerNameTxtField[i].getText().length()){
						c.controllerName[i][j] = (byte) controllerNameTxtField[i].getText().charAt(j);
					} else {
						c.controllerName[i][j] = 0;
					}
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Controller Name " + i + " too long!");
				return false;
			}
			c.controllers[i] = (byte) controllerCombo[i].getSelectedIndex();
		}
		
		for (int i = 0; i < SENSORS; i++){
			if (sensorNameTxtField[i].getText().length() <= AQUADUINO_STRING_LENGTH){
				for (int j = 0; j < AQUADUINO_STRING_LENGTH; j++){
					if (j < sensorNameTxtField[i].getText().length()){
						c.sensorName[i][j] = (byte) sensorNameTxtField[i].getText().charAt(j);
					} else {
						c.sensorName[i][j] = 0;
					}
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Sensor Name" + i +" too long!");
				return false;
			}
			if (sensorXivelyChannelTxtField[i].getText().length() <= XIVELY_CHANNEL_NAME_LENGTH){
				for (int j = 0; j < XIVELY_CHANNEL_NAME_LENGTH; j++){
					if (j < sensorXivelyChannelTxtField[i].getText().length()){
						c.sensorXivelyChannel[i][j] = (byte) sensorXivelyChannelTxtField[i].getText().charAt(j);
					} else {
						c.sensorXivelyChannel[i][j] = 0;
					}
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Xively Channel Name" + i + " too long!");
			}
			c.sensors[i] = (byte) sensorCombo[i].getSelectedIndex();
			c.sensorPorts[i] = (byte) sensorPortCombo[i].getSelectedIndex();
		}
		return true;
	}
	
	private void saveConfig(String fileName){
		Config configuration = new Config();
		if (checkConfig(configuration)){
			//printConfig(configuration);
			writeConfigToFile(fileName, configuration);
		} else {
			
		}
	}
	
	private void loadConfig(String fileName){
		Config configuration = new Config();
		readConfigFromFile(fileName, configuration);
		//printConfig(configuration);
	}

}
