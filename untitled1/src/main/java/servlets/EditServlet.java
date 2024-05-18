package servlets;

import services.MainService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {

    private final MainService mainService;

    public EditServlet() {
        this.mainService = new MainService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String encodedId = req.getParameter("id");
            int id = Integer.parseInt(URLDecoder.decode(encodedId, "UTF-8"));

            String encodedName = req.getParameter("name");
            String decodedName = new String(encodedName.getBytes("ISO-8859-1"), "UTF-8");
            String name = decodedName;

            String encodedPrice = req.getParameter("price");
            int price = Integer.parseInt(encodedPrice);

            String encodedCountry = req.getParameter("country");
            String decodedCountry = new String(encodedCountry.getBytes("ISO-8859-1"), "UTF-8");
            String country = decodedCountry;

            mainService.editName(id, name, price, country);
            resp.sendRedirect("/ikts_21_app_war/test");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID or Price");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter out = resp.getWriter();
        out.println("<html><head>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f9f9f9; }");
        out.println("h2 { color: #333; text-align: center; }");
        out.println("form { background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 400px; margin: auto; }");
        out.println("label { display: block; margin-bottom: 5px; }");
        out.println("input[type='text'], input[type='number'] { width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #ddd; }");
        out.println("input[type='submit'] { background-color: #007BFF; color: white; padding: 10px 20px; border: none; cursor: pointer; border-radius: 5px; }");
        out.println("input[type='submit']:hover { background-color: #0056b3; }");
        out.println("</style>");
        out.println("</head><body>");
        out.print("<h2>Редактирование</h2>");
        out.print("<form method='post' action='/ikts_21_app_war/edit'>");
        out.print("<input type='hidden' name='id' value='" + req.getParameter("id") + "'>");
        out.print("<label for='name'>Название:</label>");
        out.print("<input type='text' id='name' name='name' value='" + req.getParameter("name") + "'>");
        out.print("<label for='price'>Цена:</label>");
        out.print("<input type='number' id='price' name='price' value='" + req.getParameter("price") + "'>");
        out.print("<label for='country'>Страна:</label>");
        out.print("<input type='text' id='country' name='country' value='" + req.getParameter("country") + "'>");
        out.print("<input type='submit' value='Сохранить изменения'>");
        out.print("</form>");
        out.println("</body></html>");
    }
}