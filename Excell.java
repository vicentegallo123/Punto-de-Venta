package Reportes;


import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Conexiones.Conexion;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class Excell {
    public static void reporte() {
   //implementación de POI para el formato de archivo XLSX
        Workbook book = new XSSFWorkbook();
        //método createSheet() del objeto  Esta hoja de trabajo se utiliza para almacenar datos y presentarlos en una tabla en la hoja de Exce
        Sheet sheet = book.createSheet("Productos");
 
        try {
            //salida de img
            InputStream is = new FileInputStream("src/Img/Img/logo.png");
            //lee la imagen en un arreglo de bytes usando la clase IOUtils de Apache Commons.
            byte[] bytes = IOUtils.toByteArray(is);
            //agrega la imagen al libro de trabajo de Excel (Workbook) usando el método addPicture(), 
            int imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            is.close();
 //obtiene una instancia de CreationHelper y Drawing para poder crear un objeto Picture que se agregará a una hoja de trabajo de Exce
            CreationHelper help = book.getCreationHelper();
           // objeto ClientAnchor para definir la posición y tamaño de la imagen en la hoja de trabajo. 
            Drawing draw = sheet.createDrawingPatriarch();
 //posicion de la img en la columna 1 y 0 y verticalmente en fila 3
            ClientAnchor anchor = help.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(1);
            Picture pict = draw.createPicture(anchor, imgIndex);
            pict.resize(1, 3);
 //istancia para el estilo de celda y fila
            CellStyle tituloEstilo = book.createCellStyle();
            //se alinea el titulo
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
            //isntancia de la fuente
            Font fuenteTitulo = book.createFont();
            //tipo de letra
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short) 14);
            tituloEstilo.setFont(fuenteTitulo);
 //istancia y crea una nueva filas
            Row filaTitulo = sheet.createRow(1);
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Reporte de Productos");
 
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 3));
 //Se define un array de Strings contiene los nombres de las columnas que se mostrarán en la hoja de Excel.
            String[] cabecera = new String[]{"Código", "Nombre", "Precio", "Existencia"};
 //estilo de celda  para aplicar a las celdas de encabezado de la hoja. con fuentes y bordes
            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
 
            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);
 //en la hoja de Excel y se agregan las celdas de encabezado utilizando el estilo definido anteriormente.
            Row filaEncabezados = sheet.createRow(4);
 
            for (int i = 0; i < cabecera.length; i++) {
                Cell celdaEnzabezado = filaEncabezados.createCell(i);
                celdaEnzabezado.setCellStyle(headerStyle);
                celdaEnzabezado.setCellValue(cabecera[i]);
            }
 //Se establece una conexión a la base de datos de productos.
            Conexion con = new Conexion();
            //consulta de base d edatos
            PreparedStatement ps;
            //consulta SQL para obtener los datos de los productos 
            ResultSet rs;
            Connection conn = con.getConnection();
 
            int numFilaDatos = 5;
 
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderBottom(BorderStyle.THIN);
 
            ps = conn.prepareStatement("SELECT codigo, nombre, precio, stock FROM productos");
            rs = ps.executeQuery();
 
            int numCol = rs.getMetaData().getColumnCount();
 
            while (rs.next()) {
                Row filaDatos = sheet.createRow(numFilaDatos);
 
                for (int a = 0; a < numCol; a++) {
 
                    Cell CeldaDatos = filaDatos.createCell(a);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString(a + 1));
                }
 
 
                numFilaDatos++;
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            
            sheet.setZoom(150);
            String fileName = "productos";
            String home = System.getProperty("user.home");
            //Se define el nombre del archivo Excel y la ubicación donde se guardará el archivo
            File file = new File(home + "/Downloads/" + fileName + ".xlsx");
            //bjeto FileOutputStream para escribir en el archivo y se escribe el libro de trabajo
            FileOutputStream fileOut = new FileOutputStream(file);
            book.write(fileOut);
            fileOut.close();
            Desktop.getDesktop().open(file);
            JOptionPane.showMessageDialog(null, "Reporte Generado");
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excell.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Excell.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
}