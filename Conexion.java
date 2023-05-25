package Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion{
//se crea las variables para el nombre usuario y pass
    private String database_name ="sistemaventa";
    private String user ="root";
    private String password = "123456";
    //se asigna el nombre y puerto
    private String url ="jdbc:mysql://localhost:3306/"+database_name;
    
     Connection con = null;
     
     public  Connection getConnection(){
     try{
     //obtener valor del driver
     Class.forName("com.mysql.cj.jdbc.Driver");
     //obtener la conexion
     
     con = DriverManager.getConnection(url,user,password);
     }catch(ClassNotFoundException e){
         System.err.println("ha ocurrido una exception"+e.getMessage());
     }catch(SQLException e){
     System.err.println("ha ocurrido un sqlexception"+e.getMessage());
     }
     //retorno de la conexion
     return con;
         
     }

}