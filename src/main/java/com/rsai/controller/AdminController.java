package com.rsai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import com.rsai.model.Need;
import com.rsai.model.Volunteer;
import com.rsai.repo.NeedRepo;
import com.rsai.repo.VolunteerRepo;
import com.rsai.service.WhatsAppService;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired private NeedRepo needRepo;
    @Autowired private VolunteerRepo volunteerRepo;
    @Autowired private WhatsAppService wa;

    @GetMapping("/needs")
    public List<Need> getAllNeeds() {
        return needRepo.findAll();
    }

    @PostMapping("/need")
    public Need create(@RequestBody Need n) {
        n.setStatus("OPEN");
        return needRepo.save(n);
    }

    @PostMapping("/assign/{id}")
    public Need assign(@PathVariable Long id,
                       @RequestParam String phone) {
    	System.out.println(phone);
        Need n = needRepo.findById(id).orElseThrow();
        n.setVolunteerPhone(phone);
        n.setStatus("SENT");
        n.setToken(UUID.randomUUID().toString());
        needRepo.save(n);
        String acceptLink = "http://localhost:9090/accept/" + id + "?token=" + n.getToken();
        String rejectLink = "http://localhost:9090/reject/" + id + "?token=" + n.getToken();
        wa.send(phone,
                "Task: " + n.getDescription() +
                "\nAccept: " + acceptLink +
                "\nReject: " + rejectLink);
        return n;
    }

    @GetMapping("/volunteers")
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepo.findAll();
    }

    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalNeeds", needRepo.count());
        stats.put("highUrgency", needRepo.findAll().stream()
                .filter(n -> n.getUrgencyScore() >= 8).count());
        stats.put("volunteersActive", volunteerRepo.findAll().stream()
                .filter(v -> "AVAILABLE".equals(v.getStatus())).count());
        stats.put("needsResolved", (long)(needRepo.findByStatus("ACCEPTED").size() +
                needRepo.findByStatus("REJECTED").size()));
        return stats;
    }
}