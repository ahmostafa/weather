package com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherCompare {

    @SerializedName("myHomeTown")
    @Expose
    private String myHomeTown;

    @SerializedName("myHomeTownLocation")
    @Expose
    private String myHomeTownLocation;

    @SerializedName("myOtherTown")
    @Expose
    private String myOtherTown;

    @SerializedName("myOtherTownLocation")
    @Expose
    private String myOtherTownLocation;

    @SerializedName("itIsRainingIn")
    @Expose
    private String itIsRainingIn;


    @SerializedName("sunriseTimeDifference")
    @Expose
    private int sunriseTimeDifference;


    @SerializedName("tempDifference")
    @Expose
    private int tempDifference;


    @SerializedName("distance")
    @Expose
    private double distance;


	/**
	 * @return the myHomeTown
	 */
	public String getMyHomeTown() {
		return myHomeTown;
	}


	/**
	 * @param myHomeTown the myHomeTown to set
	 */
	public void setMyHomeTown(String myHomeTown) {
		this.myHomeTown = myHomeTown;
	}


	/**
	 * @return the myHomeTownLocation
	 */
	public String getMyHomeTownLocation() {
		return myHomeTownLocation;
	}


	/**
	 * @param myHomeTownLocation the myHomeTownLocation to set
	 */
	public void setMyHomeTownLocation(String myHomeTownLocation) {
		this.myHomeTownLocation = myHomeTownLocation;
	}


	/**
	 * @return the myOtherTown
	 */
	public String getMyOtherTown() {
		return myOtherTown;
	}


	/**
	 * @param myOtherTown the myOtherTown to set
	 */
	public void setMyOtherTown(String myOtherTown) {
		this.myOtherTown = myOtherTown;
	}


	/**
	 * @return the myOtherTownLocation
	 */
	public String getMyOtherTownLocation() {
		return myOtherTownLocation;
	}


	/**
	 * @param myOtherTownLocation the myOtherTownLocation to set
	 */
	public void setMyOtherTownLocation(String myOtherTownLocation) {
		this.myOtherTownLocation = myOtherTownLocation;
	}


	/**
	 * @return the itIsRainingIn
	 */
	public String getItIsRainingIn() {
		return itIsRainingIn;
	}


	/**
	 * @param itIsRainingIn the itIsRainingIn to set
	 */
	public void setItIsRainingIn(String itIsRainingIn) {
		this.itIsRainingIn = itIsRainingIn;
	}


	/**
	 * @return the sunriseTimeDifference
	 */
	public int getSunriseTimeDifference() {
		return sunriseTimeDifference;
	}


	/**
	 * @param sunriseTimeDifference the sunriseTimeDifference to set
	 */
	public void setSunriseTimeDifference(int sunriseTimeDifference) {
		this.sunriseTimeDifference = sunriseTimeDifference;
	}


	/**
	 * @return the tempDifference
	 */
	public int getTempDifference() {
		return tempDifference;
	}


	/**
	 * @param tempDifference the tempDifference to set
	 */
	public void setTempDifference(int tempDifference) {
		this.tempDifference = tempDifference;
	}


	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}


	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
    

}