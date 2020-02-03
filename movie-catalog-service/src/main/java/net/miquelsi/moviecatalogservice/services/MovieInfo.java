package net.miquelsi.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.miquelsi.moviecatalogservice.models.CatalogItem;
import net.miquelsi.moviecatalogservice.models.Movie;
import net.miquelsi.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MovieInfo {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = webClientBuilder.build()
                .get()
                .uri("http://movie-info-service/movies/" + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
        return new CatalogItem(movie.getName(), "This is a test description", rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }

}
