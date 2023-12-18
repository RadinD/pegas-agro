package ru.radin.pegasagro.models.NmeaParser;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


@Component
public class Tracker {

    private MultipartFile multipartFile;

    private File file;

    private double activeDistance;

    public Tracker(){}


    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void convertMultipartFileToFile(){

        try{
            file = new File(multipartFile.getOriginalFilename());
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public void calculateActiveDistance(){

        double resultDistance = 0;

        try{
            Scanner scanner = new Scanner(file);

            GGASentence ggaSentence = new GGASentence();
            VTGSentence vtgSentence = new VTGSentence();

            Point tempStartPoint = new Point();
            Point tempEndPoint = new Point();
            Speed tempRouteSpeed = new Speed();

            Route route = new Route(tempStartPoint,tempEndPoint, tempRouteSpeed);

            while(scanner.hasNext()){

                String line = scanner.nextLine();

                if(SentenceValidator.isSentence(line)){
                    String sentenceId = SentenceId.parseSentenceID(line);

                    if(sentenceId.equals("GGA")){
                        ggaSentence.parseGGASentence(line);

                        if(tempStartPoint.getLatitude()!=0 | tempStartPoint.getLongitude()!=0){
                            tempEndPoint.setCoordinatesFromGGA(ggaSentence);
                            resultDistance += route.calculateActiveDistance();
                            tempStartPoint.setCoordinatesFromGGA(ggaSentence);
                            tempRouteSpeed.setSpeed(0);
                        }else{
                            tempStartPoint.setCoordinatesFromGGA(ggaSentence);
                        }
                    }
                    if (sentenceId.equals("VTG")) {
                        vtgSentence.parseVTGSentence(line);
                        tempRouteSpeed.setSpeedFromVTG(vtgSentence);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        activeDistance = resultDistance;
    }

    public double getActiveDistance(){
        return activeDistance;
    }

}
