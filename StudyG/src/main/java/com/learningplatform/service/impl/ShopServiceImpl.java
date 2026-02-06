package com.learningplatform.service.impl;

import com.learningplatform.model.ShopItem;
import com.learningplatform.model.User;
import com.learningplatform.service.IShopService;
import com.learningplatform.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShopServiceImpl implements IShopService {

    @Override
    public List<ShopItem> getAllItems() throws Exception {
        List<ShopItem> items = new ArrayList<>();
        String sql = "SELECT itemId, itemName, description, price, itemType, available FROM ShopItems WHERE available = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ShopItem item = new ShopItem();
                item.setItemId(rs.getInt("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setItemType(ShopItem.ItemType.valueOf(rs.getString("itemType")));
                item.setAvailable(rs.getBoolean("available"));
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public List<ShopItem> getItemsByType(ShopItem.ItemType itemType) throws Exception {
        List<ShopItem> items = new ArrayList<>();
        String sql = "SELECT itemId, itemName, description, price, itemType, available FROM ShopItems " +
                     "WHERE itemType = ? AND available = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, itemType.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ShopItem item = new ShopItem();
                item.setItemId(rs.getInt("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setItemType(ShopItem.ItemType.valueOf(rs.getString("itemType")));
                item.setAvailable(rs.getBoolean("available"));
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public ShopItem getItemById(int itemId) throws Exception {
        String sql = "SELECT itemId, itemName, description, price, itemType, available FROM ShopItems WHERE itemId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                ShopItem item = new ShopItem();
                item.setItemId(rs.getInt("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setItemType(ShopItem.ItemType.valueOf(rs.getString("itemType")));
                item.setAvailable(rs.getBoolean("available"));
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean purchaseItem(int userId, int itemId) throws Exception {
        String getUserSql = "SELECT totalPoints FROM Users WHERE userId = ?";
        String getItemSql = "SELECT price, itemType FROM ShopItems WHERE itemId = ?";
        String updateUserSql = "UPDATE Users SET totalPoints = totalPoints - ? WHERE userId = ?";
        String recordPurchaseSql = "INSERT INTO UserPurchases (userId, itemId, purchaseDate) VALUES (?, ?, GETDATE())";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Get user's points
            int userPoints = 0;
            try (PreparedStatement stmt = conn.prepareStatement(getUserSql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    userPoints = rs.getInt("totalPoints");
                } else {
                    return false;
                }
            }
            
            // Get item price
            int itemPrice = 0;
            String itemType = "";
            try (PreparedStatement stmt = conn.prepareStatement(getItemSql)) {
                stmt.setInt(1, itemId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    itemPrice = rs.getInt("price");
                    itemType = rs.getString("itemType");
                } else {
                    return false;
                }
            }
            
            // Check if user has enough points
            if (userPoints < itemPrice) {
                return false;
            }
            
            // Deduct points
            try (PreparedStatement stmt = conn.prepareStatement(updateUserSql)) {
                stmt.setInt(1, itemPrice);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
            
            // Record purchase for decorations, add tickets for game tickets
            if ("GAME_TICKET".equals(itemType)) {
                // Add game tickets
                String addTicketsSql = "UPDATE Users SET gameTickets = gameTickets + ? WHERE userId = ?";
                try (PreparedStatement stmt = conn.prepareStatement(addTicketsSql)) {
                    stmt.setInt(1, itemPrice / 10); // Price is points, assume 10 points per ticket
                    stmt.setInt(2, userId);
                    stmt.executeUpdate();
                }
            } else {
                // Record decoration purchase
                try (PreparedStatement stmt = conn.prepareStatement(recordPurchaseSql)) {
                    stmt.setInt(1, userId);
                    stmt.setInt(2, itemId);
                    stmt.executeUpdate();
                }
            }
            
            return true;
        }
    }

    @Override
    public boolean canAffordItem(int userId, int itemId) throws Exception {
        String sql = "SELECT u.totalPoints, si.price FROM Users u WHERE u.userId = ? " +
                     "AND (SELECT price FROM ShopItems WHERE itemId = ?) <= u.totalPoints";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, itemId);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();
        }
    }

    @Override
    public List<ShopItem> getUserPurchasedItems(int userId) throws Exception {
        List<ShopItem> items = new ArrayList<>();
        String sql = "SELECT DISTINCT si.itemId, si.itemName, si.description, si.price, si.itemType " +
                     "FROM ShopItems si INNER JOIN UserPurchases up ON si.itemId = up.itemId " +
                     "WHERE up.userId = ? AND si.itemType = 'PROFILE_DECORATION'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ShopItem item = new ShopItem();
                item.setItemId(rs.getInt("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setItemType(ShopItem.ItemType.valueOf(rs.getString("itemType")));
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public boolean addItem(ShopItem item) throws Exception {
        String sql = "INSERT INTO ShopItems (itemName, description, price, itemType, available) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getDescription());
            stmt.setInt(3, item.getPrice());
            stmt.setString(4, item.getItemType().name());
            stmt.setBoolean(5, item.isAvailable());
            
            return stmt.executeUpdate() > 0;
        }
    }
}
