package ru.radin.pegasagro.models.NmeaParser;

import java.util.regex.Pattern;

public class SentenceValidator {

    private static final Pattern sentence = Pattern.compile(
            "^[$]{1}[A-Z]{5}[-,.A-Z0-9]*[*][A-Z0-9]{2}$"
    );

    private SentenceValidator(){}

    public static boolean isSentence(String nmea){
        if(nmea == null || nmea.isEmpty()){
            return false;
        }

        // Добавить проверку контрольной суммы
        // Добавить проверку длины выражения
        // Добавить проверку положения разделителя контрольной суммы

        return sentence.matcher(nmea).matches();
    }


}