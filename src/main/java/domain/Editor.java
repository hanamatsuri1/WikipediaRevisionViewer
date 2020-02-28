package domain;

import java.util.Date;

public class Editor {
    String user;
    Date timestamp;
    Integer count;
    String pagetitle;


    public Editor(String user, Date timestamp){
        this.user=user;
        this.timestamp=timestamp;
    }

    public String getUser() {
        return user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Integer getCount() {
        return count;
    }

    public String getPagetitle(){
        return pagetitle;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setPagetitle(String title){
        this.pagetitle=title;
    }

    public void addcountonetimes(){
        count=count+1;
    }

    public void minuscountonetimes(){
        count=count-1;
    }

    @Override
    public String toString() {
        return "Editor{" +
                "user='" + user + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", count=" + count +
                '}';
    }
}
