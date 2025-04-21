package com.example.donation_app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.NeedDTO;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Need;
import com.example.donation_app.Service.NeedService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("api/needs")
public class NeedController {
    
    private final NeedService needService;

    @Autowired
    public NeedController(NeedService needService) {
        this.needService = needService;
    }

    @PostMapping("/post/{charityId}")
    public String postMethodName(@PathVariable Long charityId, @RequestBody NeedDTO dto) {
        Need need = needService.postNeed(charityId, dto);
        return "Need posted successfully with id: " + need.getId();
    }

    @GetMapping("pendingNeeds")
    public ResponseEntity<List<Need>> getPendingNeeds() {
        List<Need> needs = needService.getPendingNeeds();
        if (needs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(needs);
        }
    }
    
    
}
