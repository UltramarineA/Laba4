package servlets;

import services.MainService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

    private final MainService mainService;

    public DeleteServlet() {
        this.mainService = new MainService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr!= null) {
            try {
                int id = Integer.parseInt(idStr);
                mainService.deleteName(id);
                resp.sendRedirect("/ikts_21_app_war/test");


            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID parameter.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing ID parameter.");
        }
    }
}