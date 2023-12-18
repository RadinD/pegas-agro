package ru.radin.pegasagro.models.NmeaParser;

public class Speed {

    private double speed; // [км/ч]

    public Speed(){}

    public Speed(VTGSentence vtgSentence){
        setSpeedFromVTG(vtgSentence);
    }

    public void setSpeedFromVTG(VTGSentence vtgSentence){
        speed = vtgSentence.getSpeed();
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}

