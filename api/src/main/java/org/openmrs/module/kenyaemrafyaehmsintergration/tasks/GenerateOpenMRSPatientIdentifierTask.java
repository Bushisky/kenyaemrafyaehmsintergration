package org.openmrs.module.kenyaemrafyaehmsintergration.tasks;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class GenerateOpenMRSPatientIdentifierTask extends AbstractTask {
	
	private static final Logger log = LoggerFactory.getLogger(GenerateOpenMRSPatientIdentifierTask.class);
	
	@Override
	public void execute() {
		if (!isExecuting) {
			if (log.isDebugEnabled()) {
				log.debug("Starting to generate missing openmrs ids for patients");
			}
			
			startExecuting();
			try {
				//do all the work here
				generateOpenMRSIdentifierForPatientsWithout();
			}
			catch (Exception e) {
				log.error("Error while generating OpenMRS id for patient", e);
			}
			finally {
				stopExecuting();
			}
		}
	}
	
	/**
	 * Generate an OpenMRS ID for patients who do not have one due to a migration from an old
	 * OpenMRS ID to a new one which contains a check-digit
	 **/
	protected void generateOpenMRSIdentifierForPatientsWithout() {
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
}
