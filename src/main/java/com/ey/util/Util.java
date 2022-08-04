package com.ey.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Util {	
	
	public static boolean isEmailValid(String email) {
	    final Pattern EMAIL_REGEX = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);    
	    return EMAIL_REGEX.matcher(email).matches();
	}
	
	public static boolean isPasswordValid(String password) {
		final Pattern PASSWPRD_REGEX = Pattern.compile("[A-Z].[a-z]+[0-9]{2}");
	    return PASSWPRD_REGEX.matcher(password).matches();
	
	}
	
	public static String cryptWithMD5(String pass) throws Exception{
	    try {
	    	
	    	MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] passBytes = pass.getBytes();
	        md.reset();
	        byte[] digested = md.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException ex) {
	    	throw new Exception(ex.getMessage());
	    }
	}

}
