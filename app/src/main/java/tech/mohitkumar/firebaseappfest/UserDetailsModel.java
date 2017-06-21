package tech.mohitkumar.firebaseappfest;

/**
 * Created by uddishverma on 21/06/17.
 */

public class UserDetailsModel {

//    String uid;
    String name;
    String email;
    String pno;
    String profileLink;
    String occupation;

    public UserDetailsModel() {
    }

    public UserDetailsModel(String name, String email, String pno, String profileLink, String occupation) {
        this.name = name;
        this.email = email;
        this.pno = pno;
        this.profileLink = profileLink;
        this.occupation = occupation;
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
