import model.Movie
import model.Room
import model.Seans
import service.PrepareSeansCalendarService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentLinkedQueue

fun main() {

    //example input from employee

    val room = Room(1, 15, ConcurrentLinkedQueue())
    val movie = Movie(false, 90, "Spectre")

    val strDate = "2022-06-26 11:30"
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    val startDate = LocalDateTime.parse(strDate, formatter)
    val seansList: MutableList<Seans> = mutableListOf()

    val prepareSeansCalendarService = PrepareSeansCalendarService()

    prepareSeansCalendarService.arrangeSeans(room, movie, startDate, false, seansList)

    for(seans in seansList){
        println(seans)

    }
}



