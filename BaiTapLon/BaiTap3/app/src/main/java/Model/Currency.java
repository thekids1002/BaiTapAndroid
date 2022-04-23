package Model;

import java.util.ArrayList;

public class Currency {
    public static ArrayList<Currency> currencies;

    public Currency() {
        currencies = new ArrayList<Currency>();
    }

    private String countryCode;

    private String currencyCode;

    private String CurrencyVal;

    public static ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public static void setCurrencies(ArrayList<Currency> currencies) {
        Currency.currencies = currencies;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyVal() {
        return CurrencyVal;
    }

    public void setCurrencyVal(String currencyVal) {
        CurrencyVal = currencyVal;
    }
}
