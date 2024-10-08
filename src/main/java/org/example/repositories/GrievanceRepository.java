package org.example.repositories;

import org.example.entities.Grievance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrievanceRepository extends JpaRepository<Grievance, Long> {
    @Query("SELECT e FROM Grievance e WHERE e.assignee = :name")
    List<Grievance> findByName(@Param("name") String name);

    @Query("SELECT e FROM Grievance e WHERE e.email = :email")
    List<Grievance> findByEmail(@Param("email") String email);
}
