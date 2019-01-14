package com.bookstore;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import java.util.ArrayList;

import javax.inject.Named;

//http://localhost:8080/_ah/api/discovery/v1/apis/
@Api(
    name = "books",
    version = "v1"
    /*,
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}*/
)
public class BookService {

  public static ArrayList<Book> books = new ArrayList<Book>();

  static {
    books.add(new Book("The Shining"));
    books.add(new Book("Under the domme"));
  }

  //http://localhost:8080/_ah/api/books/v1/book/1
  public Book getBook(@Named("id") Integer id) throws NotFoundException {
    try {
      return books.get(id);
    } catch (IndexOutOfBoundsException e) {
      throw new NotFoundException("Greeting not found with an index: " + id);
    }
  }

  //http://localhost:8080/_ah/api/books/v1/book
  public ArrayList<Book> listBook() {
    return books;
  }

  @ApiMethod(name = "book", httpMethod = "post")
  public Book insertGreeting(Book book) {
	  /**
    Book response = new Book();
    StringBuilder responseBuilder = new StringBuilder();
    for (int i = 0; i < times; i++) {
      responseBuilder.append(greeting.getMessage());
    }
    response.setMessage(responseBuilder.toString());
    */
	books.add(book);
	Book response = new Book();
	response.setMessage("ok");
    return response;
  }

  @ApiMethod(name = "greetings.authed", path = "hellogreeting/authed")
  public Book authedGreeting(User user) {
    Book response = new Book("hello " + user.getEmail());
    return response;
  }
}
