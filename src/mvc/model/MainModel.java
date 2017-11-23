package mvc.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * La clase MainModel recibe las peticiones de replicación de la BD desde la
 * bitácora y las procesa y ejecuta.
 * @author Rafael Rodríguez Guzmán
 */

public class MainModel {

    private final String FIELD_INDICATOR = "$";
    private final String SQL_REGISTER = "REGISTER";
    private final String SQL_UPDATE = "UPDATE";
    private final String SQL_DELETE = "DELETE";
    private final String SUCCESS = "SUCCESS";
    private final String ERROR_EMPTY_LOGBOOK = "ERROR_EMPTY_LOGBOOK";
    private final String ERROR_REPLICATE = "ERROR_REPLICATE";
    private final String LOGBOOK = "bitacora.log";
    
    /**
     * Recupera a los usuarios y las sentencias sql de la bitácora, y los envía
     * al método que se encarga de seleccionar el método DAO adecuado.
     * @return Código de resultado de la operación.
     */
    
    public String replicate() {
        String result = SUCCESS;
        ArrayList<String> logbookLines = readLogbook(LOGBOOK);
        
        if (logbookLines.isEmpty()) {
            result = ERROR_EMPTY_LOGBOOK;
        } else {
           for (String logbookLine : logbookLines) {
               User user = null;
               String sqlEvent = "";
               
               user = getUser(logbookLine);
               sqlEvent = getSQLEvent(logbookLine);
               
               if ( (user != null) && (!sqlEvent.equals("")) ) {
                   selectDBEvent(user, sqlEvent);
               } else {
                   result = ERROR_REPLICATE;
                   break;
               }
           }
        }
         
        return result;
    }
    
    /**
     * Selecciona el evento SQL adecuado (insert, update o delete) de acuerdo a
     * la consulta sql que se está procesando. Se conecta con UserDao para realizar
     * las operaciones en la BD.
     * @param user Usuario sobre el cuál se ejecutarán las transacciones.
     * @param sqlEvent Sentencia o transacción SQL que se quiere realizar.
     * @return Código de resultado de la operación.
     */
    
    private void selectDBEvent(User user, String sqlEvent) {
        UserDao dao = new UserDao();
        
        switch (sqlEvent) {
            case SQL_REGISTER:
                dao.insertUser(user);
                break;
            case SQL_UPDATE:
                dao.updateUser(user);
                break;
            case SQL_DELETE:
                dao.deleteUser(user);
                break;
        }
    }    
    
    /**
     * Abre el archivo de bitácora y recupera cada línea..
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
                String USER_DATA = "USER_DATA=";
                
                if (currentLine.contains(USER_DATA)) {
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
     * Recupera la información del usuario contenida en la línea de entrada. Usa
     * el caractér "$" para identificar los campos correspondientes a datos del
     * usuario.
     * @param logbookLine Línea de la bitácora en donde buscar los datos del
     * usuario
     * @return Objeto User que representa el usuario.
     */
    
    private User getUser(String logbookLine) {
        int USER_NAME_FIELD = 0;
        int FIRST_NAME_FIELD = 1;
        int LAST_NAME_FIELD = 2;
        int EMAIL_FIELD = 3;
        int NUMBER_OF_FIELDS = 5;
        User user = null;
        
        //Encuentra la posición en la cadena en la que comienzan los campos del usuario.
        int fieldIndex = logbookLine.indexOf(FIELD_INDICATOR) + 1;
        
        //Recupera la porción de la cadena donde se encuentran los datos del usuario.
        String userData = logbookLine.substring(fieldIndex);
        
        //Separa los campos de usuario encontrados en la cadena.
        String userFields[] = userData.split("\\$");
        
        //Comprueba que estén todos los campos de usuario, incluyendo la consulta SQL.
        if (userFields.length == NUMBER_OF_FIELDS) {
            user = new User();
            
            user.setUserName(userFields[USER_NAME_FIELD]);
            user.setFirstName(userFields[FIRST_NAME_FIELD]);
            user.setLastName(userFields[LAST_NAME_FIELD]);
            user.setEmail(userFields[EMAIL_FIELD]);
        }

        return user;
    }
    
    /**
     * Busca en la línea de bitácora qué sentencia sql se registró, la recupera
     * y la retorna en una cadena.
     * @param logbookLine Línea de texto de la bitácora donde se encuentra la
     * sentencia SQL.
     * @return String con la sentencia SQL.
     */
    
    private String getSQLEvent(String logbookLine) {        
        //Representa si la consulta sql es un insert, update o delete. Vacío si
        //no se encontró una instrucción sql.
        String sqlEvent = "";
        
        if (logbookLine.contains(SQL_REGISTER)  || 
            logbookLine.contains(SQL_UPDATE)    || 
            logbookLine.contains(SQL_DELETE)) 
        {    
            //Encuentra la posición en la cadena en la que se encuentra el último campo
            //de datos de usuario, que corresponde a la sentencia sql a ejecutar.
            int fieldIndex = logbookLine.lastIndexOf(FIELD_INDICATOR) + 1;
            //Recupera la porción de la cadena donde se encuentra la sentencia sql.
            sqlEvent = logbookLine.substring(fieldIndex);
        } 

        return sqlEvent;
    }
    
}
