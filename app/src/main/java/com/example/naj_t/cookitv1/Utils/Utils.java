package com.example.naj_t.cookitv1.Utils;

public class Utils {
    /*
    * ^                 # start-of-string
(?=.*[0-9])       # a digit must occur at least once
(?=.*[a-z])       # a lower case letter must occur at least once
(?=.*[A-Z])       # an upper case letter must occur at least once
(?=.*[@#$%^&+=])  # a special character must occur at least once
(?=\S+$)          # no whitespace allowed in the entire string
.{8,}             # anything, at least eight places though
$                 # end-of-string

*/
    public static boolean isPassword(String pass){
        String pattern ="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}";
        return pass.matches(pattern);
    }
    public static boolean isPasswordConf(String pass, String passConf){
        return pass.equals(passConf);
    }
}
