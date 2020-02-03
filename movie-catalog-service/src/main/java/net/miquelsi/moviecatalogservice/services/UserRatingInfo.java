package net.miquelsi.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.miquelsi.moviecatalogservice.models.Rating;
import net.miquelsi.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    //@HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
//        try {
//            UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
//            return userRating;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:9081/ratingsdata/users/" + userId)
                .retrieve()
                .bodyToMono(UserRating.class)
                .block();
    }

    //            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:9082/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class) // Reactive (asyncronous) call, we use .block() to wait for the response
//                    .block();


    public UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRatings(Arrays.asList(new Rating("0", 0)));
        return userRating;
    }

}
