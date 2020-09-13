package lk.ijse.dep.web.lms.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member implements SuperEntity {
    @Id
    private String id;
    private String nic;
    private String name;
    private String address;
    private String contact;
}
