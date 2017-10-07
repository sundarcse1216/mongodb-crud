/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo.crud;

import com.mongo.crud.impl.InsertDocumentsImpl;

/**
 *
 * @author sundar
 */
public class MongoCURDThread implements Runnable {

    @Override
    public void run() {
        InsertDocumentsImpl insert = new InsertDocumentsImpl();
        insert.loadMethods();
    }
}
