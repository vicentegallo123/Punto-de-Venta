
package Conexiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class ClienteDAO {
     Conexion cn = new Conexion();
 //variable de conexion
    Connection con;
    // variable para consultas de base de datos
    PreparedStatement ps;
    //obtener datos d ela columna
  ResultSet rs;
  
    public boolean  RegistrarCliente(Cliente cl){
        //se crea variable parala conuslra de base de datos 
    String sql = "INSERT INTO clientes (dni,nombre,telefono,direccion,razon)VALUES(?,?,?,?,?)";
    try{
        //se asigna variable y se muetra la conexion
    con =cn.getConnection();
    //se asigna variable ala conexion para las cunsultas y adentro de parametos de la base de datos
    ps =con.prepareStatement(sql);
    //eviar paramatros
    ps.setInt(1,cl.getDni());
    ps.setString(2, cl.getNombre());
     ps.setInt(3,cl.getTelefono());
     ps.setString(4, cl.getDireccion());
     ps.setString(5, cl.getRazon());
     //se utiliza para consultas yipos int values delete
     ps.execute();
     
 return true;
    }catch(Exception e){
        
    JOptionPane.showMessageDialog(null, e.toString());
    
    return false;
    }finally{
    try{
        //cierra conexion
    con.close();
    }catch(SQLException e){
        System.out.println(e.toString());
      }
        }
    
            }
    
      public List ListarCliente(){
          //se crea arraylist
       List<Cliente> ListaCl = new ArrayList();
       //se crea consulta sql
       String sql = "SELECT * FROM clientes";
       try {
           //con
           con = cn.getConnection();
           //consulta
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           //con el bucle recorremos
           while (rs.next()) {               
               Cliente cl = new Cliente();
               cl.setId(rs.getInt("id"));
               cl.setDni(rs.getInt("dni"));
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getInt("telefono"));
               cl.setDireccion(rs.getString("direccion"));
               cl.setRazon(rs.getString("razon"));
               ListaCl.add(cl);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaCl;
   }
   
   public boolean EliminarCliente(int id){
       String sql = "DELETE FROM clientes WHERE id = ?";
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
   
   public boolean ModificarCliente(Cliente cl){
       //se crea cosulta
       String sql = "UPDATE clientes SET dni=?, nombre=?, telefono=?, direccion=?,razon =? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);   
           ps.setInt(1, cl.getDni());
           ps.setString(2, cl.getNombre());
           ps.setInt(3, cl.getTelefono());
           ps.setString(4, cl.getDireccion());
           ps.setString(5, cl.getRazon());
            ps.setInt(6, cl.getId());
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
   
   public Cliente Buscarcliente(int dni){
       //intanciar clase
       Cliente cl = new Cliente();
       //realizar consulta
       String sql = "SELECT * FROM clientes WHERE dni = ?";
       try {
           //conecion base d e datos
           con = cn.getConnection();
           //preparacion para realizar consultas
           
           
           ps = con.prepareStatement(sql);
           ps.setInt(1, dni);
           rs = ps.executeQuery();
           if (rs.next()) {
               cl.setId(rs.getInt("id"));
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getInt("telefono"));
               cl.setDireccion(rs.getString("direccion"));
               cl.setRazon(rs.getString("razon"));
               
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return cl;
   }
   
}

  
               

