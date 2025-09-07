import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class studentdata {


    public int insert(student s) throws SQLException {
        String sql = "INSERT INTO student (name, email, course, gpa) VALUES (?,?,?,?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.setDouble(4, s.getGpa());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public student findById(int id) throws SQLException {
        String sql = "SELECT id,name,email,course,gpa FROM student WHERE id=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }


    public List<student> findAll() throws SQLException {
        String sql = "SELECT id,name,email,course,gpa FROM student ORDER BY id";
        List<student> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }


    public List<student> findByNameLike(String nameLike) throws SQLException {
        String sql = "SELECT id,name,email,course,gpa FROM student WHERE name LIKE ? ORDER BY name";
        List<student> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nameLike + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }


    public boolean update(student s) throws SQLException {
        String sql = "UPDATE student SET name=?, email=?, course=?, gpa=? WHERE id=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.setDouble(4, s.getGpa());
            ps.setInt(5, s.getId());
            return ps.executeUpdate() == 1;
        }
    }


    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM student WHERE id=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }


    public boolean emailExists(String email, Integer excludeId) throws SQLException {
        String sql = "SELECT 1 FROM student WHERE email=? " +
                (excludeId == null ? "" : "AND id<>?");
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            if (excludeId != null) ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private student map(ResultSet rs) throws SQLException {
        return new student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("course"),
                rs.getDouble("gpa")
        );
    }
}
