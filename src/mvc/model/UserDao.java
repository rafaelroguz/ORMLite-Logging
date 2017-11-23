package mvc.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;
import java.util.Properties;

/**
 * La clase UserDao permite realizar conexiones a la BD para realizar operaciones
 * de inserción, modificación o eliminación de usuarios.
 * @author Rafael Rodríguez Guzmán
 */

public class UserDao {

    private final int SUCCESS_REGISTER = 1;
    private final int ERROR_DBCONFIG_FILE = 2;
    private final int ERROR_DBCONFIG_LECTURE = 3;
    private final int ERROR_REGISTERED_USER = 4;
    
    /**
     * Realiza la inserción de un nuevo usuario a la base de datos
     * @param user Usuario a registrar en la BD.
     * @return Código de resultado de la operación. 1 si se realizó el registro
     * exitoso, 2 si el archivo de configuración no se encuentra, 3 si hubo problema
     * al leer el archivo de configuración, 4 si el usuario ya está registrado.
     */
    
    public int insertUser(User user) {
        int result = SUCCESS_REGISTER;
        
        try {
            //Archivo de configuración de la BD.
            Properties dbconfig = new Properties();
            
            InputStream input = 
                    new FileInputStream("dbconfig.properties");
            
            dbconfig.load(input);            
            
            String URL = dbconfig.getProperty("dburl");
            String DBNAME = dbconfig.getProperty("dbname");
            String USERNAME = dbconfig.getProperty("dbuser");
            String PASSWORD = dbconfig.getProperty("dbpassword");
            
            //Se realiza la conexión con la BD.
            ConnectionSource connectionSource = 
                    new JdbcConnectionSource((URL+DBNAME), USERNAME, PASSWORD);
            
            Dao<User, String> userDao = 
                    DaoManager.createDao(connectionSource, User.class);

            //Crea la tabla si no existe en la BD.
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            
            //Crea una nueva tupla en la tabla si el usuario no existe.
            userDao.create(user);
            
            //Cierra la conexión con la BD.
            connectionSource.close();

            result = SUCCESS_REGISTER;
        } catch (FileNotFoundException e1) {
            result = ERROR_DBCONFIG_FILE;
            //e1.printStackTrace();
        } catch (IOException e2) {
            result = ERROR_DBCONFIG_LECTURE;
            //e2.printStackTrace();
        } catch (SQLException e3) {
            result = ERROR_REGISTERED_USER;
            //e3.printStackTrace();
        }
        
        return result;
    }
    
}
