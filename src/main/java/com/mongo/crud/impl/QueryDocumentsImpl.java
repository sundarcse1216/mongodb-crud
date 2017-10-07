/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo.crud.impl;

import com.mongo.utils.MongoConnectionUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Filters.nin;
import static com.mongodb.client.model.Filters.regex;
import com.mongo.crud.QueryDocuments;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author sundar
 */
public class QueryDocumentsImpl implements QueryDocuments {

    private static final Logger log = Logger.getLogger(QueryDocumentsImpl.class);
    private MongoConnectionUtils mongo = null;
    private MongoClient client = null;

    public QueryDocumentsImpl() {
        mongo = new MongoConnectionUtils();
        client = mongo.getMongoConnection();
    }
    
    /**
     * It will invoke all the operation one by one
     */
    @Override
    public void loadMethods() {
        getAllDocuments();
        getSpecificDocument("EQUAL");
        getSpecificDocument("NOT-EQUAL");
        getSpecificDocument("AND");
        getSpecificDocument("OR");
        getSpecificDocument("AND-OR");
        getSpecificDocument("IN");
        getSpecificDocument("NOT-IN");
        getSpecificDocument("LESS-THAN");
        getSpecificDocument("LESS-THAN-OR-EQUAL");
        getSpecificDocument("GREATER-THAN");
        getSpecificDocument("GREATER-THAN-OR-EQUAL");
        getSpecificDocument("LIKE");
        getSpecificDocument("EXISTS");
        getSpecificDocument("NOT-EXISTS");

        finalized();
    }
    
    /**
     * This method retrieve all the document(s)
     */
    @Override
    public void getAllDocuments() {
        MongoDatabase db = null;
        MongoCollection collection = null;
        try {
            db = client.getDatabase(mongo.getDataBase());
            collection = db.getCollection(mongo.getSampleCollection());
            FindIterable<Document> docs = collection.find(); //SELECT * FROM sample;
            for (Document doc : docs) {
                log.info(doc.getString("name"));
            }
        } catch (MongoException | ClassCastException e) {
            log.error("Exception occurred while insert Value using **BasicDBObject** : " + e, e);
        }
    }
    
    /**
     * This method initialize the query based on parameter value
     * 
     * @param operator - which operation have to perform
     */
    @Override
    public void getSpecificDocument(String operator) {
        log.info(operator + " operation is started...");
        Bson query = null;
        if (operator != null) {
            switch (operator.toUpperCase()) {
                case "EQUAL":
                    query = eq("name", "Sundar"); // SELECT * FROM sample WHERE name = 'Sundar'
                    break;
                case "NOT-EQUAL":
                    query = ne("name", "Sundar"); // SELECT * FROM sample WHERE name != 'Sundar'
                    break;
                case "AND":
                    query = and(eq("name", "Sundar"), lt("age", 20)); // SELECT * FROM sample WHERE name = 'Sundar' AND age < 20
                    break;
                case "OR":
                    query = or(eq("name", "Sundar"), lt("age", 20)); // SELECT * FROM sample WHERE name = 'Sundar' OR age < 20
                    break;
                case "AND-OR":
                    query = and(eq("gender", "male"), or(eq("name", "Sundar"), lt("age", 20))); //// SELECT * FROM sample WHERE gender='male' AND (name='Sundar' OR age < 20)
                    break;
                case "IN":
                    query = in("name", "Sundar"); // SELECT * FROM sample WHERE name IN('Sundar')
                    break;
                case "NOT-IN":
                    query = nin("name", "Sundar"); // SELECT * FROM sample WHERE name NOT IN('Sundar')
                    break;
                case "LESS-THAN":
                    query = lt("age", 20); // SELECT * FROM sample WHERE age < 20
                    break;
                case "LESS-THAN-OR-EQUAL":
                    query = lte("age", 20); // SELECT * FROM sample WHERE age <= 20
                    break;
                case "GREATER-THAN":
                    query = gt("age", 20); // SELECT * FROM sample WHERE age > 20
                    break;
                case "GREATER-THAN-OR-EQUAL":
                    query = gte("age", 20); // SELECT * FROM sample WHERE age >= 20
                    break;
                case "LIKE":
                    query = regex("name", "^S"); // SELECT * FROM sample WHERE name LIKE='S%'
                    break;
                case "EXISTS":
                    query = exists("gender", true); //fieldName >> true to check for existence, false to check for absence
                    break;
                case "NOT-EXISTS":
                    query = exists("gender", false); //fieldName >> true to check for existence, false to check for absence
                    break;
                default:
                    log.info("Operator \"" + operator + "\" is not matched");
                    break;
            }
            getData(query, operator);
        } else {
            log.info("Operator Should not NULL");
        }
    }
    
    /**
     * This method retrieve document(s) based on parameters
     * @param query - query have to execute
     * @param operator - this is for debug purpose
     */
    private void getData(Bson query, String operator) {
        MongoDatabase db = null;
        MongoCollection collection = null;
        try {
            db = client.getDatabase(mongo.getDataBase());
            collection = db.getCollection(mongo.getSampleCollection());
            FindIterable<Document> list = collection.find(query);
            for (Document doc : list) {
                log.info(doc.getString("name"));
            }
        } catch (MongoException e) {
            log.error("exception occurred while getting Document using " + operator + " : " + e, e);
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
        try {
            mongo.closeMongoClient(client);
        } catch (Exception e) {
            log.info("Exception occurred while close client : " + e, e);
        }
    }
}