package mvc.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * La clase RegisterModel recibe las peticiones de registro de usuario desde la
 * clase RegisterController, y las redirige a la clase UserDao para ser procesadas.
 * Retorna el codigo del resultado del intento de inserción.
 * @author Rafael Rodríguez Guzmán
 */

public class RegisterModel {
    
    private static final int SUCCESS_REGISTER = 1; 
    private final Log log = LogFactory.getLog(UserModel.class);   
    
    /**
     * Recibe la petición de registro de usuario de RegisterController y la redirige
     * a UserDao. Si el registro fue exitoso, realiza el registro del evento en la
     * bitácora.
     * @param user Usuario a registrar en la BD.
     * @return Código de resultado de la operación, retornado por UserDao.
     */
    
    public int registerUser(User user) {
        UserDao dao = new UserDao();
        int resultCode = dao.insertUser(user);
        
        if (resultCode == SUCCESS_REGISTER) {
            log.info(user);
        }    
        
        return resultCode;
    }
    
}
