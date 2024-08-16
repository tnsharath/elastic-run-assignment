package com.elasticrun.ims.service;



import com.elasticrun.ims.model.ItemAtInventory;
import com.elasticrun.ims.model.SalesOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to handle all the inventory operations.
 * Note: This project doesnt implement db. but if we are maintaining one, we need to consider concurrency.
 */
@Service
@Slf4j
public class InventoryService {

    private static InventoryService inventoryService;
    private static Map<String, ItemAtInventory> stock;

    private InventoryService() {
        stock = new HashMap<>();
    }

    /**
     * Creating a singleton class so that we avoid multiple initialization of the class.
     */
    public static synchronized InventoryService getInventoryService() {
        if (inventoryService == null) {
            inventoryService = new InventoryService();
        }
        return inventoryService;
    }

    public void addInventory(String itemCode, long quantity, String unitOfMeasure) {
        ItemAtInventory item;
        if (stock.containsKey(itemCode)) {
            item = stock.get(itemCode);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new ItemAtInventory(itemCode, quantity, unitOfMeasure);
        }
        stock.put(itemCode, item);
    }

    public void processSalesInvoice(SalesOrder invoice) {
        for (ItemAtInventory item : invoice.getItems()) {
            ItemAtInventory inventoryItem = stock.get(item.getItemCode());
            if (inventoryItem != null) {
                inventoryItem.updateQuantity(item.getQuantity());
            }
        }
    }

    public Map<String, ItemAtInventory> displayInventory() {
        return stock;
    }

    public List<ItemAtInventory> getInventory() {
        return new ArrayList<>(stock.values());
    }

    public ItemAtInventory addInventory(ItemAtInventory item) {
        addInventory(item.getItemCode(), item.getQuantity(), item.getUnitOfMeasure());
        return stock.get(item.getItemCode());
    }
}
