package pl.studentmed.facultative.services.appointment.crud;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.studentmed.facultative.models.appointment.*;
import pl.studentmed.facultative.models.doctor.Doctor;

import java.util.List;
import java.util.Optional;

interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByAppointmentDateAndDoctor(AppointmentDate appointmentDate, Doctor doctor);

    @Query("""
            select new pl.studentmed.facultative.models.appointment.AppointmentResponseDTO
                (
                app.id,
                concat(p.userInfo.firstName, ' ', p.userInfo.lastName),
                concat(d.userInfo.firstName, ' ', d.userInfo.lastName),
                app.appointmentDate.date,
                app.patientSymptoms,
                app.medicinesTaken,
                app.status,
                app.recommendations,
                app.createdAt,
                app.modifiedAt,
                app.diagnosis
                )
            from Appointment app
            join fetch Patient p on app.patient = p
            join fetch Doctor d on app.doctor = d
            where app.id = :appointmentId
            """)
    Optional<AppointmentResponseDTO> getAppointmentById(@Param("appointmentId") Long appointmentId);

    @Query(
            """
            select new pl.studentmed.facultative.models.appointment.AppointmentResponseDTO
                (
                app.id,
                concat(p.userInfo.firstName, ' ', p.userInfo.lastName),
                concat(d.userInfo.firstName, ' ', d.userInfo.lastName),
                app.appointmentDate.date,
                app.patientSymptoms,
                app.medicinesTaken,
                app.status,
                app.recommendations,
                app.createdAt,
                app.modifiedAt,
                app.diagnosis
                )
            from Appointment app
            join fetch Patient p on app.patient = p
            join fetch Doctor d on app.doctor = d
            where d.id = :doctorId
            order by app.appointmentDate.date asc
            """
    )
    List<AppointmentResponseDTO> getAppointmentsByDoctorId(@Param("doctorId") Long doctorId, Pageable pageable);

    @Query(
            """
            select new pl.studentmed.facultative.models.appointment.AppointmentResponseDTO
                (
                app.id,
                concat(p.userInfo.firstName, ' ', p.userInfo.lastName),
                concat(d.userInfo.firstName, ' ', d.userInfo.lastName),
                app.appointmentDate.date,
                app.patientSymptoms,
                app.medicinesTaken,
                app.status,
                app.recommendations,
                app.createdAt,
                app.modifiedAt,
                app.diagnosis
                )
            from Appointment app
            join fetch Patient p on app.patient = p
            join fetch Doctor d on app.doctor = d
            where d.id = :doctorId
            and app.appointmentDate.date like :appointmentDate%
            order by app.appointmentDate.date asc
            """
    )
    List<AppointmentResponseDTO> getAppointmentsByDoctorIdAndAppointmentDateLike(@Param("doctorId") Long doctorId,
                                                                                 @Param("appointmentDate") String appointmentDate,
                                                                                 Pageable pageable);
    @Query(
            """
            select new pl.studentmed.facultative.models.appointment.AppointmentResponseDTO
                (
                app.id,
                concat(p.userInfo.firstName, ' ', p.userInfo.lastName),
                concat(d.userInfo.firstName, ' ', d.userInfo.lastName),
                app.appointmentDate.date,
                app.patientSymptoms,
                app.medicinesTaken,
                app.status,
                app.recommendations,
                app.createdAt,
                app.modifiedAt,
                app.diagnosis
                )
            from Appointment app
            join fetch Patient p on app.patient = p
            join fetch Doctor d on app.doctor = d
            where p.id = :patientId
            and app.status = :status
            and app.appointmentDate.date like :appointmentDate%
            order by app.appointmentDate.date asc
            """
    )
    List<AppointmentResponseDTO> getAppointmentsByPatientIdAndAppointmentDateLike(@Param("patientId") Long patientId,
                                                                                  @Param("status") AppointmentStatus status,
                                                                                  @Param("appointmentDate") String wantedDate,
                                                                                  Pageable pageable);

    @Query("""
           select new pl.studentmed.facultative.models.appointment.AppointmentResponseDTO
                (
                app.id,
                concat(p.userInfo.firstName, ' ', p.userInfo.lastName),
                concat(d.userInfo.firstName, ' ', d.userInfo.lastName),
                app.appointmentDate.date,
                app.patientSymptoms,
                app.medicinesTaken,
                app.status,
                app.recommendations,
                app.createdAt,
                app.modifiedAt,
                app.diagnosis
                )
            from Appointment app
            join fetch Patient p on app.patient = p
            join fetch Doctor d on app.doctor = d
            where p.id = :patientId
            and app.status = :status
            order by app.appointmentDate.date asc
           """)
    List<AppointmentResponseDTO> getAppointmentsByPatientIdAndStatus(@Param("patientId") Long patientId, @Param("status") AppointmentStatus status, Pageable pageable);


    @Query("""
           select new pl.studentmed.facultative.models.appointment.AppointmentResponseDTO
                (
                app.id,
                concat(p.userInfo.firstName, ' ', p.userInfo.lastName),
                concat(d.userInfo.firstName, ' ', d.userInfo.lastName),
                app.appointmentDate.date,
                app.patientSymptoms,
                app.medicinesTaken,
                app.status,
                app.recommendations,
                app.createdAt,
                app.modifiedAt,
                app.diagnosis
                )
            from Appointment app
            join fetch Patient p on app.patient = p
            join fetch Doctor d on app.doctor = d
            where app.status = 'NEW'
            order by app.appointmentDate.date asc
           """)
    List<AppointmentResponseDTO> getAllNewAppointments(Pageable pageable);

    @Query("""
            select
                (
                substring(app.appointmentDate.date, 12)
                )
            from Appointment app
            where app.appointmentDate.date like :appointmentDate%
            and app.doctor.id = :doctorId
           """)
    List<String> getBusyAppointmentsHoursByGivenDateAndDoctorId(@Param("appointmentDate") String appointmentDate,
                                                                @Param("doctorId") Long doctorId);

    @Query("""
            select new pl.studentmed.facultative.models.appointment.AppointmentResponseDTO
                (
                app.id,
                concat(p.userInfo.firstName, ' ', p.userInfo.lastName),
                concat(d.userInfo.firstName, ' ', d.userInfo.lastName),
                app.appointmentDate.date,
                app.patientSymptoms,
                app.medicinesTaken,
                app.status,
                app.recommendations,
                app.createdAt,
                app.modifiedAt,
                app.diagnosis
                )
            from Appointment app
            join fetch Patient p on app.patient = p
            join fetch Doctor d on app.doctor = d
            where p.id = :patientId
            and app.status = :status
            and app.appointmentDate.date between :startDate AND :endDate
            order by app.appointmentDate.date asc
           """)
    List<AppointmentResponseDTO> getAppointmentsByPatientIdAndDateBetween(@Param("patientId") Long patientId,
                                                                          @Param("status") AppointmentStatus status,
                                                                          @Param("startDate") String startDate,
                                                                          @Param("endDate") String endDate,
                                                                          PageRequest pageable);

    @Query("""
           select new pl.studentmed.facultative.models.appointment.AppointmentResponseDTO
                (
                app.id,
                concat(p.userInfo.firstName, ' ', p.userInfo.lastName),
                concat(d.userInfo.firstName, ' ', d.userInfo.lastName),
                app.appointmentDate.date,
                app.patientSymptoms,
                app.medicinesTaken,
                app.status,
                app.recommendations,
                app.createdAt,
                app.modifiedAt,
                app.diagnosis
                )
            from Appointment app
            join fetch Patient p on app.patient = p
            join fetch Doctor d on app.doctor = d
            where p.id = :patientId
            order by app.appointmentDate.date asc
           """)
    List<AppointmentResponseDTO> getAppointmentsByPatientId(@Param("patientId") Long patientId, PageRequest pageable);

    @Query("""
           select new pl.studentmed.facultative.models.appointment.AppointmentResponseDTO
                (
                app.id,
                concat(p.userInfo.firstName, ' ', p.userInfo.lastName),
                concat(d.userInfo.firstName, ' ', d.userInfo.lastName),
                app.appointmentDate.date,
                app.patientSymptoms,
                app.medicinesTaken,
                app.status,
                app.recommendations,
                app.createdAt,
                app.modifiedAt,
                app.diagnosis
                )
            from Appointment app
            join fetch Patient p on app.patient = p
            join fetch Doctor d on app.doctor = d
            order by app.appointmentDate.date asc
           """)
    List<AppointmentResponseDTO> getAllAppointmentsMapped();

}
