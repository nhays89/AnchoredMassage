package view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import controller.TherapistCardListener;

public class TherapistSearchPanel extends AbstractSearchPanel implements PropertyChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6187373824017488881L;
	private TherapistCardListener myTherapistPanelListener;
	private JButton myTherapistSearchBtn;
	private static String THERAPIST_LISTENER_PROP_NAME = "therapistPanelListener";
	
	public TherapistSearchPanel() {
		super(Color.gray);
		myTherapistSearchBtn = new JButton("Therapist Search");
		myTherapistSearchBtn.setName("therapistSearchBtn");
		add(myTherapistSearchBtn);
		
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals(THERAPIST_LISTENER_PROP_NAME)) {
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the" + propName + "object");
			myTherapistPanelListener = (TherapistCardListener) evt.getNewValue();
			System.out.println("in " + this.getClass().getSimpleName()+ ", setting button action listener");
			myTherapistSearchBtn.addActionListener(myTherapistPanelListener);
		}
	}
}
