package com.ibnsaad.thedcc.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

//import com.auth0.android.jwt.JWT;

public class AuthHelper {
    private static final String JWT_KEY_USERNAME = "username";

    private static final String PREFS = "prefs";
    private static final String PREF_TOKEN = "pref_token";
    private static final String PREF_ID="pref_id";
    private SharedPreferences mPrefs;

    private static AuthHelper sInstance;
    //context
    private AuthHelper(@NonNull Context context) {
        mPrefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sInstance = this;
    }
    public static AuthHelper getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new AuthHelper(context);
        }
        return sInstance;
    }

    public void setIdToken(@NonNull String token) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_TOKEN, token);
        editor.apply();
    }

    public void setIdUser(@NonNull int id){
        SharedPreferences.Editor editor=mPrefs.edit();
        editor.putInt(PREF_ID,id);
        editor.apply();
    }

    public int getId(){
        return  mPrefs.getInt(PREF_ID,1);
    }

    @Nullable
    public String getIdToken() {
        return mPrefs.getString(PREF_TOKEN, null);
    }

    public boolean isLoggedIn() {
        String token = getIdToken();
        return token != null;
    }

    /**
     * Gets the username of the signed in user
     * @return - username of the signed in user
     */
//    public String getUsername() {
//        if (isLoggedIn()) {
//            return decodeUsername(getIdToken());
//        }
//        return null;
//    }
//
//    @Nullable
//    private String decodeUsername(String token) {
//        JWT jwt = new JWT(token);
//        try {
//            if (jwt.getClaim(JWT_KEY_USERNAME) != null) {
//                return jwt.getClaim(JWT_KEY_USERNAME).asString();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void clear() {
        mPrefs.edit().clear().commit();
    }
}
