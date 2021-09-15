package io.quarkus.demo.wellness;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;

@RegisterRestClient(configKey="exercise-api")
@Path("exercise")
public interface ExerciseClient {

    @Path("workout/random")
    @GET
    @Operation(hidden = true) // hide operation from OpenAPI
    List<Exercise> randomWorkout();
}
