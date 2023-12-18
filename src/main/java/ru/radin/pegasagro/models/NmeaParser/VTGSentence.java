package ru.radin.pegasagro.models.NmeaParser;

public class VTGSentence {

    private double speed; // [км/ч]

    public VTGSentence(){}

    public VTGSentence(String str){
        parseVTGSentence(str);
    }


    private void parseSpeed(String strData){
        String[] strDataArray = strData.split(",");

        if(strDataArray[7].isEmpty())
            speed = 0;
        else
            speed = Double.parseDouble(strDataArray[7]);

    }

    public void parseVTGSentence(String strData){
        parseSpeed(strData);
    }

    public double getSpeed(){
        return speed;
    }

    public String toString(){
        return "Скорость: " + speed + " ;";
    }

}