package com.elasticrun.ims.controller;


import com.elasticrun.ims.model.ItemAtInventory;
import com.elasticrun.ims.service.InventoryService;
import com.elasticrun.ims.service.impl.SalesInvoiceService;
import com.elasticrun.ims.service.impl.SalesReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import org.springframework.web.bind.annotation.*;

/**
 * InventoryController acts a initiator to run through the application.
 */
@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private SalesInvoiceService salesInvoiceService;

    @Autowired
    private SalesReturnService salesReturnService;

    InventoryService inventoryService = InventoryService.getInventoryService();

    @PostMapping("/initialize-inventory")
    public List<ItemAtInventory> initInventory() {
        // Create the initial stock in the inventory
        log.info("Initiating the stock in the inventory...");
        inventoryService.addInventory("Sugar", 100, "Bag");
        inventoryService.addInventory("Oil", 50, "Tin");
        inventoryService.addInventory("Besan", 40, "Bag");
        return inventoryService.getInventory();
    }

    @PostMapping("/add")
    public ItemAtInventory addInventory(@RequestBody ItemAtInventory item) {
        log.info("adding the stock to the inventory...");
        return inventoryService.addInventory(item);
    }

    @GetMapping("/display-inventory")
    public List<ItemAtInventory> getItemInventory() {
        return inventoryService.getInventory();
    }
}
