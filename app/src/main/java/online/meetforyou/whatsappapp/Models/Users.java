package online.meetforyou.whatsappapp.Models;

public class Users {

    String profilepic, username , mail , password, userid, lastmessage, status;

    public Users(String profilepic, String username, String mail, String password, String userid, String lastmessage, String status) {
        this.profilepic = profilepic;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userid = userid;
        this.lastmessage = lastmessage;
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public Users(){}

    public Users(String username, String mail, String password) {

        this.username = username;
        this.mail = mail;
        this.password = password;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
            this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid(String key) {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }


}

