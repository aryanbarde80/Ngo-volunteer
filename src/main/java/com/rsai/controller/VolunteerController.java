package com.rsai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rsai.model.Volunteer;
import com.rsai.repo.VolunteerRepo;
import java.util.List;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    @Autowired private VolunteerRepo repo;

    @GetMapping("/all")
    public List<Volunteer> all() {
        return repo.findAll();
    }

    @PutMapping("/status/{id}")
    public Volunteer updateStatus(@PathVariable Long id,
                                  @RequestParam String status) {
        Volunteer v = repo.findById(id).orElseThrow();
        v.setStatus(status);
        return repo.save(v);
    }
}