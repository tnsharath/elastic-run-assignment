package com.elasticrun.ims.factory.imp;

import com.elasticrun.ims.enums.SalesType;
import com.elasticrun.ims.factory.SalesOrderFactory;
import com.elasticrun.ims.service.SalesOrderI;
import com.elasticrun.ims.service.impl.SalesInvoiceService;
import com.elasticrun.ims.service.impl.SalesReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.NoSuchElementException;


@Component
@Slf4j
public class SalesOrderFactoryImpl implements SalesOrderFactory {

    private  final Map<SalesType, SalesOrderI> salesOrderIMap;

    public SalesOrderFactoryImpl(SalesInvoiceService salesInvoiceService, SalesReturnService salesReturnService) {
        this.salesOrderIMap = Map.of(SalesType.SALES_ORDER, salesInvoiceService,
                SalesType.SALES_RETURN, salesReturnService );
    }

    @Override
    public SalesOrderI getSalesOrderBasedOnType(SalesType salesType) {

        if (salesType == null) {
            log.error("Invalid Sales order type");
            // need to create custom exception but due to time not taking this up.
            throw new NoSuchElementException();
        }
        SalesOrderI salesOrderI = salesOrderIMap.get(salesType);

        if (salesOrderI == null) {
            log.error("Invalid salesOrderI");
            // need to create custom exception but due to time not taking this up.
            throw new NoSuchElementException();
        }
        return salesOrderI;
    }
}
