package io.quarkus.demo.wellness;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.connectors.InMemorySink;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
@QuarkusTestResource(WiremockSpoonacular.class)
@QuarkusTestResource(ReactiveMessagingInMemoryConnector.class)
public class RecipeResourceTest {

    @Inject
    @Any
    InMemoryConnector connector;

    @Test
    public void testByTag() {
        List<Recipe> response = when().get("/recipe/tag/chicken")
                .then()
                .statusCode(200)
                .extract().response().body().as(new TypeRef<>() {
                });
        assertThat(response).singleElement().satisfies(r -> {
            assertThat(r.id()).isEqualTo(640713);
            assertThat(r.title()).isEqualTo("Creamy Tomato Soup");
        });
    }

    @Test
    public void testById() {
        when().get("/recipe/479101")
                .then()
                .statusCode(200)
                .body("title", equalTo("On the Job: Pan Roasted Cauliflower From Food52"));
    }

    @Test
    public void testOrder() {
        Order order = when().post("/recipe/479101")
                .then()
                .statusCode(200)
                .extract().response().body().as(new TypeRef<>() {
                });
        assertOrder(order);

        InMemorySink<Object> sink = connector.sink("ordered-recipes");
        assertThat(sink.received()).singleElement().satisfies(m -> assertThat(m.getPayload()).isInstanceOfSatisfying(Order.class, this::assertOrder));
    }

    private void assertOrder(Order order) {
        assertThat(order.email()).isEqualTo("test@quarkus.io");
        assertThat(order.recipe()).satisfies(r -> assertThat(r.title()).isEqualTo("On the Job: Pan Roasted Cauliflower From Food52"));
        assertThat(order.offsettingWorkout()).hasSize(2).extracting("name").containsOnly("Burpies", "Lunges");
    }
}
