package ru.radin.pegasagro.models.NmeaParser;

public class SentenceId {

        public static String parseSentenceID(String strData){
        String strSentenceId = strData.substring(3,6);
        return strSentenceId;
    }

    //Добавить перечисление со значениями идентификаторов сообщений nmea
    // Возвращать в методе parseSentenceID()  объект перечисления
    // добавить проверку значения идентификатора сообщений
}

