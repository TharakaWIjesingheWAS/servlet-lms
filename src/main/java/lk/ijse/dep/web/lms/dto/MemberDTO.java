package lk.ijse.dep.web.lms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {
    private String id;
    private String nic;
    private String name;
    private String address;
    private String contact;
}
