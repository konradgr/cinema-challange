import java.time.LocalDateTime

data class TimeSlot(
    val start: LocalDateTime,
    val end: LocalDateTime
) {
    fun inRange(timeSlot: TimeSlot): Boolean {
        if (this.start <= timeSlot.start && this.end >= timeSlot.start) {
            return true
        }
        if (this.start >= timeSlot.start && this.start <= timeSlot.end) {
            return true
        }

        return false
    }
}