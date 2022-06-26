package model

import TimeSlot
import java.time.LocalDateTime

data class Seans(
    val premier: Boolean,
    val movie: Movie,
    val room: Room,
    val startDate: LocalDateTime,
    val timeSlot: TimeSlot = TimeSlot(
        startDate, startDate.plusMinutes(movie.duration).plusMinutes(room.cleaningSlot)
    )
)