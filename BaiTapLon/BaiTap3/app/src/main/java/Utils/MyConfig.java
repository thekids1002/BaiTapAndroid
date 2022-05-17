package Utils;

public class MyConfig {
    public static String GEOSNAME_API = "http://api.geonames.org/countryInfoJSON?username=btandroid2";

    public static String getAPIConverter(String currency_name_1 , String currency_name_2){
        return  "https://" + currency_name_1 + ".fxexchangerate.com/" + currency_name_2 + ".xml";
    }
    public static String getImageLink(String s){
        return "http://img.geonames.org/flags/x/" + s.toLowerCase()+ ".gif";
    }
}
