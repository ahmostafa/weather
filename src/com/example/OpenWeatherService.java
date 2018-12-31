package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.weather.ListWeatherObj;
import com.weather.WeatherObject;


public class OpenWeatherService {
	private  final String APIKEY= "92ad93df83fcbc73dc2c92068d2c5f2f";
//	private  final String APIKEY= "92ad93df83fcbc73dc2c92068d2";//invalid key for test
//    private static final String weatherBaseUrl ="http://api.openweathermap.org/data/2.5/weather?";
	private  final String weatherBaseUrl ="http://api.openweathermap.org/data/2.5/";
	private  final String BornTownID = "360631";//Cairo EG=> "360631";Hamburg DE=>"2911298";
	private  final String HomeTownID = "108410"; //Riyadh SA=> "108410";
    private final int TimeOutMS =10000;
    /**
     * @param urlGetParams this is get parameters full url but without api key as it's added inside this function
     * @return response as String
     * @throws IOException
     * @throws MalformedURLException
     * @throws ProtocolException
     */
    private  String getResponseFromService(String urlGetParams) throws IOException, MalformedURLException,ProtocolException{
    	StringBuilder strBufResult = new StringBuilder();
    	HttpURLConnection conn= null;
    	BufferedReader reader =null;
    	
    
    	URL url;

    	try {
    		url = new URL(weatherBaseUrl+ urlGetParams+"&APPID="+APIKEY );
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	    	conn.setConnectTimeout(TimeOutMS);
	    	if(conn.getResponseCode()!=HttpURLConnection.HTTP_OK){ // 200
//	    		throw new RuntimeException("HTTP GET Request Failed with Error code : "
//	                    + conn.getResponseCode());
	    		throw new RuntimeException(conn.getResponseMessage());
	    	}
	    	
	    	reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
	    	String output = null;
	    	while((output = reader.readLine()) !=null){
	    		strBufResult.append(output);
	    	}
		}catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
			
		}
    	catch (ProtocolException e) {
			
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			
			e.printStackTrace();
			throw e;
		}finally { // adding try catch => finally here to make sure that connection disconnected and the function is throwable 
			if(reader!=null){
	    		reader.close();
	    	}
	    	if(conn!=null){
	    		conn.disconnect();
	    	}
		} 
    	
 
    	
