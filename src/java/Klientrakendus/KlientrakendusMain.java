/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klientrakendus;

import namespace.webservice._new.*;
import java.util.List;

public class KlientrakendusMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GetBooksListRequest r = new GetBooksListRequest();
        r.setAPITOKEN("1234567890");
        GetBooksListResponse response = getBooksList(r);
        List<BookType> bookList = response.getBook();
        for(BookType bookType: bookList){
            System.out.println(bookType.getTitle());
        }
    }
    
    private static GetBooksListResponse getBooksList(GetBooksListRequest parameter){
        BookService service = new BookService();
        BookPortType port = service.getBookPort();
        return port.getBooksList(parameter);
    }
    
}
