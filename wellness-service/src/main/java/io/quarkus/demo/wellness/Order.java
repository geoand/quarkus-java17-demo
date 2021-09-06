package io.quarkus.demo.wellness;

import java.util.List;

public record Order(String email, Recipe recipe, List<Exercise> offsettingWorkout) {
}
