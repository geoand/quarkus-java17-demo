package io.quarkus.demo.exercise;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class ExerciseResourceTest {

    @Test
    public void testRandomWorkout() {
        when().get("/exercise/workout/random")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(2));
    }
}
