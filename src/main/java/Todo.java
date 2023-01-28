import java.util.regex.Pattern;

public class Todo extends Task {
    public Todo (String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + "[" + super.getStatusIcon() + "] " + super.getDescription();
    }
}
