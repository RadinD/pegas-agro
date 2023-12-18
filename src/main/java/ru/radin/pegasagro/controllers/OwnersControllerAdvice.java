package ru.radin.pegasagro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.radin.pegasagro.controllers.exceptions.OwnerNotFoundException;

@ControllerAdvice
public class OwnersControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public String ownerNotFoundExceptionHandler(OwnerNotFoundException ex, Model model){
        model.addAttribute("message", ex.getMessage());
        return "exceptions/owner404";
    }

}
