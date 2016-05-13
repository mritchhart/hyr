package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Classroom;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Classroom entity.
 */
public interface ClassroomRepository extends JpaRepository<Classroom,Long> {

}
