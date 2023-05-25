
package Interfaces;

import Conexiones.Cliente;
import Conexiones.ClienteDAO;
import Conexiones.Config;
import Conexiones.Consultas;
import Conexiones.LoginDAO;
import Conexiones.Producto;
import Conexiones.ProductoDAO;
import Conexiones.Proveedor;
import Conexiones.ProveedorDAO;
import Conexiones.Venta;
import Conexiones.VentaDAO;
import Conexiones.login;
import Reportes.Excell;
import Reportes.Grafico;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import java.awt.Desktop;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System.Logger.Level;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;



public class Interfaz extends javax.swing.JFrame {

   //metodo para las modificaciones de tablas
     DefaultTableModel modelo = new  DefaultTableModel();
     DefaultTableModel tmp = new  DefaultTableModel();
      //instanciar conexiones de clases
     LoginDAO lg = new LoginDAO();
     login ln = new login ();
    Cliente cl = new Cliente();
     ClienteDAO clinet = new ClienteDAO();
     Proveedor pr = new Proveedor();
     ProveedorDAO prDAO = new ProveedorDAO();
     Producto pro = new Producto();
     ProductoDAO proDAO = new ProductoDAO();
     Venta v = new Venta();
     VentaDAO vDAO = new VentaDAO();
     Consultas Dv = new Consultas();
     Config conf = new Config ();
     //variables para funcion total pagar
     int item;
     double TotalPagar = 0.0;
     //istancias fecha
     Date fechaVenta = new Date();
     //crear variable de fecha isntanciar formato de clase
     String fechaActual = new SimpleDateFormat("dd/MM/yyy").format(fechaVenta);
     
     

     
   
  
    public Interfaz() {
        initComponents();
       proDAO.ConsultarProveedor(cbxProveedorPro);
 this.setLocationRelativeTo(null);
        txtIdCliente.setVisible(false);
         txtIDVenta.setVisible(false);
         txtIDProveVenta.setVisible(false);
         txtIDProducto.setVisible(false);
         txtIDProve.setVisible(false);
          txtIdConf.setVisible(false);
          txtIdUsuario.setVisible(false);
        //ppasar el metodo al campo jbox
        proDAO.ConsultarProveedor(cbxProveedorPro);
        //libreria para pasar cam´pos
        AutoCompleteDecorator.decorate(cbxProveedorPro);
        //metodo para listar confi
        ListarConfi();
    }
    //sobrecarga de metodos
    public Interfaz (login priv){
         initComponents();
        this.setLocationRelativeTo(null);
        txtIdCliente.setVisible(false);
         txtIDVenta.setVisible(false);
         txtIDProveVenta.setVisible(false);
         txtIDProducto.setVisible(false);
         txtIDProve.setVisible(false);
          txtIdConf.setVisible(false);
           txtIdUsuario.setVisible(false);
        //ppasar el metodo al campo jbox
        proDAO.ConsultarProveedor(cbxProveedorPro);
        //libreria para pasar cam´pos
        AutoCompleteDecorator.decorate(cbxProveedorPro);
        //metodo para listar confi
        ListarConfi();
        
        
   //roles de usuarios
        if(priv.getRol().equals("Asistente")){
            //desabilitar botones
    jButtonProveedores.setEnabled(false);
   // btnProducto.setEnabled(false);
    btnRazon.setEnabled(false);
    LabelVendedor.setText(priv.getNombre());
    
     }else{
        LabelVendedor.setText(priv.getNombre());
        }
    }
    
        //se crea metodo para lista usuarios
    public void ListarUsuario(){
        //se crea array y se enlaza con la clase y metodo
     List<login> ListarUser = lg.ListarUsuarios();
     //se implementa la table model para almacenar los datos en la tabla
     modelo = (DefaultTableModel) tablaUser.getModel();
     //arreglo con el numero de campos
     Object[] ob = new Object[5];
     //se crea for para pasas por las listas
     for (int i=0 ;i<ListarUser.size();i++){
         ob[0] = ListarUser.get(i).getId();
         ob[1] = ListarUser.get(i).getNombre();
         ob[2] = ListarUser.get(i).getCorreo();
         ob[3] = ListarUser.get(i).getPass();
         ob[4] = ListarUser.get(i).getRol();
         
         //pasas al metodo addrow para el arreglo
         modelo.addRow(ob);
     }
     //pasas los datos a la tabla
     tablaUser.setModel(modelo);
}
    //se crea metodo para lista provve
    public void ListarProveedor(){
        //se crea array y se enlaza con la clase y metodo
     List<Proveedor> ListarPr = prDAO.ListarProveedor();
     //se implementa la table model para almacenar los datos en la tabla
     modelo = (DefaultTableModel) tablaProv.getModel();
     //arreglo con el numero de campos
     Object[] ob = new Object[6];
     //se crea for para pasas por las listas
     for (int i=0 ;i<ListarPr.size();i++){
         ob[0] = ListarPr.get(i).getId();
         ob[1] = ListarPr.get(i).getRuc();
         ob[2] = ListarPr.get(i).getNombre();
         ob[3] = ListarPr.get(i).getTelefono();
         ob[4] = ListarPr.get(i).getDireccion();
         ob[5] = ListarPr.get(i).getRazon();
         //pasas al metodo addrow para el arreglo
         modelo.addRow(ob);
     }
     //pasas los datos a la tabla
     tablaProv.setModel(modelo);
}
    //listar producto
        public void ListarProducto(){
        //se crea array y se enlaza
     List<Producto> ListarPro = proDAO.ListarProductos();
     //se implementa la table model para almacenar los datos en la tabla
     modelo = (DefaultTableModel) tablaProducto.getModel();
     //arreglo con clase obket qeu permite almacenar cualquir tipo de objeto
     Object[] ob = new Object[6];
     //se crea for que evalua la iteraciond¿ con el size al tamaño de las litas listas
     for (int i=0 ;i<ListarPro.size();i++){
         ob[0] = ListarPro.get(i).getId();
         ob[1] = ListarPro.get(i).getCodigo();
         ob[2] = ListarPro.get(i).getNombre();
         ob[3] = ListarPro.get(i).getStock();
         ob[4] = ListarPro.get(i).getPrecio();
         ob[5] = ListarPro.get(i).getProveedor();
         //pasas al metodo addrow para el arreglo a una nueva fila
         modelo.addRow(ob);
     }
     //muestras los datos a la tabla
     tablaProducto.setModel(modelo);
}
        //meoto para listar confi
            public void ListarConfi(){
    //asignacion de metodo
    conf =proDAO.BuscarDatos();
    txtIdConf.setText(""+conf.getId());
     txtRucConfi.setText(""+conf.getRuc());
      txtNomConfi.setText(""+conf.getNombre());
       txtTelConfi.setText(""+conf.getTelefono());
        txtDireConfi.setText(""+conf.getDireccion());
         txtRazonConfi.setText(""+conf.getRazon());
}
        //listar venta
        public void ListarVenta(){
        //se crea array y se enlaza
     List<Venta> ListarVenta = vDAO.ListarVentas();
     //se implementa la table model para almacenar los datos en la tabla
     modelo = (DefaultTableModel) tablaVenta.getModel();
     //arreglo con clase obket qeu permite almacenar cualquir tipo de objeto
     Object[] ob = new Object[4];
     //se crea for que evalua la iteraciond¿ con el size al tamaño de las litas listas
     for (int i=0 ;i<ListarVenta.size();i++){
         ob[0] = ListarVenta.get(i).getId();
         ob[1] = ListarVenta.get(i).getCliente();
         ob[2] = ListarVenta.get(i).getVendedor();
         ob[3] = ListarVenta.get(i).getTotal();
       
         //pasas al metodo addrow para el arreglo a una nueva fila
         modelo.addRow(ob);
     }
     //muestras los datos a la tabla
     tablaVenta.setModel(modelo);
}
  
