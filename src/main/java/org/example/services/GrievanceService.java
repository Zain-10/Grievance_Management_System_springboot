package org.example.services;

import org.example.entities.Grievance;
import org.example.repositories.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrievanceService {

    private static GrievanceRepository grievanceRepository;

    @Autowired
    public GrievanceService(GrievanceRepository grievanceRepository) {
        GrievanceService.grievanceRepository = grievanceRepository;
    }

    public static List<Grievance> getGrievances() {
        return grievanceRepository.findAll();
    }

    public static Optional<Grievance> getGrievance(Long id) {
        return grievanceRepository.findById(id);
    }

    public static void addGrievance(Grievance Grievance) {
        grievanceRepository.save(Grievance);
    }

    public static List<Grievance> getGrievanceByAssignee(String name) {
        try {
            return grievanceRepository.findByName(name);
        } catch (Exception e) {
            // Log the exception and handle it appropriately
            e.printStackTrace(); // Replace with proper logging
            return List.of(); // Return an empty list or handle it as needed
        }
    }

    public static List<Grievance> getGrievanceByEmail(String email) {
        try {
            return grievanceRepository.findByEmail(email);
        } catch (Exception e) {
            // Log the exception and handle it appropriately
            e.printStackTrace(); // Replace with proper logging
            return List.of(); // Return an empty list or handle it as needed
        }
    }

    public static void updateAssignee(Long id,Grievance assigneeDetails){
        Grievance grievance = grievanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Grievance not found"));
        grievance.setAssignee(assigneeDetails.getAssignee());
        grievanceRepository.save(grievance);
    }

    public static void updateStatus(Long id,Grievance statusDetails){
        Grievance grievance = grievanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Grievance not found"));
        grievance.setStatus(statusDetails.getStatus());
        grievance.setFeedback(statusDetails.getFeedback());
        grievanceRepository.save(grievance);
    }
}
