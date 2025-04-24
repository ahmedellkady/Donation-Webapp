package com.example.donation_app.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.NeedDTO;
import com.example.donation_app.Service.NeedService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/needs")
public class NeedController {
    
    private final NeedService needService;

    public NeedController(NeedService needService) {
        this.needService = needService;
    }

    @PostMapping("/post/{charityId}")
    public ResponseEntity<NeedDTO> postMethodName(@PathVariable Long charityId, @RequestBody NeedDTO dto) {
        NeedDTO need = needService.postNeed(charityId, dto);
        return ResponseEntity.ok(need);
    }

    @GetMapping("pending-needs")
    public ResponseEntity<List<NeedDTO>> getPendingNeeds() {
        List<NeedDTO> needs = needService.getPendingNeeds();
        if (needs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(needs);
        }
    }
    
    @GetMapping("charity/{charityId}")
    public ResponseEntity<List<NeedDTO>> getCharityNeeds(@PathVariable Long charityId) {
        List<NeedDTO> needs = needService.getNeedsForCharity(charityId);
        
        return ResponseEntity.ok(needs);
    }
    
}
