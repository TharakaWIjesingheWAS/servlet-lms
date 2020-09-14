package lk.ijse.dep.web.lms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO {
    private String id;
    private String isbn;
    private String title;
    private String author;
    private double price;
    private String availability;
}
