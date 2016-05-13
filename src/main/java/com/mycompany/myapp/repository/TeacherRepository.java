package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Teacher;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Teacher entity.
 */
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

}
