package com.memd.ecookie.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity(name = "products")
public class ProductEntity extends BaseAuditEntity {
    private static final long serialVersionUID = 6374131265776588320L;
    private String name;
    private String imageUrl;
    private Double price;
    private String description;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "ref_category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;

    @Transient
    private Long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.getModifiedSet().add("name");
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.getModifiedSet().add("imageUrl");
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.getModifiedSet().add("price");
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.getModifiedSet().add("description");
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
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

        ProductEntity other = (ProductEntity) obj;
        return Objects.equals(this.name, other.name);
    }
}
