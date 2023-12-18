package ru.radin.pegasagro.models.NmeaParser;

public class GGASentence {

    private double latitude; //Широта в формате "ГГММ.МММММ"

    private double longitude; // Долгота в формате "ГГГММ.МММММ"

    public GGASentence(){}

    public GGASentence(String str){
        parseGGASentence(str);
    }

    private void parseLatitude(String strData){
        String[] strDataArray = strData.split(",");

        if (strDataArray[2].isEmpty()){
            latitude = 0;        // Не может быть == 0
            //throw new Exception("Не валидное значение долготы");
        }
        else
            latitude = Double.parseDouble(strDataArray[2]);
    }

    private void parseLongitude(String strData){
        String[] strDataArray = strData.split(",");

        if (strDataArray[4].isEmpty())
            longitude = 0;
        else
            longitude = Double.parseDouble(strDataArray[4]);

    }

    public void parseGGASentence(String strData){
        parseLatitude(strData);
        parseLongitude(strData);
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    @Override
    public String toString(){
        return "Широта: " + latitude + " ; Долгота: " + longitude + " ;";
    }

}
