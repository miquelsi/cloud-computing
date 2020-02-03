package net.miquelsi.moviecatalogservice.resources;

import net.miquelsi.moviecatalogservice.models.CatalogItem;
import net.miquelsi.moviecatalogservice.models.UserRating;
import net.miquelsi.moviecatalogservice.services.MovieInfo;
import net.miquelsi.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") final String userId) {

        // get all rated movie ID's
        UserRating ratings = userRatingInfo.getUserRating(userId);

        return ratings.getUserRatings().stream().map(rating -> movieInfo.getCatalogItem(rating)).collect(Collectors.toList());
    }




}