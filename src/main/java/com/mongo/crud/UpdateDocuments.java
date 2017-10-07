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
public interface UpdateDocuments extends Commons {

	public void updateOneDocument();

	public void updateManyDocument();

	public void updateDocumentWithCurrentDate();

}
