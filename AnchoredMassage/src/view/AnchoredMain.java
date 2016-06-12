package view;

import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.jhlabs.awt.ParagraphLayout;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Kicks off the GUI in a new thread.
 */
public class AnchoredMain {

	/**
	 * Database Connection
	 */
	private static Connection DATA_CONN;
	/**
	 * Sql server instance.
	 */
	private static SQLServerDataSource DATA_SOURCE;

	/**
	 * Main method.
	 * 
	 * @param args
	 *            command line args.
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equalsIgnoreCase(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				setupDB();
			}
		});
	}

	/**
	 * Setup database and server connections.
	 */
	private static void setupDB() {

		JDialog connDialog = new JDialog();
		connDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		connDialog.setLocationRelativeTo(null);
		
		connDialog.setSize(new Dimension(360, 350));
		connDialog.setTitle("Establish a Connection");
		JPanel connPanel = new JPanel(new ParagraphLayout());
		JLabel userNameLbl = new JLabel("UserName: ");
		JLabel passwordLbl = new JLabel("Password: ");
		JLabel serverNameLbl = new JLabel("Server Name: ");
		JLabel instanceNameLbl = new JLabel("Instance Name: ");
		JLabel portNumberLbl = new JLabel("Port Number: ");
		JLabel dbNameLbl = new JLabel("DataBase Name: ");
		JFormattedTextField userNameTxt = new JFormattedTextField("username");
		userNameTxt.setColumns(15);
		JPasswordField passwordTxt = new JPasswordField("password");
		passwordTxt.setColumns(15);
		JFormattedTextField serverNameTxt = new JFormattedTextField("server");
		serverNameTxt.setColumns(15);
		JFormattedTextField instanceNameTxt = new JFormattedTextField("instance");
		instanceNameTxt.setColumns(15);
		JFormattedTextField portNumberTxt = new JFormattedTextField("port");
		portNumberTxt.setColumns(15);
		JFormattedTextField dbNameTxt = new JFormattedTextField("AnchoredMassage");
		dbNameTxt.setColumns(15);
		connPanel.add(userNameLbl, ParagraphLayout.NEW_PARAGRAPH);
		connPanel.add(userNameTxt, ParagraphLayout.NEW_LINE);
		connPanel.add(passwordLbl, ParagraphLayout.NEW_PARAGRAPH);
		connPanel.add(passwordTxt, ParagraphLayout.NEW_LINE);
		connPanel.add(serverNameLbl, ParagraphLayout.NEW_PARAGRAPH);
		connPanel.add(serverNameTxt, ParagraphLayout.NEW_LINE);
		connPanel.add(instanceNameLbl, ParagraphLayout.NEW_PARAGRAPH);
		connPanel.add(instanceNameTxt, ParagraphLayout.NEW_LINE);
		connPanel.add(portNumberLbl, ParagraphLayout.NEW_PARAGRAPH);
		connPanel.add(portNumberTxt, ParagraphLayout.NEW_LINE);
		connPanel.add(dbNameLbl, ParagraphLayout.NEW_PARAGRAPH);
		connPanel.add(dbNameTxt, ParagraphLayout.NEW_LINE);
		JButton connectBtn = new JButton("Connect");
		connectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				DATA_SOURCE = new SQLServerDataSource();
				DATA_SOURCE.setUser(userNameTxt.getText());
				StringBuilder passwordBuilder = new StringBuilder();
				for(char c : passwordTxt.getPassword()) {
					passwordBuilder.append(c);
				}
				DATA_SOURCE.setPassword(passwordBuilder.toString()); 
				DATA_SOURCE.setServerName(serverNameTxt.getText());
				DATA_SOURCE.setInstanceName(instanceNameTxt.getText());
				DATA_SOURCE.setPortNumber(Integer.parseInt(portNumberTxt.getText()));
				DATA_SOURCE.setDatabaseName(dbNameTxt.getText());
				DATA_SOURCE.setSendTimeAsDatetime(false);
				try {
					DATA_CONN = DATA_SOURCE.getConnection();
					new AnchoredGUI(DATA_SOURCE, DATA_CONN);
					connDialog.dispose();
				} catch (SQLServerException e) {
					new MSGWindow(e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
		});
		connPanel.add(connectBtn, ParagraphLayout.NEW_LINE);
		connDialog.add(connPanel);
		connDialog.setVisible(true);
	}
}
