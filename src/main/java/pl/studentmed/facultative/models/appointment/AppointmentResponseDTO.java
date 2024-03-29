package pl.studentmed.facultative.models.appointment;

import lombok.Builder;

public record AppointmentResponseDTO(Long appointmentId,
                                     String patientName,
                                     String doctorName,
                                     String appointmentDate,
                                     String patientSymptoms,
                                     String medicinesTaken,
                                     AppointmentStatus appointmentStatus,
                                     String doctorRecommendations,
                                     String createdAt,
                                     String modifiedAt,
                                     String diagnosis) {

    @Builder public AppointmentResponseDTO {}

}
