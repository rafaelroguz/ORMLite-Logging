package mvc.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserModel {
    
    private static final int SUCCESS_REGISTER = 0;
    private static final int ERROR_FILE_NOT_FOUND = 1;
    private static final int ERROR_FILE_LECTURE = 2;
    private static final int ERROR_REGISTERED_USER = 3;   
    private final Log log = LogFactory.getLog(UserModel.class);   
    private final String BITACORA = "bitacora.log";
    
    /**
     * Lee la bitácora línea por línea.
     * @param file: Nombre y ruta del archivo donde se encuentra la bitácora.
     * @return Arreglo de Strings con las líneas de la bitácora.
     */
    
    private ArrayList<String> readLogbook(String file) {    
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        ArrayList<String> logbook = new ArrayList<String>();
        
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
                     
            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                String IGNORE_LINE = "TableUtils";
                
                if (!currentLine.contains(IGNORE_LINE)) {
                    logbook.add(currentLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                        bufferedReader.close();

                if (fileReader != null)
                        fileReader.close();
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        }
        
        return logbook;
    }
    
    /**
     * Recupera la información del usuario contenida en la línea de entrada.
     * @param logbookLine: Línea de la bitácora en donde buscar campo de usuario
     * o contraseña.
     * @return Campo de usuario o contraseña.
     */
    
    private String getUserField(String logbookLine) {
        String FIELD_INDICATOR = "@";
        int fieldIndex = logbookLine.indexOf(FIELD_INDICATOR) + 1;
        String field = logbookLine.substring(fieldIndex);
        
        return field;
    }
      
    /**
     * Realiza la inserción de un nuevo usuario a la base de datos
     * @param userName: Nombre de usuario a registrar.
     * @param password: Contraseña a registrar.
     * @return Código de resultado de la operación. 0 si se realizó el registro
     * exitoso, 1 si el archivo de configuración no se encuentra, 2 si hubo problema
     * al leer el archivo de configuración, 3 si el usuario ya está registrado.
     */
    
    private int insertInDB(String userName, String password) {
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
    
    /**
     * Recibe la petición de registro de nuevo usuario y la envía al método adecuado.
     * En caso de registro exitoso, guarda un registro del usuario en al bitácora
     * de respaldo.
     * @param userName: Nombre de usuario a registrar.
     * @param password: Contraseña a registrar.
     * @return Código de resultado de la operación. 0 si se realizó el registro
     * exitoso, 1 si el archivo de configuración no se encuentra, 2 si hubo problema
     * al leer el archivo de configuración, 3 si el usuario ya está registrado.
     */
    
    public int registerUser(String userName, String password) {
        int result = insertInDB(userName, password);
        
        if (result == SUCCESS_REGISTER) {
            log.info("@" + userName);
            log.info("@" + password);
        }    
        
        return result;
    }
    
    public boolean replicateUsers() {
        ArrayList<String> logbook = readLogbook(BITACORA);
        
        getUserField(logbook.get(0));
        
        return true;
    }
    
}
