package Repository;

import java.sql.Timestamp;

public record Message(String content, String sentBy, Timestamp sentDate) {
}
