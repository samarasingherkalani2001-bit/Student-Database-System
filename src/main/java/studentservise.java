import java.sql.SQLException;
import java.util.List;

public class studentservise {
    private final studentdata dao = new studentdata();

    public int create(student s) throws SQLException {

        validation.requireName(s.getName());
        validation.requireValidEmail(s.getEmail());
        validation.requireValidGpa(s.getGpa());

        if (dao.emailExists(s.getEmail(), null)) {
            throw new IllegalArgumentException("Email already exists.");
        }
        return dao.insert(s);
    }

    public boolean update(student s) throws SQLException {
        validation.requireName(s.getName());
        validation.requireValidEmail(s.getEmail());
        validation.requireValidGpa(s.getGpa());

        if (dao.emailExists(s.getEmail(), s.getId())) {
            throw new IllegalArgumentException("Email already used by another student.");
        }
        return dao.update(s);
    }

    public boolean delete(int id) throws SQLException {
        return dao.delete(id);
    }

    public student get(int id) throws SQLException { return dao.findById(id); }

    public List<student> getAll() throws SQLException { return dao.findAll(); }

    public List<student> searchByName(String q) throws SQLException { return dao.findByNameLike(q); }
}
