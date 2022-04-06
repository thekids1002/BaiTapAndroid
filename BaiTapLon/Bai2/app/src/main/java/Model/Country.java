package Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Country implements Serializable {
    private String image;
    private String country_name;
    private String population;
    private String areaInSqKm;
    private Bitmap flag;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCountry_name() {
        return country_name;
    }

    public Bitmap getFlag() {
        return flag;
    }

    public void setFlag(Bitmap flag) {
        this.flag = flag;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public Country(String image, String country_name, String population, String areaInSqKm) {
        this.image = image;
        this.country_name = country_name;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
    }

    public Country() {
    }

    @Override
    public String toString() {
        return "Country{" +
                "image='" + image + '\'' +
                ", country_name='" + country_name + '\'' +
                ", population='" + population + '\'' +
                ", areaInSqKm='" + areaInSqKm + '\'' +
                '}';
    }
}
