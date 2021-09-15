package io.quarkus.demo.wellness;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("recipe")
public class RecipeResource {

    private final SpoonacularRecipesClient spoonacularRecipesClient;
    private final ExerciseClient exerciseClient;
    private final Emitter<Order> orderEmitter;

    public RecipeResource(@RestClient SpoonacularRecipesClient spoonacularRecipesClient,
                          @RestClient ExerciseClient exerciseClient,
                          @Channel("ordered-recipes") Emitter<Order> orderEmitter) {
        this.spoonacularRecipesClient = spoonacularRecipesClient;
        this.exerciseClient = exerciseClient;
        this.orderEmitter = orderEmitter;
    }

    @Path("tag/{tag}")
    @GET
    public List<Recipe> byTag(String tag) {
        return spoonacularRecipesClient.random(tag, 5).recipes();
    }

    @Path("{id}")
    @GET
    public Recipe byId(long id) {
        return spoonacularRecipesClient.information(id);
    }

    @Path("{id}")
    @POST
    public Order order(@RestPath long id, @RestQuery @DefaultValue("test@quarkus.io") String email) {
        Recipe recipe = spoonacularRecipesClient.information(id);
        List<Exercise> offsettingWorkout = getOffsettingWorkout(recipe);
        Order order = new Order(email, recipe, offsettingWorkout);
        orderEmitter.send(order).toCompletableFuture().join();
        return order;
    }

    private List<Exercise> getOffsettingWorkout(Recipe recipe) {
        return recipe.veryHealthy() ? List.of() : exerciseClient.randomWorkout();
    }
}
