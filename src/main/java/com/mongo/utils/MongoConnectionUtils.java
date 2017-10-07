/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo.utils;

import com.mongo.main.MongoTest;
import com.mongodb.MongoClient;	
import com.mongodb.MongoException;
import static com.mongo.utils.Commons.MONGO_PROPERTIES;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * 
 * @author sundar
 */
public class MongoConnectionUtils {

	private static final Logger log = Logger.getLogger(MongoTest.class);

	private MongoClient mongoClient = null;
	private static Properties mongoPro = null;

	private String dataBase;
	private String sampleCollection;

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public String getSampleCollection() {
		return sampleCollection;
	}

	public void setSampleCollection(String sampleCollection) {
		this.sampleCollection = sampleCollection;
	}

	static {
		mongoPro = new Properties();
		try {
			mongoPro.load(new FileInputStream(MONGO_PROPERTIES));
		} catch (IOException ex) {
			log.error("Exception occurred while load MONGO_PROPERTIES : " + ex, ex);
		}
	}
	
	/**
	 * create the mongo client connection
	 * 
	 * @return - mongo client 
	 */
	public MongoClient getMongoConnection() {
		String host = mongoPro.getProperty("host");
		int port = Integer.valueOf(mongoPro.getProperty("port"));
		try {
			if (mongoClient == null) {
				mongoClient = new MongoClient(host, port);
				setDataBase(mongoPro.getProperty("sundarDB", "sundar"));
				setSampleCollection(mongoPro.getProperty("sampleCollection", "sample"));
			}
		} catch (MongoException e) {
			log.error("Exception occurred while get Connection : " + e, e);
		}
		return mongoClient;
	}
	
	/**
	 * close the mongo client 
	 * 
	 * @param mongoClient - client which is have to close 
	 */
	public void closeMongoClient(MongoClient mongoClient) {
		try {
			if (mongoClient != null) {
				mongoClient.close();
			}
		} catch (MongoException e) {
			log.error("Exception occurred while close MongoClient : " + e, e);
		}
	}
}
