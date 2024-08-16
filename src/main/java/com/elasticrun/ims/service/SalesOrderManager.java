package com.elasticrun.ims.service;


import com.elasticrun.ims.dto.RejectedItems;
import com.elasticrun.ims.enums.SalesType;
import com.elasticrun.ims.factory.SalesOrderFactory;
import com.elasticrun.ims.model.ItemAtInventory;
import com.elasticrun.ims.model.SalesOrder;
import com.elasticrun.ims.service.impl.SalesInvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle all the sales order operation and divert to necessary class for further operation.
 */
@Service
@Slf4j
public class SalesOrderManager {

    private final SalesOrderFactory salesOrderFactory;

    @Autowired
    SalesInvoiceService salesInvoiceService;

    private static int returnNo = 1;

    @Autowired

    public SalesOrderManager(SalesOrderFactory salesOrderFactory) {
        this.salesOrderFactory = salesOrderFactory;
    }

    public void creteSalesOrder(SalesOrder salesOrder) {
        SalesOrderI salesOrderI = salesOrderFactory.getSalesOrderBasedOnType(salesOrder.getSalesType());

        boolean isValidSalesType = salesOrderI.validateSalesOrder(salesOrder);

        if (!isValidSalesType) {
            log.error("Validation failed for SalesOrder");
            // need to create custom exception but due to time not taking this up.
            throw new RuntimeException();
        }

        SalesOrder createdSalesOrder = salesOrderI.createSalesOrder(salesOrder);

        if (createdSalesOrder == null) {
            log.error("SalesOrder creation failed");
            // need to create custom exception but due to time not taking this up.
            throw new RuntimeException();
        }

        salesOrderI.updateInventory(createdSalesOrder);
    }

    public List<SalesOrder> getAllSalesOrder() {
        SalesOrderI salesOrderI = salesOrderFactory.getSalesOrderBasedOnType(SalesType.SALES_ORDER);
        return salesOrderI.getAllOrders();
    }

    public List<SalesOrder> getAllReturnOrder() {
        SalesOrderI salesOrderI = salesOrderFactory.getSalesOrderBasedOnType(SalesType.SALES_RETURN);
        return salesOrderI.getAllOrders();
    }

    public List<SalesOrder> automaticReturn(List<RejectedItems> rejectedItems) {

        List<SalesOrder> autoCreatedSalesOrder = new ArrayList<>();
            for (SalesOrder invoice : salesInvoiceService.getAllInvoices()) {
                List<ItemAtInventory> returnItems = new ArrayList<>();
                for (RejectedItems rejectedItem: rejectedItems) {
                    for (ItemAtInventory invoiceItem : invoice.getItems()) {
                        if (invoiceItem.getItemCode().equals(rejectedItem.getItemCode()) && rejectedItem.getQuantity() > 0) {
                            long invoiceQty = invoiceItem.getQuantity();
                            long returnQty = Math.min(rejectedItem.getQuantity(), invoiceQty);
                            returnItems.add(new ItemAtInventory(invoiceItem.getItemCode(), -returnQty, invoiceItem.getUnitOfMeasure()));
                            invoiceItem.setQuantity(invoiceQty - returnQty);
                            rejectedItem.setQuantity(rejectedItem.getQuantity() - returnQty);
                        }
                    }
                }
                if (!returnItems.isEmpty()) {
                    SalesOrder salesOrder = new SalesOrder(
                            String.valueOf(returnNo++),invoice.getCustomerName(), SalesType.SALES_RETURN, returnItems);
                    creteSalesOrder(salesOrder);
                    autoCreatedSalesOrder.add(salesOrder);
                }
            }
        return autoCreatedSalesOrder;
    }
}
