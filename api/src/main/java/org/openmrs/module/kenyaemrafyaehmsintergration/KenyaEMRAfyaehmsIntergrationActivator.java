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
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.kenyaemrafyaehmsintergration.metadata.HarmonizedMetadataConstants;
import org.openmrs.module.kenyaemrafyaehmsintergration.metadata.Metadata;

import java.util.List;
import java.util.UUID;

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
		//getIdentifierMetadataForApplication();
		//Load person attribute types
		//getPersonAttributesMetadata();
		// generate OpenMRS ID for patients without the identifier
		generateOpenMRSIdentifierForPatientsWithout();
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
	
	protected PatientIdentifier generatePatientIdentifier(String type) {
		IdentifierSourceService iss = Context.getService(IdentifierSourceService.class);
		IdentifierSource idSource = iss.getIdentifierSource(1); // this is the default OpenMRS identifier source
		PatientService patientService = Context.getPatientService();
		
		UUID uuid = UUID.randomUUID();
		PatientIdentifier pid = new PatientIdentifier();
		
		PatientIdentifierType patientIdentifierType_old = patientService
		        .getPatientIdentifierTypeByUuid("dfacd928-0370-4315-99d7-6ec1c9f7ae76");
		PatientIdentifierType patientIdentifierType_new = patientService
		        .getPatientIdentifierTypeByUuid("05a29f94-c0ed-11e2-94be-8c13b969e334");
		
		if (type.equals("old")) {
			pid.setIdentifierType(patientIdentifierType_old);
			String identifier = Context.getService(IdentifierSourceService.class).generateIdentifier(
			    patientIdentifierType_old, "Registration");
			pid.setIdentifier(identifier);
			pid.setLocation(Context.getLocationService().getLocation(1));
			pid.setPreferred(true);
			pid.setUuid(String.valueOf(uuid));
		} else if (type.equals("new")) {
			pid.setIdentifierType(patientIdentifierType_new);
			String identifier = iss.generateIdentifier(idSource, "New OpenMRS ID with CheckDigit");
			pid.setIdentifier(identifier);
			pid.setLocation(Context.getLocationService().getLocation(1));
			pid.setPreferred(true);
			pid.setUuid(String.valueOf(uuid));
		}
		
		return pid;
		
	}
	
	/**
	 * Generate an OpenMRS ID for patients who do not have one due to a migration from an old
	 * OpenMRS ID to a new one which contains a check-digit
	 **/
	private void generateOpenMRSIdentifierForPatientsWithout() {
		PatientService patientService = Context.getPatientService();
		AdministrationService as = Context.getAdministrationService();
		
		List<List<Object>> patientIds_old = as
		        .executeSQL(
		            "SELECT patient_id FROM patient_identifier WHERE patient_id NOT IN (SELECT patient_id FROM patient_identifier p INNER JOIN patient_identifier_type pt ON (p.identifier_type = pt.patient_identifier_type_id AND pt.uuid = 'dfacd928-0370-4315-99d7-6ec1c9f7ae76'))",
		            true);
		
		List<List<Object>> patientIds_new = as
		        .executeSQL(
		            "SELECT patient_id FROM patient_identifier WHERE patient_id NOT IN (SELECT patient_id FROM patient_identifier p INNER JOIN patient_identifier_type pt ON (p.identifier_type = pt.patient_identifier_type_id AND pt.uuid = '05a29f94-c0ed-11e2-94be-8c13b969e334'))",
		            true);
		
		if (patientIds_old.size() == 0 || patientIds_new.size() == 0) {
			// no patients to process
			return;
		}
		// get the identifier source copied from RegistrationCoreServiceImpl
		
		for (List<Object> row : patientIds_old) {
			Patient p = patientService.getPatient((Integer) row.get(0));
			// Create new Patient Identifier
			PatientIdentifier pid = generatePatientIdentifier("old");
			pid.setPatient(p);
			try {
				log.info("Adding OpenMRS ID " + pid.getIdentifier() + " to patient with id " + p.getPatientId());
				// Save the patient Identifier
				patientService.savePatientIdentifier(pid);
			}
			catch (Exception e) {
				// log the error to the alert service but do not rethrow the exception since the module has to start
				log.error("Error updating OpenMRS identifier for patient #" + p.getPatientId(), e);
			}
		}
		log.info("All patients updated with  OLD OpenMRS ID");
		
		for (List<Object> row : patientIds_new) {
			Patient p = patientService.getPatient((Integer) row.get(0));
			// Create new Patient Identifier
			PatientIdentifier pid = generatePatientIdentifier("new");
			pid.setPatient(p);
			try {
				log.info("Adding new OpenMRS ID " + pid.getIdentifier() + " to patient with id " + p.getPatientId());
				// Save the patient Identifier
				patientService.savePatientIdentifier(pid);
			}
			catch (Exception e) {
				// log the error to the alert service but do not rethrow the exception since the module has to start
				log.error("Error updating OpenMRS identifier for patient #" + p.getPatientId(), e);
			}
		}
		log.info("All patients updated with  NEW OpenMRS ID");
	}
	
}
