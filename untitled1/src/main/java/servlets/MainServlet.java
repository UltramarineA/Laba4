    package servlets;

    import services.MainService;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.io.OutputStreamWriter;
    import java.io.PrintWriter;
    import java.net.URLEncoder;
    import java.sql.SQLException;
    import java.util.List;
    import java.util.Map;

    @WebServlet("/test")
    public class MainServlet extends HttpServlet {

        private final MainService mainService;

        public MainServlet() {
            this.mainService = new MainService();
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");

            String sort = req.getParameter("sort");
            String order = req.getParameter("order");
            if (sort == null || order == null) {
                sort = "id";
                order = "asc";
            }

            PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF8"), true);
            out.println("<html><head>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f9f9f9; }");
            out.println("h2 { color: #333; text-align: center; }");
            out.println("form { background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 400px; margin: auto; }");
            out.println("label { display: block; margin-bottom: 5px; color: #666; }");
            out.println("input[type='text'], input[type='number'] { width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #ddd; background-color: #eaeaea; }");
            out.println("input[type='submit'] { background-color: #007BFF; color: white; padding: 10px 20px; border: none; cursor: pointer; border-radius: 5px; }");
            out.println("input[type='submit']:hover { background-color: #0056b3; }");
            out.println("table { border-collapse: collapse; width: 100%; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("tr:hover { background-color: #ddd; }");
            out.println("</style>");
            out.println("</head><body>");
            out.print("<h2>Товары</h2>");
            out.print("<table border=\"1px\" style=\"width: 100%; border-collapse: collapse;\">");
            out.print("<tr>");
            out.print("<th><a href='?sort=id&order=" + (sort.equals("id") && order.equals("asc") ? "desc" : "asc") + "'>ID</a></th>");
            out.print("<th><a href='?sort=name&order=" + (sort.equals("name") && order.equals("asc") ? "desc" : "asc") + "'>Название</a></th>");
            out.print("<th><a href='?sort=price&order=" + (sort.equals("price") && order.equals("asc") ? "desc" : "asc") + "'>Цена</a></th>");
            out.print("<th><a href='?sort=country&order=" + (sort.equals("country") && order.equals("asc") ? "desc" : "asc") + "'>Страна</a></th>");
            out.print("<th>Действия</th>");
            out.print("</tr>");

            try {
                List<Map<String, Object>> items = mainService.getNames();
                if(sort.equals("id")) {
                    String finalOrder = order;
                    items.sort((o1, o2) -> finalOrder.equals("asc") ? Integer.compare((Integer)o1.get("id"), (Integer)o2.get("id")) : Integer.compare((Integer)o2.get("id"), (Integer)o1.get("id")));
                } else if(sort.equals("name")) {
                    String finalOrder1 = order;
                    items.sort((o1, o2) -> finalOrder1.equals("asc") ? o1.get("name").toString().compareTo(o2.get("name").toString()) : o2.get("name").toString().compareTo(o1.get("name").toString()));
                }
                for (Map<String, Object> item : items) {
                    out.print("<tr>");
                    out.print("<td>" + item.get("id") + "</td>");
                    out.print("<td>" + item.get("name") + "</td>");
                    out.print("<td>" + item.get("price") + "</td>");
                    out.print("<td>" + item.get("country") + "</td>");
                    out.print("<td>");
                    out.print("<a href='delete?id=" + item.get("id") + "' style='color: red;'>Удалить</a> | ");
                    out.print("<a href='edit?id=" + item.get("id") + "&name=" + URLEncoder.encode(item.get("name").toString(), "UTF-8") + "&price=" + item.get("price") + "&country=" + URLEncoder.encode(item.get("country").toString(), "UTF-8") + "' style='color: green;'>Редактировать</a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            out.print("</table>");
            out.println("<br><h2>Добавить новый товар?</h2>");
            out.print("<form method='post' action='/ikts_21_app_war/test'>");
            out.print("<label for='name'>Название:</label>");
            out.print("<input type='text' id='name' name='name'><br>");
            out.print("<label for='price'>Цена:</label>");
            out.print("<input type='text' id='price' name='price'><br>");
            out.print("<label for='country'>Страна:</label>");
            out.print("<input type='text' id='country' name='country'><br>");
            out.print("<input type='submit' value='Добавить'>");
            out.print("</form>");
            out.println("</body></html>");
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String name = new String(req.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
            int price = Integer.parseInt(req.getParameter("price"));
            String country = new String(req.getParameter("country").getBytes("ISO-8859-1"), "UTF-8");
            mainService.addName(name, price, country);
            System.out.println(name + ", " + price + ", " + country);
            resp.sendRedirect("/ikts_21_app_war/test");
        }
    }