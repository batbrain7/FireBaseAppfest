package tech.mohitkumar.firebaseappfest.Models;

/**
 * Created by mohitkumar on 21/06/17.
 */

public class CardItem {

    String title;
    String text;
    String lat;

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {

        return lat;
    }

    public String getLng() {
        return lng;
    }

    String lng;

    public CardItem(String title, String text,String lat,String lng) {
        setTitle(title);
        setText(text);
        setLat(lat);
        setLng(lng);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
