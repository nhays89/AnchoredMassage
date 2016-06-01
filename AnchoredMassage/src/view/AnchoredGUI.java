package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jhlabs.awt.ParagraphLayout;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

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
		final JPanel navPanel = new JPanel(new ParagraphLayout(50, 60, 12, 11, 4, 4));
		navPanel.setPreferredSize(new Dimension(200, 1000));
		this.getContentPane().add(navPanel, BorderLayout.WEST);

		JButton myPatientBtn = new JButton("     Patient     ");
		myPatientBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout myCardLayout = (CardLayout) myCards.getLayout();
				myCardLayout.show(myCards, PATIENTCARD);
				setTitle(PATIENTCARD);
			}
		});

		JButton myTherapistBtn = new JButton("   Therapist   ");
		myTherapistBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout myCardLayout = (CardLayout) myCards.getLayout();
				myCardLayout.show(myCards, THERAPISTCARD);
				setTitle(THERAPISTCARD);
			}
		});

		JButton myAppointmentBtn = new JButton("Appointment");
		myAppointmentBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout myCardLayout = (CardLayout) myCards.getLayout();
				myCardLayout.show(myCards, APPOINTMENTCARD);
				setTitle(APPOINTMENTCARD);
			}
		});

		navPanel.add(myPatientBtn, ParagraphLayout.NEW_PARAGRAPH);
		navPanel.add(myTherapistBtn, ParagraphLayout.NEW_PARAGRAPH);
		navPanel.add(myAppointmentBtn, ParagraphLayout.NEW_PARAGRAPH);
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		this.setSize(new Dimension(( (int) width), (int) height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Anchored Massage");
		setVisible(true);
	}
}
