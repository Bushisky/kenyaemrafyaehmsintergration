package org.openmrs.module.kenyaemrafyaehmsintergration.metadata;

import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;

public class Metadata {
	
	/** @return the PatientIdentifier that matches the passed uuid, name, or primary key id */
	public static PatientIdentifierType getPatientIdentifierType(String lookup) {
		return Context.getPatientService().getPatientIdentifierTypeByUuid(lookup);
	}
	
	/** @return the PersonAttribute that matches the passed uuid, name, or primary key id */
	public static PersonAttributeType getPersonAttributeType(String lookup) {
		return Context.getPersonService().getPersonAttributeTypeByUuid(lookup);
	}
}