    //se crea metodo para  el boton de cliente
    public void ListarCliente(){
        //se crea array y se enlaza
     List<Cliente> ListarCl =clinet.ListarCliente();
     //
     modelo = (DefaultTableModel) TablaCliente.getModel();
     //arreglo con el numero de campos
     Object[] ob = new Object[6];
     //se crea for para pasas por las listas
     for (int i=0 ;i<ListarCl.size();i++){
         ob[0] = ListarCl.get(i).getId();
         ob[1] = ListarCl.get(i).getDni();
         ob[2] = ListarCl.get(i).getNombre();
         ob[3] = ListarCl.get(i).getTelefono();
         ob[4] = ListarCl.get(i).getDireccion();
         ob[5] = ListarCl.get(i).getRazon();
         //pasas al metodo addrow para el arreglo
         modelo.addRow(ob);
     }
     //muestras los datos a la tabla
     TablaCliente.setModel(modelo);
}
    //metodo para limpiac campos de cliente
       private void LimpiarCliente() {
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtNombreCliente.setText("");
        txtTelCliente.setText("");
        txtDireccionCliente.setText("");
        txtRazonCliente.setText("");
        
    }
          //metodo para limpiac campos de cliente
       private void LimpiarUser() {
        txtIdUsuario.setText("");
        txtNomUsuario.setText("");
        txtCorreoUsuario.setText("");
        txtPassUser.setText("");
        txtRolUsuario.setText("");
        
        
    }
    //metodopara  limpiar la tabla 
    public void LimpiarTabla(){
        //devi¿uleve el numero de filas a la tabla
     for (int i=0 ;i<modelo.getRowCount();i++){
     modelo.removeRow(i);
         i = i-1;
     }
    }
      //se crea metodo par< mipiar proveedor tabla
       private void LimpiarProve() {
        txtRucProv.setText("");
        txtIDProve.setText("");
        txtNombreProv.setText("");
        txtTelefonoProv.setText("");
        txtDireccionProv.setText("");
        txtRazonProv.setText("");
        
    }
         //se crea metodo par< mipiar producto tabla
       private void LimpiarProducto() {
        txtIDProducto.setText("");
        txtCodigoProducto.setText("");
        txtDescriProducto.setText("");
        txtCantidadProducto.setText("");
        txtPrecioProducto.setText("");
        cbxProveedorPro.setSelectedItem(null);
        
    }
       //metodo para el total a pagar de la tabla clientes
       private void TotalPagar(){
           //se crea variable y se inicia en 0
       TotalPagar = 0.00;
       //se hace conteo del numero de filas
       //el metodo getrou count devulevte el numero de fi
        int numFila = jTableVenta.getRowCount();
        //se recorre con un for
        for (int i = 0; i < numFila; i++) {
            //El método valueOf de String devuelve el valor primitivo de un objeto String como un tipo de dato cadena
            double cal = Double.parseDouble(String.valueOf(jTableVenta.getModel().getValueAt(i, 4)));
            TotalPagar = TotalPagar + cal;
        }
        //se agrega a la variable label se muestra 
        labelTotalVenta.setText(String.format("%.2f", TotalPagar));
       }
       //metodo para limpiar venta
        private void LimpiarVenta(){
         txtCodigoVenta.setText("");
         txtDescripcionVenta.setText("");
         txtCantidadVenta.setText("");
         txtCodigoVenta.setText("");
        txtPrecioVenta.setText("");
        txtStockVenta.setText("");
        txtIDVenta.setText("");
       }
        //metodo para limpiar datos de cliente en la tabla venta
            private void LimpiarDatosRuc(){
        txtDniVenta.setText("");
        txtNombreVenta.setText("");
        txtTelVentas.setText("");
        txtDireccionVenta.setText("");
        txtRazonVenta.setText("");
        
       }
        //metodo para regirtar venta
         private void RegistrarVenta() {
             //catchin de variables y componentes
        String cliente = txtNombreVenta.getText();
        String vendedor = LabelVendedor.getText();
        double monto = TotalPagar;
        //mostar 
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual);
       
