package io.quarkus.demo.mail;

import java.util.List;

public record Order(String email, Recipe recipe, List<Exercise> offsettingWorkout) {
}
