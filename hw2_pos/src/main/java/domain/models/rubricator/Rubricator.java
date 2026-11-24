package domain.models.rubricator;

import domain.models.shared.CriterionId;
import domain.models.shared.Weight;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Rubricator {
    private final List<Criterion> criteria;

    public Rubricator() {
        this.criteria = new ArrayList<>();
    }

    public void addCriterion(String name, int maxScore, Weight weight) {
        CriterionId newId = CriterionId.generate();
        Criterion criterion = new Criterion(newId, name, maxScore, weight);
        criteria.add(criterion);
    }

    public Optional<CriterionView> findCriterion(CriterionId id) {
        for (Criterion c : criteria) {
            if (c.id().equals(id)) {
                return Optional.of(new CriterionView(c.id(), c.name(), c.maxScore(), c.weight()));
            }
        }
        return Optional.empty();
    }

    public void restoreCriterion(CriterionId id, String name, int maxScore, Weight weight) {
        criteria.add(new Criterion(id, name, maxScore, weight));
    }

    public List<CriterionView> getCriteriaView() {
        List<CriterionView> views = new ArrayList<>();
        for (Criterion c : criteria) {
            views.add(new CriterionView(c.id(), c.name(), c.maxScore(), c.weight()));
        }
        return views;
    }
}
