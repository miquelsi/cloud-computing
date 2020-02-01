package net.miquelsi.movieinfoservice.resources;

import net.miquelsi.movieinfoservice.models.Movie;
import net.miquelsi.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") final String movieId) {
        StringBuilder sb = new StringBuilder("https://api.themoviedb.org/3/movie/");
        sb.append(movieId).append("?api_key=").append(apiKey);
        MovieSummary movieSummary = restTemplate.getForObject(sb.toString(), MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }

}
