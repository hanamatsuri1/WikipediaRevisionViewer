package domain;

public class Editor {
    String user;
    String timestamp;

    public Editor(String user, String timestamp){
        this.user=user;
        this.timestamp=timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Editor{" +
                "username='" + user + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
