package vn.com.wespeak.wespeak.model;

public class Message {

    public String message;
    public String url;
    public int type;

    public Message(String message, String url,int type) {
        this.message = message;
        this.url = url;
        this.type = type;
    }
}
