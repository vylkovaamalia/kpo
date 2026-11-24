package domain.models.shared;

public record FinalScore(double value) {
    public FinalScore {
        if (value < 0.0) {
            throw new IllegalArgumentException("Итоговый балл не может быть отрицательным");
        }
    }
}
