package io.quarkus.demo.wellness;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("recipes")
@RegisterRestClient(configKey="spoonacular-recipe-api")
@ClientHeaderParam(name = "x-rapidapi-host", value = "${spoonacular.host}")
@ClientHeaderParam(name = "x-rapidapi-key", value = "${rapid-api.key}")
public interface SpoonacularRecipesClient {

    @GET
    @Path("random")
    @Operation(hidden = true) // hide operation from OpenAPI
    RandomRecipesResponse random(@RestQuery("tags") String tag, @RestQuery int number);

    @GET
    @Path("{id}/information")
    @Operation(hidden = true) // hide operation from OpenAPI
    Recipe information(@RestPath long id);
}
