package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import model.Patient;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will display and update information relating to each tuple
 *         of the patient table.
 */
public class PatientInfoPanel extends AbstractInfoPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 9106446617813863696L;

	/**
	 * Info Submit Button.
	 */
	JButton myPatientSubmitBtn;

	/**
	 * Constructor for Patient Information Panel.
	 */
	public PatientInfoPanel() {
		super(Color.DARK_GRAY);
		myPatientSubmitBtn = new JButton("Patient Submit");
		myPatientSubmitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updatePatientInfo();
			}
		});
		myPatientSubmitBtn.setName("patientSubmitBtn");
		this.add(myPatientSubmitBtn);
	}

	/**
	 * Fires a property change event to the PatientCard.
	 */
	protected void updatePatientInfo() {
		this.firePropertyChange("patientSubmitBtn", null, null);
	}

	/**
	 * Information to provide listeners of current data in Panel.
	 * 
	 * @return
	 */
	public Patient getPatientData() {
		return new Patient("dkajflk", "dkajflk", "dkajflk", "dkajflk", "dkajflk", "dkajflk", "dkajflk", "dkajflk");

	}

	/**
	 * Set Patient Fields.
	 */
	public void setPatientFields(Patient thePatient) {
		myPatientSubmitBtn.setText(thePatient.getFName());
	}

}
