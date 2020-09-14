package lk.ijse.dep.web.lms.api;

import lk.ijse.dep.web.lms.business.custom.BookBO;
import lk.ijse.dep.web.lms.business.custom.IssuedBO;
import lk.ijse.dep.web.lms.dto.IssuedDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@WebServlet(name = "IssueServlet",urlPatterns = "/issues")
public class IssueServlet extends HttpServlet {

    private IssuedBO issuedBO;

    @Override
    public void init() throws ServletException {
        issuedBO = ((AnnotationConfigApplicationContext) (getServletContext().getAttribute("ctx"))).getBean(IssuedBO.class);
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
        Date date = Date.valueOf(request.getParameter("date"));
        String detail = request.getParameter("detail");

        if (!id.matches("M\\d{3}") ){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType("text/plain");
        try (PrintWriter writer = response.getWriter()){
            if (issuedBO.isIssueExit(id)){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            issuedBO.saveIssue(id,date,detail);
            response.sendError(HttpServletResponse.SC_CREATED);
            writer.println("Book Issued Successfully");
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
                List<IssuedDTO> allIssue = issuedBO.getAllIssue();
                allIssue.forEach(writer::println);
            }else {
                try {
                    IssuedDTO issue = issuedBO.getIssue(id);
                    writer.println(issue);
                } catch (NoSuchElementException e) {
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
        if (queryString == null){
            return;
        }
        String id = getParameter(queryString, "id");
        if (id == null){
            return;
        }

        BufferedReader reader = req.getReader();
        String line = null;
        String requestBody = "";

        while ((line = reader.readLine()) != null){
            requestBody += line;
        }

        Date date = Date.valueOf(req.getParameter("date"));
        String detail = req.getParameter("detail");

        if (!id.matches("M\\d{3}") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp.setContentType("text/plain");
        try (PrintWriter writer = resp.getWriter()){
            if (issuedBO.isIssueExit(id)){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            issuedBO.saveIssue(id,date,detail);
            resp.sendError(HttpServletResponse.SC_CREATED);
            writer.println("Book Issued Successfully");
        }catch (Exception e){
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
