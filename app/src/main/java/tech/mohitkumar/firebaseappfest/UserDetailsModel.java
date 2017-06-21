package tech.mohitkumar.firebaseappfest;

/**
 * Created by uddishverma on 21/06/17.
 */

public class UserDetailsModel {

    String name;
    String email;
    String pno;
    String profileLink;
    String occupation;
    String latitude;
    String longitude;

    public UserDetailsModel() {
    }

    public UserDetailsModel(String name, String email, String pno, String profileLink, String occupation, String latitude, String longitude) {
        this.name = name;
        this.email = email;
        this.pno = pno;
        this.profileLink = profileLink;
        this.occupation = occupation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
