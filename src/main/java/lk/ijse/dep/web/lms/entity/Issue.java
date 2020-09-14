package lk.ijse.dep.web.lms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Issue implements SuperEntity {
    @Id
    private String id;
    private Date date;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "memberId",referencedColumnName = "id",nullable = false)
    private Member member;
    private String detail;

    public Issue(String id, Date date, String detail) {
        this.id = id;
        this.date = date;
        this.detail = detail;
    }


}
