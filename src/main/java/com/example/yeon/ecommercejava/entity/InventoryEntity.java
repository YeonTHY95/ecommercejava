package com.example.yeon.ecommercejava.entity;

import com.example.yeon.ecommercejava.dto.InventoryDimensions;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="inventories")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id ;

    String inventoryId ;
    String title ;
    String name;
    String description ;
    String brand ;
    String category ;

//    @ElementCollection
    List<String> color ;

//    @ElementCollection
    List<String> material ;

    Double hotSalesScore ;
    Double weight ;
    Double rating ;
    Double price ;
    Double discount ;
    Integer quantity ;

//    @Embedded
    InventoryDimensions dimensions ;

    String imageUrl ;
    String status ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "seller")
    private UserEntity sellerObject ; // Foreign Key to CustomUser
    private String sellerName;


//    public InventoryEntity(Long id, String inventoryId, String title, String name, String description, String brand, String category, List<String> color, List<String> material, Double hotSalesScore, Double weight, Double rating, Double price, Double discount, Integer quantity, InventoryDimensions dimensions, String imageUrl, String status, UserEntity seller) {
//        this.id = id;
//        this.inventoryId = inventoryId;
//        this.title = title;
//        this.name = name;
//        this.description = description;
//        this.brand = brand;
//        this.category = category;
//        this.color = color;
//        this.material = material;
//        this.hotSalesScore = hotSalesScore;
//        this.weight = weight;
//        this.rating = rating;
//        this.price = price;
//        this.discount = discount;
//        this.quantity = quantity;
//        this.dimensions = dimensions;
//        this.imageUrl = imageUrl;
//        this.status = status;
//        this.seller = seller;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<String> getMaterial() {
        return material;
    }

    public void setMaterial(List<String> material) {
        this.material = material;
    }

    public Double getHotSalesScore() {
        return hotSalesScore;
    }

    public void setHotSalesScore(Double hotSalesScore) {
        this.hotSalesScore = hotSalesScore;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public InventoryDimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(InventoryDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserEntity getSellerObject() {
        return sellerObject;
    }

    public void setSellerObject(UserEntity seller) {
        this.sellerObject = seller;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public String toString() {
        return "Inventory Class toString's title " + this.title ;
//        return "Order ID : " + this.id  + " item " + this.title + " from " + this.seller + " by " + this.buyer " at " + this.orderDate ;

    }
}
