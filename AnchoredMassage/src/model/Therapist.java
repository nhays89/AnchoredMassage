package model;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Object that represents the THERAPIST entity in the database. Purpose
 *         is to encapsulate data about the therapist to make data transfer
 *         easier between the Therapist Card and its sub components.
 */
public class Therapist {
	/**
	 * Therapist ID. 
	 */
	String therapistID;
	/**
	 * Therapist first name.
	 */
	String therapistFName;
	/**
	 * Therapist last name. 
	 */
	String therapistLName;
	/**
	 * Therapist date of birth. 
	 */
	String therapistDOB;
	/**
	 * Therapist street. 
	 */
	String therapistStreet;
	/**
	 * Therapist City. 
	 */
	String therapistCity;
	/**
	 * Therapist State. 
	 */
	String therapistState;
	/**
	 * Therapist Zip. 
	 */
	String therapistZip;

	/**
	 * Constructs the therapist object.
	 * 
	 * @param tID
	 *            the therapist ID.
	 * @param tFName
	 *            the therapist first name.
	 * @param tLName
	 *            the therapist last name.
	 * @param tDOB
	 *            the therapist date of birth.
	 * @param tStreet
	 *            the therapist street.
	 * @param tCity
	 *            the therapist city.
	 * @param tState
	 *            the therapist state.
	 * @param tZip
	 *            the therapist Zip code.
	 */
	public Therapist(String tID, String tFName, String tLName, String tDOB, String tStreet, String tCity, String tState,
			String tZip) {
		this.therapistID = tID;
		this.therapistFName = tFName;
		this.therapistLName = tLName;
		this.therapistDOB = tDOB;
		this.therapistStreet = tStreet;
		this.therapistCity = tCity;
		this.therapistState = tState;
		this.therapistZip = tZip;
	}

	/**
	 * Gets the therapist ID.
	 * 
	 * @return the therapist ID.
	 */
	public String getID() {
		return therapistID;
	}

	/**
	 * Gets the therapist first name.
	 * 
	 * @return the therapist first name.
	 */
	public String getFName() {
		return therapistFName;
	}

	/**
	 * Gets the therapist last name.
	 * 
	 * @return the therapist last name.
	 */
	public String getLName() {
		return therapistLName;
	}

	/**
	 * Gets the therapist date of birth.
	 * 
	 * @return the therapist date of birth.
	 */
	public String getDOB() {
		return therapistDOB;
	}

	/**
	 * Gets the therapist street address.
	 * 
	 * @return the therapist street address.
	 */
	public String getStreet() {
		return therapistStreet;
	}

	/**
	 * Gets the therapist city.
	 * 
	 * @return the therapist city.
	 */
	public String getCity() {
		return therapistCity;

	}

	/**
	 * Gets the therapist state.
	 * 
	 * @return the therpaist state.
	 */
	public String getState() {
		return therapistState;

	}

	/**
	 * Gets the therapist Zip.
	 * 
	 * @return the therapist zip.
	 */
	public String getZip() {
		return therapistZip;
	}
}
