/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Nicolas
 */
public class ProductEntity {
    
    private int productID;
    private int productCode;
    private float price;
    private int quantityHand;
    private String available;
    private String description;
    
    public ProductEntity(int productID, int productCode, float price, int quantityHand, String available, String description){
        this.productID = productID;
        this.productCode = productCode;
        this.price = price;
        this.quantityHand = quantityHand;
        this.available = available;
        this.description = description;
    }

    public int getProductID() {
        return productID;
    }

    public int getProductCode() {
        return productCode;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantityHand() {
        return quantityHand;
    }

    public String getAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantityHand(int quantityHand) {
        this.quantityHand = quantityHand;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
