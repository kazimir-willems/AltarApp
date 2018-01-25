package leif.statue.com.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "AltarPreference";
    private static final String TAG_FIRST_LOGIN = "first_login";
    private static final String TAG_LANGUAGE = "language";
    private static final String TAG_DEVICE_TOKEN = "device_token";
    private static final String TAG_PREFECTURE = "prefecture";
    private static final String TAG_AGE = "age";
    private static final String TAG_EMAIL_ADDRESS = "email";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_IS_NOTICE = "is_notice";
    private static final String TAG_LOGIN = "login_flag";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_BUDDHIST_ID = "buddhist_id";
    private static final String TAG_HONJOU_IMAGE = "honjou_image";
    private static final String TAG_BUTSUGU_BACKGROUND = "butsugu_image";
    private static final String TAG_SPEED = "speed";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_COMPLETE_HONZON = "complete_honzon";
    private static final String TAG_MUSIC_LEVEL = "music_level";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveLanguage(String language){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_LANGUAGE, language);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getLanguage(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_LANGUAGE, "en");
    }

    public boolean saveFirstLogin(boolean bFirstLogin){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TAG_FIRST_LOGIN, bFirstLogin);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public boolean getFirstLogin(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(TAG_FIRST_LOGIN, true);
    }

    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_DEVICE_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_DEVICE_TOKEN, "");
    }

    public boolean saveEmailAddress(String address){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_EMAIL_ADDRESS, address);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getEmailAddress(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_EMAIL_ADDRESS, "");
    }

    public boolean savePrefecture(int prefecture){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_PREFECTURE, prefecture);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public int getPrefecture(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_PREFECTURE, 1);
    }

    public boolean saveAge(int age){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_AGE, age);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public int getAge(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_AGE, 1);
    }

    public boolean saveGender(int gender){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_GENDER, gender);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public int getGender(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_GENDER, 1);
    }

    public boolean saveNotice(int notice){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_IS_NOTICE, notice);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public int getNotice(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_IS_NOTICE, 1);
    }

    public boolean saveLogin(boolean flag){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TAG_LOGIN, flag);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public boolean getLogin(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(TAG_LOGIN, false);
    }

    public boolean saveUserId(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_USER_ID, id);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public int getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_USER_ID, 0);
    }

    public boolean saveBuddhistId(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_BUDDHIST_ID, id);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public int getBuddhistId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_BUDDHIST_ID, 0);
    }

    public boolean saveHonjou(String honjou){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_HONJOU_IMAGE, honjou);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getHonjou(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_HONJOU_IMAGE, "");
    }

    public boolean saveBuddhistImage(String image){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_BUTSUGU_BACKGROUND, image);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getBuddhistImage(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_BUTSUGU_BACKGROUND, "");
    }

    public boolean saveSpeed(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_SPEED, id);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public int getSpeed(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_SPEED, 5);
    }

    public boolean savePassword(String password){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_PASSWORD, password);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getPassword(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_PASSWORD, "");
    }

    public boolean saveCompleteHonzon(String honzon){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_COMPLETE_HONZON, honzon);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getCompleteHonzon(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_COMPLETE_HONZON, "");
    }

    public boolean saveMusicLevel(int value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_MUSIC_LEVEL, value);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public int getMusicLevel(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(TAG_MUSIC_LEVEL, 1);
    }
}