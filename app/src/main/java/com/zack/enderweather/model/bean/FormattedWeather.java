package com.zack.enderweather.model.bean;

public class FormattedWeather {

    private String cityName;
    private String condition;
    private String temperature;
    private String updateTime;
    private String sensibleTemp;
    private String tempRange;
    private String airQuality;
    private String[] weeks;
    private String[] conditions;
    private int[] maxTemps;
    private int[] minTemps;

    public FormattedWeather(String cityName, String condition, String temperature, String updateTime,
                            String sensibleTemp, String tempRange, String airQuality, String[] weeks,
                            String[] conditions, int[] maxTemps, int[] minTemps) {
        this.cityName = cityName;
        this.condition = condition;
        this.temperature = temperature;
        this.updateTime = updateTime;
        this.sensibleTemp = sensibleTemp;
        this.tempRange = tempRange;
        this.airQuality = airQuality;
        this.weeks = weeks;
        this.conditions = conditions;
        this.maxTemps = maxTemps;
        this.minTemps = minTemps;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSensibleTemp() {
        return sensibleTemp;
    }

    public void setSensibleTemp(String sensibleTemp) {
        this.sensibleTemp = sensibleTemp;
    }

    public String getTempRange() {
        return tempRange;
    }

    public void setTempRange(String tempRange) {
        this.tempRange = tempRange;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
    }

    public String[] getWeeks() {
        return weeks;
    }

    public void setWeeks(String[] weeks) {
        this.weeks = weeks;
    }

    public String[] getConditions() {
        return conditions;
    }

    public void setConditions(String[] conditions) {
        this.conditions = conditions;
    }

    public int[] getMaxTemps() {
        return maxTemps;
    }

    public void setMaxTemps(int[] maxTemps) {
        this.maxTemps = maxTemps;
    }

    public int[] getMinTemps() {
        return minTemps;
    }

    public void setMinTemps(int[] minTemps) {
        this.minTemps = minTemps;
    }
}