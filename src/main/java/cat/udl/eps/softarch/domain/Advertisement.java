package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class Advertisement extends UriEntity{

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Column(length = 2000)
    @Size(max = 2000)
    private String description;

    private String owner;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime modifiedAt;

    @DecimalMin(message = "Price has to be bigger than 0.01", value = "0.01")
    private Double price;

    private Boolean negotiablePrice = false;

    private Boolean paidShipping = false;

    @ElementCollection
    private Set<String> tags = new HashSet<>();

    @OneToMany(mappedBy = "depicts", cascade = CascadeType.ALL)
    private Set<Picture> pictures = new HashSet<>();

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.REMOVE)
    private Purchase purchase;

    /* technical product data */
    private String category;
    private String brand;
    private String color;
    private Double weight;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getModifiedAt() {
        return modifiedAt;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean isNegotiablePrice() {
        return negotiablePrice;
    }

    public Boolean isPaidShipping() {
        return paidShipping;
    }

    public Set<String> getTags() {
        return tags;
    }

    public Set<Picture> getPictures() { return pictures; }

    public Purchase getPurchase() {
        return purchase;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public Double getWeight() {
        return weight;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @JsonIgnore
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNegotiablePrice(Boolean negotiablePrice) {
        this.negotiablePrice = negotiablePrice;
    }

    public void setPaidShipping(Boolean paidShipping) {
        this.paidShipping = paidShipping;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setPictures(Set<Picture> pictures) { this.pictures = pictures; }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}
