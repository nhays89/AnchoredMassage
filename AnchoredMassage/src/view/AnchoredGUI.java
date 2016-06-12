package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import com.jhlabs.awt.ParagraphLayout;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * 
 * @author Nicholas A. Hays
 * @version 1.0
 * 
 *          A database application for a small therapy clinic.
 */
public class AnchoredGUI extends JFrame {

	/**
	 * The appointment card.
	 */
	AppointmentCard myAppointmentCard;
	/**
	 * The therapist card.
	 */
	TherapistCard myTherapistCard;
	/**
	 * The patient card.
	 */
	PatientCard myPatientCard;
	/**
	 * Container for all cards.
	 */
	JPanel myCards;
	/**
	 * Patient card identifier.
	 */
	static final String PATIENTCARD = "PATIENT CARD";
	/**
	 * Therapist card identifier.
	 */
	static final String THERAPISTCARD = "THERAPIST CARD";
	/**
	 * Appointment card identifier.
	 */
	static final String APPOINTMENTCARD = "APPOINTMENT CARD";
	/**
	 * default serial id.
	 */
	private static final long serialVersionUID = 7505147285638924755L;
	/**
	 * database connection.
	 */
	protected static Connection DB_CONNECTION;
	/**
	 * database source.
	 */
	protected static SQLServerDataSource DATA_SOURCE;
	/**
	 * Creates the main GUI of the application.
	 */
	public AnchoredGUI(SQLServerDataSource theDB, Connection theConn) {
		DATA_SOURCE = theDB;
		DB_CONNECTION = theConn;
		createCards();
		myCards = new JPanel(new CardLayout());
		myCards.add(myPatientCard, PATIENTCARD);
		myCards.add(myTherapistCard, THERAPISTCARD);
		myCards.add(myAppointmentCard, APPOINTMENTCARD);
		this.getContentPane().add(myCards);
		createNav();
		initGUI();
	}

	/**
	 * Creates card components that will be managed by a card layout manager.
	 */
	private void createCards() {
		myAppointmentCard = new AppointmentCard();
		myPatientCard = new PatientCard();
		myTherapistCard = new TherapistCard();
	}

	/**
	 * The navigation bar.
	 */
	private void createNav() {

		final JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.darkGray);
		String patient = "Patient", therapist = "Therapist", appointment = "Appointment";
		this.setJMenuBar(menuBar);
		
		JMenu selectMenu = new JMenu(" View ");
		JMenuItem mntmPatient = new JMenuItem(" Patient ");
		selectMenu.add(mntmPatient);
		mntmPatient.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout myCardLayout = (CardLayout) myCards.getLayout();
				myCardLayout.show(myCards, PATIENTCARD);
				selectMenu.setText(patient);
			}
		});

		JMenuItem mntmTherapist = new JMenuItem(" Therapist ");
		selectMenu.add(mntmTherapist);
		mntmTherapist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout myCardLayout = (CardLayout) myCards.getLayout();
				myCardLayout.show(myCards, THERAPISTCARD);
				selectMenu.setText(therapist);
			}
		});

		JMenuItem mntmAppt = new JMenuItem(" Appointment ");
		selectMenu.add(mntmAppt);
		mntmAppt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout myCardLayout = (CardLayout) myCards.getLayout();
				myCardLayout.show(myCards, APPOINTMENTCARD);
				selectMenu.setText(appointment);
			}
		});

		menuBar.add(selectMenu, ParagraphLayout.NEW_PARAGRAPH);
	
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		System.out.println(width);
		double height = screenSize.getHeight();
		System.out.println(height);
		this.setSize(new Dimension(( (int) width), (int) height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Anchored Massage");
		setVisible(true);
	}
}
