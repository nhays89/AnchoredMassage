package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.AppointmentCardListener;
import controller.PatientCardListener;
import controller.TherapistCardListener;

public class AnchoredGUI extends JFrame implements PropertyChangeListener {

/*	CardFactory myPatientCard;
	CardFactory myTherapistCard;
	CardFactory myAppointmentCard;*/
	
	
	AppointmentCard myAppointmentCard;
	TherapistCard myTherapistCard;
	PatientCard myPatientCard;
	
	

	JPanel myCards;
	static final String PATIENTCARD = "patientCard";
	static final String THERAPISTCARD = "therpaistCard";
	static final String APPOINTMENTCARD = "appointmentCard";

	AppointmentCardListener myAppointmentPanelListener;
	PatientCardListener myPatientPanelListener;
	TherapistCardListener myTherapistPanelListener;

	AbstractInfoPanel myPatientInfoPanel;
	AbstractSearchPanel myPatientSearchPanel;
	AbstractTablePanel myPatientTablePanel;

	AbstractInfoPanel myTherapistInfoPanel;
	AbstractSearchPanel myTherapistSearchPanel;
	AbstractTablePanel myTherapistTablePanel;

	AbstractInfoPanel myAppointmentInfoPanel;
	AbstractSearchPanel myAppointmentSearchPanel;
	AbstractTablePanel myAppointmentTablePanel;
	
	
	private AppointmentCardListener myAppointmentCardListener;
	private PatientCardListener myPatientCardListener;
	private TherapistCardListener myTherapistCardListener;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7505147285638924755L;

	public AnchoredGUI() {

		addCardListeners();
		/*addPatientPanels();
		addTherapistPanel();
		addAppointmentPanel();*/
		myCards = new JPanel(new CardLayout());

		/*myPatientCard = new CardFactory(myPatientInfoPanel, myPatientSearchPanel, myPatientTablePanel);
		myTherapistCard = new CardFactory(myTherapistInfoPanel, myTherapistSearchPanel, myTherapistTablePanel);
		myAppointmentCard = new CardFactory(myAppointmentInfoPanel, myAppointmentSearchPanel, myAppointmentTablePanel);*/

		myCards.add(myPatientCard, PATIENTCARD);
		myCards.add(myTherapistCard, THERAPISTCARD);
		myCards.add(myAppointmentCard, APPOINTMENTCARD);

		// NavPanel myNavPanel = new NavPanel();
		this.getContentPane().add(myCards);
		createNav();

		initGUI();

	}
/*
	private void addAppointmentPanel() {
		myAppointmentInfoPanel = new AppointmentInfoPanel();
		this.firePropertyChange("appointmentInfoPanel", null, myAppointmentInfoPanel);
		this.addPropertyChangeListener((PropertyChangeListener) myAppointmentInfoPanel);
		myAppointmentInfoPanel.addPropertyChangeListener(myAppointmentPanelListener);

		myAppointmentSearchPanel = new AppointmentSearchPanel();
		this.firePropertyChange("appointmentSearchPanel", null, myAppointmentSearchPanel);
		this.addPropertyChangeListener((PropertyChangeListener) myAppointmentSearchPanel);
		myAppointmentSearchPanel.addPropertyChangeListener(myAppointmentPanelListener);

		myAppointmentTablePanel = new AppointmentTablePanel();
		this.firePropertyChange("appointmentTablePanel", null, myAppointmentTablePanel);
		this.addPropertyChangeListener((PropertyChangeListener) myAppointmentTablePanel);
		myAppointmentTablePanel.addPropertyChangeListener(myAppointmentPanelListener);

		this.firePropertyChange("appointmentPanelListener", null, myAppointmentPanelListener);
	}*/

	/*private void addTherapistPanel() {
		myTherapistInfoPanel = new TherapistInfoPanel();
		this.firePropertyChange("therapistInfoPanel", null, myTherapistInfoPanel);
		this.addPropertyChangeListener((PropertyChangeListener) myTherapistInfoPanel);
		myTherapistInfoPanel.addPropertyChangeListener(myTherapistPanelListener);

		myTherapistSearchPanel = new TherapistSearchPanel();
		this.firePropertyChange("therapistSearchPanel", null, myTherapistSearchPanel);
		this.addPropertyChangeListener((PropertyChangeListener) myTherapistSearchPanel);
		myTherapistSearchPanel.addPropertyChangeListener(myTherapistPanelListener);

		myTherapistTablePanel = new TherapistTablePanel();
		this.firePropertyChange("therapistTablePanel", null, myTherapistTablePanel);
		this.addPropertyChangeListener((PropertyChangeListener) myTherapistTablePanel);
		myTherapistTablePanel.addPropertyChangeListener(myTherapistPanelListener);

		this.firePropertyChange("therapistPanelListener", null, myTherapistPanelListener);
	}*/

