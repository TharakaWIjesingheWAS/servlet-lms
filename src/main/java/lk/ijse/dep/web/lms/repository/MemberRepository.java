package lk.ijse.dep.web.lms.repository;

import lk.ijse.dep.web.lms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MemberRepository extends JpaRepository<Member,String> {
}
