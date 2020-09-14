package lk.ijse.dep.web.lms.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IssuedDTO {
   private String id;
   private Date date;
   private String detail;
}
