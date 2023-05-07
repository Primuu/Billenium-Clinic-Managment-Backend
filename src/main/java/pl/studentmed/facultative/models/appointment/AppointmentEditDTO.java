package pl.studentmed.facultative.models.appointment;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AppointmentEditDTO(@NotNull Long appointmentId,
                                 String newStatus,
                                 LocalDateTime newAppointmentDate) {
}