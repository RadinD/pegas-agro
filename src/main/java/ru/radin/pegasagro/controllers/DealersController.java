package ru.radin.pegasagro.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import ru.radin.pegasagro.models.Dealer;
import ru.radin.pegasagro.repositories.CarRepository;
import ru.radin.pegasagro.repositories.DealerRepository;
import ru.radin.pegasagro.repositories.OwnerRepository;

@Controller
@RequestMapping("/dealers")
public class DealersController {

    private final DealerRepository dealerRepository;
    private final OwnerRepository ownerRepository;
    private final CarRepository carRepository;

    @Autowired
    public DealersController(DealerRepository dealerRepository, OwnerRepository ownerRepository,
                             CarRepository carRepository) {
        this.dealerRepository = dealerRepository;
        this.ownerRepository = ownerRepository;
        this.carRepository = carRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("dealers", dealerRepository.findAll());
        return "dealers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {

        if (dealerRepository.findById(id).isPresent())
            model.addAttribute("dealer", dealerRepository.findById(id).get());

        model.addAttribute("owners", ownerRepository.getOwnersByDealerId(id));
        model.addAttribute("cars", carRepository.getCarsByDealerId(id));
        return "dealers/show";
    }

    @GetMapping("/new")
    public String newDealer(Model model) {
        model.addAttribute("dealer", new Dealer());
        return "dealers/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("dealer") Dealer dealer){
        dealerRepository.save(dealer);
        return "redirect:/dealers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        dealerRepository.delete(id);
        return "redirect:/dealers";
    }

}
