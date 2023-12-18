package ru.radin.pegasagro.controllers.exceptions;

public class OwnerNotFoundException extends  RuntimeException{

    public OwnerNotFoundException(String ownerId){
        super("Владелец с ID = " + ownerId + " не найден!");
    }

}
