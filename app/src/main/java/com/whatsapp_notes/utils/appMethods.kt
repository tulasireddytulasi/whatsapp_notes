import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

/**
 * Converts a UTC timestamp string (e.g., "2025-07-13T16:07:00Z") into a human-readable
 * relative time string (e.g., "5 mins ago", "Yesterday 6:20 PM", "Monday - 3:20 PM").
 *
 * @param timestampString The timestamp in ISO 8601 UTC format (YYYY-MM-DDTHH:MM:SSZ).
 * @return A string representing the relative time, or an error message if the format is invalid.
 */
fun getRelativeTime(timestampString: String): String {
    try {
        val pastInstant = Instant.parse(timestampString)
        val nowInstant = Instant.now()

        val duration = Duration.between(pastInstant, nowInstant)

        val seconds = duration.toSeconds()
        val minutes = duration.toMinutes()
        val hours = duration.toHours()

        // Convert Instants to ZonedDateTimes using the system's default time zone
        val pastDateTime = ZonedDateTime.ofInstant(pastInstant, ZoneId.systemDefault())
        val nowDateTime = ZonedDateTime.ofInstant(nowInstant, ZoneId.systemDefault())

        // Calculate differences in calendar units for accurate "days ago", "yesterday" logic
        val daysBetween = ChronoUnit.DAYS.between(pastDateTime.toLocalDate(), nowDateTime.toLocalDate())

        // Define formatters for 12-hour time with AM/PM
        val timeFormatter = DateTimeFormatter.ofPattern("h:mm a") // e.g., 3:15 PM
        val dayOfWeekTimeFormatter = DateTimeFormatter.ofPattern("EEEE - h:mm a") // e.g., Monday - 3:20 PM
        val fullDateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy - h:mm a") // e.g., 3 July 2025 - 4:34 PM

        return when {
            seconds < 60 -> "Just now" // Less than 1 minute
            minutes < 60 -> "${minutes} mins ago" // Less than 1 hour
            hours < 24 && daysBetween == 0L -> pastDateTime.format(timeFormatter) // Today, but more than an hour ago
            daysBetween == 1L -> "Yesterday ${pastDateTime.format(timeFormatter)}" // Exactly 1 day ago (calendar day)
            daysBetween < 7 -> pastDateTime.format(dayOfWeekTimeFormatter) // Less than 1 week, but not yesterday or today
            else -> pastDateTime.format(fullDateTimeFormatter) // Older than 1 week
        }
    } catch (e: DateTimeParseException) {
        return "Invalid timestamp format: ${e.message}"
    } catch (e: Exception) {
        return "Error calculating time: ${e.message}"
    }
}
