package tech.mohitkumar.firebaseappfest.GeoClient;

import java.util.ArrayList;

/**
 * Created by Lenovo on 21-06-2017.
 */
public class GeoResponse {

    ArrayList<Results> results;

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    public class Results {
        Geometery geometry;

        public Geometery getGeometery() {
            return geometry;
        }

        public void setGeometery(Geometery geometery) {
            this.geometry = geometery;
        }

        public class Geometery {
            Loc location;

            public Loc getLocation() {
                return location;
            }

            public void setLocation(Loc location) {
                this.location = location;
            }

            public class Loc {
                String lat;
                String lng;

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }
            }
        }
    }
}
