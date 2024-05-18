package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainService {

    private final DBService dbService;

    public MainService() {
        dbService = new DBService();
    }

    public void addName(String name, int price, String country) {
        String sql = "INSERT INTO public.ikts_example (name, price, country) VALUES (?,?,?)";
        dbService.insert(sql, name, price, country);
    }

    public void deleteName(int id) {
        String sql = "DELETE FROM public.ikts_example WHERE id =?";
        dbService.delete(sql, id);
    }

    public void editName(int id, String name, int price, String country) {
        String sql = "UPDATE public.ikts_example SET name =?, price =?, country =? WHERE id =?";
        dbService.update(sql, name, price, country, id);
    }

    public List<Map<String, Object>> getNames() throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT * FROM public.ikts_example";
        ResultSet rs = dbService.select(sql);
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", rs.getInt("id"));
            row.put("name", rs.getString("name"));
            row.put("price", rs.getInt("price")); // Assuming price is stored as integer
            row.put("country", rs.getString("country")); // Assuming country is stored as varchar
            result.add(row);
        }
        return result;
    }

}