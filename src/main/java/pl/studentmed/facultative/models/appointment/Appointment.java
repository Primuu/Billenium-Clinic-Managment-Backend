package pl.studentmed.facultative.models.appointment;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.studentmed.facultative.models.BasicEntity;
import pl.studentmed.facultative.models.doctor.Doctor;
import pl.studentmed.facultative.models.patient.Patient;

import java.time.LocalDateTime;

import static pl.studentmed.facultative.models.StudentMedDateUtils.DAY_MONTH_YEAR_TIME;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment extends BasicEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    private AppointmentDate appointmentDate;

    private String createdAt;

    private String modifiedAt;

    private String patientSymptoms;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String recommendations;

    private String medicinesTaken;
    private String diagnosis;

    public Appointment(Patient patient, Doctor doctor, AppointmentDate appointmentDate, String patientSymptoms, String medicinesTaken) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.patientSymptoms = patientSymptoms;
        this.status = AppointmentStatus.NEW;
        var now = LocalDateTime.now();
        this.createdAt = now.format(DAY_MONTH_YEAR_TIME);
        this.modifiedAt = now.format(DAY_MONTH_YEAR_TIME);
        this.medicinesTaken = medicinesTaken;
    }

    public boolean canBeCanceled() {
        var appointmentDate = this.appointmentDate.toLocalDateTime();
        var now = LocalDateTime.now();
        var appDateWithSubtracted24Hours = appointmentDate.minusHours(24);
        return appDateWithSubtracted24Hours.isAfter(now);
    }

}