        vDAO.RegistrarVenta(v);
    }
         //metodo para registar el detalle de las ventas en la tabla clientes
           private void RegistrarDetalle() {
               //asignas el n id con la conexion de cla calse venta
       int id = vDAO.IdVenta();
       //mueves en un for la tabla selecciona 
        for (int i = 0; i < jTableVenta.getRowCount(); i++) {
            //asignas tipos de variables y la posicion que quiers mostrar de la tabla
            String cod_pro = jTableVenta.getValueAt(i, 0).toString();
            int cant = Integer.parseInt(jTableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(jTableVenta.getValueAt(i, 4).toString());
            //istancia de la clase consultas y muestra de los campos encapsulados
            Dv.setCod_pro(cod_pro);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            //istanciar la clase ventadao con el metodo de registrar detalle
            vDAO.RegistrarDetalle(Dv);

        }
      
    }
           
           //metodo para actualizar producto
             private void ActualizarStock() {
                 //recorrer con for la tabla venta
        for (int i = 0; i < jTableVenta.getRowCount(); i++) {
            //seleccion de tipo string el espacio de la tabla 0 de cod
            String cod = jTableVenta.getValueAt(i, 0).toString();
            int cant = Integer.parseInt(jTableVenta.getValueAt(i, 2).toString());
            //para actualizar codigo isntanciamos la clase producto dao
            pro = proDAO.BuscarPro(cod);
            //declaramos una nueva variable inicializamos el stock menos la camntidad
            int StockActual = pro.getStock() - cant;
            vDAO.ActualizarStock(StockActual, cod);

        }
    }
             //metodo para limpiar tabla venta
              private void LimpiarTablaVenta() {
                  // la asigna con una referencia al modelo de tabla utilizado por el componente JTable llamado "jTableVenta
        tmp = (DefaultTableModel) jTableVenta.getModel();
        // obtiene el número de filas actualmente presentes en un objeto  y lo asigna a una variable de tipo entero 
        int fila = jTableVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
       public void pdf (){
       try{
           //consultar id de venta por base de datos
           int id = vDAO.IdVenta();
           //ad libreria parentaa guardar archivos
           FileOutputStream archivo;
           //instanciar clase
           File file = new File("src/pdf/venta"+id+".pdf");
           //donde s eva a gurdar y en que ruta
           archivo = new FileOutputStream(file);
           //crear documento
           Document doc = new Document();
           //Constructor en el cual se le pasa el documento el cual se va a escribir (document) y el path de salida del pdf generado
           PdfWriter.getInstance(doc, archivo);
           //abrir documento
           doc.open();
           //agregra imagen e instanciar clase con la ruta
           Image img = Image.getInstance("src/Img/Img/logo.png");
           //Itancias la clase para añadir parrafors
           Paragraph fecha = new Paragraph ();
           //tipo de letra clase tamaño y clor
           Font negra = new Font (Font.FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.WHITE);
           //add fecha y clase
           fecha.add(Chunk.NEWLINE);
           //importar fecha
           Date date = new Date ();
           //formato de fecha
           fecha.add("Factura:"+id+"\n"+"Fecha:"+new SimpleDateFormat("dd-mm-yyyy").format(date)+"\n\n");
           //instancias clase con el numero de columnas de tabla
           PdfPTable encabezado = new PdfPTable(4);
           //porcentaje
           encabezado.setWidthPercentage(100);
           //bordes
           encabezado.getDefaultCell().setBorder(0);
           //tamaño de celdas definido con un arreglo
           float[] ColumnaEncabezado = new float[]{20f,30f,70f,40f};
           //pasar los datos
           encabezado.setWidths(ColumnaEncabezado);
           // del encabezado y alinazion
           encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
           
           //añadir imahen
           encabezado.addCell(img);
       //añadir datos de la base d ed atos
           String ruc =  txtRucConfi.getText();
           String nombre =  txtNomConfi.getText();
           String  tel =  txtTelConfi.getText();
           String dir =  txtDireConfi.getText();
           String razon =  txtRazonConfi.getText();
           
           //agregar al encabezado
           
          encabezado.addCell("");
          encabezado.addCell("Ruc:"+ruc+"\nNombre :"+nombre+"\nTelefono:"+tel+"\nDireccion"+"\nRazon Social :"+razon);
          //agregar fecha
          encabezado.addCell(fecha);
          
          //agregrar todas las celdas al codumento
          doc.add(encabezado);
          //isntanciar clase para añadir parrafo
          Paragraph cli = new Paragraph ();
          //agregar una nueva linea en cliente
          cli.add(Chunk.NEWLINE);
          //agregra datos de clientes en la linea
          cli.add("Datos del Cliente"+"\n\n");
          //agregar al documento
          doc.add(cli);
          
          //agregar nueva tabala para datos del cliente con el tamaño de ñas columnas
         PdfPTable tablacli = new  PdfPTable(4);
         //porcentaje
           tablacli.setWidthPercentage(100);
          tablacli.getDefaultCell().setBorder(0);
           //tamaño de celdas definido con un arreglo
           float[] Columnacli = new float[]{20f,50f,30f,40f};
           //pasar los datos
           tablacli.setWidths(Columnacli);
           // del encabezado y alinazion
           tablacli.setHorizontalAlignment(Element.ALIGN_LEFT);
           //agregar titulos en celdas y importar la clase
           PdfPCell cl1 = new PdfPCell (new Phrase("Dni/Ruc",negra));
           //agregar los demas datos a las celdas
           PdfPCell cl2 = new PdfPCell (new Phrase("Nombre",negra));
           PdfPCell cl3 = new PdfPCell (new Phrase("Télefono",negra));
           PdfPCell cl4 = new PdfPCell (new Phrase("Dirección",negra));
           //qiotar los bordes en cada una de las celdas
           cl1.setBorder(0);
           cl2.setBorder(0);
           cl3.setBorder(0);
           cl4.setBorder(0);
           //agregar celda a la tabla
           tablacli.addCell(cl1);
           tablacli.addCell(cl2);
           tablacli.addCell(cl3);
           tablacli.addCell(cl4);
           //agregar los valores a la celda de los componentes
           tablacli.addCell(txtDniVenta.getText());
           tablacli.addCell(txtNombreVenta.getText());
           tablacli.addCell(txtTelVentas.getText());
           tablacli.addCell(txtDireccionVenta.getText());
           //agregar al documento
           doc.add(tablacli);
           //agregar los productos
            //agregar nueva tabala para datos del producto con el tamaño de ñas columnas
         PdfPTable tablapro = new  PdfPTable(4);
         //porcentaje
           tablapro.setWidthPercentage(100);
          tablapro.getDefaultCell().setBorder(0);
           //tamaño de celdas definido con un arreglo
           float[] Columnapro = new float[]{10f,30f,15f,20f};
           //pasar los datos
           tablapro.setWidths(Columnapro);
           // del encabezado y alinazion
           tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
           //agregar titulos en celdas y importar la clase
           PdfPCell pro1 = new PdfPCell (new Phrase("Cantidad",negra));
           //agregar los demas datos a las celdas
           PdfPCell pro2 = new PdfPCell (new Phrase("Descripción",negra));
           PdfPCell pro3 = new PdfPCell (new Phrase("Precio Unitario",negra));
           PdfPCell pro4 = new PdfPCell (new Phrase("Precio total",negra));
           //qiotar los bordes en cada una de las celdas
           pro1.setBorder(0);
           pro2.setBorder(0);
           pro3.setBorder(0);
           pro4.setBorder(0);
           //cambair el color
           pro1.setBackgroundColor(BaseColor.DARK_GRAY);
           pro2.setBackgroundColor(BaseColor.DARK_GRAY);
           pro3.setBackgroundColor(BaseColor.DARK_GRAY);
           pro4.setBackgroundColor(BaseColor.DARK_GRAY);
           //agregar celda a la tabla
           tablapro.addCell(pro1);
           tablapro.addCell(pro2);
           tablapro.addCell(pro3);
           tablapro.addCell(pro4);
           //para recorrer toda la tabla de la venta
           for(int i =0;i<jTableVenta.getRowCount();i++){
           String cantidad =jTableVenta.getValueAt(i, 2).toString();
           String producto =jTableVenta.getValueAt(i, 1).toString();
           String precio =jTableVenta.getValueAt(i, 3).toString();
           String total =jTableVenta.getValueAt(i, 4).toString();
           //agregar los valores a la celda de los componentes
           tablapro.addCell(cantidad);
           tablapro.addCell(producto);
           tablapro.addCell(precio);
           tablapro.addCell(total);
           }
           
           doc.add(tablapro);
           //nuevo parrafo con la informacon
           Paragraph info = new Paragraph ();
           //agregar nueva linea
           info.add(Chunk.NEWLINE);
           //agregar titulo
           info.add("Total a Pagar"+"\n"+TotalPagar);
           //posicionar hacia la derecha
           info.setAlignment(Element.ALIGN_RIGHT);
           //agregar al documento
           doc.add(info);
            //nuevo parrafo firma
           Paragraph firma = new Paragraph ();
           //agregar nueva linea
           firma.add(Chunk.NEWLINE);
           //agregar titulo
           firma.add("Cancelación y Firma\n\n");
           firma.add("_________________________");
           //posicionar hacia la derecha
           firma.setAlignment(Element.ALIGN_CENTER);
           //agregar al documento
           doc.add(firma);
            //agergar parrafo para mensaje
        Paragraph msj = new Paragraph ();
           //agregar nueva linea
           msj.add(Chunk.NEWLINE);
           //agregar titulo
           msj.add("Gracias Por su Compra");
           //posicionar hacia la derecha
           msj.setAlignment(Element.ALIGN_CENTER);
           
           //agregar al doc
           doc.add(msj);
           //cerrar documento
           doc.close();
           archivo.close();
           //lamar meotdo destok para guardar ruta y abir
           Desktop.getDesktop().open(file);
           
       //importar librerias y hacer la exception
       }catch (DocumentException | IOException e ){
           System.out.println(e.toString());
       }
       }
 


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonNuevaVenta = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButtonClientes = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButtonProveedores = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnProducto = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButtonVentas = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnRazon = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockVenta = new javax.swing.JTextField();
        botonBorrarVenta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableVenta = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDniVenta = new javax.swing.JTextField();
        txtNombreVenta = new javax.swing.JTextField();
        botonImprimirVenta = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        labelTotalVenta = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtTelVentas = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtDireccionVenta = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtRazonVenta = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtIDProveVenta = new javax.swing.JTextField();
        btnGrafica = new javax.swing.JButton();
        miDate = new com.toedter.calendar.JDateChooser();
        jLabel47 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        txtRazonCliente = new javax.swing.JTextField();
        botonGuardarCliente = new javax.swing.JButton();
        btnActualizarCliente = new javax.swing.JButton();
        btnBorrarCliente = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        TablaCliente = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProv = new javax.swing.JTable();
        txtRucProv = new javax.swing.JTextField();
        txtNombreProv = new javax.swing.JTextField();
        txtTelefonoProv = new javax.swing.JTextField();
        txtDireccionProv = new javax.swing.JTextField();
        txtRazonProv = new javax.swing.JTextField();
        btnGuardarProv = new javax.swing.JButton();
        btnActuProv = new javax.swing.JButton();
        btnBorrarProv = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        txtIDProve = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtCodigoProducto = new javax.swing.JTextField();
        txtDescriProducto = new javax.swing.JTextField();
        txtCantidadProducto = new javax.swing.JTextField();
        txtPrecioProducto = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaProducto = new javax.swing.JTable();
        btnGuardarProducto = new javax.swing.JButton();
        btnActualizarProducto = new javax.swing.JButton();
        btnEcxellProducto = new javax.swing.JButton();
        btnBorrarProducto = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        txtIDProducto = new javax.swing.JTextField();
        cbxProveedorPro = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        btnPdfVenta = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        txtIDVenta = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtRucConfi = new javax.swing.JTextField();
        txtNomConfi = new javax.swing.JTextField();
        txtTelConfi = new javax.swing.JTextField();
        txtDireConfi = new javax.swing.JTextField();
        txtRazonConfi = new javax.swing.JTextField();
        btnGuardarConfi = new javax.swing.JButton();
        txtIdConf = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaUser = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txtIdUsuario = new javax.swing.JTextField();
        txtNomUsuario = new javax.swing.JTextField();
        txtCorreoUsuario = new javax.swing.JTextField();
        txtRolUsuario = new javax.swing.JTextField();
        btnGuardarUser = new javax.swing.JButton();
        btnActuUser = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        txtPassUser = new javax.swing.JTextField();
        LabelVendedor = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 1060, 20));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 1060, 20));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 1060, 20));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 1060, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/logo refa.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 480, 70));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Configuración");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 130, 20));

        jButtonNuevaVenta.setBackground(new java.awt.Color(255, 255, 255));
        jButtonNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/nueva_venta-removebg-preview.png"))); // NOI18N
        jButtonNuevaVenta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevaVentaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonNuevaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 130, 40));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nueva Venta");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 130, 20));

        jButtonClientes.setBackground(new java.awt.Color(255, 255, 255));
        jButtonClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/clientes.png"))); // NOI18N
        jButtonClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClientesActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 130, 40));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Clientes");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 130, 20));

        jButtonProveedores.setBackground(new java.awt.Color(255, 255, 255));
        jButtonProveedores.setForeground(new java.awt.Color(255, 255, 255));
        jButtonProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/repartidor.png"))); // NOI18N
        jButtonProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProveedoresActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 130, 40));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Proveedores");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 130, 20));

        btnProducto.setBackground(new java.awt.Color(255, 255, 255));
        btnProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/agregar-producto.png"))); // NOI18N
        btnProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoActionPerformed(evt);
            }
        });
        jPanel1.add(btnProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 130, 40));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Productos");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 130, 20));

        jButtonVentas.setBackground(new java.awt.Color(255, 255, 255));
        jButtonVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/negocios-y-finanzas.png"))); // NOI18N
        jButtonVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVentasActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 130, 40));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Ventas");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 130, 20));

        btnRazon.setBackground(new java.awt.Color(255, 255, 255));
        btnRazon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/configuraciones.png"))); // NOI18N
        btnRazon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRazonActionPerformed(evt);
            }
        });
        jPanel1.add(btnRazon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 130, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/logo2-removebg-preview.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 90));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(204, 204, 204));
        jLabel9.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Código");

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Descripción");

        jLabel11.setBackground(new java.awt.Color(204, 204, 204));
        jLabel11.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Cantidad");

        jLabel12.setBackground(new java.awt.Color(204, 204, 204));
        jLabel12.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Precio");

        jLabel13.setBackground(new java.awt.Color(204, 204, 204));
        jLabel13.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Stock");

        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });

        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyTyped(evt);
            }
        });

        txtStockVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockVentaActionPerformed(evt);
            }
        });
        txtStockVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockVentaKeyTyped(evt);
            }
        });

        botonBorrarVenta.setBackground(new java.awt.Color(204, 204, 204));
        botonBorrarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/borrar.png"))); // NOI18N
        botonBorrarVenta.setBorder(null);
        botonBorrarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBorrarVentaActionPerformed(evt);
            }
        });

        jTableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO", "PRECIO TOTAL"
            }
        ));
        jScrollPane1.setViewportView(jTableVenta);
        if (jTableVenta.getColumnModel().getColumnCount() > 0) {
            jTableVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTableVenta.getColumnModel().getColumn(2).setPreferredWidth(40);
            jTableVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            jTableVenta.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("DNI");

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Nombre");

        txtDniVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDniVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniVentaKeyTyped(evt);
            }
        });

        txtNombreVenta.setEditable(false);

        botonImprimirVenta.setBackground(new java.awt.Color(204, 204, 204));
        botonImprimirVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/imrpirmir.png"))); // NOI18N
        botonImprimirVenta.setBorder(null);
        botonImprimirVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonImprimirVentaActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Total Pagar");

        labelTotalVenta.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        labelTotalVenta.setForeground(new java.awt.Color(0, 0, 0));
        labelTotalVenta.setText("........");

        jLabel17.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Telefono");

        txtTelVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelVentasKeyTyped(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("Direccion");

        jLabel40.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Razon Social");

        jLabel45.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("ID");

        btnGrafica.setBackground(new java.awt.Color(204, 204, 204));
        btnGrafica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/grafico-removebg-preview.png"))); // NOI18N
        btnGrafica.setBorder(null);
        btnGrafica.setPreferredSize(new java.awt.Dimension(60, 49));
        btnGrafica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficaActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Seleccionar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDniVenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtDireccionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(txtRazonVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel16)))
                        .addGap(42, 42, 42)
                        .addComponent(labelTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(btnGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(miDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIDProveVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(botonImprimirVenta)
                        .addGap(18, 18, 18)
                        .addComponent(botonBorrarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13)
                                .addComponent(jLabel45))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(botonImprimirVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonBorrarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(txtIDProveVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel47)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(miDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDniVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTotalVenta)
                    .addComponent(jLabel16)
                    .addComponent(txtTelVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nueva Venta", jPanel3);

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setForeground(new java.awt.Color(204, 204, 204));

        jLabel18.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("DNI:");

        jLabel19.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Nombre:");

        jLabel20.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Telefono:");

        jLabel21.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Dirección:");

        jLabel22.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Razon Social:");

        txtDniCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniClienteActionPerformed(evt);
            }
        });
        txtDniCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniClienteKeyTyped(evt);
            }
        });

        txtTelCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelClienteKeyTyped(evt);
            }
        });

        botonGuardarCliente.setBackground(new java.awt.Color(204, 204, 204));
        botonGuardarCliente.setForeground(new java.awt.Color(204, 204, 204));
        botonGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/salvar.png"))); // NOI18N
        botonGuardarCliente.setBorder(null);
        botonGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarClienteActionPerformed(evt);
            }
        });

        btnActualizarCliente.setBackground(new java.awt.Color(204, 204, 204));
        btnActualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/actualizar-flecha.png"))); // NOI18N
        btnActualizarCliente.setBorder(null);
        btnActualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarClienteActionPerformed(evt);
            }
        });

        btnBorrarCliente.setBackground(new java.awt.Color(204, 204, 204));
        btnBorrarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/borrar.png"))); // NOI18N
        btnBorrarCliente.setBorder(null);
        btnBorrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarClienteActionPerformed(evt);
            }
        });

        txtIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteActionPerformed(evt);
            }
        });

        TablaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        TablaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaClienteMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TablaCliente);
        if (TablaCliente.getColumnModel().getColumnCount() > 0) {
            TablaCliente.getColumnModel().getColumn(0).setPreferredWidth(60);
            TablaCliente.getColumnModel().getColumn(1).setPreferredWidth(60);
            TablaCliente.getColumnModel().getColumn(2).setPreferredWidth(200);
            TablaCliente.getColumnModel().getColumn(3).setPreferredWidth(150);
            TablaCliente.getColumnModel().getColumn(4).setPreferredWidth(250);
            TablaCliente.getColumnModel().getColumn(5).setPreferredWidth(250);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRazonCliente))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelCliente)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addComponent(txtNombreCliente))))
                .addGap(29, 29, 29)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(botonGuardarCliente)
                        .addGap(73, 73, 73)
                        .addComponent(btnActualizarCliente)
                        .addGap(63, 63, 63)
                        .addComponent(btnBorrarCliente))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtTelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtRazonCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonGuardarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clientes", jPanel9);

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setForeground(new java.awt.Color(0, 0, 0));

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Ruc:");

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Nombre:");

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Telefono:");

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Direccion:");

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Razon Social:");

        tablaProv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        tablaProv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProvMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaProv);
        if (tablaProv.getColumnModel().getColumnCount() > 0) {
            tablaProv.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaProv.getColumnModel().getColumn(1).setPreferredWidth(30);
            tablaProv.getColumnModel().getColumn(2).setPreferredWidth(60);
            tablaProv.getColumnModel().getColumn(3).setPreferredWidth(60);
            tablaProv.getColumnModel().getColumn(4).setPreferredWidth(100);
            tablaProv.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        txtTelefonoProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProvKeyTyped(evt);
            }
        });

        btnGuardarProv.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/salvar.png"))); // NOI18N
        btnGuardarProv.setBorder(null);
        btnGuardarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProvActionPerformed(evt);
            }
        });

        btnActuProv.setBackground(new java.awt.Color(204, 204, 204));
        btnActuProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/actualizar-flecha.png"))); // NOI18N
        btnActuProv.setBorder(null);
        btnActuProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActuProvActionPerformed(evt);
            }
        });

        btnBorrarProv.setBackground(new java.awt.Color(204, 204, 204));
        btnBorrarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/borrar.png"))); // NOI18N
        btnBorrarProv.setBorder(null);
        btnBorrarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarProvActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("ID:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(16, 16, 16)
                        .addComponent(txtRazonProv, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIDProve, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel26))
                                .addGap(38, 38, 38)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDireccionProv, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(txtTelefonoProv)
                            .addComponent(txtNombreProv)
                            .addComponent(txtRucProv))))
                .addGap(36, 36, 36)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnGuardarProv)
                        .addGap(65, 65, 65)
                        .addComponent(btnActuProv)
                        .addGap(65, 65, 65)
                        .addComponent(btnBorrarProv))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(txtIDProve, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtRucProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtNombreProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefonoProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(txtDireccionProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(txtRazonProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel27)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnActuProv, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrarProv))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proveedores", jPanel10);

        jPanel11.setBackground(new java.awt.Color(204, 204, 204));

        jLabel28.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Código:");

        jLabel29.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Descripción:");

        jLabel30.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Cantidad:");

        jLabel31.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Precio:");

        jLabel32.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Proveedor:");

        txtCodigoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProductoKeyTyped(evt);
            }
        });

        txtCantidadProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProductoKeyTyped(evt);
            }
        });

        txtPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProductoKeyTyped(evt);
            }
        });

        tablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO", "PROVEEDOR"
            }
        ));
        tablaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaProducto);
        if (tablaProducto.getColumnModel().getColumnCount() > 0) {
            tablaProducto.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaProducto.getColumnModel().getColumn(1).setPreferredWidth(30);
            tablaProducto.getColumnModel().getColumn(2).setPreferredWidth(60);
            tablaProducto.getColumnModel().getColumn(3).setPreferredWidth(30);
            tablaProducto.getColumnModel().getColumn(4).setPreferredWidth(30);
            tablaProducto.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        btnGuardarProducto.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardarProducto.setForeground(new java.awt.Color(204, 204, 204));
        btnGuardarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/salvar.png"))); // NOI18N
        btnGuardarProducto.setBorder(null);
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });

        btnActualizarProducto.setBackground(new java.awt.Color(204, 204, 204));
        btnActualizarProducto.setForeground(new java.awt.Color(204, 204, 204));
        btnActualizarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/actualizar-flecha.png"))); // NOI18N
        btnActualizarProducto.setBorder(null);
        btnActualizarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProductoActionPerformed(evt);
            }
        });

        btnEcxellProducto.setBackground(new java.awt.Color(204, 204, 204));
        btnEcxellProducto.setForeground(new java.awt.Color(204, 204, 204));
        btnEcxellProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/png-transparent-microsoft-excel-logo-microsoft-excel-computer-icons-spreadsheet-computer-software-microsoft-angle-text-rectangle-removebg-preview.png"))); // NOI18N
        btnEcxellProducto.setBorder(null);
        btnEcxellProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEcxellProductoActionPerformed(evt);
            }
        });

        btnBorrarProducto.setBackground(new java.awt.Color(204, 204, 204));
        btnBorrarProducto.setForeground(new java.awt.Color(204, 204, 204));
        btnBorrarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/borrar.png"))); // NOI18N
        btnBorrarProducto.setBorder(null);
        btnBorrarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarProductoActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("ID:");

        cbxProveedorPro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige Proveedor" }));
        cbxProveedorPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProveedorProActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(33, 33, 33)
                        .addComponent(cbxProveedorPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(btnActualizarProducto)
                                .addGap(70, 70, 70)
                                .addComponent(btnEcxellProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(btnBorrarProducto)))
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescriProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtDescriProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtCantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(cbxProveedorPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBorrarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEcxellProducto))))))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Productos", jPanel11);

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));

        tablaVenta.setBackground(new java.awt.Color(255, 255, 255));
        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        tablaVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVentaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaVenta);
        if (tablaVenta.getColumnModel().getColumnCount() > 0) {
            tablaVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaVenta.getColumnModel().getColumn(1).setPreferredWidth(60);
            tablaVenta.getColumnModel().getColumn(2).setPreferredWidth(60);
            tablaVenta.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btnPdfVenta.setBackground(new java.awt.Color(204, 204, 204));
        btnPdfVenta.setForeground(new java.awt.Color(51, 51, 51));
        btnPdfVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/img/png-transparent-computer-icons-pdf-others-text-rectangle-logo-removebg-preview.png"))); // NOI18N
        btnPdfVenta.setBorder(null);
        btnPdfVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentaActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("ID:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIDVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(btnPdfVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1039, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnPdfVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(txtIDVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ventas", jPanel12);

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));

        jLabel33.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("GESTION DE USUARIOS");

        jLabel34.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("RUC:");

        jLabel35.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Nombre:");

        jLabel36.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Teléfono:");

        jLabel37.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Dirección:");

        jLabel38.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Razon Social:");

        txtTelConfi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelConfiKeyTyped(evt);
            }
        });

        btnGuardarConfi.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardarConfi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/agregar.png"))); // NOI18N
        btnGuardarConfi.setBorder(null);
        btnGuardarConfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarConfiActionPerformed(evt);
            }
        });

        txtIdConf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdConfActionPerformed(evt);
            }
        });

        tablaUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "CORREO", "PASS", "ROL"
            }
        ));
        tablaUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUserMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaUser);
        if (tablaUser.getColumnModel().getColumnCount() > 0) {
            tablaUser.getColumnModel().getColumn(0).setPreferredWidth(15);
            tablaUser.getColumnModel().getColumn(0).setHeaderValue("ID");
            tablaUser.getColumnModel().getColumn(1).setPreferredWidth(60);
            tablaUser.getColumnModel().getColumn(2).setPreferredWidth(80);
            tablaUser.getColumnModel().getColumn(3).setPreferredWidth(30);
            tablaUser.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jLabel48.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Nombre:");

        jLabel49.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Correo:");

        jLabel50.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Rol:");

        jLabel51.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("DATOS DE LA EMPRESA");

        txtIdUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdUsuarioKeyTyped(evt);
            }
        });

        btnGuardarUser.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/salvar.png"))); // NOI18N
        btnGuardarUser.setBorder(null);
        btnGuardarUser.setMaximumSize(new java.awt.Dimension(50, 50));
        btnGuardarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUserActionPerformed(evt);
            }
        });

        btnActuUser.setBackground(new java.awt.Color(204, 204, 204));
        btnActuUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/actualizar-flecha.png"))); // NOI18N
        btnActuUser.setBorder(null);
        btnActuUser.setMaximumSize(new java.awt.Dimension(50, 50));
        btnActuUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActuUserActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/borrar.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.setPreferredSize(new java.awt.Dimension(50, 50));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Pass:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(148, 148, 148))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDireConfi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRucConfi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addComponent(btnGuardarConfi, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRazonConfi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelConfi, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(txtNomConfi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdConf, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtRolUsuario)
                                        .addComponent(txtPassUser)
                                        .addComponent(txtCorreoUsuario)
                                        .addGroup(jPanel13Layout.createSequentialGroup()
                                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(78, 78, 78)))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(txtNomUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(82, 82, 82)))
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(btnGuardarUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(171, 171, 171)
                                .addComponent(btnActuUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(153, 153, 153)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(12, 12, 12)
                        .addComponent(txtCorreoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRolUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGuardarUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelConfi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazonConfi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRucConfi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGuardarConfi, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDireConfi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(btnActuUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, Short.MAX_VALUE))))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomConfi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Razón Social de la Empresa", jPanel13);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 1060, 360));

        LabelVendedor.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        LabelVendedor.setForeground(new java.awt.Color(0, 0, 0));
        LabelVendedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(LabelVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 110, 30));

        jLabel41.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/agente.png"))); // NOI18N
        jPanel1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 90, -1));

        jButton1.setBackground(new java.awt.Color(0, 102, 153));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Img/cerrar-sesion.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 10, -1, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtStockVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockVentaActionPerformed

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void botonGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarClienteActionPerformed
      //valicadion de campos  y rellenado 
        if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelCliente.getText()) || !"".equals(txtDireccionCliente.getText())|| !"".equals(txtRazonCliente.getText())) {
           
            cl.setDni(Integer.parseInt(txtDniCliente.getText()));
            cl.setNombre(txtNombreCliente.getText());
             cl.setTelefono(Integer.parseInt(txtTelCliente.getText()));
             cl.setDireccion(txtDireccionCliente.getText());
             cl.setRazon(txtRazonCliente.getText());
             
             clinet.RegistrarCliente(cl);
             LimpiarTabla();
             LimpiarCliente();
             ListarCliente();
             //se manda el msh si se cumple la condicon
           JOptionPane.showMessageDialog(null, "cliente Registrado");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_botonGuardarClienteActionPerformed

    private void btnActualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarClienteActionPerformed
if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelCliente.getText())|| !"".equals(txtDireccionCliente.getText())|| !"".equals(txtRazonCliente.getText())) {
                cl.setDni(Integer.parseInt(txtDniCliente.getText()));
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(Integer.parseInt(txtTelCliente.getText()));
                cl.setDireccion(txtDireccionCliente.getText());
                cl.setRazon(txtRazonCliente.getText());
                cl.setId(Integer.parseInt(txtIdCliente.getText()));
                clinet.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null, "Cliente Modificado");
                LimpiarTabla();
                LimpiarCliente();
                ListarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }        
    }//GEN-LAST:event_btnActualizarClienteActionPerformed

