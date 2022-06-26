import model.Movie
import model.Room
import model.Seans
import org.junit.jupiter.api.Test
import service.PrepareSeansCalendarService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.test.assertEquals

class PrepareSeansCalendarServiceTest {
    private val prepareSeansCalendarService = PrepareSeansCalendarService()
    private val room = Room(1, 15, ConcurrentLinkedQueue())
    private val movie = Movie(false, 90, "Spectre")
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")


    @Test
    fun `when two overlapping seanses then one booked`() {

        //when
        val strDateMovieOne = "2022-06-26 11:30"
        val strDateMovieTwo = "2022-06-26 12:30"
        val startDateOne = LocalDateTime.parse(strDateMovieOne, formatter)
        val startDateTwo = LocalDateTime.parse(strDateMovieTwo, formatter)
        val seansList: MutableList<Seans> = mutableListOf()

        prepareSeansCalendarService.arrangeSeans(room, movie, startDateOne, false, seansList)
        prepareSeansCalendarService.arrangeSeans(room, movie, startDateTwo, false, seansList)

        assertEquals(1, seansList.size)

    }

    @Test
    fun `when premier movie before 17 then not booked`() {

        //when
        val strDateMovieOne = "2022-06-26 11:30"
        val startDate = LocalDateTime.parse(strDateMovieOne, formatter)
        val seansList: MutableList<Seans> = mutableListOf()

        prepareSeansCalendarService.arrangeSeans(room, movie, startDate, true, seansList)

        assertEquals(0, seansList.size)

    }

    @Test
    fun `when movie before 8 then not booked`() {

        //when
        val strDateMovieOne = "2022-06-26 07:30"
        val startDate = LocalDateTime.parse(strDateMovieOne, formatter)
        val seansList: MutableList<Seans> = mutableListOf()

        prepareSeansCalendarService.arrangeSeans(room, movie, startDate, false, seansList)

        assertEquals(0, seansList.size)

    }

    @Test
    fun `when movie starts after 22 then not booked`() {

        //when
        val strDateMovieOne = "2022-06-26 22:30"
        val startDate = LocalDateTime.parse(strDateMovieOne, formatter)
        val seansList: MutableList<Seans> = mutableListOf()

        prepareSeansCalendarService.arrangeSeans(room, movie, startDate, false, seansList)

        assertEquals(0, seansList.size)

    }

    @Test
    fun `when movie finish after 22 then not booked`() {

        //when
        val strDateMovieOne = "2022-06-26 21:30"
        val startDate = LocalDateTime.parse(strDateMovieOne, formatter)
        val seansList: MutableList<Seans> = mutableListOf()

        prepareSeansCalendarService.arrangeSeans(room, movie, startDate, false, seansList)

        assertEquals(0, seansList.size)

    }

    @Test
    fun `when room is not available then not booked`() {

        //when
        val strDateMovieOne = "2022-06-26 21:30"
        val startDate = LocalDateTime.parse(strDateMovieOne, formatter)

        val roomNotAvailableStartDateStr = "2022-06-26 05:30"
        val roomNotAvailableStartDate = LocalDateTime.parse(roomNotAvailableStartDateStr, formatter)

        val roomNotAvailableEndDateStr = "2022-06-27 22:00"
        val roomNotAvailableEndDate = LocalDateTime.parse(roomNotAvailableEndDateStr, formatter)

        val seansList: MutableList<Seans> = mutableListOf()

        room.nonAvailableSlots.add(TimeSlot(roomNotAvailableStartDate, roomNotAvailableEndDate))

        prepareSeansCalendarService.arrangeSeans(room, movie, startDate, false, seansList)

        assertEquals(0, seansList.size)

    }

}