package tech.mohitkumar.firebaseappfest.Models;

/**
 * Created by mohitkumar on 21/06/17.
 */

public class Events {

    String lat, longi, venue;

    public Events() {
    }

    public Events(String lat, String longi, String venue) {
        this.lat = lat;
        this.longi = longi;
        this.venue = venue;
    }
}
