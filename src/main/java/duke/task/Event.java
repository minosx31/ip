package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event that is a Task.
 */
public class Event extends Task {
    private String[] period;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM d yyyy, h:mm a");

    public Event(String description, String[] period) {
        super(description);
        this.period = period;
        this.fromDateTime = LocalDateTime.parse(period[0]);
        this.toDateTime = LocalDateTime.parse(period[1]);
    }

    /**
     * Translates the task into data format.
     * @return A String that represents the task in data format.
     */
    public String toData() {
        return String.format("E | %s | %s | %s to %s",this.getStatusIcon(),
                this.getDescription(), this.period[0], this.period[1]);
    }

    @Override
    public String toString() {
        return "[E]" + "[" + this.getStatusIcon() + "] " + this.getDescription()
                + " (from: " + this.fromDateTime.format(DATE_TIME_FORMATTER)
                + " to: " + this.toDateTime.format(DATE_TIME_FORMATTER) + ")";
    }
}
