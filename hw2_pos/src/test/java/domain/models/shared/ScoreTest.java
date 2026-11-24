package domain.models.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void shouldThrowExceptionIfScoreIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Score(-1, 10));
    }

    @Test
    void shouldThrowExceptionIfScoreExceedsMax() {
        assertThrows(IllegalArgumentException.class, () -> new Score(11, 10));
    }

    @Test
    void shouldCreateValidScore() {
        Score score = new Score(8, 10);
        assertEquals(8, score.value());
    }
}
