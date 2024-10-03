package org.example.controllers;

import org.example.entities.Grievance;
import org.example.services.GrievanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grievances")
public class GrievanceController {

    @Autowired
    private GrievanceService grievanceService;

    @GetMapping
    public List<Grievance> getAllGrievances() {
        return GrievanceService.getGrievances();
    }

    @GetMapping ("/{id}")
    public Optional<Grievance> getGrievanceById(@PathVariable Long id) {
        return GrievanceService.getGrievance(id);
    }

    @PostMapping
    public ResponseEntity<String> addGrievance(@RequestBody Grievance grievance) {
        GrievanceService.addGrievance(grievance);
        return ResponseEntity.ok("Grievance Submitted successfully");
    }

    @GetMapping ("/assignee/{name}")
    public ResponseEntity<List<Grievance>> getGrievanceByAssignee(@PathVariable String name){
        try{
            List<Grievance> grievances = grievanceService.getGrievanceByAssignee(name);
            return ResponseEntity.ok(grievances);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);// Replace with proper logging
        }
    }

    @PutMapping ("/{id}")
    public ResponseEntity<String> updateAssignee(@PathVariable Long id,@RequestBody Grievance grievance){
        grievanceService.updateAssignee(id,grievance);
        return ResponseEntity.ok("Assignee added");
    }

    @PutMapping ("/status/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id){
        grievanceService.updateStatus(id);
        return ResponseEntity.ok("status updated");
    }

}
