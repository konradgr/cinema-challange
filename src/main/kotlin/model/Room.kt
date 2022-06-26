package model

import TimeSlot
import java.util.concurrent.ConcurrentLinkedQueue

data class Room(
    val id: Int,
    val cleaningSlot: Long,
    val nonAvailableSlots: ConcurrentLinkedQueue<TimeSlot>
)