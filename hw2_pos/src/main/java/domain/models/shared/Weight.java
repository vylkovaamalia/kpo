package domain.models.shared;

public record Weight(double value) {
    public Weight {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("Вес должен быть от 0.0 до 1.0");
        }
    }
}
