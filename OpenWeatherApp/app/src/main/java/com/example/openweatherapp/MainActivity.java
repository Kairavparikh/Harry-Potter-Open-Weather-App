package com.example.openweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    ProgressBar progress;
    JSONObject jsonObject;
    JSONObject jsonObject2;
    String zipCode;
    String initialTemp;
    String place;
    String time;
    TextView textView, showLat,showLon,showLocation,initialTemp2, show, mintemp2, maxtemp2;
    ImageView image3;
    ListView listView;
    EditText editText;
    Button button;
    String tempFeel;
    String description;

    int temp456;
    String text;
    String initTemp1;
    String c;
    List<Weather> weather;
    List<String> feelLike;
    List<String> minTemperature;
    List<String> maxTemperature;

    List<String> descriptions;

    List<String> initWeather;
    List<Integer> image;
    List<String> times;
    List<Weather> weather2;


    String country;
    Date currentTime;
    String lat;
    String lon;
    String locationOfWeather;
    String maxTemp;
    String minTemp;
    String maxTemp1;
    String minTemp1;

    ArrayAdapter<Weather> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weather = new ArrayList<>();
        feelLike = new ArrayList<>();
        descriptions = new ArrayList<>();
        initWeather = new ArrayList<>();
        minTemperature = new ArrayList<>();
        maxTemperature = new ArrayList<>();
        weather2 = new ArrayList<>();
        image = new ArrayList<>();
        image3 = findViewById(R.id.imageView2);
        times = new ArrayList<>();
        progress = findViewById(R.id.progressBar);
        initialTemp2 = findViewById(R.id.textView2);
        listView = findViewById(R.id.list_id);
        show = findViewById(R.id.textView);
        editText = findViewById(R.id.editTextText2);
        button = findViewById(R.id.button);
        mintemp2 = findViewById(R.id.textView7);
        maxtemp2 = findViewById(R.id.textView8);
        textView = findViewById(R.id.textView);
        showLat = findViewById(R.id.textViewLat);
        showLon = findViewById(R.id.textViewLon);
        showLocation = findViewById(R.id.textView3);
        jsonObject = new JSONObject();
        jsonObject2 = new JSONObject();
        show.setText("This Harry Potter themed APP will allow you to see the 5 days forecast for any place in the US!!");
        progress.setVisibility(View.INVISIBLE);
        mintemp2.setVisibility(View.INVISIBLE);
        maxtemp2.setVisibility(View.INVISIBLE);
        showLocation.setVisibility(View.INVISIBLE);
        showLat.setVisibility(View.INVISIBLE);
        showLon.setVisibility(View.INVISIBLE);
        initialTemp2.setVisibility(View.INVISIBLE);
        arrayAdapter = new CustomAdapter(this, R.layout.adapter_layout, weather);
        currentTime = Calendar.getInstance().getTime();
        Log.d("weather", String.valueOf(currentTime));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                text = s.toString();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zipCode = text;
                Log.d("ZipCode", zipCode);
                AsyncThread task = new AsyncThread();
                weather.clear();
                task.execute(zipCode);
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            }
        });

    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (weather != null) {
                progress.setVisibility(View.VISIBLE);
                mintemp2.setVisibility(View.VISIBLE);
                maxtemp2.setVisibility(View.VISIBLE);
                showLocation.setVisibility(View.VISIBLE);
                showLat.setVisibility(View.VISIBLE);
                showLon.setVisibility(View.VISIBLE);
                initialTemp2.setVisibility(View.VISIBLE);
                image3.setImageResource(R.drawable.hometrwo);

            }
            else
                Toast.makeText(MainActivity.this, "Please Enter a Valid ZipCode", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected Void doInBackground(String... strings) {
            JSONObject jsonObject;
            String zipCode = strings[0];
            Log.d("ZipCode",zipCode);
            initialTemp = " ";
            String API;
            BufferedReader bufferedReader;
            BufferedReader bufferedReader2;
            String information;

            // This is where you will download your data.
            // You will need to override another method to update the UI
            //zipCode = editText.getText().toString();
            API = "https://api.openweathermap.org/geo/1.0/zip?zip=" + zipCode + ",US&appid=7856984d74cbe10f5ecfeeb42e092851";
            try {
                URL link = new URL(API);
                URLConnection connection = link.openConnection();
                InputStream inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                information = bufferedReader.readLine();
                jsonObject = new JSONObject(information);
                lat = jsonObject.getString("lat");
                lon = jsonObject.getString("lon");
                country = jsonObject.getString("country");
                locationOfWeather = jsonObject.getString("name");
                Log.d("LONG", lon);
                Log.d("LAT", lat);
                Log.d("Location", locationOfWeather);

            }
            catch (MalformedURLException e) {
            return null;            }
            catch (IOException e) {
                return null;            }
            catch (JSONException e) {
                return null;            }
            Log.d("TAG", "Thread ");


            String API2 = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&units=imperial&appid=7856984d74cbe10f5ecfeeb42e092851";
            try {

                URL link2 = new URL(API2);
                URLConnection connection2 = link2.openConnection();
                InputStream inputStream2 = connection2.getInputStream();
                bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
                String information2;
                information2 = bufferedReader2.readLine();
                jsonObject2 = new JSONObject(information2);
                Log.d("Information2", information2);
                JSONArray jsonArray = jsonObject2.getJSONArray("list");
                Log.d("Length", String.valueOf(jsonArray.length()));
                for (int i = 0; i < jsonArray.length(); i += 8) {

                    JSONObject mainObject = jsonArray.getJSONObject(i).getJSONObject("main");
                    initTemp1 = mainObject.getString("temp");
                    initialTemp = String.valueOf(((int) (Double.parseDouble(initTemp1) + 0.5)));
                    initWeather.add(initialTemp);
                    //tempFeel = mainObject.getString("feels_like");
                    //feelLike.add(tempFeel);
                    maxTemp1 = mainObject.getString("temp_max");
                    maxTemp = String.valueOf(((int) (Double.parseDouble(maxTemp1) + 0.5)));
                    maxTemperature.add(maxTemp);
                    minTemp1 = mainObject.getString("temp_min");
                    minTemp = String.valueOf(((int) (Double.parseDouble(minTemp1) + 0.5)));
                    minTemperature.add(minTemp);
                    JSONObject mainObject2 = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
                    description = mainObject2.getString("description");
                    if (description.equals("overcast clouds") || description.equals("broken clouds") || description.equals("few clouds") || description.equals("scattered clouds")) {
                        image.add(R.drawable.hppcloudy);
                    }
                    if (description.equals("light snow") || description.equals("heavy snow")) {
                        image.add(R.drawable.hppsnowy);

                    }
                    if (description.equals("heavy rain") || description.equals("light rain") || description.equals("moderate rain")) {
                        image.add(R.drawable.hpprainy);
                    }
                    if (description.equals("clear sky")) {
                        image.add(R.drawable.hppclearsky);

                    }
                    descriptions.add(description);
                    //Log.d("Feel Temperature: ", tempFeel);
                    Log.d("Description Temperature: ", description);
                }
                //weather.add(initialTemp, initWeather,initWeather,description,description);


                for (int z = 0; z < jsonArray.length(); z += 8) {
                    JSONObject mainObject4 = jsonArray.getJSONObject(z);
                    time = mainObject4.getString("dt_txt");

                    String[] separated = time.split("\\s+");
                    String newTime = separated[0];
                    times.add(newTime);
                    Log.d("Time : ", newTime);
                }
                for(int z = 0;z < 8; z++) {
                JSONObject mainObject = jsonArray.getJSONObject(z).getJSONObject("main");
                initTemp1 = mainObject.getString("temp");
                initialTemp = String.valueOf(((int) (Double.parseDouble(initTemp1) + 0.5)));
                initWeather.add(initialTemp);
                Log.d("Initial Temperature: ", initialTemp);

                }
                String[] separated2 = String.valueOf(currentTime).split("\\s+");
                String newTime2 = separated2[3];
                String[] separated22 = String.valueOf(newTime2).split(":");
                c = separated22[0];
                Log.d("CurrentTime", c);



                bufferedReader2.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Log.d("TAG2", "Thread2 ");

            return null;

        }


        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            mintemp2.setText("Min Temp:");
            maxtemp2.setText("Max Temp:");
            if(Integer.parseInt(c) <= 3 || Integer.parseInt(c) <= 4 || Integer.parseInt(c) <= 5)
                initialTemp2.setText("Current Temp " + initWeather.get(0) + "˚F");
            if(Integer.parseInt(c) <= 6 || Integer.parseInt(c) <= 7 || Integer.parseInt(c) <= 8)
                initialTemp2.setText("Current Temp " + initWeather.get(1) + "˚F");
            if(Integer.parseInt(c) <= 9 || Integer.parseInt(c) <= 10 || Integer.parseInt(c) <= 11)
                initialTemp2.setText("Current Temp " + initWeather.get(2) + "˚F");
            if(Integer.parseInt(c) <= 12 || Integer.parseInt(c) <= 13 || Integer.parseInt(c) <= 14)
                initialTemp2.setText("Current Temp " + initWeather.get(3) + "˚F");
            if(Integer.parseInt(c) < 15 || Integer.parseInt(c) <= 16 || Integer.parseInt(c) <= 17)
                initialTemp2.setText("Current Temp " + initWeather.get(4) + "˚F");
            if(Integer.parseInt(c) <= 18 || Integer.parseInt(c) <= 19 || Integer.parseInt(c) <= 20)
                initialTemp2.setText("Current Temp " + initWeather.get(5) + "˚F");
            if(Integer.parseInt(c) <= 21|| Integer.parseInt(c) <= 22|| Integer.parseInt(c) <= 23)
                initialTemp2.setText("Current Temp " + initWeather.get(6) + "˚F");
            if(Integer.parseInt(c) <= 24)
                initialTemp2.setText("Current Temp " + initWeather.get(7) + "˚F");
            show.setText("Feels Like " + tempFeel + "˚F. ");
            if(description.equals("overcast clouds") || description.equals("broken clouds")|| description.equals("few clouds") || description.equals("scattered clouds")){
                //image3.setImageDrawable(Drawable.createFromPath("hppcloudy"));
                show.setText("\"Let him sleep. For in dreams, we enter a world that is entirely our own. Let him swim in the deepest ocean or glide over the highest cloud. - Dumbledore");

            }
            if(description.equals("light snow") || description.equals("heavy snow")){
                //image3.setImageDrawable(Drawable.createFromPath("hppsnowy"));
                show.setText("Two weeks before the end of term, the sky lightened suddenly to a dazzling, opaline white and the muddy grounds were revealed one morning covered in glittering frost. ");

            }
            if(description.equals("heavy rain") || description.equals("light rain") || description.equals("moderate rain")){
                //image3.setImageDrawable(Drawable.createFromPath("hpprainy"));
                show.setText("What's coming will come and we'll figure it out when it comes - Hagrid");

            }
            if(description.equals("clear sky")){
                //image3.setImageDrawable(Drawable.createFromPath("hppclearsky"));
                show.setText("“The sky outside the window was changing rapidly from deep, velvety blue to cold, steely gray and then, slowly, to pink shot with gold.”");
            }


            //describe.setText(description);
            showLat.setText("Latitude: " + lat);
            showLon.setText("Longitude: "+lon);
            showLocation.setText(locationOfWeather + ", " + country);
            progress.setVisibility(View.INVISIBLE);
            for(int i =0; i < times.size();i++) {
                weather.add(new Weather(times.get(i), minTemperature.get(i), maxTemperature.get(i), descriptions.get(i), image.get(i)));
                Log.d("Time", times.get(i));
                Log.d("minTemperature", minTemperature.get(i));
                Log.d("maxTemperature", maxTemperature.get(i));
                Log.d("descriptions", descriptions.get(i));
                Log.d("image", String.valueOf(image.get(i)));

            }

            listView.setAdapter(arrayAdapter);
        }

    }


}
