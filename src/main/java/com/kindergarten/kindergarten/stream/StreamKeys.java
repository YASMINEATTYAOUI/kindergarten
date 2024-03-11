package com.kindergarten.kindergarten.stream;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class StreamKeys {

    @Id
    private String emailParent;
    private Integer idEnfant = 0;
    private String keyParent = "";
    private String keyEnfant = "";

    public StreamKeys() {
    }

    /**
     * @return String return the emailParent
     */
    public String getEmailParent() {
        return emailParent;
    }

    /**
     * @param emailParent the emailParent to set
     */
    public void setEmailParent(String emailParent) {
        this.emailParent = emailParent;
    }

    /**
     * @return Integer return the idEnfant
     */
    public Integer getIdEnfant() {
        return idEnfant;
    }

    /**
     * @param idEnfant the idEnfant to set
     */
    public void setIdEnfant(Integer idEnfant) {
        this.idEnfant = idEnfant;
    }

    /**
     * @return String return the keyParent
     */
    public String getKeyParent() {
        return keyParent;
    }

    /**
     * @param keyParent the keyParent to set
     */
    public void setKeyParent(String keyParent) {
        this.keyParent = keyParent;
    }

    /**
     * @return String return the keyEnfant
     */
    public String getKeyEnfant() {
        return keyEnfant;
    }

    /**
     * @param keyEnfant the keyEnfant to set
     */
    public void setKeyEnfant(String keyEnfant) {
        this.keyEnfant = keyEnfant;
    }

}
