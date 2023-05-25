
package Conexiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ProveedorDAO {
   //variable de conexion
     Connection con;
     //asignacion de la variable
    Conexion cn = new Conexion();
    //variable de sonulta
    PreparedStatement ps;
    //variable de datos de columna
    ResultSet rs;
    
        public boolean RegistrarProveedor(Proveedor pr){
        String sql = "INSERT INTO proveedor(ruc, nombre, telefono, direccion,razon) VALUES (?,?,?,?,?)";
        try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setInt(1, pr.getRuc());
           ps.setString(2, pr.getNombre());
           ps.setInt(3, pr.getTelefono());
           ps.setString(4, pr.getDireccion());
           ps.setString(5, pr.getRazon());
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
        
         public List ListarProveedor(){
             //array
        List<Proveedor> Listapr = new ArrayList();
        //comando sql
        String sql = "SELECT * FROM proveedor";
        try {
            //conexion
            con = cn.getConnection();
            //consul
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Proveedor pr = new Proveedor();
                pr.setId(rs.getInt("id"));
                pr.setRuc(rs.getInt("ruc"));
                pr.setNombre(rs.getString("nombre"));
                pr.setTelefono(rs.getInt("telefono"));
                pr.setDireccion(rs.getString("direccion"));
                 pr.setRazon(rs.getString("razon"));
                
                Listapr.add(pr);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Listapr;
    }
         
           public boolean EliminarProveedor(int id){
               //comando de sql
        String sql = "DELETE FROM proveedor WHERE id = ? ";
        //exception
        try {
            //conexion
            con = cn.getConnection();
            //consultas
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            //ejecutar el query
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                //cerrar conexion
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
           
           public boolean ModificarProveedor(Proveedor pr){
               //consulta de sql
        String sql = "UPDATE proveedor SET ruc=?, nombre=?, telefono=?, direccion=? ,razon =? WHERE id=?";
        //expetion
        try {
            //conecion
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getRuc());
            ps.setString(2, pr.getNombre());
            ps.setInt(3, pr.getTelefono());
            ps.setString(4, pr.getDireccion());
            ps.setString(5, pr.getRazon());
            ps.setInt(6, pr.getId());
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
