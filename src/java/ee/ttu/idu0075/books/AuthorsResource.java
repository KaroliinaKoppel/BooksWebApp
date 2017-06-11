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
import namespace.webservice._new.AddAuthorRequest;
import namespace.webservice._new.AuthorType;
import namespace.webservice._new.GetAuthorRequest;
import namespace.webservice._new.GetAuthorsListRequest;
import namespace.webservice._new.GetAuthorsListResponse;

/**
 * REST Web Service
 *
 * @author karoliina
 */
@Path("authors")
public class AuthorsResource {

    @Context
    private UriInfo context;

    public AuthorsResource() {
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public AuthorType addAuthor(AuthorType content, @QueryParam("API_TOKEN") String token) {
        BooksWebService ws = new BooksWebService();
        AddAuthorRequest request = new AddAuthorRequest();
        request.setAPITOKEN(token);
        request.setFirstName(content.getFirstName());
        request.setLastName(content.getLastName());
        return ws.addAuthor(request);
    }
    
    @GET
    @Path("{id : \\d+}")
    @Produces("application/json")
    public AuthorType getAuthor(@PathParam("id") String id, @QueryParam("API_TOKEN") String token) {
        BooksWebService ws = new BooksWebService();
        GetAuthorRequest request = new GetAuthorRequest();
        request.setAPITOKEN(token);
        request.setId(BigInteger.valueOf(Integer.parseInt(id)));
        return ws.getAuthor(request);
    }
    
    @GET
    @Produces("application/json")
    public GetAuthorsListResponse getAuthorsList(@QueryParam("API_TOKEN") String token) {
        BooksWebService ws = new BooksWebService();
        GetAuthorsListRequest request = new GetAuthorsListRequest();
        request.setAPITOKEN(token);
        return ws.getAuthorsList(request);
    }
}
