# Домашнее задание №2: ПОС

## 1. Архитектура
Выбрана **Onion Architecture**.

*   **Domain (`domain`):** Ядро. Не знает про JSON, файлы и консоль.
    *   `models`: Агрегаты (`Student`, `Rubricator`), сущности (`Grade`, `Criterion`) и Value Objects (`Score`, `Weight`, `...Id`).
    *   `services`: Доменный сервис `FinalScoreCalculator`.
    *   `repositories`: Интерфейсы (порты) для сохранения.
*   **Application (`application`):** Сценарии использования (Use Cases).
    *   `services`: `AddGradeService`, `CalculateFinalScoreService` и др.
    *   `port`: Выходной порт `ReportExporter`.
*   **Infrastructure (`infrastructure`):** Адаптеры. Реализация репозиториев для JSON, DTO, мапперы, реализация экспортера.
*   **Presentation (`presentation`):** Консольный UI. Зависит только от слоя Application.
*   **Composition Root:** `Main.java` — единственное место, где собираются все слои (DI-конструктор).

## 2. DDD
*   **Студент (`Student`)** — Aggregate Root. Управляет оценками.
*   **Критерий (`Criterion`)** — Entity внутри агрегата `Rubricator`. Имеет максимум и вес. Класс скрыт (package-private).
*   **Оценка (`Grade`)** — Entity внутри агрегата `Student`. Скрыта от внешнего мира (package-private), наружу отдается через безопасный `GradeView`.
*   **Итог (`FinalScore`)** — Value Object. Рассчитывается на основе оценок и весов.
*   **Инварианты:**
    *   Балл не может быть < 0 или > максимума (`Score`).
    *   Вес критерия от 0.0 до 1.0 (`Weight`).
    *   Нельзя выставить две оценки по одному критерию (`Student.addGrade`).
    *   Оценку можно выставить только по существующему критерию (проверяется в `AddGradeService`).
*   **Domain Service:** `FinalScoreCalculator` — правило расчета итога не принадлежит ни студенту, ни критерию, поэтому вынесено в отдельный сервис.

## 3. Паттерны GoF
В проекте применены паттерны из разных групп:
*   **Порождающий (Creational): Factory Method.**
    *   *Где:* Статические методы `StudentId.generate()`, `CriterionId.generate()`, а также компактные конструкторы в `Score` и `Weight`.
    *   *Зачем:* Единая точка создания объектов с гарантией валидности (защита инвариантов на этапе создания).
*   **Структурный (Structural): Facade.**
    *   *Где:* `QueryService`.
    *   *Зачем:* Предоставляет простой интерфейс для получения данных из разных репозиториев (студенты, рубрикатор, оценки).
*   **Поведенческий (Behavioral): Strategy.**
    *   *Где:* Интерфейс `ReportExporter` (в Application) и его реализация `JsonReportExporter` (в Infrastructure).
    *   *Зачем:* Слой Application не привязан к конкретному формату файла. Он работает через абстракцию (порт). Если завтра потребуется сохранять отчет не в JSON, а в CSV, просто создадим новую реализацию, не меняя бизнес-логики.

## 4. Роль ИИ
ИИ использовался для обсуждения архитектурных решений, генерации шаблонного кода и вопросов по java.