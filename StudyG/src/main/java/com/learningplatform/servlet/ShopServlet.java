package com.learningplatform.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.learningplatform.model.User;
import com.learningplatform.model.ShopItem;
import com.learningplatform.service.IShopService;
import com.learningplatform.service.impl.ShopServiceImpl;
import com.learningplatform.util.ServletUtils;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IShopService shopService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.shopService = new ShopServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = ServletUtils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            List<ShopItem> allItems = shopService.getAllItems();
            List<ShopItem> userItems = shopService.getUserPurchasedItems(user.getUserId());
            
            request.setAttribute("allItems", allItems);
            request.setAttribute("userItems", userItems);
            request.setAttribute("user", user);
            request.getRequestDispatcher("jsp/shop.jsp").forward(request, response);
        } catch (Exception e) {
            ServletUtils.setErrorMessage(request, "Error loading shop: " + e.getMessage());
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = ServletUtils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        String itemIdStr = request.getParameter("itemId");

        try {
            if ("purchase".equals(action) && itemIdStr != null) {
                int itemId = Integer.parseInt(itemIdStr);
                
                if (shopService.purchaseItem(user.getUserId(), itemId)) {
                    ServletUtils.setSuccessMessage(request, "Item purchased successfully!");
                } else {
                    ServletUtils.setErrorMessage(request, "Not enough points to purchase this item");
                }
            }
            response.sendRedirect("shop");
        } catch (Exception e) {
            ServletUtils.setErrorMessage(request, "Error: " + e.getMessage());
            response.sendRedirect("shop");
        }
    }
}
