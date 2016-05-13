package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.EntryStatus;

/**
 * A Point_entry.
 */
@Entity
@Table(name = "point_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Point_entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ent_value", nullable = false)
    private Integer ent_value;

    @Enumerated(EnumType.STRING)
    @Column(name = "ent_status")
    private EntryStatus ent_status;

    @Column(name = "ent_submission_time")
    private ZonedDateTime ent_submission_time;

    @Column(name = "ent_action_time")
    private ZonedDateTime ent_action_time;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Social_skill social_skill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEnt_value() {
        return ent_value;
    }

    public void setEnt_value(Integer ent_value) {
        this.ent_value = ent_value;
    }

    public EntryStatus getEnt_status() {
        return ent_status;
    }

    public void setEnt_status(EntryStatus ent_status) {
        this.ent_status = ent_status;
    }

    public ZonedDateTime getEnt_submission_time() {
        return ent_submission_time;
    }

    public void setEnt_submission_time(ZonedDateTime ent_submission_time) {
        this.ent_submission_time = ent_submission_time;
    }

    public ZonedDateTime getEnt_action_time() {
        return ent_action_time;
    }

    public void setEnt_action_time(ZonedDateTime ent_action_time) {
        this.ent_action_time = ent_action_time;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Social_skill getSocial_skill() {
        return social_skill;
    }

    public void setSocial_skill(Social_skill social_skill) {
        this.social_skill = social_skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point_entry point_entry = (Point_entry) o;
        if(point_entry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, point_entry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Point_entry{" +
            "id=" + id +
            ", ent_value='" + ent_value + "'" +
            ", ent_status='" + ent_status + "'" +
            ", ent_submission_time='" + ent_submission_time + "'" +
            ", ent_action_time='" + ent_action_time + "'" +
            '}';
    }
}
