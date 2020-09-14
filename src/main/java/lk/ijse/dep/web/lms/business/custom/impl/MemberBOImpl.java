package lk.ijse.dep.web.lms.business.custom.impl;

import lk.ijse.dep.web.lms.business.custom.MemberBO;
import lk.ijse.dep.web.lms.dto.MemberDTO;
import lk.ijse.dep.web.lms.entity.Member;
import lk.ijse.dep.web.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component
@Transactional
public class MemberBOImpl implements MemberBO {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberDTO> getAllMembers() throws Exception {
        List<Member> allMembers = memberRepository.findAll();

        List<MemberDTO> members = new ArrayList<>();
        for (Member member : allMembers){
            members.add(new MemberDTO(member.getId(),member.getNic(),member.getName(),member.getAddress(),member.getContact()));
        }
        return members;
    }

    @Override
    public MemberDTO getMember(String id) throws Exception {
        Member member = memberRepository.findById(id).get();
        return new MemberDTO(member.getId(),member.getNic(),member.getName(),member.getAddress(),member.getContact());
    }

    @Override
    public void saveMember(String id, String nic, String name, String address, String contact) throws Exception {
        memberRepository.save(new Member(id,nic,name,address,contact));
    }

    @Override
    public void deleteMember(String memberId) throws Exception {
        memberRepository.deleteById(memberId);
    }

    @Override
    public void updateMember(String nic, String name, String address, String contact, String memberId) throws Exception {
        memberRepository.save(new Member(memberId,nic,name,address,contact));
    }

    @Transactional(readOnly = true)
    @Override
    public String getNewMemberId() throws Exception {
        String lastMemberId = memberRepository.getFirstLastMemberIdByOrderByIdDesc().getId();

        if (lastMemberId == null){
            return "M001";
        }else {
            int maxId = Integer.parseInt(lastMemberId.replace("M",""));
            maxId = maxId +1;
            String id = "";
            if (maxId < 10 ){
                id = "M00" + maxId;
            }else if (maxId < 100){
                id = "M0" + maxId;
            }else {
                id = "M" + maxId;
            }
            return id;
        }
    }

    @Override
    public boolean isMemberExit(String id) throws Exception {
        return memberRepository.findById(id).isPresent();
    }
}
