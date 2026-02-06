package com.learningplatform.service;

import com.learningplatform.model.ShopItem;
import java.util.List;

public interface IShopService {
    /**
     * Get all available shop items
     */
    List<ShopItem> getAllItems() throws Exception;

    /**
     * Get items by type (GAME_TICKET or PROFILE_DECORATION)
     */
    List<ShopItem> getItemsByType(ShopItem.ItemType itemType) throws Exception;

    /**
     * Get item by ID
     */
    ShopItem getItemById(int itemId) throws Exception;

    /**
     * Purchase item with points
     */
    boolean purchaseItem(int userId, int itemId) throws Exception;

    /**
     * Check if user has enough points to purchase
     */
    boolean canAffordItem(int userId, int itemId) throws Exception;

    /**
     * Get user's purchased items (decorations)
     */
    List<ShopItem> getUserPurchasedItems(int userId) throws Exception;

    /**
     * Add item to shop
     */
    boolean addItem(ShopItem item) throws Exception;
}
