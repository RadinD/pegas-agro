package ru.radin.pegasagro.models;

import lombok.Data;

@Data
public class Owner {

    private long id;

    private String fullName;

    private String phoneNumber;

    private String email;

}
