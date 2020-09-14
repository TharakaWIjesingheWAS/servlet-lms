package lk.ijse.dep.web.lms.business.custom;

import lk.ijse.dep.web.lms.business.SuperBO;
import lk.ijse.dep.web.lms.dto.MemberDTO;

import java.util.List;

public interface MemberBO extends SuperBO {

    List<MemberDTO> getAllMembers() throws Exception;

    MemberDTO getMember(String id) throws Exception;

    void saveMember(String id,String nic,String name,String address,String contact) throws Exception;

    void deleteMember(String memberId) throws Exception;

    void updateMember(String nic,String name,String address,String contact,String memberId) throws Exception;

    String getNewMemberId() throws Exception;

    boolean isMemberExit(String id) throws Exception;
}
