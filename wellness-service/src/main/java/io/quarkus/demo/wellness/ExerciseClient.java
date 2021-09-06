package io.quarkus.demo.wellness;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@RegisterRestClient(configKey="exercise-api")
@Path("exercise")
public interface ExerciseClient {

    @Path("workout/random")
    @GET
    List<Exercise> randomWorkout();
}
