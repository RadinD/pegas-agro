package ru.radin.pegasagro.models;


import lombok.Data;

import java.time.LocalDate;

@Data
public class Car {

    private long id;

    private String numberPlate;

    private LocalDate buildDate;


}
