package io.quarkus.demo.mail;

public record Recipe(Long id, String instructions, boolean glutenFree, boolean veryPopular, double healthScore,
					 String title, int readyInMinutes, boolean vegetarian, String imageType,
					 String summary, String image, boolean veryHealthy, boolean vegan,
					 double spoonacularScore, String sourceName, String sourceUrl) {
}