    	return strBufResult.toString();
    	
    }
    
    /**
     * @param cityId you can get city id from the documentation of open weather api
     * @return single city String json response
     * @throws IOException
     * @throws MalformedURLException
     */
    private  String getWeatherByCityIdJsonResponse(String cityId) throws IOException, MalformedURLException{
    	return getResponseFromService("weather?"+"id="+cityId);
    }
    
    /**
     * @param homeCityId : first city ID
     * @param otherCityId : second city id
     * @return two cities String Json response
     * @throws IOException
     * @throws MalformedURLException
     */
    private  String getWeatherGroupCitiesIdsResponse(String homeCityId,String otherCityId) throws IOException, MalformedURLException{
    	String cities=homeCityId+","+otherCityId;
    	return getResponseFromService("group?"+"id="+cities);
    }
    
    /**
     * @param homeCityId
     * @param otherCityId
     * @param requestMode
     * @return Map with key city id and value type WeatherObject
     * @throws MalformedURLException
     * @throws IOException
     */
    private  Map<String,WeatherObject> getWeatherResponse(String homeCityId,String otherCityId, RequestMode requestMode) throws MalformedURLException, IOException{
    	Map <String,WeatherObject> result = new HashMap<String,WeatherObject>();
    	if (requestMode== RequestMode.GROUP){

    		String citiesList=getWeatherGroupCitiesIdsResponse(homeCityId,otherCityId);
        	Gson gson = new GsonBuilder().create();
        	ListWeatherObj weatherObjsList = gson.fromJson(citiesList, ListWeatherObj.class);
        	result.put(String.valueOf(weatherObjsList.getWeatherObjcs().get(0).getId()), weatherObjsList.getWeatherObjcs().get(0));
        	result.put(String.valueOf(weatherObjsList.getWeatherObjcs().get(1).getId()), weatherObjsList.getWeatherObjcs().get(1));
        	
    	}
    	else if (requestMode== RequestMode.ONEBYONE){

    		String homeTownRes=getWeatherByCityIdJsonResponse(homeCityId);
        	String bornTownRes = getWeatherByCityIdJsonResponse(otherCityId);
        	Gson gson = new GsonBuilder().create();
        	WeatherObject weatherHomeObj = gson.fromJson(homeTownRes, WeatherObject.class);
        	WeatherObject weatherBornObj = gson.fromJson(bornTownRes, WeatherObject.class);
        	result.put(homeCityId , weatherHomeObj);
        	result.put(otherCityId, weatherBornObj);
        	
    	}
    	else{
    		throw new RuntimeException("Unsupported request type");
    	}
    	//else throw exception 
    	return result;
    }
    
    
    
    
    
    
    /**
     * @return this return the json response as requested  this is the only public function
     * @throws MalformedURLException
     * @throws IOException
     */
    public  String getWeatherComparisonJsonResponse() throws MalformedURLException, IOException{
    	// try with one type 
    	// toggle between next two lines to test the requests mode 
//    	Map<String,WeatherObject> citiesMap =getWeatherResponse(HomeTownID,BornTownID,RequestMode.ONEBYONE);
    	Map<String,WeatherObject> citiesMap =getWeatherResponse(HomeTownID,BornTownID,RequestMode.GROUP);
    	
    	return compareTwoCities(citiesMap.get(HomeTownID),citiesMap.get(BornTownID));

    }
    /*
     * compareTwoCities: this to compare home city and the onther city
     * weatherHomeObj: home city of type WeatherObject
     * weatherBornObj: other city of type WeatherObject
     * return : String comparison but in Json format for  WeatherCompare class
     */
    private  String compareTwoCities(WeatherObject weatherHomeObj,WeatherObject weatherBornObj){
    	double distanceKM = distance(weatherHomeObj.getCoord().getLat(),weatherBornObj.getCoord().getLat(),weatherHomeObj.getCoord().getLon(),weatherBornObj.getCoord().getLon());
    	int sunriseTimeDifference = Math.abs(weatherHomeObj.getSys().getSunrise()-weatherBornObj.getSys().getSunrise())/60;
    	int tempDifference = (int) (weatherHomeObj.getMain().getTemp() -  weatherBornObj.getMain().getTemp());// as requested temp difference integer value
    	String itIsRainingIn ="nowhere" ;
    	if(weatherHomeObj.getWeather().get(0).getId()>=500 && weatherHomeObj.getWeather().get(0).getId()<=599){ // raining group
    		itIsRainingIn= weatherHomeObj.getName();
    	}
    	else if(weatherBornObj.getWeather().get(0).getId()>=500 && weatherBornObj.getWeather().get(0).getId()<=599){
    		itIsRainingIn= weatherBornObj.getName();
    	}
    	
    	Gson gson = new Gson();
    	//this example if we want to use only JsonObjec
    	/*JsonObject jsonResultObj = new JsonObject();
    	jsonResultObj.addProperty("myHomeTown", weatherHomeObj.getName());
    	jsonResultObj.addProperty("myHomeTownLocation", weatherHomeObj.getCoord().getLat()+","+weatherHomeObj.getCoord().getLon());
    	jsonResultObj.addProperty("myOtherTown", weatherBornObj.getName());
    	jsonResultObj.addProperty("myOtherTownLocation", weatherBornObj.getCoord().getLat()+","+weatherBornObj.getCoord().getLon());
    	jsonResultObj.addProperty( "itIsRainingIn",itIsRainingIn);
    	jsonResultObj.addProperty("sunriseTimeDifference", sunriseTimeDifference);
    	jsonResultObj.addProperty("tempDifference", tempDifference);
    	jsonResultObj.addProperty("distance", distanceKM);
    	return jsonResultObj.toString();*/
    	
    	WeatherCompare weatherCompareResponseObject = new WeatherCompare();
    	weatherCompareResponseObject.setMyHomeTown(weatherHomeObj.getName());
    	weatherCompareResponseObject.setMyHomeTownLocation(weatherHomeObj.getCoord().getLat()+","+weatherHomeObj.getCoord().getLon());
    	weatherCompareResponseObject.setMyOtherTown(weatherBornObj.getName());
    	weatherCompareResponseObject.setMyOtherTownLocation(weatherBornObj.getCoord().getLat()+","+weatherBornObj.getCoord().getLon());
    	weatherCompareResponseObject.setItIsRainingIn(itIsRainingIn);
    	weatherCompareResponseObject.setSunriseTimeDifference(sunriseTimeDifference);
    	weatherCompareResponseObject.setTempDifference(tempDifference);
    	weatherCompareResponseObject.setDistance(distanceKM);
    	return gson.toJson(weatherCompareResponseObject);
    	
 
    }
    
    /**
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @return distance between two points in km
     */
    private static double distance(double lat1, double lat2, double lon1,
            double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = R * c * 1000; // convert to meters
        double distance = R * c ; // convert to K meters
//        double height = el1 - el2;

      //  distance = Math.pow(distance, 2); //+ Math.pow(height, 2);

        return distance; //Math.sqrt(distance);
    }
}
/**
 * enum RequestMode :to define which mode to call openweather api
 * GROUP: so we will use one call which will get the cities together
 * ONEBYONE: will make a request for each city alone so will be two requests
 * */
enum RequestMode{
	GROUP,ONEBYONE
}
