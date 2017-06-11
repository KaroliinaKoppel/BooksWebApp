/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ttu.idu0075.books;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import namespace.webservice._new.*;

/**
 *
 * @author karoliina
 */
@WebService(serviceName = "BookService", portName = "BookPort", endpointInterface = "namespace.webservice._new.BookPortType", targetNamespace = "http://new.webservice.namespace", wsdlLocation = "WEB-INF/wsdl/BooksWebService/BooksService.wsdl")
public class BooksWebService {
    
    static int nextBookId = 1;
    static int nextAuthorId = 1;
    static List<BookType> bookList = new ArrayList<>();
    static List<AuthorType> authorList = new ArrayList<>();

    public BookType addBook(AddBookRequest parameter) {
        BookType book = new BookType();
        if(parameter.getAPITOKEN().equalsIgnoreCase("1234567890")) {
            if (parameter.getLang() == null || parameter.getTitle() == null) {
                return null;
            }
            if (!parameter.getLang().matches("[A-Z]{3}")) {
                return null;
            }
            book.setId(BigInteger.valueOf(nextBookId++));
            book.setLang(parameter.getLang());
            book.setTitle(parameter.getTitle());
            if (parameter.getCategory() != null) {
                parameter.getCategory().forEach((category) -> {
                    book.getCategory().add(category);
                });
            }
            if (parameter.getSeries() != null) {
                parameter.getSeries().forEach((series) -> {
                    book.getSeries().add(series);
                });
            }
            if (parameter.getSeriesNumber() != null) {
                parameter.getSeriesNumber().forEach((seriesNo) -> {
                    book.getSeriesNumber().add(seriesNo);
                });
            }
            bookList.add(book);
        }
        return book;
    }

    public AuthorType addAuthor(AddAuthorRequest parameter) {
        AuthorType author = new AuthorType();
        if(parameter.getAPITOKEN().equalsIgnoreCase("1234567890")) {
            if (parameter.getFirstName() == null || parameter.getLastName() == null) {
                return null;
            }
            author.setId(BigInteger.valueOf(nextAuthorId++));
            author.setFirstName(parameter.getFirstName());
            author.setLastName(parameter.getLastName());
            authorList.add(author);
        } 
        return author;
    }

    public BookType addBookAuthor(AddBookAuthorRequest parameter) {
        BookType book = new BookType();
        AuthorType author = new AuthorType();
        if(parameter.getAPITOKEN().equalsIgnoreCase("1234567890")) {
            for(BookType b : bookList) {
                if (b.getId().equals(parameter.getBookId())) {
                    book = b;
                }
            }
            if (parameter.getFirstName() == null || parameter.getLastName() == null) {
                if (parameter.getAuthorId() == null) {
                    return book;
                } else {
                    for (AuthorType a : authorList) {
                        if (a.getId().equals(parameter.getAuthorId())) {
                            author = a;
                        }
                    } 
                }
            } else {
                for (AuthorType a : authorList) {
                    if (a.getFirstName().equals(parameter.getFirstName()) && a.getLastName().equals(parameter.getLastName())) {
                        author = a;
                    }
                } 
                if (author.getFirstName() == null) {
                    author.setId(BigInteger.valueOf(nextAuthorId++));
                    author.setFirstName(parameter.getFirstName());
                    author.setLastName(parameter.getLastName());
                    authorList.add(author);
                }
            }
            
            for (AuthorType a : book.getAuthor()) {
                if (a.getId().equals(author.getId())) {
                    return book;
                }
            }
            book.getAuthor().add(author);
        }
        return book;
    }

    public BookType getBook(GetBookRequest parameter) {
        BookType book = new BookType();
        if(parameter.getAPITOKEN().equalsIgnoreCase("1234567890")) {
            for (BookType b : bookList) {
                if (b.getId().equals(parameter.getId())) {
                    book = b;
                }
            }
        }
        return book;
    }

    public AuthorType getAuthor(GetAuthorRequest parameter) {
        AuthorType author = new AuthorType();
        if(parameter.getAPITOKEN().equalsIgnoreCase("1234567890")) {
            for (AuthorType a : authorList) {
                if (a.getId().equals(parameter.getId())) {
                    author = a;
                }
            }
        }
        return author;
    }

    public GetBooksListResponse getBooksList(GetBooksListRequest parameter) {
        GetBooksListResponse response =  new GetBooksListResponse();
        if (parameter.getAPITOKEN().equalsIgnoreCase("1234567890")) {
            bookList.forEach((book) -> {
                if (parameter.getLang() != null) {
                    if (parameter.getCategory() != null) {
                        if (parameter.getSeries() != null) {
                            if(parameter.getLang().equals(book.getLang()) &&
                                    book.getCategory().contains(parameter.getCategory()) && 
                                    book.getSeries().contains(parameter.getSeries())) {
                                response.getBook().add(book);
                            }
                        } else {
                            if(parameter.getLang().equals(book.getLang()) && 
                                    book.getCategory().contains(parameter.getCategory())) {
                                response.getBook().add(book);
                            }
                        }
                    } else if (parameter.getSeries() != null) {
                        if(parameter.getLang().equals(book.getLang()) && 
                                book.getSeries().contains(parameter.getSeries())) {
                            response.getBook().add(book);
                        }
                    } else {
                        if(parameter.getLang().equals(book.getLang())) {
                            response.getBook().add(book);
                        }
                            
                    }
                } else if (parameter.getCategory() != null) {
                    if (parameter.getSeries() != null) {
                        if(book.getCategory().contains(parameter.getCategory()) &&
                                book.getSeries().contains(parameter.getSeries())) {
                            response.getBook().add(book);
                        }
                    } else {
                        if(book.getCategory().contains(parameter.getCategory())) {
                            response.getBook().add(book);
                        }
                    }
                } else {
                    if (parameter.getSeries() != null) {
                        if(book.getSeries().contains(parameter.getSeries())) {
                            response.getBook().add(book);
                        }
                    } else {
                        response.getBook().add(book);
                    }
                }
            });
        }
        return response;
    }

    public GetAuthorsListResponse getAuthorsList(GetAuthorsListRequest parameter) {
        GetAuthorsListResponse response =  new GetAuthorsListResponse();
        if (parameter.getAPITOKEN().equalsIgnoreCase("1234567890")) {
            authorList.forEach((author) -> {
                response.getAuthor().add(author);
            });
        }
        return response;
    }

    public GetBookAuthorsResponse getBookAuthors(GetBookAuthorsRequest parameter) {
        GetBookAuthorsResponse response =  new GetBookAuthorsResponse();
        if (parameter.getAPITOKEN().equalsIgnoreCase("1234567890")) {
            bookList.stream().filter((book) -> (
                    book.getId().equals(parameter.getBookId()))
            ).forEachOrdered((book) -> {
                book.getAuthor().forEach((author) -> {
                    response.getAuthor().add(author);
                });
            });
        }
        return response;
    }
    
}
