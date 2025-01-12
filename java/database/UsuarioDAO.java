package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Usuario;

public class UsuarioDAO {

    private static Connection conn;

    public UsuarioDAO() throws ClassNotFoundException, SQLException {
        conn = Conexao.getConn();
    }

    public ArrayList<Usuario> getAllUsers() throws SQLException {

        ArrayList<Usuario> list = new ArrayList();

        String query = "select * from usuarios;";

        PreparedStatement prep = conn.prepareStatement(query);

        ResultSet result = prep.executeQuery();

        while (result.next()) {
            Usuario user = new Usuario();

            user.setId(result.getInt("id"));
            user.setNome(result.getString("nome"));
            user.setEmail(result.getString("email"));
            user.setNascimento(result.getString("nascimento"));
            user.setSenha(result.getString("senha"));
            user.setNoticias(
                    (result.getInt("noticias") == 1)
            );
            list.add(user);
        }
        prep.close();
        return list;
    }

    public void setNewUser(Usuario user) throws SQLException {
        String query = "insert into usuarios(nome, email, nascimento, senha, noticias)"
                + "values( ?, ?, ?, sha1(?), ? )";

        PreparedStatement prep = conn.prepareStatement(query);

        prep.setString(1, user.getNome());
        prep.setString(2, user.getEmail());
        prep.setString(3, user.getNascimento());
        prep.setString(4, user.getSenha());
        prep.setInt(5, (user.isNoticias()) ? 1 : 0);

        prep.execute();
        prep.close();

    }

    
    public void deleteUser(int id) throws SQLException{
        String query ="delete from usuarios where id = "+id;
        
        PreparedStatement prep = conn.prepareStatement(query);
        
        prep.execute();
        prep.close();
    }
}
