package com.example.githubactions.services;

import com.example.githubactions.dto.PatientRegistrationRequest;
import com.example.githubactions.model.Patient;
import com.example.githubactions.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Patient.class)
class PatientServiceTest {

    private PatientRepository patientRepository = Mockito.mock(PatientRepository.class);;
    private PatientService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PatientService(patientRepository);
    }

    @Test
    void addPatient() {

        PatientRegistrationRequest request = new PatientRegistrationRequest(
                "Benchikh",
                "Anas",
                "0639767500");
        // when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());

        underTest.addPatient(request);

        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        verify(patientRepository).save(patientArgumentCaptor.capture());

        Patient capturedPatient = patientArgumentCaptor.getValue();

        assertThat(capturedPatient.getId()).isNull();
        assertThat(capturedPatient.getNom()).isEqualTo(request.nom());
        assertThat(capturedPatient.getPrenom()).isEqualTo(request.prenom());
        assertThat(capturedPatient.getTelephone()).isEqualTo(request.telephone());
    }

    @Test
    void getPatients() {
        // when
        underTest.getPatients();
        // then
        verify(patientRepository).findAll();
    }
}