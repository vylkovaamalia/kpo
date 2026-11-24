package domain.models.student;

import domain.models.shared.CriterionId;
import domain.models.shared.Score;
import java.time.LocalDate;

class Grade {
    private final CriterionId criterionId;
    private final LocalDate date;
    private final String comment;
    private Score score;

    Grade(CriterionId criterionId, Score score, LocalDate date, String comment) {
        this.criterionId = criterionId;
        this.score = score;
        this.date = date;
        this.comment = comment;
    }

    public CriterionId criterionId() { return criterionId; }
    public Score score() { return score; }
    public LocalDate date() { return date; }
    public String comment() { return comment; }

    void changeScore(Score newScore) {
        this.score = newScore;
    }
}