//boton borrar clienet
    private void btnBorrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarClienteActionPerformed
           //verificacion de campos vacios
        if (!"".equals(txtIdCliente.getText())) {
            //pregunta de cuadro de dialogo con showConfirmDialog
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());
                clinet.EliminarCliente(id);
                LimpiarTabla();
                LimpiarCliente();
                ListarCliente();
            }
            //si el campo seleccionado esta vacio
        }else{
        JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnBorrarClienteActionPerformed

    private void jButtonClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClientesActionPerformed
         //metodo para que no se repitala lista de tablas
        LimpiarTabla();
        //se ejecuta el metodo creado
        ListarCliente();  
        //se selecciona el componenete y pestaña
        
        LimpiarCliente();
         jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButtonClientesActionPerformed
//metodo para rellenar los campos
    private void TablaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaClienteMouseClicked
       //metodo para seleccion de espacios
       //"rowAtPoint" de la tabla toma esa posición y devuelve el índice de fila 
       //"evt.getPoint()" devuelve la posición del clic del ratón en la tabla. 
        int fila = TablaCliente.rowAtPoint(evt.getPoint());
        //campos a rellenar
        txtIdCliente.setText(TablaCliente.getValueAt(fila, 0).toString());
         txtDniCliente.setText(TablaCliente.getValueAt(fila, 1).toString());
          txtNombreCliente.setText(TablaCliente.getValueAt(fila, 2).toString());
           txtTelCliente.setText(TablaCliente.getValueAt(fila, 3).toString());
            txtDireccionCliente.setText(TablaCliente.getValueAt(fila, 4).toString());
             txtRazonCliente.setText(TablaCliente.getValueAt(fila, 5).toString());
       
       
    }//GEN-LAST:event_TablaClienteMouseClicked

    private void btnGuardarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProvActionPerformed
         if (!"".equals(txtRucProv.getText()) || !"".equals(txtNombreProv.getText()) || !"".equals(txtTelefonoProv.getText()) || !"".equals(txtDireccionProv.getText())|| !"".equals(txtRazonProv.getText())) {
           
            pr.setRuc(Integer.parseInt(txtRucProv.getText()));
            pr.setNombre(txtNombreProv.getText());
             pr.setTelefono(Integer.parseInt(txtTelefonoProv.getText()));
             pr.setDireccion(txtDireccionProv.getText());
             pr.setRazon(txtRazonProv.getText());
             
             prDAO.RegistrarProveedor(pr);
             
             LimpiarTabla();
             LimpiarProve();
             ListarProveedor();
           
           JOptionPane.showMessageDialog(null, "Proveedor Registrado");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarProvActionPerformed

    private void jButtonProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProveedoresActionPerformed
        //metodo para que no se repitala lista de tablas
        LimpiarTabla();
        //se ejecuta el metodo creado
        ListarProveedor();  
        //se selecciona el componenete y pestaña
        LimpiarProve();
         jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButtonProveedoresActionPerformed

    private void tablaProvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProvMouseClicked
        int fila = tablaProv.rowAtPoint(evt.getPoint());
        //campos a rellenar
        txtIDProve.setText(tablaProv.getValueAt(fila, 0).toString());
         txtRucProv.setText(tablaProv.getValueAt(fila, 1).toString());
          txtNombreProv.setText(tablaProv.getValueAt(fila, 2).toString());
           txtTelefonoProv.setText(tablaProv.getValueAt(fila, 3).toString());
            txtDireccionProv.setText(tablaProv.getValueAt(fila, 4).toString());
             txtRazonProv.setText(tablaProv.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tablaProvMouseClicked

    private void btnBorrarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarProvActionPerformed
       //se verifica si el campo de texto tiene algun valor
        if (!"".equals(txtIDProve.getText())) {
            //se abfe una pregujta y en caso de pones si =0 se elimina
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIDProve.getText());
              //se llama el metodo eliminar prdao
                prDAO.EliminarProveedor(id);
                //se limpia los campos con los siguientes meotdos
                LimpiarTabla();
                LimpiarProve();
                ListarProveedor();
            }
        }else{
        JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnBorrarProvActionPerformed

    private void btnActuProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActuProvActionPerformed
       //validacion de camp
        if ("".equals(txtIDProve.getText())) {
            //si estan vacios arroja este 
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(txtRucProv.getText()) || !"".equals(txtNombreProv.getText()) || !"".equals(txtTelefonoProv.getText())|| !"".equals(txtDireccionProv.getText())|| !"".equals(txtRazonProv.getText())) {
               
                pr.setRuc(Integer.parseInt(txtRucProv.getText()));
                pr.setNombre(txtNombreProv.getText());
                pr.setTelefono(Integer.parseInt(txtTelefonoProv.getText()));
                pr.setDireccion(txtDireccionProv.getText());
                pr.setRazon(txtRazonProv.getText());
                pr.setId(Integer.parseInt(txtIDProve.getText()));
                prDAO.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                LimpiarTabla();
                ListarProveedor();
                LimpiarProve();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }        
    }//GEN-LAST:event_btnActuProvActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
     
        
 if ("".equals(txtCodigoProducto.getText()) && "".equals(txtDescriProducto.getText()) && "".equals(cbxProveedorPro.getSelectedItem()) && "".equals(txtCantidadProducto.getText()) && "".equals(txtPrecioProducto.getText())) {
    JOptionPane.showMessageDialog(null, "Los campos están vacíos");
} else {
    pro.setCodigo(txtCodigoProducto.getText());
    pro.setNombre(txtDescriProducto.getText());
    pro.setProveedor(cbxProveedorPro.getSelectedItem().toString());
    pro.setStock(Integer.parseInt(txtCantidadProducto.getText()));
    pro.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
    proDAO.RegistrarProductos(pro);
    LimpiarTabla();
    ListarProducto();
    LimpiarProducto();
    
    JOptionPane.showMessageDialog(null, "Productos Registrados");
  
  
    cbxProveedorPro.removeAllItems();
}

    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void tablaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductoMouseClicked
         //metodo para seleccion de espacios
        int fila = tablaProducto.rowAtPoint(evt.getPoint());
        //campos a rellenar
        txtIDProducto.setText(tablaProducto.getValueAt(fila, 0).toString());
         txtCodigoProducto.setText(tablaProducto.getValueAt(fila, 1).toString());
          txtDescriProducto.setText(tablaProducto.getValueAt(fila, 2).toString());
           txtCantidadProducto.setText(tablaProducto.getValueAt(fila, 3).toString());
            txtPrecioProducto.setText(tablaProducto.getValueAt(fila, 4).toString());
            cbxProveedorPro.setSelectedItem(tablaProducto.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tablaProductoMouseClicked

    private void btnProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoActionPerformed
          //metodo para que no se repitala lista de tablas
        LimpiarTabla();
        ListarProducto();
         jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_btnProductoActionPerformed

    private void cbxProveedorProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProveedorProActionPerformed
       
    }//GEN-LAST:event_cbxProveedorProActionPerformed

    private void btnBorrarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarProductoActionPerformed
          //se verifica si el campo de texto tiene algun valor
        if (!"".equals(txtIDProducto.getText())) {
            //se abfe una pregujta y en caso de pones si =0 se elimina
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIDProducto.getText());
              //se llama el metodo eliminar prdao
                proDAO.EliminarProductos(id);
                //se limpia los campos con los siguientes meotdos
                LimpiarTabla();
                LimpiarProducto();
                ListarProducto();
            }
        }else{
        JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnBorrarProductoActionPerformed

    private void btnActualizarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProductoActionPerformed
       //validacion de camp
        if ("".equals(txtIDProducto.getText())) {
            //si estan vacios arroja este 
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(txtCodigoProducto.getText()) || !"".equals(txtDescriProducto.getText()) || !"".equals(txtCantidadProducto.getText())|| !"".equals(txtPrecioProducto.getText())) {
               
                pro.setCodigo(txtCodigoProducto.getText());
                pro.setNombre(txtDescriProducto.getText());
                pro.setProveedor(cbxProveedorPro.getSelectedItem().toString());
                pro.setStock(Integer.parseInt(txtCantidadProducto.getText()));
                pro.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
                pro.setId(Integer.parseInt(txtIDProducto.getText()));
                
                proDAO.ModificarProductos(pro);
                JOptionPane.showMessageDialog(null, "Producto Modificado");
                LimpiarTabla();
                ListarProducto();
                LimpiarProducto();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        } 
    }//GEN-LAST:event_btnActualizarProductoActionPerformed

    private void btnEcxellProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEcxellProductoActionPerformed
        Excell.reporte();
    }//GEN-LAST:event_btnEcxellProductoActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
       //este metodo sirve para cuando das un click de enter
       //validacion de campos
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                pro = proDAO.BuscarPro(cod);
                if (pro.getNombre() != null) {
                    //txtIdPro.setText("" + pro.getId());
                    txtDescripcionVenta.setText("" + pro.getNombre());
                    txtPrecioVenta.setText("" + pro.getPrecio());
                    txtStockVenta.setText("" + pro.getStock());
                    txtCantidadVenta.requestFocus();
                } else {
                    txtDescripcionVenta.setText("");
                    txtPrecioVenta.setText("");
                    txtStockVenta.setText("");
                    txtCodigoVenta.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del productos");
                txtCodigoVenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
       //validacion de campos
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //si es igual
            if (!"".equals(txtCantidadVenta.getText())) {
                //ASIGNAS VARIABLES DE COMPONENTES
                String cod  = txtCodigoVenta.getText();
                String descripcion = txtDescripcionVenta.getText();
                int cant = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double total = cant * precio;
                int stock = Integer.parseInt(txtStockVenta.getText());
                //validacion si el stock es menor qeu cantidad
                if (stock >= cant) {
                    item = item + 1;
                    tmp = (DefaultTableModel) jTableVenta.getModel();
                    //se reccore y si se encuentra conicidencia manda msj
                    for (int i = 0; i < jTableVenta.getRowCount(); i++) {
                        if (jTableVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(cod);
                    lista.add(descripcion);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);
                    Object[] O = new Object[5];
                    //AGREGAS A LA TABLA POR COLUMNAS
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    tmp.addRow(O);
                    jTableVenta.setModel(tmp);
                    TotalPagar();
                    LimpiarVenta();
                    txtCodigoVenta.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void botonBorrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorrarVentaActionPerformed

        
 modelo = (DefaultTableModel) jTableVenta.getModel();
// Verificar si la tabla está vacía
if (modelo.getRowCount() == 0) {
    // La tabla está vacía, realiza la validación que necesites
    JOptionPane.showMessageDialog(null, "No had nada que eliminar");
} else {
    // La tabla no está vacía, puedes continuar con el resto del código
    modelo.removeRow(jTableVenta.getSelectedRow());
    TotalPagar();
    txtCodigoVenta.requestFocus();
}

    }//GEN-LAST:event_botonBorrarVentaActionPerformed

    private void txtDniVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniVentaKeyPressed
        //validacion del clcik  
        if (evt.getKeyCode() == KeyEvent.VK_ENTER ) {
            if (!"".equals(txtDniVenta.getText())) {
                int dni = Integer.parseInt(txtDniVenta.getText());
                //conexion con la clase y el metodo buscar clientedao
                cl = clinet.Buscarcliente(dni);
                if (cl.getNombre() != null) {
                    //en caso de ser correcto llena los siguientes campos
                    txtNombreVenta.setText("" + cl.getNombre());
                    txtTelVentas.setText("" + cl.getTelefono());
                    txtDireccionVenta.setText("" + cl.getDireccion());
                    txtRazonVenta.setText("" + cl.getRazon());
                } else {
                    //validacion si el cliente esta vacio o no es correcto
                    txtDniVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_txtDniVentaKeyPressed

    private void botonImprimirVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonImprimirVentaActionPerformed
    //validacion de tabla venta
    if(jTableVenta.getRowCount()>0){
       if (!"".equals(txtNombreVenta.getText())){
         //metodos para el boton de imprimir
        RegistrarVenta();
      RegistrarDetalle();
      ActualizarStock();
      pdf();
      LimpiarTablaVenta();
      LimpiarDatosRuc();  
       }else{
       JOptionPane.showMessageDialog(null,"Debes buscar un Cliente");
       }
    }else{
     JOptionPane.showMessageDialog(null,"No hay producto");
    }
        
    }//GEN-LAST:event_botonImprimirVentaActionPerformed

    private void jButtonNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevaVentaActionPerformed
       jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButtonNuevaVentaActionPerformed

    private void txtDniClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniClienteActionPerformed
      
        
    }//GEN-LAST:event_txtDniClienteActionPerformed
//validacion dni cliente
    private void txtDniClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniClienteKeyTyped
           char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtDniClienteKeyTyped
//validacion tabla venta dni
    private void txtDniVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniVentaKeyTyped
         char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtDniVentaKeyTyped
//validaciones codigo producto tabla venta
    private void txtCodigoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProductoKeyTyped
         char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtCodigoProductoKeyTyped
//validacion cantidad tabla venta
    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
         char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtCantidadVentaKeyTyped
//validacion precio tabla venta
    private void txtPrecioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyTyped
          char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtPrecioVentaKeyTyped
//validacion stock tabla venta
    private void txtStockVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockVentaKeyTyped
         char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtStockVentaKeyTyped
//validacion telefono tabla ventas
    private void txtTelVentasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelVentasKeyTyped
         char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtTelVentasKeyTyped
//validacion  tel tabla cliente
    private void txtTelClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelClienteKeyTyped
       char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtTelClienteKeyTyped
//validacion tel tabla proveedores
    private void txtTelefonoProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProvKeyTyped
          char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtTelefonoProvKeyTyped
//validacion cantidat tabal producto
    private void txtCantidadProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProductoKeyTyped
          char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtCantidadProductoKeyTyped
//validacion precio tabal producto
    private void txtPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProductoKeyTyped
         char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtPrecioProductoKeyTyped

    private void txtIdConfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdConfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdConfActionPerformed

    private void btnRazonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRazonActionPerformed
        jTabbedPane1.setSelectedIndex(5);
        ListarUsuario();
        ListarConfi();
       
    }//GEN-LAST:event_btnRazonActionPerformed
//metodo para actualizar confi de datos
    private void btnGuardarConfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarConfiActionPerformed
  if (!"".equals(txtRucConfi.getText()) || !"".equals(txtNomConfi.getText()) || !"".equals(txtTelConfi.getText())|| !"".equals(txtDireConfi.getText())|| !"".equals(txtRazonConfi.getText())) {
               
                conf.setRuc(txtRucConfi.getText());
                conf.setNombre(txtNomConfi.getText());
                  conf.setTelefono(Integer.parseInt(txtTelConfi.getText()));
                  conf.setDireccion(txtDireConfi.getText());
                  conf.setRazon(txtRazonConfi.getText());
                  proDAO.ModificarDatos(conf);
                  JOptionPane.showMessageDialog(null, "Datos de Empresa Modificados");
            } else {
                JOptionPane.showMessageDialog(null, "Campos Vacios");
            }
            
    
    }//GEN-LAST:event_btnGuardarConfiActionPerformed
//validacion de campo en actualizacion de tabla
    private void txtTelConfiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelConfiKeyTyped
       char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtTelConfiKeyTyped
//validacion de codgio venta
    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
           char car = evt.getKeyChar();
        if((car<'0' || car>'9') && (car<',' || car>'.')) evt.consume();
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void jButtonVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVentasActionPerformed
      
        jTabbedPane1.setSelectedIndex(4);
         //metodo para limpiar tabla
         LimpiarTabla();
         ListarVenta();
    }//GEN-LAST:event_jButtonVentasActionPerformed
//metodo para ver la venta por id dando un clcick
    private void tablaVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentaMouseClicked
      //crear el evento en la tabla
        int fila = tablaVenta.rowAtPoint(evt.getPoint());
        //pasarlo al campo id venta seleccioanrlo por indice de columna y fila y convertiloa a string
        txtIDVenta.setText(tablaVenta.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_tablaVentaMouseClicked
//metodo para generar reporte de venta en pdf por id
    private void btnPdfVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentaActionPerformed
   
    if (txtIDVenta.getText().isEmpty()) {
    JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún ID de venta");
} else {
    try {
        int id = Integer.parseInt(txtIDVenta.getText());
        File file = new File("src/pdf/venta" + id + ".pdf");
        Desktop.getDesktop().open(file);
    } catch (IOException ex) {
        java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
}

    }//GEN-LAST:event_btnPdfVentaActionPerformed
//metodo para graficar
    private void btnGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficaActionPerformed
//validacion si el campo esta vacio
if (miDate.getDate() == null) {
    JOptionPane.showMessageDialog(null, "Seleccione una fecha");
} else {
     String fechaReporte = new SimpleDateFormat("dd/MM/yyyy").format(miDate.getDate());
        //istanciar el metodo con la variable d efehca actual
        Grafico.Graficar(fechaReporte);
}   
        
    }//GEN-LAST:event_btnGraficaActionPerformed
//tabla de usuarios
    private void tablaUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUserMouseClicked
        
        //metodo para seleccion de espacios
       //"rowAtPoint" de la tabla toma esa posición y devuelve el índice de fila 
       //"evt.getPoint()" devuelve la posición del clic del ratón en la tabla. 
        int fila = tablaUser.rowAtPoint(evt.getPoint());
        //campos a rellenar
        txtIdUsuario.setText(tablaUser.getValueAt(fila, 0).toString());
         txtNomUsuario.setText(tablaUser.getValueAt(fila, 1).toString());
          txtCorreoUsuario.setText(tablaUser.getValueAt(fila, 2).toString());
           txtPassUser.setText(tablaUser.getValueAt(fila, 3).toString());
            txtRolUsuario.setText(tablaUser.getValueAt(fila, 4).toString());
             
    }//GEN-LAST:event_tablaUserMouseClicked
