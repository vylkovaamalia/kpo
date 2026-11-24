package domain.models.shared;

import java.util.Objects;
import java.util.UUID;

public record CriterionId(UUID value) {
    public CriterionId {
        Objects.requireNonNull(value, "CriterionId не может быть null");
    }

    public static CriterionId generate() {
        return new CriterionId(UUID.randomUUID());
    }

    public static CriterionId fromString(String uuidString) {
        try {
            return new CriterionId(UUID.fromString(uuidString));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Некорректный формат UUID критерия: " + uuidString, e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
