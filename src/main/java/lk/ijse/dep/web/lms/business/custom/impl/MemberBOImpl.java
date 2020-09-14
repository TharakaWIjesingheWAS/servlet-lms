package lk.ijse.dep.web.lms.business.custom.impl;

import lk.ijse.dep.web.lms.business.custom.MemberBO;
import lk.ijse.dep.web.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class MemberBOImpl implements MemberBO {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<>
}
