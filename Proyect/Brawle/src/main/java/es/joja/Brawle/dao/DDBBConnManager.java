package es.joja.Brawle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class DDBBConnManager {

    enum DatabaseType {
        MYSQL, H2
    }
    DatabaseType databaseType;

    /**
     * @return the conexion
     */
    public Connection getConnection() {

        Connection con = null;

        if (basicDataSource == null) {
            try {
                con = DriverManager.getConnection(jdbcUrl, user, key);
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        } else {
            synchronized (basicDataSource) {
                try {
                    con = basicDataSource.getConnection();
                } catch (SQLException ex) {
                    System.exit(1);
                }

            }
        }

        return con;

    }

    String jdbcUrl;
    String user;
    String key;
    String ddbb;

    public String getDdbb() {
        return ddbb;
    }

    //descomentar si queremos pool de conexiones
    BasicDataSource basicDataSource;

    public DDBBConnManager(String strDatabaseType, String ddbb, String nombreUsuario, String password) {
        this.databaseType = databaseType.valueOf(strDatabaseType);

        user = nombreUsuario;
        key = password;
        this.ddbb = ddbb;

        switch (this.databaseType) {
            case MYSQL:
                jdbcUrl = "jdbc:mysql://localhost/" + this.ddbb + "?serverTimezone=UTC";
                cargarDriverMysql();

                //descomentar si queremos pool de conexiones el siguiente bloque 
                {
                basicDataSource = new BasicDataSource();
                basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
                basicDataSource.setUrl(jdbcUrl);
                basicDataSource.setUsername(nombreUsuario);
                basicDataSource.setPassword(password);
                }
                
                break;

            case H2:
                cargarDriverH2();
                //ejecutamos una primera conexión para iniciar la ddbb
                jdbcUrl = "jdbc:h2:file:/tmp/" + this.ddbb + ";INIT=RUNSCRIPT FROM 'classpath:" + this.ddbb + ".sql';MODE=MYSQL";

                try (
                        Connection cn = getConnection();) {

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(-1);
                }

                //las siguiente conexiones NO reinician la ddbb
                jdbcUrl = "jdbc:h2:file:/tmp/" + this.ddbb + ";MODE=MYSQL";

                //si queremos pool de conexiones H2 descomentamos el siguiente bloque:
                {
                basicDataSource = new BasicDataSource();
               
                basicDataSource.setDriverClassName("org.h2.Driver");
                basicDataSource.setUrl(jdbcUrl);
                basicDataSource.setUsername(nombreUsuario);
                basicDataSource.setPassword(password);
                }
                
                break;

            default:
                System.exit(1);

        }

    }

    private static void cargarDriverMysql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    private static void cargarDriverH2() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

}
