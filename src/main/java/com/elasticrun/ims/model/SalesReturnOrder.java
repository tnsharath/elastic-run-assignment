package com.elasticrun.ims.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Dto for keeping Sales Return
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalesReturnOrder {

    String originalInvoiceNumber;
    int returnNo;
    String customer;
    List<ItemAtInventory> items;

}
