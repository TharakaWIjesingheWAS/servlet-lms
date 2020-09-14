package lk.ijse.dep.web.lms.business.custom;

import lk.ijse.dep.web.lms.business.SuperBO;
import lk.ijse.dep.web.lms.dto.BookDTO;
import lk.ijse.dep.web.lms.dto.MemberDTO;

import java.util.List;

public interface BookBO extends SuperBO {
    List<BookDTO> getAllBooks() throws Exception;

    BookDTO getBook(String id) throws Exception;

    void saveBook(String id,String isbn,String title,String author,double price,String availability) throws Exception;

    void deleteBook(String bookId) throws Exception;

    void updateBook(String BookId,String isbn,String title,String author,double price,String availability) throws Exception;

    String getNewBookId() throws Exception;

    boolean isBookExit(String id) throws Exception;
}
