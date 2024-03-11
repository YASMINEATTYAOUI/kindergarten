package com.kindergarten.kindergarten.accesspermission;

import java.util.ArrayList;
import java.util.List;

import com.kindergarten.kindergarten.compte.Compte;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

@Entity
public class AccessPermission {

    @Id

    private String id;
    @Column(nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "authority"), inverseJoinColumns = @JoinColumn(name = "username"))
    protected List<Compte> accesspermissions = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccessPermission() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}