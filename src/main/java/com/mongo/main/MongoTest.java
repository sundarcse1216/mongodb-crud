package com.mongo.main;

import com.mongo.crud.impl.DeleteDocumentsImpl;
import com.mongo.crud.impl.InsertDocumentsImpl;
import com.mongo.crud.impl.QueryDocumentsImpl;
import com.mongo.crud.impl.UpdateDocumentsImpl;
import com.mongo.crud.DeleteDocuments;
import com.mongo.crud.InsertDocuments;
import com.mongo.crud.QueryDocuments;
import com.mongo.crud.UpdateDocuments;
import static com.mongo.utils.Commons.INVALID_MSG;
import org.apache.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 
 * @author sundar
 */
public class MongoTest {
	private static Logger log = Logger.getLogger(MongoTest.class);

	/**
	 * This is constructor, validate the user given arguments
	 */
	public MongoTest(String[] args) {
		boolean valid = true;
		for (int i = 0; i < args.length; i++) {
			if (Integer.valueOf(args[i]) > 4) {
				valid = false;
			}
		}
		if (valid) {
			startProcess(args);
		} else {
			log.info(INVALID_MSG);
		}
	}

	/**
	 * Start the operation(s) based on input
	 * 
	 * @param args - operation ID, which is given by user
	 */
	private void startProcess(String[] args) {
		for (String arg : args) {
			if (Integer.valueOf(arg) > 4) {
				log.info(INVALID_MSG);
				break;
			} else {
				log.info("--------------------------------------");
				switch (Integer.valueOf(arg)) {
				case 1:
					log.info("Read Process is started...");
					log.info("--------------------------------------");
					QueryDocuments document = new QueryDocumentsImpl();
					document.loadMethods();
					break;
				case 2:
					log.info("Write Process is started...");
					log.info("--------------------------------------");
					InsertDocuments insert = new InsertDocumentsImpl();
					insert.loadMethods();
					break;
				case 3:
					log.info("Update Process is started...");
					log.info("--------------------------------------");
					UpdateDocuments update = new UpdateDocumentsImpl();
					update.loadMethods();
					break;
				case 4:
					log.info("Delete Process is started...");
					log.info("--------------------------------------");
					DeleteDocuments delete = new DeleteDocumentsImpl();
					delete.loadMethods();
					break;
				default:
					log.info("Invalid Argument : " + arg);
					log.info("--------------------------------------");
					break;
				}
			}
		}
	}

	/**
	 * This is Main method of this project
	 * 
	 * @param args - command line input(s) 
	 */
	public static void main(String[] args) {
		try {
			log.info("Welcome to MongoDB CRUD Operations!!!");
			if (args.length > 0 && args.length <= 4) {
				new MongoTest(args);
			} else {
				log.info("At least one argument, at most four argument is Required, (" + args.length + " given)");
			}
		} catch (Exception e) {
			log.error("Exception occurred : " + e, e);
		}
	}
}
