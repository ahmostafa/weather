package com.weather;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListWeatherObj {
@SerializedName("list")
 @Expose
 private List<WeatherObject> weatherObjcs = new ArrayList<WeatherObject>();
@SerializedName("cnt")
@Expose
private int cnt;

/**
 *
 * @return The weatherObjcs
 */
 public List<WeatherObject> getWeatherObjcs() {
  return weatherObjcs;
 }
/**
 *
 * @param weatherObjcs
 * The weatherObjcs
 */
 public void setWeatherObjcs(List<WeatherObject> weatherObjcs) {
  this.weatherObjcs = weatherObjcs;
 }
/**
 * @return the cnt
 */
public int getCnt() {
	return cnt;
}
/**
 * @param cnt the cnt to set
 */
public void setCnt(int cnt) {
	this.cnt = cnt;
}
}