	private void addPatientPanels() {
		/*myPatientInfoPanel = new PatientInfoPanel();
		myPatientInfoPanel.addPropertyChangeListener(myPatientPanelListener);
		this.addPropertyChangeListener((PropertyChangeListener) myPatientInfoPanel);
		this.firePropertyChange("patientInfoPanel", null, myPatientInfoPanel);

		myPatientSearchPanel = new PatientSearchPanel();
		myPatientSearchPanel.addPropertyChangeListener(myPatientPanelListener);
		this.addPropertyChangeListener((PropertyChangeListener) myPatientSearchPanel);
		this.firePropertyChange("patientSearchPanel", null, myPatientSearchPanel);

		myPatientTablePanel = new PatientTablePanel();
		myPatientTablePanel.addPropertyChangeListener(myPatientPanelListener);
		this.addPropertyChangeListener((PropertyChangeListener) myPatientSearchPanel);
		this.firePropertyChange("patientTablePanel", null, myPatientTablePanel);

		this.firePropertyChange("patientPanelListener", null, myPatientPanelListener);*/
		
		
		//AppointmentCard myAppointmentCard = new AppointmentCard();
		
		
		
		
		
		
		
	}

	private void addCardListeners() {
		/*myAppointmentPanelListener = new AppointmentCardListener();
		this.addPropertyChangeListener(myAppointmentPanelListener);

		myPatientPanelListener = new PatientPanelListener();
		this.addPropertyChangeListener(myPatientPanelListener);

		myTherapistPanelListener = new TherapistPanelListener();
		this.addPropertyChangeListener(myTherapistPanelListener);*/
		
		
		myAppointmentCardListener = new AppointmentCardListener();
		myAppointmentCard = new AppointmentCard();
		this.addPropertyChangeListener(myAppointmentCard);
		this.addPropertyChangeListener(myAppointmentCardListener);
		this.firePropertyChange("appointmentCardListener", null, myAppointmentCardListener);
		this.firePropertyChange("appointmentCard", null, myAppointmentCard);
		
		myPatientCardListener = new PatientCardListener();
		myPatientCard = new PatientCard();
		this.addPropertyChangeListener(myPatientCardListener);
		this.addPropertyChangeListener(myPatientCard);
		this.firePropertyChange("patientCard", null, myPatientCard);
		this.firePropertyChange("patientCardListener", null, myPatientCardListener);
		
		
		myTherapistCardListener = new TherapistCardListener();
		myTherapistCard = new TherapistCard();
		this.addPropertyChangeListener(myTherapistCardListener);
		this.addPropertyChangeListener(myTherapistCard);
		this.firePropertyChange("therapistCardListener", null, myTherapistCardListener);
		this.firePropertyChange("therapistCard", null, myTherapistCard);
		
		
		

	}

	private void createNav() {
		final JPanel navPanel = new JPanel();
		navPanel.setPreferredSize(new Dimension(200, 1000));
		this.getContentPane().add(navPanel, BorderLayout.WEST);

		JButton myPatientBtn = new JButton("Patient");
		myPatientBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout myCardLayout = (CardLayout) myCards.getLayout();
				myCardLayout.show(myCards, PATIENTCARD);
			}
		});

		JButton myTherapistBtn = new JButton("Therapist");
		myTherapistBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout myCardLayout = (CardLayout) myCards.getLayout();
				myCardLayout.show(myCards, THERAPISTCARD);
			}
		});

		JButton myAppointmentBtn = new JButton("Appointment");
		myAppointmentBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout myCardLayout = (CardLayout) myCards.getLayout();
				myCardLayout.show(myCards, APPOINTMENTCARD);
			}
		});

		navPanel.add(myPatientBtn);
		navPanel.add(myTherapistBtn);
		navPanel.add(myAppointmentBtn);

	}

	private void initGUI() {

		setSize(1200, 1000);

		setVisible(true);
		pack();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

}
