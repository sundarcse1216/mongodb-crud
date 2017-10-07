/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo.utils;

/**
 * 
 * @author sundar
 */
public interface Commons {

	public final String INVALID_MSG = "Invalid Argument(s) \n1 - Read / 2 - Write / 3 - Update / 4 - Delete";
	public final String MONGO_PROPERTIES = "./conf/mongo.properties";
	
	public void loadMethods();

	public void finalized();
}
