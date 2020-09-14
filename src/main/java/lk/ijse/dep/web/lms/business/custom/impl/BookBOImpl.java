package lk.ijse.dep.web.lms.business.custom.impl;

import lk.ijse.dep.web.lms.business.custom.BookBO;
import lk.ijse.dep.web.lms.dto.BookDTO;
import lk.ijse.dep.web.lms.entity.Book;
import lk.ijse.dep.web.lms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class BookBOImpl implements BookBO {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() throws Exception {
        List<Book> allBooks = bookRepository.findAll();

        List<BookDTO> books = new ArrayList<>();
        for (Book book : allBooks){
            books.add(new BookDTO(book.getId(),book.getIsbn(),book.getTitle(),book.getAuthor(),book.getPrice(),book.getAvailability()));
        }
        return books;
    }

    @Override
    public BookDTO getBook(String id) throws Exception {
        Book book = bookRepository.findById(id).get();
        return new BookDTO(book.getId(),book.getIsbn(),book.getTitle(),book.getAuthor(),book.getPrice(),book.getAvailability());
    }

    @Override
    public void saveBook(String id, String isbn, String title, String author, double price, String availability) throws Exception {
        bookRepository.save(new Book(id,isbn,title,author,price,availability));
    }

    @Override
    public void deleteBook(String bookId) throws Exception {
        bookRepository.deleteById(bookId);
    }

    @Override
    public void updateBook(String BookId, String isbn, String title, String author, double price, String availability) throws Exception {
        bookRepository.save(new Book(BookId,isbn,title,author,price,availability));
    }

    @Override
    @Transactional(readOnly = true)
    public String getNewBookId() throws Exception {
        String lastBookId = bookRepository.getFirstLastBookIdByOrderByIdDesc().getId();

        if (lastBookId == null){
            return "B001";
        }else {
            int maxId = Integer.parseInt(lastBookId.replace("B",""));
            maxId = maxId +1;
            String id = "";
            if (maxId < 10 ){
                id = "B00" + maxId;
            }else if (maxId < 100){
                id = "B0" + maxId;
            }else {
                id = "B" + maxId;
            }
            return id;
        }
    }

    @Override
    public boolean isBookExit(String id) throws Exception {
        return bookRepository.findById(id).isPresent();
    }
}
