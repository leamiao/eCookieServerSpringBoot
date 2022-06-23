package com.memd.ecookie.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = -1594524024291353952L;

    @Id
    @Column(name = "id", columnDefinition = "int(32)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    protected Set<String> modifiedSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Set<String> getModifiedSet() {
        return this.modifiedSet;
    }

    @JsonIgnore
    public boolean isModified(String name) {
        if (this.modifiedSet.isEmpty())
            return true;

        return this.modifiedSet.contains(name);
    }
}
