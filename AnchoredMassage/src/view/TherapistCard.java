package view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.PatientCardListener;
import controller.TherapistCardListener;

public class TherapistCard extends JPanel implements PropertyChangeListener {

	TherapistInfoPanel myTherapistInfoPanel;
	TherapistSearchPanel myTherapistSearchPanel;
	TherapistTablePanel myTherapistTablePanel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2398946988371590567L;
	private String THERAPIST_CARD_LISTENER_PROP = "therapistCardListener";
	private TherapistCardListener myTherapistCardListener;

	public TherapistCard() {
		setLayout(new BorderLayout());
		myTherapistInfoPanel = new TherapistInfoPanel();
		myTherapistSearchPanel = new TherapistSearchPanel();
		myTherapistTablePanel = new TherapistTablePanel();
		add(myTherapistInfoPanel, BorderLayout.EAST);
		add(myTherapistSearchPanel, BorderLayout.NORTH);
		add(myTherapistTablePanel, BorderLayout.CENTER);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals(THERAPIST_CARD_LISTENER_PROP)) {
			myTherapistCardListener = (TherapistCardListener) evt.getNewValue();
			System.out.println(" in " + this.getClass().getSimpleName() + ", getting the " + propName + " object");
		}

	}
}
