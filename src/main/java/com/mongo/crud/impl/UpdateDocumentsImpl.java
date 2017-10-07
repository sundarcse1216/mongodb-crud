/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo.crud.impl;

import com.mongo.utils.MongoConnectionUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.UpdateResult;
import com.mongo.crud.UpdateDocuments;
import org.apache.log4j.Logger;
import org.bson.conversions.Bson;

/**
 * 
 * @author sundar
 */
public class UpdateDocumentsImpl implements UpdateDocuments {

	private static final Logger log = Logger
			.getLogger(UpdateDocumentsImpl.class);

	private MongoConnectionUtils mongo = null;
	private MongoClient client = null;

	public UpdateDocumentsImpl() {
		mongo = new MongoConnectionUtils();
		client = mongo.getMongoConnection();
	}

	/**
	 * It will invoke all the methods one by one
	 */
	@Override
	public void loadMethods() {
		updateOneDocument();
		updateManyDocument();
		updateDocumentWithCurrentDate();
		finalized();
	}

	/**
	 * This method update only one one document which is matched first
	 */
	@Override
	public void updateOneDocument() {
		MongoDatabase db = null;
		MongoCollection collection = null;
		Bson filter = null;
		Bson query = null;
		try {
			db = client.getDatabase(mongo.getDataBase());
			collection = db.getCollection(mongo.getSampleCollection());
			filter = eq("name", "Sundar");
			query = combine(set("age", 23), set("gender", "Male"));
			UpdateResult result = collection.updateOne(filter, query);
			log.info("UpdateOne Status : " + result.wasAcknowledged());
			log.info("No of Record Modified : " + result.getModifiedCount());
		} catch (MongoException e) {
			log.error("Exception occurred while update single Document : " + e, e);
		}
	}

	/**
	 * This method update all the matches document
	 */
	@Override
	public void updateManyDocument() {
		MongoDatabase db = null;
		MongoCollection collection = null;
		Bson filter = null;
		Bson query = null;
		try {
			db = client.getDatabase(mongo.getDataBase());
			collection = db.getCollection(mongo.getSampleCollection());
			filter = eq("name", "Sundar");
			query = combine(set("age", 23), set("gender", "Male"));
			UpdateResult result = collection.updateMany(filter, query);
			log.info("UpdateMany Status : " + result.wasAcknowledged());
			log.info("No of Record Modified : " + result.getModifiedCount());
		} catch (MongoException e) {
			log.error("Exception occurred while update Many Document : " + e, e);
		}
	}
	
	/**
	 * This method update document with lastmodified properties 
	 */
	@Override
	public void updateDocumentWithCurrentDate() {
		MongoDatabase db = null;
		MongoCollection collection = null;
		Bson filter = null;
		Bson query = null;
		try {
			db = client.getDatabase(mongo.getDataBase());
			collection = db.getCollection(mongo.getSampleCollection());
			filter = eq("name", "Sundar");
			query = combine(set("age", 23), set("gender", "Male"),
					currentDate("lastModified"));
			UpdateResult result = collection.updateOne(filter, query);
			log.info("Update with date Status : " + result.wasAcknowledged());
			log.info("No of Record Modified : " + result.getModifiedCount());
		} catch (MongoException e) {
			log.error("Exception occurred while update Many Document with Date : " + e, e);
		}
	}

	/**
	 * This is closed the mongo client, no need to fours to close the mongo
	 * client, mongo cluster will take care about close the mongo client, for
	 * safety purpose we close the client
	 */
	@Override
	public void finalized() {
		mongo.closeMongoClient(client);
	}
}
