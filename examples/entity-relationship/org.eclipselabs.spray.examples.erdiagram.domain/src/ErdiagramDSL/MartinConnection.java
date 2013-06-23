/**
 */
package ErdiagramDSL;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Martin Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ErdiagramDSL.MartinConnection#getFromText <em>From Text</em>}</li>
 *   <li>{@link ErdiagramDSL.MartinConnection#getToText <em>To Text</em>}</li>
 *   <li>{@link ErdiagramDSL.MartinConnection#getStart <em>Start</em>}</li>
 *   <li>{@link ErdiagramDSL.MartinConnection#getEnd <em>End</em>}</li>
 * </ul>
 * </p>
 *
 * @see ErdiagramDSL.ErdiagramDSLPackage#getMartinConnection()
 * @model
 * @generated
 */
public interface MartinConnection extends DiagramElement {
	/**
	 * Returns the value of the '<em><b>From Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From Text</em>' attribute.
	 * @see #setFromText(String)
	 * @see ErdiagramDSL.ErdiagramDSLPackage#getMartinConnection_FromText()
	 * @model required="true"
	 * @generated
	 */
	String getFromText();

	/**
	 * Sets the value of the '{@link ErdiagramDSL.MartinConnection#getFromText <em>From Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From Text</em>' attribute.
	 * @see #getFromText()
	 * @generated
	 */
	void setFromText(String value);

	/**
	 * Returns the value of the '<em><b>To Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To Text</em>' attribute.
	 * @see #setToText(String)
	 * @see ErdiagramDSL.ErdiagramDSLPackage#getMartinConnection_ToText()
	 * @model required="true"
	 * @generated
	 */
	String getToText();

	/**
	 * Sets the value of the '{@link ErdiagramDSL.MartinConnection#getToText <em>To Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To Text</em>' attribute.
	 * @see #getToText()
	 * @generated
	 */
	void setToText(String value);

	/**
	 * Returns the value of the '<em><b>Start</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' reference.
	 * @see #setStart(MartinEntity)
	 * @see ErdiagramDSL.ErdiagramDSLPackage#getMartinConnection_Start()
	 * @model required="true"
	 * @generated
	 */
	MartinEntity getStart();

	/**
	 * Sets the value of the '{@link ErdiagramDSL.MartinConnection#getStart <em>Start</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' reference.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(MartinEntity value);

	/**
	 * Returns the value of the '<em><b>End</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End</em>' reference.
	 * @see #setEnd(MartinEntity)
	 * @see ErdiagramDSL.ErdiagramDSLPackage#getMartinConnection_End()
	 * @model required="true"
	 * @generated
	 */
	MartinEntity getEnd();

	/**
	 * Sets the value of the '{@link ErdiagramDSL.MartinConnection#getEnd <em>End</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End</em>' reference.
	 * @see #getEnd()
	 * @generated
	 */
	void setEnd(MartinEntity value);

} // MartinConnection
