package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

  public static Connection getConnection() throws SQLException, FileNotFoundException {
    Properties props = new Properties();
    Connection con = null;
    /* create a `db.properties` in `src/main/resources`
    DB_DRIVER_CLASS=oracle.jdbc.driver.OracleDriver
    DB_URL=jdbc:oracle:thin:[AWSLINK]:[YOUR_SERVICE_ID]
    DB_USERNAME=
    DB_PASSWORD=
    */
    URL url = DBConnection.class.getClassLoader().getResource("db.properties");
    if (url == null) {
      throw new FileNotFoundException();
    }
    try (FileInputStream dbProps = new FileInputStream(url.getFile())){
      props.load(dbProps);
      Class.forName(props.getProperty("DB_DRIVER_CLASS"));

      con = DriverManager.getConnection(props.getProperty("DB_URL"), props.getProperty("DB_USERNAME"),
          props.getProperty("DB_PASSWORD"));
    } catch (IOException | ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    if (con == null) {
      throw new SQLException();
    }
    return con;
  }
}
