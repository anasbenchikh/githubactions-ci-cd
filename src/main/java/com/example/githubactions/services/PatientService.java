package com.example.githubactions.services;

import com.example.githubactions.dto.PatientRegistrationRequest;
import com.example.githubactions.exception.ResourceNotFoundException;
import com.example.githubactions.model.Patient;
import com.example.githubactions.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void addPatient(PatientRegistrationRequest patientRegistrationRequest) {
        Patient patient = new Patient(
                patientRegistrationRequest.nom(),
                patientRegistrationRequest.prenom(),
                patientRegistrationRequest.telephone()
        );

        patientRepository.save(patient);
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll().stream().toList();
    }

    public Patient findPatientById(Integer id) {
        if (id == null)
            throw new IllegalArgumentException();
        return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The patient with the id " + id + "is not found"));
    }

    public void deletePatient(int id) {
        this.patientRepository.deleteById(id);
    }

}
