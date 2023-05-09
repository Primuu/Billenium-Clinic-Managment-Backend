package pl.studentmed.facultative.services.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.studentmed.facultative.models.appointment.*;
import pl.studentmed.facultative.services.appointment.crud.AppointmentCRUDService;
import pl.studentmed.facultative.services.doctor.crud.DoctorCRUDService;
import pl.studentmed.facultative.services.patient.crud.PatientCRUDService;

import java.time.LocalDate;
import java.util.List;

import static pl.studentmed.facultative.models.DTOMapper.toDTO;

@Component
@RequiredArgsConstructor
class AppointmentFacade {

    private final AppointmentCRUDService appointmentCRUDService;
    private final PatientCRUDService patientCRUDService;
    private final DoctorCRUDService doctorCRUDService;

    public AppointmentResponseDTO getAppointmentDTOById(Long appointmentId) {
        return appointmentCRUDService.getAppointmentDTOById(appointmentId);
    }

    public AppointmentResponseDTO createAppointment(AppointmentCreateDTO dto) {
        var patient = patientCRUDService.getPatientById(dto.patientId());
        var doctor = doctorCRUDService.getDoctorById(dto.doctorId());
        var appointmentDate = new AppointmentDate(dto.appointmentDate());
        var appointment = appointmentCRUDService.createAppointment(patient, doctor, appointmentDate, dto.patientSymptoms());
        return toDTO(appointment);
    }

    public List<AppointmentBusyHoursDTO> getBusyAppointmentHoursForDate(LocalDate givenDate) {
        return appointmentCRUDService.getBusyAppointmentHoursForDate(givenDate);
    }

    public AppointmentResponseDTO editAppointment(AppointmentEditDTO dto) {
        var appointmentToEdit = appointmentCRUDService.getAppointmentById(dto.appointmentId());
        var editedAppointment = appointmentCRUDService.editAppointment(appointmentToEdit, dto);
        return toDTO(editedAppointment);
    }

}
