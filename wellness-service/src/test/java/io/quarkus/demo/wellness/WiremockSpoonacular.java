package io.quarkus.demo.wellness;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.AnythingPattern;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public class WiremockSpoonacular implements QuarkusTestResourceLifecycleManager {

    private static final String API_KEY = "12345";
    private static final String HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";

    private WireMockServer wireMockServer;


    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        stubRandomRecipe();
        stubRecipeInformation();
        stubRandomWorkout();

        return Map.of(
                "spoonacular-recipe-api/mp-rest/url", wireMockServer.baseUrl(),
                "exercise-api/mp-rest/url", wireMockServer.baseUrl(),
                "rapid-api.key", API_KEY
        );
    }

    private void stubRandomRecipe() {
        stubFor(get(urlPathEqualTo("/recipes/random"))
                .withHeader("x-rapidapi-host", equalTo(HOST))
                .withHeader("x-rapidapi-key", equalTo(API_KEY))
                .withQueryParam("number", equalTo("5"))
                .withQueryParam("tags", new AnythingPattern())
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                """
                                {
                                  "recipes": [
                                    {
                                      "cheap": false,
                                      "cuisines": [],
                                      "dairyFree": false,
                                      "diets": [
                                        "lacto ovo vegetarian"
                                      ],
                                      "dishTypes": [
                                        "soup"
                                      ],
                                      "gaps": "no",
                                      "glutenFree": false,
                                      "healthScore": 10.0,
                                      "id": 640713,
                                      "image": "https://spoonacular.com/recipeImages/640713-556x370.jpg",
                                      "imageType": "jpg",
                                      "instructions": "<ol><li>In a large saucepan heat oil over medium heat.</li><li>Add onion and seasonings and cook, stirring occasionally, until onion is tender and golden.</li><li>Add tomatoes and tomato paste.</li><li>Stir and simmer for 10 minutes.</li><li>In a small bowl whisk together the flour and 1/4 cup of the chicken broth.</li><li>Stir into tomato mixture until well blended.</li><li>Add the remaining broth. Simmer for 30 minutes, stirring frequently.</li><li>Puree with an immersion blender until smooth.</li><li>Add the Splenda and evaporated milk.</li><li>Heat through.</li></ol>",
                                      "license": "CC BY 3.0",
                                      "lowFodmap": false,
                                      "occasions": [
                                        "fall",
                                        "winter"
                                      ],
                                      "originalId": null,
                                      "pricePerServing": 83.63,
                                      "readyInMinutes": 45,
                                      "servings": 8,
                                      "sourceName": "Foodista",
                                      "sourceUrl": "http://www.foodista.com/recipe/KPKRM7Q7/creamy-tomato-soup",
                                      "spoonacularScore": 61.0,
                                      "spoonacularSourceUrl": "https://spoonacular.com/creamy-tomato-soup-640713",
                                      "summary": "Creamy Tomato Soup might be just the soup you are searching for. For <b>83 cents per serving</b>, this recipe <b>covers 8%</b> of your daily requirements of vitamins and minerals. One serving contains <b>121 calories</b>, <b>4g of protein</b>, and <b>6g of fat</b>. 39 people have tried and liked this recipe. It will be a hit at your <b>Autumn</b> event. If you have basil, tomato paste, splenda, and a few other ingredients on hand, you can make it. It is a good option if you're following a <b>vegetarian</b> diet. From preparation to the plate, this recipe takes approximately <b>45 minutes</b>. All things considered, we decided this recipe <b>deserves a spoonacular score of 63%</b>. This score is good. Try <a href=\\"https://spoonacular.com/recipes/creamy-tomato-soup-393043\\">Creamy Tomato Soup</a>, <a href=\\"https://spoonacular.com/recipes/creamy-tomato-soup-26780\\">Creamy Tomato Soup</a>, and <a href=\\"https://spoonacular.com/recipes/creamy-tomato-soup-393047\\">Creamy Tomato Soup</a> for similar recipes.",
                                      "sustainable": false,
                                      "title": "Creamy Tomato Soup",
                                      "vegan": false,
                                      "vegetarian": true,
                                      "veryHealthy": false,
                                      "veryPopular": false,
                                      "weightWatcherSmartPoints": 3
                                    }
                                  ]
                                }
                                """
                        )));
    }

    private void stubRecipeInformation() {
        stubFor(get(urlEqualTo("/recipes/479101/information"))
                .withHeader("x-rapidapi-host", equalTo(HOST))
                .withHeader("x-rapidapi-key", equalTo(API_KEY))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                """
                                                {
                                                  "aggregateLikes": 225,
                                                  "analyzedInstructions": [
                                                    {
                                                      "name": "",
                                                      "steps": [
                                                        {
                                                          "equipment": [
                                                            {
                                                              "id": 404645,
                                                              "image": "pan.png",
                                                              "localizedName": "frying pan",
                                                              "name": "frying pan"
                                                            }
                                                          ],
                                                          "ingredients": [
                                                            {
                                                              "id": 11135,
                                                              "image": "cauliflower.jpg",
                                                              "localizedName": "cauliflower",
                                                              "name": "cauliflower"
                                                            },
                                                            {
                                                              "id": 4053,
                                                              "image": "olive-oil.jpg",
                                                              "localizedName": "olive oil",
                                                              "name": "olive oil"
                                                            }
                                                          ],
                                                          "number": 1,
                                                          "step": "Cut the florets off the stems and and then chop them into tiny florets. You can also chop up the stems into tiny pieces if you want. You should have about 6 cups of chopped cauliflower. In a large skillet heat 2 tablespoons of olive oil over medium-high heat."
                                                        }
                                                      ]
                                                    }
                                                  ],
                                                  "cheap": false,
                                                  "cookingMinutes": 10,
                                                  "creditsText": "Feed Me Phoebe",
                                                  "cuisines": [],
                                                  "dairyFree": true,
                                                  "diets": [
                                                    "dairy free"
                                                  ],
                                                  "dishTypes": [
                                                    "side dish"
                                                  ],
                                                  "gaps": "no",
                                                  "glutenFree": false,
                                                  "healthScore": 46.0,
                                                  "id": 479101,
                                                  "image": "https://spoonacular.com/recipeImages/479101-556x370.jpg",
                                                  "imageType": "jpg",
                                                  "instructions": "Cut the florets off the stems and and then chop them into tiny florets. You can also chop up the stems into tiny pieces if you want. You should have about 6 cups of chopped cauliflower. In a large skillet heat 2 tablespoons of olive oil over medium-high heat. Add the cauliflower, 1 teaspoon of salt, rosemary, and sumac. Sauté until cauliflower is tender and starts to brown a bit, stirring as necessary, about 15 minutes. You can also add a bit of olive oil if the pan starts to get too dry or the cauliflower is starting to stick. Meanwhile, in a small skillet, toast the pinenuts over medium heat until golden brown. Set aside. Heat the remaining 2 tablespoons of olive oil in the same pan. Once oil is shimmering, toss in the breadcrumbs and stir, toasting the breadcrumbs. Season with a pinch of kosher salt and a few turns of freshly ground black pepper. Remove from the heat and toss in half of the chopped parsley. When cauliflower is done, remove from the heat and season to taste with freshly ground black pepper and a pinch or so of salt if necessary. Toss in the toasted pine nuts, the chopped raisins, and the remaining parsley. When ready to serve, sprinkle the top with the toasted breadcrumbs and some pecorino.",
                                                  "lowFodmap": false,
                                                  "occasions": [],
                                                  "originalId": null,
                                                  "preparationMinutes": 10,
                                                  "pricePerServing": 199.25,
                                                  "readyInMinutes": 20,
                                                  "servings": 4,
                                                  "sourceName": "Feed Me Phoebe",
                                                  "sourceUrl": "http://feedmephoebe.com/2013/11/job-food52s-pan-roasted-cauliflower/",
                                                  "spoonacularScore": 96.0,
                                                  "summary": "On the Job: Pan Roasted Cauliflower From Food52 is a <b>dairy free</b> side dish. One portion of this dish contains roughly <b>7g of protein</b>, <b>26g of fat</b>, and a total of <b>350 calories</b>. This recipe serves 4. For <b>$1.99 per serving</b>, this recipe <b>covers 20%</b> of your daily requirements of vitamins and minerals. This recipe is liked by 225 foodies and cooks. A mixture of breadcrumbs, rosemary, sea salt, and a handful of other ingredients are all it takes to make this recipe so tasty. From preparation to the plate, this recipe takes around <b>20 minutes</b>. All things considered, we decided this recipe <b>deserves a spoonacular score of 97%</b>. This score is tremendous. Try <a href=\\"https://spoonacular.com/recipes/food52s-roasted-broccoli-with-smoked-paprika-vinaigrette-and-marcona-almonds-110229\\">Food52's Roasted Broccoli with Smoked Paprika Vinaigrette and Marconan Almonds</a>, <a href=\\"https://spoonacular.com/recipes/sheet-pan-chicken-cauliflower-921598\\">Sheet Pan Chicken Cauliflower</a>, and <a href=\\"https://spoonacular.com/recipes/sheet-pan-roasted-broccoli-918006\\">Sheet Pan Roasted Broccoli</a> for similar recipes.",
                                                  "sustainable": false,
                                                  "title": "On the Job: Pan Roasted Cauliflower From Food52",
                                                  "vegan": false,
                                                  "vegetarian": false,
                                                  "veryHealthy": false,
                                                  "veryPopular": false,
                                                  "weightWatcherSmartPoints": 9,
                                                  "winePairing": {}
                                                }
                                                                                
                                        """
                        )));
    }

    private void stubRandomWorkout() {
        stubFor(get(urlPathEqualTo("/exercise/workout/random"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                """
                                [
                                    {
                                        "name": "Burpies",
                                        "type": "Cardio"
                                    },
                                    {
                                        "name": "Lunges",
                                        "type": "Weights"
                                    }
                                ]
                                """
                        )));
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}
