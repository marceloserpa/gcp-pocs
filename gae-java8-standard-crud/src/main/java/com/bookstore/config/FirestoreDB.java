package com.bookstore.config;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

public class FirestoreDB {
	
	private static final String PROJECT_ID = "my-project";
	private Firestore db;
	
	private FirestoreDB() {
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setProjectId(PROJECT_ID).build();
		this.db = firestoreOptions.getService();	
	}
	
	private static class FirestoreConfigHolder {
		 static final FirestoreDB INSTANCE = new FirestoreDB();
	}
	
	public static FirestoreDB getInstance() {
		return FirestoreConfigHolder.INSTANCE;
	}
	
	public Firestore getConnection() {
		return this.db;
	}
	
	

}
