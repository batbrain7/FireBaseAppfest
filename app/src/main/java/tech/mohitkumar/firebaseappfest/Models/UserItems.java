package tech.mohitkumar.firebaseappfest.Models;

/**
 * Created by mohitkumar on 21/06/17.
 */

public class UserItems {

    String name,occupation,profile,pno;

    public UserItems(String name, String occupation, String profile, String pno) {
        setName(name);
        setOccupation(occupation);
        setPno(pno);
        setProfile(profile);
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
