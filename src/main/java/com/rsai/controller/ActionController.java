package com.rsai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rsai.model.Need;
import com.rsai.repo.NeedRepo;

@RestController
public class ActionController {

    @Autowired
    private NeedRepo repo;

    @GetMapping("/accept/{id}")
    public String accept(@PathVariable Long id,
                         @RequestParam String token) {
        Need n = repo.findById(id).orElseThrow();
        if (!n.getToken().equals(token)) {
            return "Invalid token";
        }
        n.setStatus("ACCEPTED");
        repo.save(n);
        return "Accepted! Thank you for accepting this task.";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable Long id,
                         @RequestParam String token) {
        Need n = repo.findById(id).orElseThrow();
        n.setStatus("REJECTED");
        repo.save(n);
        return "Rejected. We will assign another volunteer.";
    }
}