package com.bookstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.inject.Named;

import com.bookstore.config.FirestoreDB;
import com.google.api.core.ApiFuture;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

//http://localhost:8080/_ah/api/discovery/v1/apis/
@Api(name = "books", version = "v1"
/*
 * , scopes = {Constants.EMAIL_SCOPE}, clientIds = {Constants.WEB_CLIENT_ID,
 * Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID}, audiences =
 * {Constants.ANDROID_AUDIENCE}
 */
)
public class BookService {
	
	private final static String PROJECT_ID = "my-project";

	// http://localhost:8080/_ah/api/books/v1/book/1
	public Book getBook(@Named("id") String id) throws NotFoundException {
		Firestore db = FirestoreDB.getInstance().getConnection();
		DocumentReference documentRef = db.collection("books").document(id);		
		ApiFuture<DocumentSnapshot> future = documentRef.get();				
		try {
			DocumentSnapshot document = future.get();
			if(!document.exists()) {
				throw new NotFoundException("Greeting not found with an index: " + id);
			}
			Book book = new Book();
			book.setAuthor(document.getString("author"));
			book.setTitle(document.getString("title"));
			book.setCategory(document.getString("category"));
			return book;
		} catch (InterruptedException | ExecutionException  e) {
			throw new RuntimeException("Greeting not found with an index: " + id);
		}
	}

	// http://localhost:8080/_ah/api/books/v1/book
	public List<Book> listBook() throws InterruptedException, ExecutionException {
		Firestore db = FirestoreDB.getInstance().getConnection();

		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("books").get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		List<Book> books = new ArrayList<>();
		for (QueryDocumentSnapshot document : documents) {
			Book book = new Book();
			book.setAuthor(document.getString("author"));
			book.setTitle(document.getString("title"));
			book.setCategory(document.getString("category"));
			books.add(book);

		}

		return books;
	}

	@ApiMethod(name = "book", httpMethod = "post")
	public Book insertGreeting(Book book) throws InterruptedException, ExecutionException {		
		Firestore db = FirestoreDB.getInstance().getConnection();
		DocumentReference docRef = db.collection("books").document(book.getTitleID());
		Map<String, Object> data = new HashMap<>();
		data.put("author", book.getAuthor());
		data.put("title", book.getTitle());
		data.put("category", book.getCategory());
		
		ApiFuture<WriteResult> result = docRef.set(data);
		result.get();
		Book response = new Book();
		return response;
	}

}
