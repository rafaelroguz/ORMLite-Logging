package mvc.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
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

    private final int SUCCESS = 1;
    private final int ERROR_DBCONFIG_FILE = 2;
    private final int ERROR_DBCONFIG_LECTURE = 3;
    private final int ERROR_REGISTERED_USER = 4;
    private final int ERROR_UPDATE = 5;
    private final int ERROR_DELETE = 6;
    
    /**
     * Realiza la inserción de un nuevo usuario a la base de datos
     * @param user Usuario a registrar en la BD.
     * @return Código de resultado de la operación. 1 si se realizó el registro
     * exitoso, 2 si el archivo de configuración no se encuentra, 3 si hubo problema
     * al leer el archivo de configuración, 4 si el usuario ya está registrado.
     */
    
    public int insertUser(User user) {
        int result = SUCCESS;
        
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

            result = SUCCESS;
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
    
    /**
     * Busca un usuario en la BD por el userName.
     * @param userName Nombre de usuario con el cuál queremos encontrar al usuario.
     * @return Objeto User si el usuario fue encontrado, null en caso contrario.
     */
    
    public User findUserByUserName(String userName) {
        User user = null;
        String USERNAME_FIELD_NAME = "userName";
        
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
            
            //Obtenemos un constructor de consultas usando el dao.
            QueryBuilder<User, String> queryBuilder = userDao.queryBuilder();
            //El campo de userName en la BD debe ser igual a la cadena UserName recibida.
            queryBuilder.where().eq(USERNAME_FIELD_NAME, userName);
            //Prepara la sentencia SQL.
            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            
            //Recupera el usuario que coincida con la cadena userName.
            if (!userDao.query(preparedQuery).isEmpty()) {
                user = (User) userDao.query(preparedQuery).get(0);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (SQLException e3) {
            e3.printStackTrace();
        }
        
        return user;
    }
    
    /**
     * Realiza la actualización de un usuario a la base de datos
     * @param user Usuario a actualizar en la BD.
     * @return Código de resultado de la operación. 1 si la actualización fue
     * exitosa, 2 si el archivo de configuración no se encuentra, 3 si hubo problema
     * al leer el archivo de configuración, 5 si el usuario no pudo actualizarse.
     */
    
    public int updateUser(User user) {
        int result = SUCCESS;
        
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
            
            //Obtenemos un constructor de actualización usando el dao.
            UpdateBuilder<User, String> updateBuilder = userDao.updateBuilder();
            //Actualizamos el campo de firstName.
            updateBuilder.updateColumnValue("firstName", user.getFirstName());
            //Actualizamos el campo de lastName.
            updateBuilder.updateColumnValue("lastName", user.getLastName());
            //Actualizamos el campo de email.
            updateBuilder.updateColumnValue("email", user.getEmail());
            //Esblecemos un criterio para saber a qué tuplas se aplicará la actualización.
            updateBuilder.where().eq("userName", user.getUserName());
            //Actualiza la tupla.
            updateBuilder.update();
        } catch (FileNotFoundException e1) {
            result = ERROR_DBCONFIG_FILE;
            //e1.printStackTrace();
        } catch (IOException e2) {
            result = ERROR_DBCONFIG_LECTURE;
            //e2.printStackTrace();
        } catch (SQLException e3) {
            result = ERROR_UPDATE;
            //e3.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Realiza la eliminación de un usuario a la base de datos
     * @param user Usuario a eliminar en la BD.
     * @return Código de resultado de la operación. 1 si la eliminación fue
     * exitosa, 2 si el archivo de configuración no se encuentra, 3 si hubo problema
     * al leer el archivo de configuración, 6 si el usuario no pudo eliminarse.
     */
    
    public int deleteUser(User user) {
        int result = SUCCESS;
        
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
            
            //Obtenemos un constructor de eliminación usando el dao.
            DeleteBuilder<User, String> deleteBuilder = userDao.deleteBuilder();
            //Esblecemos un criterio para saber a qué tuplas se aplicará la eliminación.
            deleteBuilder.where().eq("userName", user.getUserName());
            //Elimina la tupla.
            deleteBuilder.delete();
        } catch (FileNotFoundException e1) {
            result = ERROR_DBCONFIG_FILE;
            //e1.printStackTrace();
        } catch (IOException e2) {
            result = ERROR_DBCONFIG_LECTURE;
            //e2.printStackTrace();
        } catch (SQLException e3) {
            result = ERROR_DELETE;
            //e3.printStackTrace();
        }
        
        return result;
    }
    
}
