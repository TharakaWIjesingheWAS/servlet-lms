package lk.ijse.dep.web.lms.api;

import lk.ijse.dep.web.lms.business.custom.BookBO;
import lk.ijse.dep.web.lms.business.custom.MemberBO;
import lk.ijse.dep.web.lms.dto.MemberDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "MemberServlet",urlPatterns = "/members")
public class MemberServlet extends HttpServlet {

    private MemberBO memberBO;

    @Override
    public void init() throws ServletException {
        memberBO = ((AnnotationConfigApplicationContext) (getServletContext().getAttribute("ctx"))).getBean(MemberBO.class);
    }

    public static String getParameter(String queryString,String parameterName) {
        if (queryString == null || parameterName == null || queryString.trim().isEmpty() || parameterName.trim().isEmpty()){
            return null;
        }
        String[] queryParameters = queryString.split("&");
        for (String queryParameter : queryParameters){
            if (queryParameter.contains("=") && queryParameter.startsWith(parameterName)){
                return queryParameter.split("=")[1];
            }
        }
        return null;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String nic = request.getParameter("nic");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");

        if (!id.matches("M\\d{3}") || nic.trim().length() <10 || name.trim().length() < 3 || address.trim().length() < 3 || contact.matches("\\d{10}")){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType("text/plain");
        try (PrintWriter writer = response.getWriter()){
            if (memberBO.isMemberExit(id)){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            memberBO.saveMember(id,nic,name,address,contact);
            response.sendError(HttpServletResponse.SC_CREATED);
            writer.println("Book Saved Successfully");
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        response.setContentType("text/plain");
        try (PrintWriter writer = response.getWriter()){
            if (id == null){
                List<MemberDTO> allMembers = memberBO.getAllMembers();
                allMembers.forEach(writer::println);
            }else {
                try {
                    MemberDTO member = memberBO.getMember(id);
                    writer.println(member);
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();
        if (queryString == null) {
            return;
        }
        String id = getParameter(queryString, "id");
        if (id == null) {
            return;
        }

        BufferedReader reader = req.getReader();
        String line = null;
        String requestBody = "";

        while ((line = reader.readLine()) != null) {
            requestBody += line;
        }

        String nic = req.getParameter("nic");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String contact = req.getParameter("contact");

        if (!id.matches("M\\d{3}") || nic.trim().length() <10 || name.trim().length() < 3 || address.trim().length() < 3 || contact.matches("\\d{10}")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp.setContentType("text/plain");
        try (PrintWriter writer = resp.getWriter()){
            if (!memberBO.isMemberExit(id)){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            memberBO.saveMember(id,nic,name,address,contact);
            resp.sendError(HttpServletResponse.SC_CREATED);
            writer.println("Book Update Successfully");
        }catch (Exception e){
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();
        String id = getParameter(queryString, "id");
        if (id == null){
            return;
        }
        System.out.println("Member removed: " + id);
    }
}
