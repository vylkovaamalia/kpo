package domain.models.shared;

public record Score(int value, int max) {
    public Score {
        if (max <= 0) {
            throw new IllegalArgumentException("Максимальный балл должен быть больше нуля");
        }
        if (value < 0 || value > max) {
            throw new IllegalArgumentException("Балл должен быть от 0 до " + max);
        }
    }
}
