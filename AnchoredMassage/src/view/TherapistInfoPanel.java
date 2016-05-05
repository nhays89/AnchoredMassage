package view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import controller.TherapistCardListener;

public class TherapistInfoPanel extends AbstractInfoPanel implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4024841346169919998L;
	private TherapistCardListener myTherapistPanelListener;
	private JButton myTherapistSubmitBtn;
	private static String THERAPIST_LISTENER_PROP_NAME = "therapistPanelListener";

	
	public TherapistInfoPanel() {
		super(new Color(123,12,56));
		myTherapistSubmitBtn = new JButton("Therapist Submit");
		myTherapistSubmitBtn.setName("therapistSubmitBtn");
		this.add(myTherapistSubmitBtn);
		
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals(THERAPIST_LISTENER_PROP_NAME)) {
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the" + propName + "object");
			myTherapistPanelListener = (TherapistCardListener) evt.getNewValue();
			System.out.println("in " + this.getClass().getSimpleName()+ ", setting button action listener");
			myTherapistSubmitBtn.addActionListener(myTherapistPanelListener);
		}
	}
	
	
}
