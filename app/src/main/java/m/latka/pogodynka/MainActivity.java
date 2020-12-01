package m.latka.pogodynka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void checkWeather(View view) {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("KEY_CITY", editText.getText().toString());
        startActivity(intent);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String city = sharedPreferences.getString("KEY_SAVED_CITY", "");
        String message = sharedPreferences.getString("KEY_MESSAGE", "");
        editText.setText(city);
        textView.setText(message);
    }
}