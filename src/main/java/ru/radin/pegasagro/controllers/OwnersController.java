package ru.radin.pegasagro.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import ru.radin.pegasagro.controllers.exceptions.OwnerNotFoundException;
import ru.radin.pegasagro.models.Owner;
import ru.radin.pegasagro.repositories.CarRepository;
import ru.radin.pegasagro.repositories.DealerRepository;
import ru.radin.pegasagro.repositories.OwnerRepository;

@Controller
@RequestMapping("/owners")
public class OwnersController {

    private final OwnerRepository ownerRepository;
    private final CarRepository carRepository;
    private final DealerRepository dealerRepository;

    @Autowired
    public OwnersController(OwnerRepository ownerRepository, CarRepository carRepository,
                            DealerRepository dealerRepository){
        this.ownerRepository = ownerRepository;
        this.carRepository = carRepository;
        this.dealerRepository = dealerRepository;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("owners", ownerRepository.findAll());
        return "owners/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model){

        this.validateOwner(id);

        if(ownerRepository.findById(id).isPresent())
            model.addAttribute("owner", ownerRepository.findById(id).get());

        if(dealerRepository.getOwnerDealer(id).isPresent())
            model.addAttribute("dealer", dealerRepository.getOwnerDealer(id).get());

        model.addAttribute("cars", carRepository.getCarsByOwnerId(id));
        return "owners/show";
    }

    @GetMapping("/new")
    public String newOwner(Model model){
        model.addAttribute("owner", new Owner());
        return "owners/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("owner") Owner owner){
        ownerRepository.save(owner);
        return "redirect:/owners";
    }

    @GetMapping("/{id}/selectDealer")
    public String assignDealer(Model model, @PathVariable("id") String id){

        if(ownerRepository.findById(id).isPresent())
            model.addAttribute("owner", ownerRepository.findById(id).get());

        return "owners/assignDealer";
    }

    @PatchMapping("/{id}")
    public String assignDealer(@ModelAttribute("owner") Owner owner, @PathVariable("id") String id,
                               @RequestParam("dealerId") String dealerId){
        ownerRepository.assignDealerByDealerId(dealerId, owner);
        return "redirect:/owners";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id){
        ownerRepository.delete(id);
        return "redirect:/owners";
    }

    private void validateOwner(String ownerId){
        this.ownerRepository.findById(ownerId).orElseThrow(
                ()->new OwnerNotFoundException(ownerId));
    }

}
