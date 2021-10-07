package io.quarkus.demo.exercise;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Path("exercise")
public class ExerciseResource {

    public static final RandomGenerator RANDOM_GENERATOR = RandomGenerator.getDefault();

    @GET
    @Path("workout/random")
    public List<ExerciseDTO> randomWorkout() {
        List<Exercise> allWeights = Exercise.list("type", ExerciseType.Weights.name());
        List<Exercise> allCardio = Exercise.list("type", ExerciseType.Cardio.name());
        var cardio = random(allCardio);
        var weights = random(allWeights);
        if (cardio.isEmpty() || weights.isEmpty()) {
            throw new WebApplicationException("Unable to obtain a random workout");
        }
        return List.of(fromExercise(cardio.get()), fromExercise(weights.get()));
    }

    private Optional<Exercise> random(List<Exercise> all) {
        if (all.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(all.get(RANDOM_GENERATOR.nextInt(all.size())));
    }

    private ExerciseDTO fromExercise(Exercise exercise) {
        return new ExerciseDTO(exercise.name, ExerciseType.valueOf(exercise.type));
    }
}
