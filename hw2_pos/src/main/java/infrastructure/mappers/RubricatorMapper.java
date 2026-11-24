package infrastructure.mappers;

import domain.models.rubricator.CriterionView;
import domain.models.rubricator.Rubricator;
import domain.models.shared.CriterionId;
import domain.models.shared.Weight;
import infrastructure.dtos.CriterionDto;
import infrastructure.dtos.RubricatorDto;

import java.util.ArrayList;

public class RubricatorMapper {
    public RubricatorDto toDto(Rubricator rubricator) {
        RubricatorDto dto = new RubricatorDto();
        dto.criteria = new ArrayList<>();
        for (CriterionView view : rubricator.getCriteriaView()) {
            CriterionDto c = new CriterionDto();
            c.id = view.id().toString();
            c.name = view.name();
            c.maxScore = view.maxScore();
            c.weight = view.weight().value();
            dto.criteria.add(c);
        }
        return dto;
    }

    public Rubricator toDomain(RubricatorDto dto) {
        Rubricator rubricator = new Rubricator();
        if (dto != null && dto.criteria != null) {
            for (CriterionDto c : dto.criteria) {
                rubricator.restoreCriterion(
                        CriterionId.fromString(c.id),
                        c.name,
                        c.maxScore,
                        new Weight(c.weight)
                );
            }
        }
        return rubricator;
    }
}
