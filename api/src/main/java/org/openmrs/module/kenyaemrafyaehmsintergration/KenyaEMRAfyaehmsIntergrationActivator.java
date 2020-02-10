/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemrafyaehmsintergration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.kenyaemrafyaehmsintergration.metadata.HarmonizedMetadataConstants;
import org.openmrs.module.kenyaemrafyaehmsintergration.metadata.Metadata;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class KenyaEMRAfyaehmsIntergrationActivator extends BaseModuleActivator {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * @see #started()
	 */
	public void started() {
		//save the identifiers to the respective DB
		getIdentifierMetadataForApplication();
		//Load person attribute types
		getPersonAttributesMetadata();
		log.info("Started KenyaEMR Afyaehms Intergration");
	}
	
	/**
	 * @see #shutdown()
	 */
	public void shutdown() {
		log.info("Shutdown KenyaEMR Afyaehms Intergration");
	}
	
	private void getIdentifierMetadataForApplication() {
		PatientService patientService = Context.getPatientService();
		
		//National ID
		PatientIdentifierType nationalId = Metadata
		        .getPatientIdentifierType(HarmonizedMetadataConstants._PatientIdentifierType.NATIONAL_ID);
		PatientIdentifierType patientClinicNumber = Metadata
		        .getPatientIdentifierType(HarmonizedMetadataConstants._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
		PatientIdentifierType uniquePatientNumber = Metadata
		        .getPatientIdentifierType(HarmonizedMetadataConstants._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
		PatientIdentifierType cwc = Metadata
		        .getPatientIdentifierType(HarmonizedMetadataConstants._PatientIdentifierType.CWC_NUMBER);
		PatientIdentifierType hivUniqueNumber = Metadata
		        .getPatientIdentifierType(HarmonizedMetadataConstants._PatientIdentifierType.HIV_UNIQUE_PATIENT_NUMBER);
		
		if (nationalId == null) {
			PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
			patientIdentifierType.setName("National Id");
			patientIdentifierType.setUuid(HarmonizedMetadataConstants._PatientIdentifierType.NATIONAL_ID);
			patientService.savePatientIdentifierType(patientIdentifierType);
		}
		
		if (patientClinicNumber == null) {
			PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
			patientIdentifierType.setName("Patient Clinic Number");
			patientIdentifierType.setUuid(HarmonizedMetadataConstants._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
			patientService.savePatientIdentifierType(patientIdentifierType);
		}
		
		if (uniquePatientNumber == null) {
			PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
			patientIdentifierType.setName("Unique Patient Number");
			patientIdentifierType
			        .setUuid(HarmonizedMetadataConstants._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
			patientService.savePatientIdentifierType(patientIdentifierType);
		}
		
		if (cwc == null) {
			PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
			patientIdentifierType.setName("CWC");
			patientIdentifierType.setUuid(HarmonizedMetadataConstants._PatientIdentifierType.CWC_NUMBER);
			patientService.savePatientIdentifierType(patientIdentifierType);
		}
		if (hivUniqueNumber == null) {
			PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
			patientIdentifierType.setName("Unique Patient Number");
			patientIdentifierType.setDescription("Assigned to every HIV patient");
			patientIdentifierType.setUuid(HarmonizedMetadataConstants._PatientIdentifierType.CWC_NUMBER);
			patientService.savePatientIdentifierType(patientIdentifierType);
		}
	}
	
	private void getPersonAttributesMetadata() {
		PersonService personService = Context.getPersonService();
		PersonAttributeType nextOfKinAddress = Metadata
		        .getPersonAttributeType(HarmonizedMetadataConstants._PersonAttributeType.NEXT_OF_KIN_ADDRESS);
		PersonAttributeType nextOfKinContact = Metadata
		        .getPersonAttributeType(HarmonizedMetadataConstants._PersonAttributeType.NEXT_OF_KIN_CONTACT);
		PersonAttributeType nextOfKinName = Metadata
		        .getPersonAttributeType(HarmonizedMetadataConstants._PersonAttributeType.NEXT_OF_KIN_NAME);
		PersonAttributeType nextOfKinRelationship = Metadata
		        .getPersonAttributeType(HarmonizedMetadataConstants._PersonAttributeType.NEXT_OF_KIN_RELATIONSHIP);
		PersonAttributeType subChiefName = Metadata
		        .getPersonAttributeType(HarmonizedMetadataConstants._PersonAttributeType.SUBCHIEF_NAME);
		PersonAttributeType telephoneContact = Metadata
		        .getPersonAttributeType(HarmonizedMetadataConstants._PersonAttributeType.TELEPHONE_CONTACT);
		PersonAttributeType emailAddress = Metadata
		        .getPersonAttributeType(HarmonizedMetadataConstants._PersonAttributeType.EMAIL_ADDRESS);
		
		if (nextOfKinAddress == null) {
			PersonAttributeType personAttributeType = new PersonAttributeType();
			personAttributeType.setName("Next of kin address");
			personAttributeType.setUuid(HarmonizedMetadataConstants._PersonAttributeType.NEXT_OF_KIN_ADDRESS);
			personAttributeType.setDescription("Address of the next of kin person");
			personService.savePersonAttributeType(personAttributeType);
		}
		
		if (nextOfKinContact == null) {
			PersonAttributeType personAttributeType = new PersonAttributeType();
			personAttributeType.setName("Next of kin contact");
			personAttributeType.setUuid(HarmonizedMetadataConstants._PersonAttributeType.NEXT_OF_KIN_CONTACT);
			personAttributeType.setDescription("Contact of the next of kin person");
			personService.savePersonAttributeType(personAttributeType);
		}
		
		if (nextOfKinName == null) {
			PersonAttributeType personAttributeType = new PersonAttributeType();
			personAttributeType.setName("Next of kin name");
			personAttributeType.setUuid(HarmonizedMetadataConstants._PersonAttributeType.NEXT_OF_KIN_NAME);
			personAttributeType.setDescription("Names of the next of kin person");
			personService.savePersonAttributeType(personAttributeType);
		}
		if (nextOfKinRelationship == null) {
			PersonAttributeType personAttributeType = new PersonAttributeType();
			personAttributeType.setName("relationship of patient to the next of kin person");
			personAttributeType.setUuid(HarmonizedMetadataConstants._PersonAttributeType.NEXT_OF_KIN_RELATIONSHIP);
			personAttributeType.setDescription("Relationship  of the next of kin person to the patient");
			personService.savePersonAttributeType(personAttributeType);
		}
		
		if (subChiefName == null) {
			PersonAttributeType personAttributeType = new PersonAttributeType();
			personAttributeType.setName("Name of the assistant chief");
			personAttributeType.setUuid(HarmonizedMetadataConstants._PersonAttributeType.SUBCHIEF_NAME);
			personAttributeType.setDescription("Name of the assistant chief of the area");
			personService.savePersonAttributeType(personAttributeType);
		}
		if (telephoneContact == null) {
			PersonAttributeType personAttributeType = new PersonAttributeType();
			personAttributeType.setName("telephone Contact");
			personAttributeType.setUuid(HarmonizedMetadataConstants._PersonAttributeType.TELEPHONE_CONTACT);
			personAttributeType.setDescription("Telephone Contact");
			personService.savePersonAttributeType(personAttributeType);
		}
		
		if (telephoneContact == null) {
			PersonAttributeType personAttributeType = new PersonAttributeType();
			personAttributeType.setName("telephone Contact");
			personAttributeType.setUuid(HarmonizedMetadataConstants._PersonAttributeType.TELEPHONE_CONTACT);
			personAttributeType.setDescription("Telephone Contact");
			personService.savePersonAttributeType(personAttributeType);
		}
		
		if (emailAddress == null) {
			PersonAttributeType personAttributeType = new PersonAttributeType();
			personAttributeType.setName("Email Address");
			personAttributeType.setUuid(HarmonizedMetadataConstants._PersonAttributeType.EMAIL_ADDRESS);
			personAttributeType.setDescription("Email Address");
			personService.savePersonAttributeType(personAttributeType);
		}
	}
	
}
