/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ttu.idu0075.books;

import java.math.BigInteger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import namespace.webservice._new.AddBookAuthorRequest;
import namespace.webservice._new.AddBookRequest;
import namespace.webservice._new.AuthorType;
import namespace.webservice._new.BookType;
import namespace.webservice._new.GetBookAuthorsRequest;
import namespace.webservice._new.GetBookAuthorsResponse;
import namespace.webservice._new.GetBookRequest;
import namespace.webservice._new.GetBooksListRequest;
import namespace.webservice._new.GetBooksListResponse;

/**
 * REST Web Service
 *
 * @author karoliina
 */
@Path("books")
public class BooksResource {

    @Context
    private UriInfo context;

    public BooksResource() {
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public BookType addBook(BookType content, @QueryParam("API_TOKEN") String token) {
        BooksWebService ws = new BooksWebService();
        AddBookRequest request = new AddBookRequest();
        request.setAPITOKEN(token);
        request.setTitle(content.getTitle());
        request.setLang(content.getLang());
        System.out.println(content.getSeries());
        for (String category : content.getCategory()) {
            request.getCategory().add(category);
        }
        for (String series : content.getSeries()) {
            request.getSeries().add(series);
        }
        for (BigInteger seriesNo : content.getSeriesNumber()) {
            request.getSeriesNumber().add(seriesNo);
        }
        return ws.addBook(request);
    }

    @POST
    @Path("{id : \\d+}/authors")
    @Consumes("application/json")
    @Produces("application/json")
    public BookType addBookAuthor(AuthorType content, @PathParam("id") BigInteger id, @QueryParam("API_TOKEN") String token) {
        BooksWebService ws = new BooksWebService();
        AddBookAuthorRequest request = new AddBookAuthorRequest();
        request.setAPITOKEN(token);
        request.setBookId(id);
        request.setFirstName(content.getFirstName());
        request.setLastName(content.getLastName());
        return ws.addBookAuthor(request);
    }


    @GET
    @Path("{id : \\d+}")
    @Produces("application/json")
    public BookType getBook(@PathParam("id") String id, @QueryParam("API_TOKEN") String token) {
        BooksWebService ws = new BooksWebService();
        GetBookRequest request = new GetBookRequest();
        request.setAPITOKEN(token);
        request.setId(BigInteger.valueOf(Integer.parseInt(id)));
        return ws.getBook(request);
    }


    @GET
    @Path("{id : \\d+}/authors")
    @Produces("application/json")
    public GetBookAuthorsResponse getBookAuthors(@PathParam("id") String id, @QueryParam("API_TOKEN") String token) {
        BooksWebService ws = new BooksWebService();
        GetBookAuthorsRequest request = new GetBookAuthorsRequest();
        request.setAPITOKEN(token);
        request.setBookId(BigInteger.valueOf(Integer.parseInt(id)));
        return ws.getBookAuthors(request);
    }


    @GET
    @Produces("application/json")
    public GetBooksListResponse getBooksList(@QueryParam("API_TOKEN") String token) {
        BooksWebService ws = new BooksWebService();
        GetBooksListRequest request = new GetBooksListRequest();
        request.setAPITOKEN(token);
        return ws.getBooksList(request);
    }
}
