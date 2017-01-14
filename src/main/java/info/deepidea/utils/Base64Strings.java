package info.deepidea.utils;


import java.util.Base64;

public final class Base64Strings {
    private Base64Strings(){
        throw new IllegalStateException("forbidden to build object for util class");
    }

    public static byte[] decode(String base64String){
        return Base64.getDecoder().decode(base64String);
    }

    public static String encode(byte [] bytes){
        return new String(Base64.getEncoder().encode(bytes));
    }
}
