package lk.ijse.dep.web.lms.api;

import lk.ijse.dep.web.lms.business.custom.BookBO;
import lk.ijse.dep.web.lms.dto.BookDTO;
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
import java.util.NoSuchElementException;

@WebServlet(name = "BookServlet",urlPatterns = "/books")
public class BookServlet extends HttpServlet {

    private BookBO bookBO;

    @Override
    public void init() throws ServletException {
        bookBO = ((AnnotationConfigApplicationContext) (getServletContext().getAttribute("ctx"))).getBean(BookBO.class);
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
        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        double price = Double.parseDouble(request.getParameter("price"));
        String availability = request.getParameter("availability");

        if (!id.matches("B\\d{3}") || isbn.trim().length() <13 || title.trim().length() < 2 || author.trim().length() < 3 /*|| price.matches("Rs\\d{2}")*/){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType("text/plain");
        try (PrintWriter writer = response.getWriter()){
            if (bookBO.isBookExit(id)){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            bookBO.saveBook(id,isbn,title,author,price,availability);
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
                List<BookDTO> allBooks = bookBO.getAllBooks();
                allBooks.forEach(writer::println);
            }else {
                try {
                    BookDTO book = bookBO.getBook(id);
                    writer.println(book);
                }catch (NoSuchElementException e){
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

        String isbn = getParameter(requestBody, "isbn");
        String title = getParameter(requestBody, "title");
        String author = getParameter(requestBody, "author");
        double price = Double.parseDouble(getParameter(requestBody, "price"));
        String availability = getParameter(requestBody, "availability");

        if (!id.matches("B\\d{3}") || isbn.trim().length() <13 || title.trim().length() < 2 || author.trim().length() < 3 /*|| price.matches("Rs\\d{2}")*/){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp.setContentType("text/plain");
        try (PrintWriter writer = resp.getWriter()){
            if (bookBO.isBookExit(id)){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            bookBO.saveBook(id,isbn,title,author,price,availability);
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
        System.out.println("Book removed: " + id );
    }
}
