package m.latka.pogodynka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;
import java.time.ZoneId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    TextView detail1;
    TextView detail2;
    TextView detail3;
    TextView detail4;
    TextView detail5;
    TextView cityName;
    TextView cityTime;

    LocalTime localTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        detail1 = findViewById(R.id.detail1);
        detail2 = findViewById(R.id.detail2);
        detail3 = findViewById(R.id.detail3);
        detail4 = findViewById(R.id.detail4);
        detail5 = findViewById(R.id.detail5);
        cityName = findViewById(R.id.cityName);
        cityTime = findViewById(R.id.cityTime);

        localTime = LocalTime.now(ZoneId.of("UTC"));

        Intent intent = getIntent();
        final String city = intent.getStringExtra("KEY_CITY");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        String api = "749561a315b14523a8f5f1ef95e45864";

        Call<Post> call = jsonPlaceholderAPI.getPost(city, api, "metric");

        final Intent intentBack = new Intent(this, MainActivity.class);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    saveMessage("City name error");
                    saveData("");
                    startActivity(intentBack);
                    return;
                }

                Post posts = response.body();
                int timeDiff = posts.getTimezone() / 3600;
                localTime = localTime.plusHours(timeDiff);

                cityTime.setText(localTime.toString());
                detail1.setText(posts.getMain().get("temp").toString() + " °C");
                detail2.setText(posts.getMain().get("pressure").toString() + " hPa");
                detail3.setText(posts.getMain().get("humidity").toString() + " %");
                detail4.setText(posts.getMain().get("temp_min").toString() + " °C");
                detail5.setText(posts.getMain().get("temp_max").toString() + " °C");
                cityName.setText(posts.getName());

                saveData(posts.getName());
                saveMessage("");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                saveMessage("API error");
                saveData("");
                startActivity(intentBack);
            }
        });
    }

    public void saveData(String output) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY_SAVED_CITY", output);
        editor.apply();
    }

    public void saveMessage(String output) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY_MESSAGE", output);
        editor.apply();
    }
}