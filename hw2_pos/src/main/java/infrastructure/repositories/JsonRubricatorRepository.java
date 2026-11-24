package infrastructure.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.models.rubricator.Rubricator;
import domain.repositories.RubricatorRepository;
import infrastructure.dtos.RubricatorDto;
import infrastructure.mappers.RubricatorMapper;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class JsonRubricatorRepository implements RubricatorRepository {
    private final File file;
    private final ObjectMapper mapper;
    private final RubricatorMapper rubricatorMapper = new RubricatorMapper();
    private Rubricator cache;

    public JsonRubricatorRepository(String filePath) {
        this.file = new File(filePath);
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.cache = load();
    }

    private Rubricator load() {
        if (!file.exists()) return new Rubricator();
        try {
            RubricatorDto dto = mapper.readValue(file, RubricatorDto.class);
            return rubricatorMapper.toDomain(dto);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения рубрикатора", e);
        }
    }

    private void saveToFile() {
        try {
            RubricatorDto dto = rubricatorMapper.toDto(cache);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, dto);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи рубрикатора", e);
        }
    }

    @Override
    public void save(Rubricator rubricator) {
        this.cache = rubricator;
        saveToFile();
    }

    @Override
    public Optional<Rubricator> get() {
        return Optional.of(cache);
    }
}
