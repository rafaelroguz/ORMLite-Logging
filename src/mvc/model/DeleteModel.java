package mvc.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * La clase DeleteModel recibe las peticiones de eliminación de usuario desde la
 * clase DeleteController, y las redirige a la clase UserDao para ser procesadas.
 * Retorna el codigo del resultado del intento de inserción.
 * @author Rafael Rodríguez Guzmán
 */

public class DeleteModel {

    private final String SUCCESS = "SUCCESS"; 
    private final Log log = LogFactory.getLog(DeleteModel.class);
    
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
     * Recibe la petición de eliminación de usuario de DeleteController y la 
     * redirige a UserDao. Si la eliminación fue exitosa, realiza el registro 
     * del evento en la bitácora.
     * @param user Usuario a eliminar en la BD.
     * @return Código de resultado de la operación, retornado por UserDao.
     */
    
    public String deleteUser(User user) {
        UserDao dao = new UserDao();
        String resultCode = dao.deleteUser(user);
        
        if (resultCode.equals(SUCCESS)) {
            log.info(user + "$DELETE");
        } 
        
        return resultCode;
    }
    
}
