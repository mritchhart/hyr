package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Social_skill;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Social_skill entity.
 */
public interface Social_skillRepository extends JpaRepository<Social_skill,Long> {

}
