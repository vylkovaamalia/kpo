package domain.models.student;

import java.util.Objects;
import java.util.UUID;

public record StudentId(UUID value) {
    public StudentId {
        Objects.requireNonNull(value, "StudentId не может быть null");
    }

    public static StudentId generate() {
        return new StudentId(UUID.randomUUID());
    }

    public static StudentId fromString(String uuidString) {
        try {
            return new StudentId(UUID.fromString(uuidString));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Некорректный формат UUID: " + uuidString, e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
