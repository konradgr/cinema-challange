package service

import model.Movie
import model.Room
import model.Seans
import java.time.LocalDateTime
import java.time.LocalTime

class PrepareSeansCalendarService {

    companion object {
        val premierTime: LocalTime = LocalTime.of(17, 0)
        val cinemaCloseHour: LocalTime = LocalTime.of(22, 0)
        val cinemaOpenHour: LocalTime = LocalTime.of(8, 0)
    }

    /*
    main method for booking seanses takes given movie in chosen room at startDate (with time),
    takes flag if seans is a premier
    seansList treated as Data Base
     */
    fun arrangeSeans(
        room: Room,
        movie: Movie,
        startDate: LocalDateTime,
        premier: Boolean,
        seansList: MutableList<Seans>
    ): MutableList<Seans> {

        val seans = Seans(premier, movie, room, startDate)

        if (isRoomAvailable(seans) && isOkForPremier(seans) && isCinemaOpen(seans)) {
            seansList.add(seans)
            seans.timeSlot.let { seans.room.nonAvailableSlots.add(it) }
        }

        return seansList

    }


    /*
    checks if given seans timeSlot doesn't overLap already taken time slots in given room
     */
    private fun isRoomAvailable(seans: Seans): Boolean {
        for (nonAvailableSlot in seans.room.nonAvailableSlots) {
            if (seans.timeSlot.inRange(nonAvailableSlot)) {
                return false
            }
        }
        return true
    }

    private fun isOkForPremier(seans: Seans): Boolean {
        return if (seans.premier) {
            seans.startDate.hour >= premierTime.hour
        } else true
    }

    /*
    First check is for time before cinema opens
    Second check is for time after cinema closes
    Third check is for time after cinema closes - hours after midnight
     */
    private fun isCinemaOpen(seans: Seans): Boolean {
        return seans.startDate.hour >= cinemaOpenHour.hour && LocalTime.of(
            seans.timeSlot.end.hour,
            seans.timeSlot.end.minute
        ) <= cinemaCloseHour && LocalTime.of(
            seans.timeSlot.end.hour,
            seans.timeSlot.end.minute
        ) >= cinemaOpenHour
    }
}