package m.latka.pogodynka;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceholderAPI {

    @GET("weather")
    Call<Post> getPost(@Query("q") String city, @Query("APPID") String api, @Query("units") String units);
}
