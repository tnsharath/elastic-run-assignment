package com.elasticrun.ims.service;

import com.elasticrun.ims.model.SalesOrder;

import java.util.List;

public interface SalesOrderI {
    boolean validateSalesOrder(SalesOrder salesOrder);
    SalesOrder createSalesOrder(SalesOrder salesOrder);

    void updateInventory(SalesOrder salesOrder);

    List<SalesOrder> getAllOrders();
}
