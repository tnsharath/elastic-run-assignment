package com.elasticrun.ims.model;


import com.elasticrun.ims.enums.SalesType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Dto for Sales Order
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalesOrder {


    String invoiceNumber;
    String customerName;
    SalesType salesType;
    List<ItemAtInventory> items;
}
