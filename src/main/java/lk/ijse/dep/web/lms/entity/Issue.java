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

    public Issue(String id, Date date, Member member) {
        this.id = id;
        this.date = date;
        this.member = member;
    }
}
