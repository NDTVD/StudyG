package com.learningplatform.model;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum ItemType {
        GAME_TICKET,
        PROFILE_DECORATION
    }

    private int itemId;
    private String itemName;
    private String description;
    private int price; // in points
    private ItemType itemType;
    private String imageUrl;
    private boolean available;

    public ShopItem() {}

    public ShopItem(String itemName, String description, int price, ItemType itemType) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.itemType = itemType;
        this.available = true;
    }

    // Getters and Setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "ShopItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", itemType=" + itemType +
                ", available=" + available +
                '}';
    }
}
