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
    
    private final String SUCCESS = "SUCCESS"; 
    private final Log log = LogFactory.getLog(RegisterModel.class);   
    
    /**
     * Recibe la petición de registro de usuario de RegisterController y la redirige
     * a UserDao. Si el registro fue exitoso, realiza el registro del evento en la
     * bitácora.
     * @param user Usuario a registrar en la BD.
     * @return Código de resultado de la operación, retornado por UserDao.
     */
    
    public String registerUser(User user) {
        UserDao dao = new UserDao();
        String resultCode = dao.insertUser(user);
        
        if (resultCode.equals(SUCCESS)) {
            log.info(user + "$REGISTER");
        }    
        
        return resultCode;
    }
    
}
