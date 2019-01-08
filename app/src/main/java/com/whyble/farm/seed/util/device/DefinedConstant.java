package com.whyble.farm.seed.util.device;

/**
 * Created by silve on 2018-04-18.
 */

public class DefinedConstant {

    public static final boolean DEBUG = true; //디버그모드 작동
    public static final boolean DEBUG_IMAGE = false;
    public static final int DATABASE_VERSION = 3;
    public static final String IMAGE_CACHE_DIR = "thumbs";

    //플레그먼트 캐쉬주기 JSON (분)
    public static final int SYNC_CHANNEL_TIME_MIN = -1; //방송채널

    //신규 친구 표현시간
    public static final int NEW_FRIEND_HOUR = 1; //color표현
    public static final int NEW_FRIEND_NEWLIST_HOUR = 6; //새로운친구 section

    public static final String APIKEY = "4abdc697cee582d3a5316c6bc1984e05"; //web api

    public static final String SENDER_ID = "839265673754"; //Google 프로젝트ID

    /**
     * Intent used to display a message in the screen.
     */
    public static final String DISPLAY_MESSAGE_ACTION = "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

    /**
     * Intent's extra 메시지 디스플레이
     */
    public static final String EXTRA_MESSAGE = "message";
    public static final String DATA_ACTION_ALARM = "kr.coinplus.wallet.ALARM";
    public static final String DATA_ACTION_MSG_ALARM = "kr.coinplus.wallet.MSGALARM";
    public static final String DATA_ACTION_STATUS_ALARM = "kr.coinplus.wallet.STATUSALARM";

    public static final String DATA_ACTION_WALLET_ALARM = "kr.coinplus.wallet.WALLETALARM";




    public static final String SERVER_PREFERRNCE = "svpreferences";
    public static final String MEMBER_PREFERRNCE = "mbpreferences";

    //메인컨텐츠 타일 타입
    public static final int TYPE_PRODUCT = 0;
    public static final int TYPE_PROMOTION = 1;
    public static final int TYPE_CONTENT = 2;
    public static final int TYPE_FRIEND = 3;
    public static final int TYPE_MAX_COUNT = 4;

    //채팅
    public static final String CHAT_MESSAGE_INFO = "INFO";
    public static final String CHAT_MESSAGE_TEXT = "TEXT";
    public static final String CHAT_MESSAGE_ATTACHTEXT = "ATTACHTEXT"; //장문
    public static final String CHAT_MESSAGE_IMAGE = "IMAGE";
    public static final String CHAT_MESSAGE_VIDEO = "VIDEO";
    public static final String CHAT_MESSAGE_CONTACT= "CONTACT";



    public static final int ACTIVITY_CHAT_LOGS = 30;
    public static final int ACTIVITY_TICKER_LOGS = 99;


    public static final int STATUS_ONLINE = 1000;
    public static final int STATUS_AWAY = 1001;
    public static final int STATUS_E_AWAY = 1002;
    public static final int STATUS_DND = 1003;
    public static final int STATUS_FREE = 1004;
    public static final int STATUS_OFFLINE = 1005;

    // Presence Types
    public static final int PRESENCETYPE_AVAILABLE = 1000;
    public static final int PRESENCETYPE_UNAVAILABLE = 1001;
    public static final int PRESENCETYPE_NULL = 1099;

    // Presence Modes
    public static final int PRESENCEMODE_NULL = 1000;
    public static final int PRESENCEMODE_CHAT = 1001;
    public static final int PRESENCEMODE_AWAY = 1002;
    public static final int PRESENCEMODE_XA = 1003;
    public static final int PRESENCEMODE_DND = 1004;
    public static final String IMAGE_MSGBOX_URL = "http://talk.nesaram.com/Share/chSendImgBox";


    public static final String TALK_SERVER_URL = "http://";
    public static final String MEMBER_REQ_URL = TALK_SERVER_URL+"memberReqList?";
    public static final String SEND_IMG_TO_CHATROOM_URL = TALK_SERVER_URL+"sendImageToChatRoom";
    public static final String SEND_IMG_TO_USER_URL = TALK_SERVER_URL+"sendImageToUser";

    public static final String MAKE_CHAT_ROOM_URL = TALK_SERVER_URL+"chatRoomCreate";
    public static final String INVITE_CHAT_ROOM_URL = TALK_SERVER_URL+"chatRoomInvite";
    public static final String EXIT_CHAT_ROOM_URL = TALK_SERVER_URL+"chatRoomExit";


    public static final String WHYBLE_HOST = "https://milicecoinwallet.com";
    public static final String API_HOST = "http://miliceapi.com";

    //쉐어드프리퍼런스
    public static final String SMS_CODE = "SMS_CODE"; //Google 프로젝트ID
    public static final String USER_PHONE_NUMBER = "USER_PHONE_NUMBER"; //Google 프로젝트ID
    public static final String USER_PASSWORD = "USER_PASSWORD"; //Google 프로젝트ID
    public static final String USER_UNIQUE_ID = "USER_UNIQUE_ID"; //Google 프로젝트ID
    public static final String USER_LANG = "USER_LANG"; //Google 프로젝트ID
    public static final String USER_LANG_INDEX = "USER_LANG_INDEX"; //Google 프로젝트ID
    public static final String USER_FACE_ID = "USER_FACE_ID"; //Google 프로젝트ID

    public static final String COIN_FLAG = "milce";
}
