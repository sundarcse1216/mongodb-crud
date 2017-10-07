/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo.crud;

import com.mongo.utils.Commons;

/**
 *
 * @author sundar
 */
public abstract interface DeleteDocuments extends Commons {

    public abstract void deleteOneDocument();

    public abstract void deleteManyDocument();

}
