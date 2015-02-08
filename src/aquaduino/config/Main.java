package aquaduino.config;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;

public class Main {
	private ActuatorConfiguration actuatorConfig;
	private ControllerConfiguration controllerConfig;
	private NetworkConfiguration networkConfig;
	private SensorConfiguration sensorConfig;
	private XivelyConfiguration xivelyConfig;
	private JFrame frame;
	private JFileChooser fileChooser;
		
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
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setDialogTitle("Aquaduino configuration directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		JButton btnLoadConfig = new JButton("Load Config");
		btnLoadConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(frame.getContentPane()) == JFileChooser.APPROVE_OPTION)
				{
					//loadConfig(fileChooser.getSelectedFile().getAbsolutePath());
				}
				
			}
		});
		btnLoadConfig.setBounds(384, 559, 110, 28);
		frame.getContentPane().add(btnLoadConfig);
		
		JButton btnSaveConfig = new JButton("Save Config");
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showSaveDialog(frame.getContentPane()) == JFileChooser.APPROVE_OPTION){
//					if (fileChooser.getSelectedFile().exists()){
//						if (JOptionPane.showConfirmDialog(frame,"Overwrite " + fileChooser.getSelectedFile() + "?","Overwrite File?", JOptionPane.YES_NO_OPTION) == 1)
//							return;
//					}
					try{
						networkConfig.saveConfig(fileChooser.getSelectedFile().getPath()+"/net.cfg");
					} catch (IOException e)
					{
						
					}
				}
			}
		});
		btnSaveConfig.setBounds(498, 559, 110, 28);
		frame.getContentPane().add(btnSaveConfig);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(16, 6, 608, 550);
		frame.getContentPane().add(tabbedPane);
				
		JPanel networkPanel = new JPanel();
		JPanel actuatorPanel = new JPanel();
		JPanel controllerPanel = new JPanel();
		JPanel sensorPanel = new JPanel();
		JPanel xivelyPanel = new JPanel();

		JPanel networkTopPanel = new JPanel();
		JPanel xivelyTopPanel = new JPanel();
		
		networkConfig = new NetworkConfiguration();
		actuatorConfig = new ActuatorConfiguration();
		controllerConfig = new ControllerConfiguration();
		sensorConfig = new SensorConfiguration();
		xivelyConfig = new XivelyConfiguration();

		networkTopPanel.setBounds(10, 6, 283, 159);
		xivelyTopPanel.setBounds(10, 6, 283, 159);
		networkTopPanel.add(networkPanel);
		xivelyTopPanel.add(xivelyPanel);
		
		tabbedPane.addTab("Network", null, networkTopPanel, null);
		tabbedPane.addTab("Actuators", null, actuatorPanel, null);
		tabbedPane.addTab("Controllers", null, controllerPanel, null);
		tabbedPane.addTab("Sensors", null, sensorPanel, null);
		tabbedPane.addTab("Xively", null, xivelyTopPanel, null);
				
		networkConfig.initNetworkPanel(frame, networkPanel);		
		actuatorConfig.initActuatorsPanel(frame, actuatorPanel);
		controllerConfig.initControllersPanel(frame, controllerPanel);
		sensorConfig.initSensorPanel(frame, sensorPanel);
		xivelyConfig.initXivelyPanel(frame, xivelyPanel);
	}
	
}
