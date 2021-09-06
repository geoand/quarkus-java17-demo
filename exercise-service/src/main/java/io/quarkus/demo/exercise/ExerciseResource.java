package io.quarkus.demo.exercise;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Path("exercise")
public class ExerciseResource {

    @Path("workout/random")
    @GET
    public List<ExerciseDTO> randomWorkout() {
        Optional<ExerciseDTO> cardio = randomExercise(Exercise.list("type", ExerciseType.Cardio.name()));
        Optional<ExerciseDTO> weights = randomExercise(Exercise.list("type", ExerciseType.Weights.name()));
        if (cardio.isEmpty() || weights.isEmpty()) {
            throw new WebApplicationException("Unable to obtain random exercise", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return List.of(cardio.get(), weights.get());
    }

    private Optional<ExerciseDTO> randomExercise(List<Exercise> all) {
        if (all.isEmpty()) {
            return Optional.empty();
        }
        Exercise exercise = all.get(ThreadLocalRandom.current().nextInt(all.size()));
        return Optional.of(new ExerciseDTO(exercise.name, ExerciseType.valueOf(exercise.type)));
    }
}
