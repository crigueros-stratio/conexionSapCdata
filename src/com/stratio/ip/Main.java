package com.stratio.ip;

import cdata.jdbc.saperp.SAPERPDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;


public class Main {

  public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

    System.out.println(new java.util.Date());
    Instant start = Instant.now();
    Class.forName("cdata.jdbc.saperp.SAPERPDriver");

    String ruta = "/tmp/output.txt";
    File file = new File(ruta);
    // Si el archivo no existe es creado
    if (!file.exists()) {
      file.createNewFile();
    }

    try (FileWriter fw = new FileWriter(ruta, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {

      SAPERPDataSource ds = new SAPERPDataSource("jdbc:saperp:Host=10.205.100.140;User=DATACENTRIC;Password=Stratiocea202005*;Client=300;System Number=00;ConnectionType=JCO;RTK=52595247565A31323237323133305745425452314131000000000000000000000000000000000000444445303954315800005743385A4B4245455A3656360000;");
      Connection conn = ds.getConnection();

      System.out.println("CONNECTED");
      String query = "SELECT * FROM BUT000 limit 1 ";

      Statement sqlStatement = conn.createStatement();
      ResultSet rs = sqlStatement.executeQuery(query);

      int pointer = 0;
      while (rs.next()) {
        StringBuilder aux = new StringBuilder();

        if (pointer==0)
          for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++)
              aux.append(rs.getMetaData().getColumnName(i)).append(" ");


        for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
          aux.append(rs.getObject(i)).append(" ");
        }
        out.println(aux.toString());
        pointer ++;
      }
      sqlStatement.close();

      System.out.println("END");
    } catch (IOException e) {
      e.printStackTrace();

    }
    Instant finish = Instant.now();

    System.out.println("Seconds elapsed: " + Duration.between(start, finish).toSeconds());
    try {
      Thread.sleep(9999);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
