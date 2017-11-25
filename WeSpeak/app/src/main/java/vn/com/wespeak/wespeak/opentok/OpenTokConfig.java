package vn.com.wespeak.wespeak.opentok;

import android.webkit.URLUtil;

public class OpenTokConfig {
    // *** Fill the following variables using your own Project info from the OpenTok dashboard  ***
    // ***                      https://dashboard.tokbox.com/projects                           ***

    // Replace with your OpenTok API key
    public static final String API_KEY = "45969452";
    // Replace with a generated Session ID
    public static final String SESSION_ID = "1_MX40NTk2OTQ1Mn5-MTUxMDM3MzIxNTgwMH5vVWEwb2lES0tnQUt3RHYzZnZ6Vk45Rk5-fg";
    // Replace with a generated token (from the dashboard or using an OpenTok server SDK)
//    public static final String TOKEN = "T1==cGFydG5lcl9pZD00NTk2OTQ1MiZzZGtfdmVyc2lvbj1kZWJ1Z2dlciZzaWc9ZWFiM2M4NGNhMDVlMmI3NjM5NTc4NzE2MzJkNzRhZTBlOWVhMTQ1MjpzZXNzaW9uX2lkPTFfTVg0ME5UazJPVFExTW41LU1UVXhNRE0zTXpJeE5UZ3dNSDV2VldFd2IybEVTMHRuUVV0M1JIWXpablo2Vms0NVJrNS1mZyZjcmVhdGVfdGltZT0xNTEwMzczNjM1JnJvbGU9bW9kZXJhdG9yJm5vbmNlPTE1MTAzNzM2MzUuODEzODU2OTI1ODA2NyZleHBpcmVfdGltZT0xNTEyOTY1NjM1";
    public static final String TOKEN = "T1==cGFydG5lcl9pZD00NTk2OTQ1MiZzZGtfdmVyc2lvbj1kZWJ1Z2dlciZzaWc9NzcyYjkwODVjOGJkZThmZTliNmU1N2M5NTY5MDBjOWNiODNmMzdjYjpzZXNzaW9uX2lkPTFfTVg0ME5UazJPVFExTW41LU1UVXhNRE0zTXpJeE5UZ3dNSDV2VldFd2IybEVTMHRuUVV0M1JIWXpablo2Vms0NVJrNS1mZyZjcmVhdGVfdGltZT0xNTEwMzczMjE1JnJvbGU9bW9kZXJhdG9yJm5vbmNlPTE1MTAzNzMyMTUuODIxMTE3Mzk5MTk1MTMmZXhwaXJlX3RpbWU9MTUxMjk2NTIxNQ==";

    /*                           ***** OPTIONAL *****
     If you have set up a server to provide session information replace the null value
     in CHAT_SERVER_URL with it.

     For example: "https://yoursubdomain.com"
    */
    public static final String CHAT_SERVER_URL = null;
    public static final String SESSION_INFO_ENDPOINT = CHAT_SERVER_URL + "/session";


    // *** The code below is to validate this configuration file. You do not need to modify it  ***

    public static String webServerConfigErrorMessage;
    public static String hardCodedConfigErrorMessage;

    public static boolean areHardCodedConfigsValid() {
        if (!OpenTokConfig.API_KEY.isEmpty() && !OpenTokConfig.SESSION_ID.isEmpty() && !OpenTokConfig.TOKEN.isEmpty()) {
            return true;
        }
        else {
            hardCodedConfigErrorMessage = "API KEY, SESSION ID and TOKEN in OpenTokConfig.java cannot be null or empty.";
            return false;
        }
    }

    public static boolean isWebServerConfigUrlValid(){
        webServerConfigErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java must not be null or empty";
        return false;
    }
}
