package com.elasticrun.ims.controller;


import com.elasticrun.ims.dto.RejectedItems;
import com.elasticrun.ims.enums.SalesType;
import com.elasticrun.ims.model.ItemAtInventory;
import com.elasticrun.ims.model.SalesOrder;
import com.elasticrun.ims.service.InventoryService;
import com.elasticrun.ims.service.SalesOrderManager;
import com.elasticrun.ims.service.impl.SalesInvoiceService;
import com.elasticrun.ims.service.impl.SalesReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * InventoryController acts a initiator to run through the application.
 */
@RestController
@Slf4j
@RequestMapping("/sales-order")
public class SalesOrderController {

    @Autowired
    private SalesInvoiceService salesInvoiceService;

    @Autowired
    private SalesReturnService salesReturnService;

    @Autowired
    private SalesOrderManager salesOrderManager;

    @GetMapping("/simulate")
    public void runSimulation() {
        InventoryService inventoryService = InventoryService.getInventoryService();

        // Create the initial stock in the inventory
        log.info("Initiating the stock in the inventory...");
        inventoryService.addInventory("Sugar", 100, "Bag");
        inventoryService.addInventory("Oil", 50, "Tin");
        inventoryService.addInventory("Besan", 40, "Bag");

        log.info("-----------------------------------------------------------------------------------------");
        // Display the initial stock
        inventoryService.displayInventory().forEach((code, item) ->
                log.info("Item Code: " + code + ", Quantity: " + item.getQuantity() + " " + item.getUnitOfMeasure())
        );

        log.info("-----------------------------------------------------------------------------------------");

        // Process Sales Invoices
        log.info("Processing Sales Invoices...");
        List<ItemAtInventory> items1 = new ArrayList<>();
        items1.add(new ItemAtInventory("Sugar", 10, "Bag"));
        items1.add(new ItemAtInventory("Oil", 4, "Tin"));
        SalesOrder invoice1 = new SalesOrder("1", "Anand Kirana", SalesType.SALES_ORDER, items1);
        salesOrderManager.creteSalesOrder(invoice1);

        List<ItemAtInventory> items2 = new ArrayList<>();
        items2.add(new ItemAtInventory("Sugar", 10, "Bag"));
        items2.add(new ItemAtInventory("Besan", 4, "Bag"));
        items2.add(new ItemAtInventory("Oil", 10, "Tin"));
        SalesOrder invoice2 = new SalesOrder("2", "Rakesh Kirana", SalesType.SALES_ORDER, items2);
        salesOrderManager.creteSalesOrder(invoice2);


        log.info("-----------------------------------------------------------------------------------------");
        log.info("Inventory after sales order creation...");
        log.info("-----------------------------------------------------------------------------------------");
        inventoryService.displayInventory().forEach((code, item) ->
                log.info("Item Code: " + code + ", Quantity: " + item.getQuantity() + " " + item.getUnitOfMeasure())
        );


        // Process Sales Return
        log.info("Processing Sales Return...");
        List<ItemAtInventory> items3 = new ArrayList<>();
        items3.add(new ItemAtInventory("Sugar", -1, "Bag"));
        items3.add(new ItemAtInventory("Besan", -1, "Bag"));
        SalesOrder invoice3 = new SalesOrder("3", "Rakesh Kirana", SalesType.SALES_RETURN, items3);
        salesOrderManager.creteSalesOrder(invoice3);

        log.info("Problem 1: Calculating Ending Stock...");
        log.info("-----------------------------------------------------------------------------------------");
        log.info("Inventory after sales return creation... ");
        log.info("-----------------------------------------------------------------------------------------");

        inventoryService.displayInventory().forEach((code, item) ->
                log.info("Item Code: " + code + ", Quantity: " + item.getQuantity() + " " + item.getUnitOfMeasure())
        );

        log.info("-----------------------------------------------------------------------------------------");


        List<RejectedItems> rejectedItems = new ArrayList<>();
        RejectedItems rejectedItems1 = RejectedItems.builder().itemCode("Sugar").quantity(3).build();
        RejectedItems rejectedItems2 = RejectedItems.builder().itemCode("Oil").quantity(6).build();
        rejectedItems.add(rejectedItems1);
        rejectedItems.add(rejectedItems2);
        List<SalesOrder> automatedReturns = automaticReturn(rejectedItems);

        log.info("Problem 2: Automate Return...");
        log.info("-----------------------------------------------------------------------------------------");
        log.info(automatedReturns.toString());

    }

    @GetMapping("/get-sales-order")
    public List<SalesOrder> getAllSalesOrder() {
        return salesOrderManager.getAllSalesOrder();
    }

    @GetMapping("/get-return-orders")
    public List<SalesOrder> getAllReturnOrder() {
        return salesOrderManager.getAllReturnOrder();
    }

    @PostMapping("/automatic-return")
    public List<SalesOrder> automaticReturn(@RequestBody List<RejectedItems> rejectedItems) {
        return salesOrderManager.automaticReturn(rejectedItems);
    }

}
