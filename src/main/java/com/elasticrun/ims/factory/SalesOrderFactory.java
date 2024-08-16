package com.elasticrun.ims.factory;

import com.elasticrun.ims.enums.SalesType;
import com.elasticrun.ims.service.SalesOrderI;

public interface SalesOrderFactory {
    SalesOrderI getSalesOrderBasedOnType(SalesType salesType);

}
