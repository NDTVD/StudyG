package com.learningplatform.model;

import java.io.Serializable;

public class GameTicket implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ticketId;
    private int userId;
    private int quantity;
    private String acquiredDate;

    public GameTicket() {}

    public GameTicket(int userId, int quantity) {
        this.userId = userId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getAcquiredDate() { return acquiredDate; }
    public void setAcquiredDate(String acquiredDate) { this.acquiredDate = acquiredDate; }

    @Override
    public String toString() {
        return "GameTicket{" +
                "ticketId=" + ticketId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                '}';
    }
}
