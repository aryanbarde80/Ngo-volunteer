package com.rsai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rsai.model.Need;
import com.rsai.repo.NeedRepo;

@RestController
@RequestMapping("/public")
public class PublicActionController {

    @Autowired private NeedRepo repo;

    @GetMapping("/status/{id}")
    public String status(@PathVariable Long id) {
        Need n = repo.findById(id).orElseThrow();
        return "Need #" + id + " status: " + n.getStatus();
    }
}