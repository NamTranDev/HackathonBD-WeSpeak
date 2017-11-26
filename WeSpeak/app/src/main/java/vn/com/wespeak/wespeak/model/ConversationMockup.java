package vn.com.wespeak.wespeak.model;

public class ConversationMockup {
    public int type;
    public String data;
    public String url;

    public ConversationMockup(String url,int type, String data) {
        this.type = type;
        this.data = data;
        this.url = url;
    }
}
