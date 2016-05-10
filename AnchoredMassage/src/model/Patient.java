package model;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Object that represents the PATIENT entity in the database. Purpose is
 *         to encapsulate data about the patient to make data transfer easier
 *         between the Patient Card and its sub components.
 */
public class Patient {
	/**
	 * Patient ID.
	 */
	String patientID;
	/**
	 * Patient first name.
	 */
	String patientFName;
	/**
	 * Patient last name.
	 */
	String patientLName;
	/**
	 * Patient date of birth.
	 */
	String patientDOB;
	/**
	 * Patient street.
	 */
	String patientStreet;
	/**
	 * Patient city.
	 */
	String patientCity;
	/**
	 * Patient state.
	 */
	String patientState;
	/**
	 * Patient Zip.
	 */
	String patientZip;

	/**
	 * Constructs the Patient object.
	 * 
	 * @param pID
	 *            the patient ID.
	 * @param pFName
	 *            the patient first name.
	 * @param pLName
	 *            the patient last name.
	 * @param pDOB
	 *            the patient date of birth.
	 * @param pStreet
	 *            the patient street.
	 * @param pCity
	 *            the patient city.
	 * @param pState
	 *            the patient state.
	 * @param pZip
	 *            the patient zip.
	 */
	public Patient(String pID, String pFName, String pLName, String pDOB, String pStreet, String pCity, String pState,
			String pZip) {
		this.patientID = pID;
		this.patientFName = pFName;
		this.patientLName = pLName;
		this.patientDOB = pDOB;
		this.patientStreet = pStreet;
		this.patientCity = pCity;
		this.patientState = pState;
		this.patientZip = pZip;
	}

	/**
	 * Gets the patient ID.
	 * 
	 * @return the patient ID.
	 */
	public String getPID() {
		return patientID;
	}

	/**
	 * Gets the patient first name.
	 * 
	 * @return the patient first name.
	 */
	public String getFName() {
		return patientFName;
	}

	/**
	 * Gets the patient last name.
	 * 
	 * @return the patient last name.
	 */
	public String getLName() {
		return patientLName;
	}

	/**
	 * Gets the patient date of birth.
	 * 
	 * @return the patient date of birth.
	 */
	public String getDOB() {
		return patientDOB;
	}

	/**
	 * Gets the patient street address.
	 * 
	 * @return the patient street address.
	 */
	public String getStreet() {
		return patientStreet;
	}

	/**
	 * Gets the patient city.
	 * 
	 * @return the patient city.
	 */
	public String getCity() {
		return patientCity;

	}

	/**
	 * Gets the patient state.
	 * 
	 * @return the patient state.
	 */
	public String getState() {
		return patientState;

	}

	/**
	 * Gets the patient zip code.
	 * 
	 * @return the patient zip code.
	 */
	public String getZip() {
		return patientZip;
	}
}
