
package Conexiones;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {
    //variable de conexion
    Connection con;
    // variable para consultas de base de datos
    PreparedStatement ps;
    //obtener datos d ela columna
    ResultSet rs;
    Conexion cn = new Conexion();
    //se crea funcion
   
    
    public login log(String correo, String pass){
        //se inicia el  objeto
        login l = new login();
        //se hace elcomando
        String sql = "SELECT * FROM usuario WHERE correo =  ? AND pass = ?";
        //se hace expetion de conexion
        try {
            con = cn.getConnection();
            //preparando plantillas de consul
            ps = con.prepareStatement(sql);
            //añadiendo los campos de consulta
            ps.setString(1, correo);
            ps.setString(2, pass);
            rs= ps.executeQuery();
            if (rs.next()) {
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setPass(rs.getString("pass"));
                l.setRol(rs.getString("rol"));
               
                
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }
    
    public boolean Registrar(login reg){
        String sql = "INSERT INTO usuario (nombre, correo, pass, rol) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getCorreo());
            ps.setString(3, reg.getPass());
            ps.setString(4, reg.getRol());
           
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List ListarUsuarios(){
       List<login> Lista = new ArrayList();
       String sql = "SELECT * FROM usuario";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               login lg = new login();
               lg.setId(rs.getInt("id"));
               lg.setNombre(rs.getString("nombre"));
               lg.setCorreo(rs.getString("correo"));
               lg.setPass(rs.getString("pass"));
               lg.setRol(rs.getString("rol"));
       
               Lista.add(lg);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Lista;
   }
    
    //metodo para eliminar producto
        public boolean EliminarUsuario(int id){
       String sql = "DELETE FROM usuario WHERE id = ?";
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
        //modificar usuario
           public boolean ModificarUsuario(login lo){
               
               
       String sql = "UPDATE usuario SET nombre=?, correo=?,pass=?,rol=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, lo.getNombre());
           ps.setString(2, lo.getCorreo());
            ps.setString(3, lo.getPass());
           ps.setString(4, lo.getRol());
           ps.setInt(5, lo.getId());
           
           
           
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
   }
}
