package net.miquelsi.ratingsdataservice.resources;

import net.miquelsi.ratingsdataservice.models.Rating;
import net.miquelsi.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") final String movieId){
        return new Rating(movieId, 4);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") final String userId){
        List<Rating> ratings = Arrays.asList(
                new Rating("550", 4),
                new Rating("551", 3)
        );
        return new UserRating(ratings);
    }
}
