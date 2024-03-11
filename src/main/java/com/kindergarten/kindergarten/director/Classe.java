package com.kindergarten.kindergarten.director;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kindergarten.kindergarten.kindergarten.KinderGarten;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

@Entity
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String niveau;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KinderGarten kindergarten;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "participer", joinColumns = @JoinColumn(name = "id_classe"), inverseJoinColumns = @JoinColumn(name = "id_activity"))
    private List<Activities> activities = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "affecter", joinColumns = @JoinColumn(name = "id_classe"), inverseJoinColumns = @JoinColumn(name = "id_teacher"))
    private List<Teacher> teachers = new ArrayList<>();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Director directeur;

    public Classe() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return String return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return String return the niveau
     */
    public String getNiveau() {
        return niveau;
    }

    /**
     * @param niveau the niveau to set
     */
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    /**
     * @return KinderGarten return the kindergarten
     */
    public KinderGarten getKindergarten() {
        return kindergarten;
    }

    /**
     * @param kindergarten the kindergarten to set
     */
    public void setKindergarten(KinderGarten kindergarten) {
        this.kindergarten = kindergarten;
    }

    /**
     * @return Director return the directeur
     */
    public Director getDirecteur() {
        return directeur;
    }

    /**
     * @param directeur the directeur to set
     */
    public void setDirecteur(Director directeur) {
        this.directeur = directeur;
    }

    /**
     * @return List<Activities> return the activities
     */
    public List<Activities> getActivities() {
        return activities;
    }

    /**
     * @param activities the activities to set
     */
    public void setActivities(List<Activities> activities) {
        this.activities = activities;
    }

    /**
     * @return List<Teacher> return the teachers
     */
    public List<Teacher> getTeachers() {
        return teachers;
    }

    /**
     * @param teachers the teachers to set
     */
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

}