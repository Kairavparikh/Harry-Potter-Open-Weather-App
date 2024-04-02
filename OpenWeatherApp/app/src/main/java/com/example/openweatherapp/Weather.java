package com.example.openweatherapp;
public class Weather{
    private String time;
    private String minTemp;
    private String maxTemp;
    private String description;
    private int image;

    public Weather(String time, String minTemp, String maxTemp, String description, int  image){
        this.time = time;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.description = description;
        this.image = image;
    }
    public String getTime(){
        return time;
    }
    public String getMinTemp() {
        return minTemp;
    }
    public String getMaxTemp() {
        return maxTemp;
    }
    public String getDescription() {
        return description;
    }
    public int getImage(){
        return image;
    }



}
