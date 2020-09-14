package lk.ijse.dep.web.lms.business.custom;

import lk.ijse.dep.web.lms.business.SuperBO;
import lk.ijse.dep.web.lms.dto.IssuedDTO;

import java.sql.Date;
import java.util.List;

public interface IssuedBO extends SuperBO {
    List<IssuedDTO> getAllIssue() throws Exception;

    IssuedDTO getIssue(String id) throws Exception;

    void saveIssue(String id, Date date, String detail) throws Exception;

    String getNewIssueId() throws Exception;

    boolean isIssueExit(String id) throws Exception;
}
