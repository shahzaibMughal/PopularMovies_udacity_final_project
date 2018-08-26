package network;

import java.util.List;

import model.Movie;
import model.MovieDetail;
import model.MovieVideo;
import model.Review;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    String API_KEY = "a78b604d75194c1363c79ba4852173bd";

    @GET("popular?api_key="+API_KEY)
    Call<Movie> getPopularMovies();

    @GET("upcoming?api_key="+API_KEY)
    Call<Movie> getUpcomingMovies();

    @GET("now_playing?api_key="+API_KEY)
    Call<Movie> getNowPlayingMovies();

    @GET("{movieID}?api_key="+API_KEY)
    Call<MovieDetail> getMovieDetail(@Path("movieID") String movieID);

    @GET("{movieID}/reviews?api_key="+API_KEY)
    Call<Review> getReviews(@Path("movieID") String movieID);

    @GET("{movieID}/videos?api_key="+API_KEY)
    Call<MovieVideo> getVideoDetail(@Path("movieID") String movieID);

    // https://api.themoviedb.org/3/movie/299536/videos?api_key=a78b604d75194c1363c79ba4852173bd
}
