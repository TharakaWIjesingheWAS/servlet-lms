package lk.ijse.dep.web.lms.business.custom.impl;

import lk.ijse.dep.web.lms.business.custom.IssuedBO;
import lk.ijse.dep.web.lms.dto.IssuedDTO;
import lk.ijse.dep.web.lms.entity.Issue;
import lk.ijse.dep.web.lms.repository.IssuedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class IssuedBOImpl implements IssuedBO {

    @Autowired
    private IssuedRepository issuedRepository;

    @Override
    public List<IssuedDTO> getAllIssue() throws Exception {
        List<Issue> allIssues = issuedRepository.findAll();

        List<IssuedDTO> issues = new ArrayList<>();
        for (Issue issue : allIssues){
            issues.add(new IssuedDTO(issue.getId(),issue.getDate(),issue.getDetail()));
        }
        return issues;
    }

    @Override
    public IssuedDTO getIssue(String id) throws Exception {
        Issue issue = issuedRepository.findById(id).get();
        return new IssuedDTO(issue.getId(),issue.getDate(),issue.getDetail());
    }

    @Override
    public void saveIssue(String id, Date date, String detail) throws Exception {
        issuedRepository.save(new Issue(id,date,detail));
    }

    @Override
    public String getNewIssueId() throws Exception {
        String lastIssueId = issuedRepository.getFirstLastIssueIdByOrderByIdDesc().getId();

        if (lastIssueId == null){
            return "I001";
        }else {
            int maxId = Integer.parseInt(lastIssueId.replace("I",""));
            maxId = maxId +1;
            String id = "";
            if (maxId < 10 ){
                id = "I00" + maxId;
            }else if (maxId < 100){
                id = "I0" + maxId;
            }else {
                id = "I" + maxId;
            }
            return id;
        }
    }
}
