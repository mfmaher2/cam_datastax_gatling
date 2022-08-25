package com.datastax;

import com.datastax.oss.driver.api.core.cql.BoundStatement;

public class GeneratedDataExchange<D> {

    protected D dataObject;
    protected BoundStatement bndStatement;

    public GeneratedDataExchange(D data, BoundStatement stmt){
        dataObject = data;
        bndStatement = stmt;
    }

    public D getDataObject(){
        return dataObject;
    }

    public BoundStatement getBoundStatement(){
        return bndStatement;
    }
}
