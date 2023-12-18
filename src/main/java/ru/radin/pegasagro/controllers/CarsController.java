package ru.radin.pegasagro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import ru.radin.pegasagro.models.Car;
import ru.radin.pegasagro.repositories.CarRepository;
import ru.radin.pegasagro.repositories.OwnerRepository;

@Controller
@RequestMapping("/cars")
public class CarsController {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CarsController(CarRepository carRepository, OwnerRepository ownerRepository){
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("cars", carRepository.findAll());
        return "cars/index";
    }

    @GetMapping("/{id}")
    public String show (@PathVariable("id") String id, Model model){

        if(carRepository.findById(id).isPresent())
            model.addAttribute("car", carRepository.findById(id).get());
        // добавить исключение, если внутри Optional хранится значение null

        if(ownerRepository.getCarOwner(id).isPresent())
            model.addAttribute("owner", ownerRepository.getCarOwner(id).get());

        return "cars/show";
    }

    @GetMapping("/new")
    public String newCar(Model model){
        model.addAttribute("car", new Car());
        return "cars/new";
    }

    @PostMapping()
    public String create(@ModelAttribute ("car") Car car){
        carRepository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/{id}/selectOwner")
    public String assignOwner(Model model, @PathVariable("id") String id){

        if(carRepository.findById(id).isPresent())
            model.addAttribute("car", carRepository.findById(id).get());

        return "cars/assignOwner";
    }

    @PatchMapping("/{id}")
    public String assignOwner(@ModelAttribute("car") Car car, @PathVariable("id") String id,
                              @RequestParam("ownerId") String ownerId){
        carRepository.assignOwnerByOwnerId(ownerId, car);
        return "redirect:/cars";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id){
        carRepository.delete(id);
        return "redirect:/cars";
    }

}
