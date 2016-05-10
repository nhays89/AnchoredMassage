package model;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Object that represents the APPOINTMENT entity in the database.
 *         Purpose is to encapsulate data about the appointment to make data
 *         transfer easier between the Appointment Card and its sub components.
 */
public class Appointment {
	/**
	 * appointment ID.
	 */
	String apptID;
	/**
	 * Therapist first name.
	 */
	String apptTFName;
	/**
	 * Therapist last name.
	 */
	String apptTLName;
	/**
	 * Patient first name.
	 */
	String apptPFName;
	/**
	 * Patient last name.
	 */
	String apptPLName;
	/**
	 * Appointment Date.
	 */
	String apptDate;
	/**
	 * Appointment Time.
	 */
	String apptTime;
	/**
	 * Appointment service ID.
	 */
	String apptServiceID;

	/**
	 * Constructs the Appointment.
	 * 
	 * @param theApptID
	 *            the appointment ID.
	 * @param theApptTFName
	 *            the therapist first name.
	 * @param theApptTLName
	 *            the therapist last name.
	 * @param theApptPFName
	 *            the patient first name.
	 * @param theApptPLName
	 *            the patient last name.
	 * @param theApptDate
	 *            the appointment date.
	 * @param theApptTime
	 *            the appointment time.
	 * @param theApptServiceID
	 *            the appointment service ID.
	 */
	public Appointment(String theApptID, String theApptTFName, String theApptTLName, String theApptPFName,
			String theApptPLName, String theApptDate, String theApptTime, String theApptServiceID) {
		this.apptID = theApptID;
		this.apptTFName = theApptTFName;
		this.apptPFName = theApptTLName;
		this.apptPLName = theApptPFName;
		this.apptTime = theApptPLName;
		this.apptDate = theApptDate;
		this.apptTime = theApptTime;
		this.apptServiceID = theApptServiceID;
	}

	/**
	 * Gets the appointment ID.
	 * 
	 * @return the appointment ID.
	 */
	public String getApptID() {
		return apptID;
	}

	/**
	 * Gets the therapist last name.
	 * 
	 * @return the therapist last name.
	 */
	public String getApptTLName() {
		return apptTLName;
	}

	/**
	 * Gets the therapist first name.
	 * 
	 * @return the therapist first name.
	 */
	public String getApptTFName() {
		return apptTFName;
	}

	/**
	 * Gets the patient last name.
	 * 
	 * @return the patient last name.
	 */
	public String getApptPLName() {
		return apptPLName;
	}

	/**
	 * Gets the patient first name.
	 * 
	 * @return the patient first name.
	 */
	public String getApptPFName() {
		return apptPFName;
	}

	/**
	 * Gets the appointment date.
	 * 
	 * @return the appointment date.
	 */
	public String getApptDate() {
		return apptDate;
	}

	/**
	 * Gets the appointment time.
	 * 
	 * @return the appointment time.
	 */
	public String getApptTime() {
		return apptTime;

	}

	/**
	 * Gets the appointment service ID.
	 * 
	 * @return the appointment service ID.
	 */
	public String getApptServiceID() {
		return apptServiceID;
	}
}
