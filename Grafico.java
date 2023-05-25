package Reportes;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.sql.*;

public class Grafico {
    public static void Graficar(String fecha) {
        Connection con;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;
        try {
            String sql = "SELECT total, vendedor FROM ventas WHERE fecha = ?";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            DefaultPieDataset dataset = new DefaultPieDataset();
            while (rs.next()) {
                double total = rs.getDouble("total");
                String vendedor = rs.getString("vendedor");
                dataset.setValue(vendedor + " - " + total, total);
            }
            JFreeChart chart = ChartFactory.createPieChart("Total de Ventas por Vendedor", dataset);
            ChartFrame frame = new ChartFrame("Reporte de Ventas", chart);
            frame.setSize(1000, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
