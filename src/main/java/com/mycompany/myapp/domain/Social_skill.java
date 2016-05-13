package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Social_skill.
 */
@Entity
@Table(name = "social_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Social_skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "level")
    private String level;

    @OneToMany(mappedBy = "social_skill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Point_entry> point_entries = new HashSet<>();

    @OneToMany(mappedBy = "social_skill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Student_social_skill> student_social_skills = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<Point_entry> getPoint_entries() {
        return point_entries;
    }

    public void setPoint_entries(Set<Point_entry> point_entries) {
        this.point_entries = point_entries;
    }

    public Set<Student_social_skill> getStudent_social_skills() {
        return student_social_skills;
    }

    public void setStudent_social_skills(Set<Student_social_skill> student_social_skills) {
        this.student_social_skills = student_social_skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Social_skill social_skill = (Social_skill) o;
        if(social_skill.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, social_skill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Social_skill{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", level='" + level + "'" +
            '}';
    }
}
