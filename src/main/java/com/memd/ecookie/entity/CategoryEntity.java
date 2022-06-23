package com.memd.ecookie.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity(name = "categories")
public class CategoryEntity extends BaseEntity {

    private static final long serialVersionUID = -7796579802284473558L;

    private String name;

    @OneToMany(mappedBy = "category",
            // cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    private List<ProductEntity> products;

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.getModifiedSet().add("name");
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        CategoryEntity other = (CategoryEntity) obj;
        return Objects.equals(this.name, other.name);
    }
}
