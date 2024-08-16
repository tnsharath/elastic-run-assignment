package com.elasticrun.ims.service.impl;


import com.elasticrun.ims.model.SalesOrder;
import com.elasticrun.ims.service.InventoryService;
import com.elasticrun.ims.service.SalesOrderI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SalesReturnService implements SalesOrderI {

    @Autowired
    private SalesInvoiceService salesInvoiceService;


    private final List<SalesOrder> returnInvoices = new ArrayList<>();


    @Override
    public boolean validateSalesOrder(SalesOrder salesOrder) {
        return true;
    }

    @Override
    public SalesOrder createSalesOrder(SalesOrder salesOrder) {
        // Actual creation would be different for simplification I am just using list and storing this way.
        returnInvoices.add(salesOrder);
        return salesOrder;
    }

    @Override
    public void updateInventory(SalesOrder salesOrder) {
        InventoryService.getInventoryService().processSalesInvoice(salesOrder);
    }

    @Override
    public List<SalesOrder> getAllOrders() {
        return returnInvoices;
    }

}
