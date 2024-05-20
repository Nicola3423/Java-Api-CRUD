package org.example.repositories;


import org.example.infrastructure.OracleDbConfiguration;
import org.example.entites.Usuario;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UsuarioRepository {


    public static final String TB_NAME = "TB_USUARIO";


    public static List<Usuario> getAll(){
        var usuarios = new ArrayList<Usuario>();
        try(var conn = new OracleDbConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" ORDER BY ID_USUARIO")){
            var rs = stmt.executeQuery();
            while(rs.next()){
                usuarios.add(new Usuario(
                        rs.getString("NOME_COMPLETO"),
                        rs.getString("CARGO"),
                        rs.getString("EMAIL_CORPORATIVO"),
                        rs.getInt("CONTATO"),
                        rs.getString("EMPRESA"),
                        rs.getString("PAIS")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return usuarios;
    }


    /**
     * Método que retorna uma lista de produtos de acordo com os filtros e ordenação
     * @param orderBy Coluna para ordenação
     * @param direction Direção da ordenação
     * @param limit Quantidade de registros
     * @param offset Quantidade de registros a serem pulados
     * @return Lista de produtos
     */
    public List<Usuario> getAll(String NOME_COMPLETO, String CARGO,String EMAIL_CORPORATIVO, int CONTATO,String EMPRESA,String PAIS,String orderBy, String direction, int limit, int offset) {
        var usuarios = new ArrayList<Usuario>();
        try(var conn = new OracleDbConfiguration().getConnection();
            var stmt = conn.prepareStatement(
                    // Query para buscar os produtos
                    // O OFFSET é a quantidade de registros que serão pulados
                    // O FETCH NEXT é a quantidade de registros que serão retornados
                    // O ORDER BY é a coluna que será ordenada
                    // O ASC é a direção da ordenação
                    // O LIKE é um operador de comparação que busca registros que contenham o valor
                    // ex completo da query: SELECT * FROM PRODUTO WHERE NOME LIKE ? AND PRECO <= ? ORDER BY ID ASC OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY
                    // esta query busca produtos que contenham o nome passado no parâmetro nome e que tenham o preço menor ou igual ao preço passado no parâmetro preco
                    ("SELECT * FROM %s WHERE NOME_COMPLETO LIKE ? AND CARGO <= ? ORDER BY %s %s OFFSET %d " +
                            "ROWS FETCH NEXT %d ROWS ONLY")
                            .formatted(TB_NAME, orderBy, direction == null || direction.isEmpty() ? "ASC"
                                            : direction,
                                    offset, limit == 0 ? 10 : limit)
            )){
            stmt.setString(1, "%"+NOME_COMPLETO+"%");
            stmt.setString(2, "%"+CARGO+"%");
            stmt.setString(3, "%"+EMAIL_CORPORATIVO+"%");
            stmt.setDouble(4, CONTATO);
            stmt.setString(5, "%"+EMPRESA+"%");
            stmt.setString(6, "%"+PAIS+"%");
            var rs = stmt.executeQuery();
            while (rs.next()){
                usuarios.add(new Usuario(
                        rs.getString("NOME_COMPLETO"),
                        rs.getString("CARGO"),
                        rs.getString("EMAIL_CORPORATIVO"),
                        rs.getInt("CONTATO"),
                        rs.getString("EMPRESA"),
                        rs.getString("PAIS")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }


    public List<Usuario> getAllByCategoria(int ID_USUARIO){
        var usuarios = new ArrayList<Usuario>();
        try(var conn = new OracleDbConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_USUARIO = ?");){
            stmt.setInt(1, ID_USUARIO);
            var rs = stmt.executeQuery();
            while (rs.next()){
                usuarios.add(new Usuario(
                        rs.getString("NOME_COMPLETO"),
                        rs.getString("CARGO"),
                        rs.getString("EMAIL_CORPORATIVO"),
                        rs.getInt("CONTATO"),
                        rs.getString("EMPRESA"),
                        rs.getString("PAIS")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }


    public Optional<Usuario> get(int id){
        try(var conn = new OracleDbConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_USUARIO = ?")
        ){
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Usuario(
                        rs.getString("NOME_COMPLETO"),
                        rs.getString("CARGO"),
                        rs.getString("EMAIL_CORPORATIVO"),
                        rs.getInt("CONTATO"),
                        rs.getString("EMPRESA"),
                        rs.getString("PAIS")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return Optional.empty();
    }


    public void create(Usuario usuario){
        try(var conn = new OracleDbConfiguration().getConnection();
            var stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (NOME_COMPLETO, CARGO, EMAIL_CORPORATIVO, CONTATO, EMPRESA, PAIS) VALUES (?,?,?,?,?,?)")){
            stmt.setString(1, usuario.getNOME_COMPLETO());
            stmt.setString(2, usuario.getCARGO());
            stmt.setString(3, usuario.getEMAIL_CORPORATIVO());
            stmt.setInt(4, usuario.getCONTATO());
            stmt.setString(5, usuario.getEMPRESA());
            stmt.setString(6, usuario.getPAIS());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(int id, Usuario usuario){
        try(var conn = new OracleDbConfiguration().getConnection();
            var stmt = conn.prepareStatement("UPDATE "+ TB_NAME + " SET NOME_COMPLETO = ?, WHERE ID_USUARIO = ?");)
        {
            stmt.setString(1, usuario.getNOME_COMPLETO());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int count(String NOME_COMPLETO, String CARGO){
        try(var conn = new OracleDbConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT COUNT(*) FROM " +
                    TB_NAME + " WHERE NOME_COMPLETO LIKE ? AND CARGO <= ? ")){
            stmt.setString(1, "%"+NOME_COMPLETO+"%");
            stmt.setString(1, "%"+CARGO+"%");
            var result = stmt.executeQuery();
            if(result.next()){
                return result.getInt(1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }


    public void delete(int id){
        try(var conn = new OracleDbConfiguration().getConnection();
            var stmt = conn.prepareStatement("DELETE FROM "+ TB_NAME + " WHERE ID_USUARIO = ?");)
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}