package com.kindergarten.kindergarten.compte;

import java.util.ArrayList;
import java.util.List;

import com.kindergarten.kindergarten.accesspermission.AccessPermission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "users")
public class Compte {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String email;

    private String type;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirm_password;

    private boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "authority"))
    protected List<AccessPermission> accesspermissions = new ArrayList<>();

    public Compte() {
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return String return the confirm_password
     */
    public String getConfirm_password() {
        return confirm_password;
    }

    /**
     * @param confirm_password the confirm_password to set
     */
    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    /**
     * @return boolean return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return List<AccessPermission> return the accesspermissions
     */
    public List<AccessPermission> getAccesspermissions() {
        return accesspermissions;
    }

    /**
     * @param accesspermissions the accesspermissions to set
     */
    public void setAccesspermissions(List<AccessPermission> accesspermissions) {
        this.accesspermissions = accesspermissions;
    }

}