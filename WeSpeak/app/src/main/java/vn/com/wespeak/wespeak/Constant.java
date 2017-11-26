package vn.com.wespeak.wespeak;

public interface Constant {

    boolean DEBUG = true;

    interface EXTRA {
        String USER_LOGIN = "User Login";
        String PROFILE_USER = "Profile user";
        String DIALOG_START_CALLING = "Dialog start calling";
        String USER_ANOTHER = "User receiver";
        String USER_MAIN = "User calling";
        String CALL_TYPE = "Call type";
        String USER_RATING_TYPE = "User rating type";
        String CONVERSATION_ID = "Conversation id";
        String CONVERSATION_ID_RATING = "Conversation id rating";
        String USER_RATING = "User Rating";
        String USER_CONVERSATION = "User conversation";
        String SEARCH_TYPE = "Search type";
        String DIALOG_FIND_TYPE = "Dialog find type";
        String USER_URL = "User url";
    }

    interface TypeLogin {
        int LEANER = 1;
        int TEACHER = 2;
    }

    interface POSITION {
        int SEARCH = 0;
        int CONVERSTATION = 1;
        int PROFILE = 2;
    }

    interface CallType{
        int CALLING = 1;
        int RECEIVER = 2;
    }
}
