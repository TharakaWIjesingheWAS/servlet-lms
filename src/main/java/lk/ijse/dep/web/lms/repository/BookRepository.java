package lk.ijse.dep.web.lms.repository;

import lk.ijse.dep.web.lms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,String> {
    Book getFirstLastBookIdByOrderByIdDesc() throws Exception;
}
