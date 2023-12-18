package ru.radin.pegasagro.models.NmeaParser;

public class Route {

    private Point startPoint;
    private Point endPoint;
    private Speed routeSpeed;

    public Route(){}

    public Route(Point startPoint, Point endPoint, Speed routeSpeed){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.routeSpeed = routeSpeed;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Speed getRouteSpeed() {
        return routeSpeed;
    }

    public void setRouteSpeed(Speed routeSpeed) {
        this.routeSpeed = routeSpeed;
    }

    // Формула гаверсинусов
    public double calculateDistance(){
        double earthRadius = 6372795; //[м]

        double deltaLatitude = endPoint.getLatitude() - startPoint.getLatitude();
        double deltaLongitude = endPoint.getLongitude() - startPoint.getLongitude();

        double distance = earthRadius * 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(deltaLatitude/2),2) +
                        Math.cos(startPoint.getLatitude()) *
                                Math.cos(endPoint.getLatitude()) *
                                Math.pow(Math.sin(deltaLongitude/2),2)
        ));
        return distance;
    }

    public double calculateActiveDistance(){

        if (routeSpeed.getSpeed() == 0)
            return 0;
        else
            return calculateDistance();

    }


}