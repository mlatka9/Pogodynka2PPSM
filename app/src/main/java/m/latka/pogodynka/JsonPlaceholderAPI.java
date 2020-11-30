package m.latka.pogodynka;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderAPI {

    @GET("weather?q=Krakow,pl&APPID=749561a315b14523a8f5f1ef95e45864&units=metric")
    Call<Post> getPost();
}
