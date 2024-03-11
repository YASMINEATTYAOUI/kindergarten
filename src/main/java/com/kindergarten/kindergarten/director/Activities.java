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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Director directeur;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KinderGarten kindergarten;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "participer", joinColumns = @JoinColumn(name = "id_activity"), inverseJoinColumns = @JoinColumn(name = "id_classe"))
    private List<Classe> classes = new ArrayList<>();

    public Activities() {
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
     * @return List<Classe> return the classes
     */
    public List<Classe> getClasses() {
        return classes;
    }

    /**
     * @param classes the classes to set
     */
    public void setClasses(List<Classe> classes) {
        this.classes = classes;
    }

}