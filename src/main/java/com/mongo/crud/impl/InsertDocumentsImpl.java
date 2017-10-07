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
import com.mongo.crud.InsertDocuments;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.log4j.Logger;
import org.bson.Document;

/**
 * 
 * @author sundar
 */
public class InsertDocumentsImpl implements InsertDocuments {

	private static final Logger log = Logger
			.getLogger(InsertDocumentsImpl.class);

	private MongoConnectionUtils mongo = null;
	private MongoClient client = null;

	public InsertDocumentsImpl() {
		mongo = new MongoConnectionUtils();
		client = mongo.getMongoConnection();

	}

	/**
	 * invoke the all insert methods
	 */
	@Override
	public void loadMethods() {
		insertUsingDocument();
		insertUsingMap();
		insertSingleDocument();
		insertMultipleDocuments();
		finalized();
	}

	/**
	 * This method insert the document using Document object
	 */
	@Override
	public void insertUsingDocument() {
		MongoDatabase db = null;
		MongoCollection collection = null;
		try {
			db = client.getDatabase(mongo.getDataBase());
			collection = db.getCollection(mongo.getSampleCollection());
			Document obj1 = new Document();
			obj1.put("name", "Sivaraman");
			obj1.put("age", 23);
			obj1.put("gender", "male");
			collection.insertOne(obj1);
			log.info("Document Insert Successfully using Document Obj...");
		} catch (MongoException | ClassCastException e) {
			log.error("Exception occurred while insert Value using **Document** : " + e, e);
		}
	}

	/**
	 * This method insert the document using Map
	 */
	@Override
	public void insertUsingMap() {
		MongoDatabase db = null;
		MongoCollection collection = null;
		try {
			db = client.getDatabase(mongo.getDataBase());
			collection = db.getCollection(mongo.getSampleCollection());
			final Map<String, Object> empMap = new HashMap<>();
			empMap.put("_id", new Random().nextInt(999));
			empMap.put("name", "Vel");
			empMap.put("age", 25);
			empMap.put("desicnation", "Java Developer");
			empMap.put("gender", "Male");
			empMap.put("salary", "10000");
			log.info("Employ Details : " + empMap);
			collection.insertOne(new Document(empMap));
			log.info("Document Insert Successfully using Map...");
		} catch (MongoException | ClassCastException e) {
			log.error("Exception occurred while insert Value using **UsingMap** : " + e, e);
		}
	}

	/**
	 * This method insert the single document
	 */
	@Override
	public void insertSingleDocument() {
		MongoDatabase db = null;
		MongoCollection collection = null;
		try {
			db = client.getDatabase(mongo.getDataBase());
			collection = db.getCollection(mongo.getSampleCollection());

			Document canvas = new Document("item", "canvas").append("qty", 100)
					.append("tags", singletonList("cotton"));

			Document size = new Document("h", 28).append("w", 35.5).append(
					"uom", "cm");

			canvas.append("size", size);
			collection.insertOne(canvas);
			log.info("Single Document Insert Successfully...");
		} catch (MongoException | ClassCastException e) {
			log.error("Exception occurred while insert **Single Document** : " + e, e);
		}
	}

	/**
	 * This method insert the more than one document at a time
	 */
	@Override
	public void insertMultipleDocuments() {
		MongoDatabase db = null;
		MongoCollection collection = null;
		try {
			db = client.getDatabase(mongo.getDataBase());
			collection = db.getCollection(mongo.getSampleCollection());

			Document journal = new Document("item", "journal")
					.append("qty", 25).append("tags", asList("blank", "red"));

			Document journalSize = new Document("h", 14).append("w", 21)
					.append("uom", "cm");
			journal.put("size", journalSize);

			Document mat = new Document("item", "mat").append("qty", 85)
					.append("tags", singletonList("gray"));

			Document matSize = new Document("h", 27.9).append("w", 35.5)
					.append("uom", "cm");
			mat.put("size", matSize);

			Document mousePad = new Document("item", "mousePad").append("qty",
					25).append("tags", asList("gel", "blue"));

			Document mousePadSize = new Document("h", 19).append("w", 22.85)
					.append("uom", "cm");
			mousePad.put("size", mousePadSize);
			collection.insertMany(asList(journal, mat, mousePad));
			log.info("Multiple Document Insert Successfully...");
		} catch (MongoException | ClassCastException e) {
			log.error("Exception occurred while insert **Multiple Document** : " + e, e);
		}
	}
	
    /**
     * This is closed the mongo client,
     * no need to fours to close the mongo client,
     * mongo cluster will take care about close the mongo client,
     * for safety purpose we close the client
     */
	@Override
	public void finalized() {
		mongo.closeMongoClient(client);
	}
}