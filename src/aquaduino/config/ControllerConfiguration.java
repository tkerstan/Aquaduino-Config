package aquaduino.config;

import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControllerConfiguration {
	
	public ControllerConfiguration(){
		
	}
	
	public static final int CONTROLLERS = 8;
	private String[] controllerNames = {"None", "Level", "Temperature", "ClockTimer"};
	
	private JComboBox controllerCombo[];
	
	public void initControllersPanel(JFrame parent, JPanel controllerPanel){
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

}
