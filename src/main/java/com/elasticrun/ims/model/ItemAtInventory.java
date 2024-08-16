package com.elasticrun.ims.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Keeps track of inventory
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemAtInventory {
    String itemCode;
    long quantity;
    String unitOfMeasure;

    public void updateQuantity(long amount) {
        this.quantity -= amount;
    }
}
