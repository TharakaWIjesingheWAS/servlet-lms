package lk.ijse.dep.web.lms.repository;

import lk.ijse.dep.web.lms.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IssuedRepository extends JpaRepository<Issue,String> {
    Issue getFirstLastIssueIdByOrderByIdDesc();
}