//boton guardar usuario
    private void btnGuardarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUserActionPerformed
       
        if (!"".equals(txtNomUsuario.getText()) || !"".equals(txtCorreoUsuario.getText()) || !"".equals(txtPassUser.getText())|| !"".equals(txtRolUsuario.getText())) {
           
           
            ln.setNombre(txtNomUsuario.getText());
             ln.setCorreo(txtCorreoUsuario.getText());
             ln.setPass(txtPassUser.getText());
             ln.setRol(txtRolUsuario.getText());
             
             lg.Registrar(ln);
             LimpiarTabla();
             LimpiarUser();
             ListarUsuario();
             //se manda el msh si se cumple la condicon
           JOptionPane.showMessageDialog(null, "Usuario Registrado");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarUserActionPerformed
//boton borrar usuario
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            //se verifica si el campo de texto tiene algun valor
        if (!"".equals(txtIdUsuario.getText())) {
            //se abfe una pregujta y en caso de pones si =0 se elimina
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdUsuario.getText());
              //se llama el metodo eliminar prdao
                lg.EliminarUsuario(id);
                //se limpia los campos con los siguientes meotdos
                LimpiarTabla();
                LimpiarUser();
                ListarUsuario();
            }
        }else{
        JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_jButton3ActionPerformed
//actualizar usuarios
    private void btnActuUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActuUserActionPerformed
        
        if (!"".equals(txtIdUsuario.getText()) || !"".equals(txtNomUsuario.getText()) || !"".equals(txtCorreoUsuario.getText())|| !"".equals(txtPassUser.getText())|| !"".equals(txtRolUsuario.getText())) {
               
                ln.setId(Integer.parseInt(txtIdUsuario.getText()));
                ln.setNombre(txtNomUsuario.getText());
                  ln.setCorreo(txtCorreoUsuario.getText());
                  ln.setPass(txtPassUser.getText());
                  ln.setRol(txtRolUsuario.getText());
                 
                  lg.ModificarUsuario(ln);
                  
                  JOptionPane.showMessageDialog(null, "Usuario Actualizado");
            } else {
                JOptionPane.showMessageDialog(null, "Campos Vacios");
            }
    }//GEN-LAST:event_btnActuUserActionPerformed

