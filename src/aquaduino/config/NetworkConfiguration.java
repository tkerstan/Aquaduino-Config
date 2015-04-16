package aquaduino.config;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.javalobby.validate.IPValidator;
import org.javalobby.validate.IntegerValidator;
import org.javalobby.validate.MACValidator;

class NetworkConfigurationRAWType{
	byte[] mac = new byte [6];
	byte dhcp;
	byte[] ip = new byte[4];
	byte[] netmask = new byte[4];
	byte[] gw = new byte[4];
	byte[] dns = new byte[4];
	byte ntp;
	byte[] ntpServer = new byte[4];
	byte ntpSyncIntvl;
	byte gmt;
}

public class NetworkConfiguration {
	private JTextField macTxtField;
	private JTextField ipTxtField;
	private JTextField netmaskTxtField;
	private JTextField defaultGWTxtField;
	private JTextField dnsTxtField;
	private JSlider gmtSlider;
	private JCheckBox ntpCBox;
	private JCheckBox dhcpCBox;
	private JLabel lblGMTVal;
	private JTextField ntpServerTxtField;
	private JTextField ntpSyncIntvlTxtField;
	
	public void initNetworkPanel(JFrame parent, JPanel networkPanel){
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

		JLabel lblDns = new JLabel("DNS Server");
		networkLblPanel.add(lblDns);

		JPanel networkComboPanel = new JPanel();
		networkPanel.add(networkComboPanel);
		networkComboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		macTxtField = new JTextField();
		macTxtField.setInputVerifier(new MACValidator(parent, macTxtField, "Please enter a valid MAC address without separators."));
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
					dnsTxtField.setEnabled(true);
				} else
				{
					ipTxtField.setEnabled(false);
					netmaskTxtField.setEnabled(false);
					defaultGWTxtField.setEnabled(false);
					dnsTxtField.setEnabled(false);
				}
			}
		});
		networkComboPanel.add(dhcpCBox);
		
		ipTxtField = new JTextField();
		ipTxtField.setInputVerifier(new IPValidator(parent, ipTxtField, "Please enter a valid IP address", 0, 254));
		ipTxtField.setEnabled(false);
		networkComboPanel.add(ipTxtField);
		ipTxtField.setColumns(15);
		
		netmaskTxtField = new JTextField();
		netmaskTxtField.setInputVerifier(new IPValidator(parent, netmaskTxtField, "Please enter a valid IP Mask", 0, 255));
		netmaskTxtField.setEnabled(false);
		networkComboPanel.add(netmaskTxtField);
		netmaskTxtField.setColumns(15);
		
		defaultGWTxtField = new JTextField();
		defaultGWTxtField.setInputVerifier(new IPValidator(parent, defaultGWTxtField, "Please enter a valid IP address", 0 ,254));
		defaultGWTxtField.setEnabled(false);
		networkComboPanel.add(defaultGWTxtField);
		defaultGWTxtField.setColumns(15);

		dnsTxtField = new JTextField();
		dnsTxtField.setInputVerifier(new IPValidator(parent, dnsTxtField, "Please enter a valid IP address.", 0, 254));
		dnsTxtField.setEnabled(false);
		networkComboPanel.add(dnsTxtField);
		dnsTxtField.setColumns(15);

		JLabel lblNtp = new JLabel("NTP");
		networkLblPanel.add(lblNtp);
		
		JLabel lblNtpServer = new JLabel("NTP Server");
		networkLblPanel.add(lblNtpServer);
		
		JLabel lblSyncInterval = new JLabel("Sync Interval");
		networkLblPanel.add(lblSyncInterval);
		
		JLabel lblGmt = new JLabel("GMT");
		networkLblPanel.add(lblGmt);
		
		JLabel lblSlider = new JLabel("");
		networkLblPanel.add(lblSlider);
				
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
		networkComboPanel.add(ntpCBox);
		
		ntpServerTxtField = new JTextField();
		ntpServerTxtField.setInputVerifier(new IPValidator(parent, ntpServerTxtField, "Please enter a valid IP address.", 0, 254));
		ntpServerTxtField.setEnabled(false);
		networkComboPanel.add(ntpServerTxtField);
		ntpServerTxtField.setColumns(10);
		
		ntpSyncIntvlTxtField = new JTextField();
		ntpSyncIntvlTxtField.setInputVerifier(new IntegerValidator(parent, ntpSyncIntvlTxtField, "Please enter a valid snyc interval (0-65535) seconds.", 0, 65535));
		ntpSyncIntvlTxtField.setEnabled(false);
		networkComboPanel.add(ntpSyncIntvlTxtField);
		ntpSyncIntvlTxtField.setColumns(10);
		
		gmtSlider = new JSlider();
		gmtSlider.setEnabled(false);
		gmtSlider.setSnapToTicks(true);
		gmtSlider.setMinorTickSpacing(1);
		gmtSlider.setMajorTickSpacing(1);
		gmtSlider.setValue(0);
		gmtSlider.setMinimum(-12);
		gmtSlider.setMaximum(12);
		networkComboPanel.add(gmtSlider);
		
		lblGMTVal = new JLabel("0");
		lblGMTVal.setHorizontalAlignment(SwingConstants.CENTER);
		networkComboPanel.add(lblGMTVal);				
		
		gmtSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblGMTVal.setText(String.valueOf(gmtSlider.getValue()));
			}
		});

	}
	
	public int saveConfig(String filename) throws IOException{
		NetworkConfigurationRAWType raw = new NetworkConfigurationRAWType();
		
		boolean status = true;
		
		status = status & macTxtField.getInputVerifier().verify(macTxtField);
		if (!dhcpCBox.isSelected()){
			status = status & ipTxtField.getInputVerifier().verify(ipTxtField);
			status = status & netmaskTxtField.getInputVerifier().verify(netmaskTxtField);
			status = status & defaultGWTxtField.getInputVerifier().verify(defaultGWTxtField);
			status = status & dnsTxtField.getInputVerifier().verify(dnsTxtField);
		}
		if (ntpCBox.isSelected()){
			status = status & ntpServerTxtField.getInputVerifier().verify(ntpServerTxtField);
			status = status & ntpSyncIntvlTxtField.getInputVerifier().verify(ntpSyncIntvlTxtField);
		}
		
		System.out.println(filename);
		System.out.println("Config is valid: "+status);
			
		FileOutputStream f = new FileOutputStream(filename);
		
		byte[] tmp;
		InetAddress ipAddress;
		
		tmp = new BigInteger(macTxtField.getText(),16).toByteArray();
		System.arraycopy(tmp, 1, raw.mac, 0, tmp.length-1);
		
		raw.dhcp = dhcpCBox.isSelected() ? (byte) 1 : (byte) 0;

		tmp = ipTxtField.getText().getBytes();
		ipAddress = Inet4Address.getByName(ipTxtField.getText());
		System.arraycopy(ipAddress.getAddress(), 0, raw.ip, 0, ipAddress.getAddress().length);

		tmp = netmaskTxtField.getText().getBytes();
		ipAddress = Inet4Address.getByName(netmaskTxtField.getText());
		System.arraycopy(ipAddress.getAddress(), 0, raw.netmask, 0, ipAddress.getAddress().length);

		tmp = defaultGWTxtField.getText().getBytes();
		ipAddress = Inet4Address.getByName(defaultGWTxtField.getText());
		System.arraycopy(ipAddress.getAddress(), 0, raw.gw, 0, ipAddress.getAddress().length);

		tmp = dnsTxtField.getText().getBytes();
		ipAddress = Inet4Address.getByName(dnsTxtField.getText());
		System.arraycopy(ipAddress.getAddress(), 0, raw.dns, 0, ipAddress.getAddress().length);
		
		raw.ntp = ntpCBox.isSelected() ? (byte) 1 : (byte) 0;

		tmp = ntpServerTxtField.getText().getBytes();
		ipAddress = Inet4Address.getByName(dnsTxtField.getText());
		System.arraycopy(ipAddress.getAddress(), 0, raw.ntpServer, 0, ipAddress.getAddress().length);
		
		raw.ntpSyncIntvl = (byte) Integer.parseInt("0" + ntpSyncIntvlTxtField.getText());
		
		raw.gmt = (byte) gmtSlider.getValue();
		
		f.write(raw.mac);
		f.write(raw.dhcp);
		f.write(raw.ip);
		f.write(raw.netmask);
		f.write(raw.gw);
		f.write(raw.dns);
		f.write(raw.ntp);
		f.write(raw.ntpServer);
		f.write(raw.ntpSyncIntvl);
		f.write(raw.gmt);
		
		f.close();
		return 0;
	}

}
