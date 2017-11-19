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

public class UserModel {
    
    private static final int SUCCESS_REGISTER = 0;
    private static final int ERROR_FILE_NOT_FOUND = 1;
    private static final int ERROR_FILE_LECTURE = 2;
    private static final int ERROR_REGISTERED_USER = 3;   
    
    public int registerUser(String userName, String password) {
        
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
            
            User user = new User(userName, password);
            
            //Crea una nueva tupla en la tabla si el usuario no existe.
            userDao.create(user);
            
            //Cierra la conexión con la BD.
            connectionSource.close();
            
            result = SUCCESS_REGISTER;
        } catch (FileNotFoundException e1) {
            result = ERROR_FILE_NOT_FOUND;
            e1.printStackTrace();
        } catch (IOException e2) {
            result = ERROR_FILE_LECTURE;
            e2.printStackTrace();
        } catch (SQLException e3) {
            result = ERROR_REGISTERED_USER;
            e3.printStackTrace();
        }
        
        return result;
    }
    
}
