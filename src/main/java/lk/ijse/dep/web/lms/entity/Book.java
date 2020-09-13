package lk.ijse.dep.web.lms.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book implements SuperEntity {
    @Id
    private String id;
    private String isbn;
    private String title;
    private String author;
    private double price;
    private String availability;
}