//boton salir
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Login lg = new Login ();
        
        lg.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtIdUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdUsuarioKeyTyped

    }//GEN-LAST:event_txtIdUsuarioKeyTyped


    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelVendedor;
    private javax.swing.JTable TablaCliente;
    private javax.swing.JButton botonBorrarVenta;
    private javax.swing.JButton botonGuardarCliente;
    private javax.swing.JButton botonImprimirVenta;
    private javax.swing.JButton btnActuProv;
    private javax.swing.JButton btnActuUser;
    private javax.swing.JButton btnActualizarCliente;
    private javax.swing.JButton btnActualizarProducto;
    private javax.swing.JButton btnBorrarCliente;
    private javax.swing.JButton btnBorrarProducto;
    private javax.swing.JButton btnBorrarProv;
    private javax.swing.JButton btnEcxellProducto;
    private javax.swing.JButton btnGrafica;
    private javax.swing.JButton btnGuardarConfi;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnGuardarProv;
    private javax.swing.JButton btnGuardarUser;
    private javax.swing.JButton btnPdfVenta;
    private javax.swing.JButton btnProducto;
    private javax.swing.JButton btnRazon;
    private javax.swing.JComboBox<String> cbxProveedorPro;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonClientes;
    private javax.swing.JButton jButtonNuevaVenta;
    private javax.swing.JButton jButtonProveedores;
    private javax.swing.JButton jButtonVentas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableVenta;
    private javax.swing.JLabel labelTotalVenta;
    private com.toedter.calendar.JDateChooser miDate;
    private javax.swing.JTable tablaProducto;
    private javax.swing.JTable tablaProv;
    private javax.swing.JTable tablaUser;
    private javax.swing.JTable tablaVenta;
    private javax.swing.JTextField txtCantidadProducto;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoProducto;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtCorreoUsuario;
    private javax.swing.JTextField txtDescriProducto;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireConfi;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionProv;
    private javax.swing.JTextField txtDireccionVenta;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtDniVenta;
    private javax.swing.JTextField txtIDProducto;
    private javax.swing.JTextField txtIDProve;
    private javax.swing.JTextField txtIDProveVenta;
    private javax.swing.JTextField txtIDVenta;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdConf;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JTextField txtNomConfi;
    private javax.swing.JTextField txtNomUsuario;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreProv;
    private javax.swing.JTextField txtNombreVenta;
    private javax.swing.JTextField txtPassUser;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRazonCliente;
    private javax.swing.JTextField txtRazonConfi;
    private javax.swing.JTextField txtRazonProv;
    private javax.swing.JTextField txtRazonVenta;
    private javax.swing.JTextField txtRolUsuario;
    private javax.swing.JTextField txtRucConfi;
    private javax.swing.JTextField txtRucProv;
    private javax.swing.JTextField txtStockVenta;
    private javax.swing.JTextField txtTelCliente;
    private javax.swing.JTextField txtTelConfi;
    private javax.swing.JTextField txtTelVentas;
    private javax.swing.JTextField txtTelefonoProv;
    // End of variables declaration//GEN-END:variables


   
}
