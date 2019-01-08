package com.whyble.farm.seed.util.device;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.whyble.farm.seed.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DeviceUtils {

	private static final String TAG = "DeviceUtils";

	public static void vibrator(Context context, int time) {
	}

	public static String getCountryZipCode(Context context) {

		String CountryID = "";
		String CountryZipCode = "";

		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		//getNetworkCountryIso
		CountryID = manager.getSimCountryIso().toUpperCase();
		String[] rl = context.getResources().getStringArray(R.array.CountryCodes);
		for (int i = 0; i < rl.length; i++) {
			String[] g = rl[i].split(",");
			if (g[1].trim().equals(CountryID.trim())) {
				CountryZipCode = g[0];
				break;
			}
		}
		return CountryZipCode;

//		TelephonyManager manager = (TelephonyManager)ac
//                .getSystemService(Context.TELEPHONY_SERVICE);
//
//        return manager.getNetworkCountryIso().toUpperCase();

	}

	public static String getEmail(Context context) {
		AccountManager accountManager = AccountManager.get(context);
		Account account = getAccount(accountManager);

		if (account == null) {
			return null;
		} else {
			return account.name;
		}
	}

	private static Account getAccount(AccountManager accountManager) {
	/*	Account[] accounts = accountManager.getAccountsByType("com.google");
		Account account;
		if (accounts.length > 0) {
			account = accounts[0];
		} else {
			account = null;
		}*/
		return null;
	}

	public static String getCountryCode(Context context) {
//		String CountryID="";
//		String CountryCode="";
//
//		TelephonyManager manager = (TelephonyManager) ac.getSystemService(Context.TELEPHONY_SERVICE);
//			  //getNetworkCountryIso
//		CountryID= manager.getSimCountryIso().toUpperCase();
//		String[] rl=ac.getResources().getStringArray(R.array.CountryCodes);
//		for(int i=0;i<rl.length;i++){
//			String[] g=rl[i].split(",");
//			if(g[1].trim().equals(CountryID.trim())){
//				CountryCode=g[1];
//				break;
//			}
//		}
//		return CountryCode;

		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		return manager.getNetworkCountryIso().toUpperCase();

	}

	/*
	 * test mode
	 */
//	public static String getPhoneNumber(Context context){
//		return "01000000001";
//	}
	public static String getPhoneNumber(Context context) {
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		@SuppressLint("MissingPermission") String phoneNumber = manager.getLine1Number();
		//phoneNumber = "+82 10-1222-1111";
		phoneNumber = PhoneNumberUtils.stripSeparators(phoneNumber);

		String countryCode = getCountryZipCode(context);
		//GLOBAL 번호
		if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
			//PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
			if(phoneNumber.startsWith("+"+countryCode  ))
				phoneNumber = phoneNumber.replace("+"+countryCode, "").trim();
			else if(phoneNumber.startsWith(countryCode))
				phoneNumber = phoneNumber.replace(countryCode, "").trim();
			
			if(!phoneNumber.startsWith("0")) phoneNumber = "0"+phoneNumber;
		}else{
		}
		return phoneNumber;
	}

	public static String StrNum(String str){
		try{
			for (int i=0; i < str.length() ; i++){
				if (!(str.substring(i,i+1).indexOf("0") > -1)) {
					return str.substring(i);
				}
			}
		}catch (IndexOutOfBoundsException e){
			str = "";
		}
		return str;
	}

	public static void deleteFile(String path){
		File file = new File(path);
		file.delete();
	}

	public static void setClipBoardLink(Context context , String link){

		ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
		ClipData clipData = ClipData.newPlainText("label", link);
		clipboardManager.setPrimaryClip(clipData);
		Toast.makeText(context, context.getString(R.string.toast_text_clipboard_adress), Toast.LENGTH_SHORT).show();
	}

	public static String getOperatorName(Context context){

		TelephonyManager tm =(TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
		String operatorName = tm.getNetworkOperatorName();
		if(operatorName==null || operatorName.equals("")){
			operatorName = tm.getSimOperatorName();
		}
		if(operatorName==null || operatorName.equals("")){
			operatorName = "unknown";
		}
		return operatorName;
	}
	
	public static String getOSVersion(){
		return System.getProperty("os.version");
	}
	
	public static String getFirmwareVersion(){
		return Build.VERSION.RELEASE+"";
	}
	
	public static String getDeviceModel(){
		return Build.MODEL+"";
	}

	public static String getDeviceName() {
		return Build.MANUFACTURER+"";
	}
	
	public static boolean hasStorage(boolean requireWriteAccess) {
	    String state = Environment.getExternalStorageState();
	    if(DefinedConstant.DEBUG) Log.v(TAG, "storage state is " + state);

	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        if (requireWriteAccess) {
	            boolean writable = checkFsWritable();
	            if(DefinedConstant.DEBUG) Log.v(TAG, "storage writable is " + writable);
	            return writable;
	        } else {
	            return true;
	        }
	    } else if (!requireWriteAccess && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	private static boolean checkFsWritable() {
        // Create a temporary file to see whether a volume is really writeable.
        // It's important not to put it in the root directory which may have a
        // limit on the number of files.
        String directoryName = Environment.getExternalStorageDirectory().toString() + "/DCIM";
        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }
        return directory.canWrite();
    }

    public static String getCurrentDate(){
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format ( currentTime );
		return mTime;
	}
	
}
