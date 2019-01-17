package com.bookstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.inject.Named;

import com.google.api.core.ApiFuture;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
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

	public static ArrayList<Book> books = new ArrayList<Book>();

	static {
		books.add(new Book("The Shining"));
		books.add(new Book("Under the domme"));
	}

	// http://localhost:8080/_ah/api/books/v1/book/1
	public Book getBook(@Named("id") Integer id) throws NotFoundException {
		try {
			return books.get(id);
		} catch (IndexOutOfBoundsException e) {
			throw new NotFoundException("Greeting not found with an index: " + id);
		}
	}

	// http://localhost:8080/_ah/api/books/v1/book
	public ArrayList<Book> listBook() throws InterruptedException, ExecutionException {
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("MY-PROJECT")
				.build();
		Firestore db = firestoreOptions.getService();

		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("books").get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		books = new ArrayList<>();
		for (QueryDocumentSnapshot document : documents) {
			Book book = new Book();
			book.setMessage(document.getString("author"));
			books.add(book);

		}

		return books;
	}

	@ApiMethod(name = "book", httpMethod = "post")
	public Book insertGreeting() throws InterruptedException, ExecutionException {
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("MY_PROJECT")
				.build();
		Firestore db = firestoreOptions.getService();
		DocumentReference docRef = db.collection("books").document("theshining");
		Map<String, Object> data = new HashMap<>();
		data.put("author", "Stephen King");
		data.put("title", "The Shining");
		data.put("category", "Horror");
		ApiFuture<WriteResult> result = docRef.set(data);
		result.get();
		Book response = new Book();
		response.setMessage("ok");
		return response;
	}

}
