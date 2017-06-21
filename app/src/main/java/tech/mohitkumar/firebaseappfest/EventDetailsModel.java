package tech.mohitkumar.firebaseappfest;

/**
 * Created by Lenovo on 21-06-2017.
 */

public class EventDetailsModel {

    String poi_nick_name;
    String venue;
    String lat;
    String lng;
    String radius;

    public EventDetailsModel(String poi_nick_name, String venue, String lat, String lng, String radius) {
        this.poi_nick_name = poi_nick_name;
        this.venue = venue;
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
    }

    public String getPoi_nick_name() {
        return poi_nick_name;
    }

    public String getVenue() {
        return venue;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getRadius() {
        return radius;
    }
}
