package com.elasticrun.ims.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Dto for keeping Sales Invoice
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalesInvoiceOrder {

     int invoiceNo;
     String customer;
     List<ItemAtInventory> items;
}
