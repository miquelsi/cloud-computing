package net.miquelsi.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.miquelsi.moviecatalogservice.models.CatalogItem;
import net.miquelsi.moviecatalogservice.models.Movie;
import net.miquelsi.moviecatalogservice.models.Rating;
import net.miquelsi.moviecatalogservice.models.UserRating;
import net.miquelsi.moviecatalogservice.services.MovieInfo;
import net.miquelsi.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") final String userId) {


        // get all rated movie ID's
        UserRating ratings = userRatingInfo.getUserRating(userId);

        return ratings.getUserRatings().stream().map(rating -> movieInfo.getCatalogItem(rating)).collect(Collectors.toList());
    }




}

//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:9082/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class) // Reactive (asyncronous) call, we use .block() to wait for the response
//                    .block();
