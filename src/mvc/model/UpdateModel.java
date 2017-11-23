package mvc.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * La clase UpdateModel recibe las peticiones de actualización de usuario desde la
 * clase UpdateController, y las redirige a la clase UserDao para ser procesadas.
 * Retorna el codigo del resultado del intento de inserción.
 * @author Rafael Rodríguez Guzmán
 */

public class UpdateModel {

    private static final int SUCCESS_UPDATE = 1; 
    private final Log log = LogFactory.getLog(UserModel.class);   
    
    /**
     * Recibe la petición de búsqueda de un usuario en la BD por su nombre de
     * usuario y la redirige a UserDao.
     * @param userName Nombre de usuario con el cuál queremos ubicar una tupla en
     * la BD.
     * @return Objeto User si el usuario fue encontrado, null en caso contrario.
     */
    
    public User findUser(String userName) {
        UserDao dao = new UserDao();
        User user = dao.findUserByUserName(userName);
        
        return user;
    }
    
    /**
     * Recibe la petición de actualización de usuario de UpdateController y la 
     * redirige a UserDao. Si la actualización fue exitosa, realiza el registro 
     * del evento en la bitácora.
     * @param user Usuario a actualizar en la BD.
     * @return Código de resultado de la operación, retornado por UserDao.
     */
    
    public int updateUser(User user) {
        UserDao dao = new UserDao();
        int resultCode = dao.updateUser(user);
        
        if (resultCode == SUCCESS_UPDATE) {
            log.info(user + "$@UPDATE");
        } 
        
        return resultCode;
    }
    
}
