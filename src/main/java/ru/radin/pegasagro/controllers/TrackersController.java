package ru.radin.pegasagro.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.radin.pegasagro.models.NmeaParser.Tracker;


@Controller
@RequestMapping("/trackers")
public class TrackersController {

    private final Tracker tracker;

    @Autowired
    public TrackersController(Tracker tracker){
        this.tracker = tracker;
    }

    @GetMapping()
    public String newTracker(){
        return "trackers/new";
    }

    @PostMapping()
    public String uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile){
        tracker.setMultipartFile(multipartFile);
        tracker.convertMultipartFileToFile();
        return "redirect:trackers/info";
    }

    @GetMapping("/info")
    public String info(Model model){
        tracker.calculateActiveDistance();
        model.addAttribute("result", tracker.getActiveDistance());
        return "trackers/info";
    }

}
