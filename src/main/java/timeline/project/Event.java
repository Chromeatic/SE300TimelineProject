package timeline.project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event implements Comparable<Event> {

    private String name;
    private String timeString;
    private String description;
    private LocalDate date;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy_MM_dd");

    public Event(String name, String time, String description)
    {
        this.name = name.trim();
        this.timeString = time.trim();
        this.description = description.trim();
        this.date = parseTime(this.timeString); 
    }

    private LocalDate parseTime(String timeStr) {
        timeStr = timeStr.trim();
        try {
            return LocalDate.parse(timeStr, FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Warning: Could not parse date string: '" + timeStr + "' using format 'yyyy_MM_dd'. Defaulting date to minimum.");
            return LocalDate.MIN;
        }
    }


    public String getName() {
        return name;
    }

    public String getTimeString()
    {
        return timeString;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription()
    {
        return description;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setTime(String time) {
        this.timeString = time.trim();
        this.date = parseTime(this.timeString); 
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    @Override
    public int compareTo(Event other) {
        if (this.date == null && other.date == null) {
            return 0;
        } else if (this.date == null) {
            return -1; 
        } else if (other.date == null) {
            return 1; 
        }
        return this.date.compareTo(other.date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return java.util.Objects.equals(name, event.name) &&
               java.util.Objects.equals(date, event.date);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, date);
    }
}
