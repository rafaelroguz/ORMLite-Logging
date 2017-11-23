package mvc.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * La clase User es el modelo del usuario a registrar. Consiste en atributos para
 * nombre de usuario (que debe ser único), nombre, apellido y correo electrónico.
 * @author Rafael Rodríguez Guzmán
 */

@DatabaseTable(tableName = "user")
public class User {
    
    //El atributo "name" será usado como clave primaria en la tabla "user".
    @DatabaseField(id = true, columnName = "userName")
    private String userName;
    //El atributo firstName tendrá una columna "firstName" en la tabla.
    @DatabaseField(canBeNull = false, columnName = "firstName")
    private String firstName;
    //El atributo lastName tendrá una columna "lastName" en la tabla.
    @DatabaseField(canBeNull = false, columnName = "lastName")
    private String lastName;
    //El atributo email tendrá una columna "email" en la tabla.
    @DatabaseField(canBeNull = false, columnName = "email")
    private String email;
    
    public User() {
        userName = "";
        firstName = "";
        lastName = "";
        email = "";
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "USER_DATA=$" + userName + "$" + firstName + "$" + lastName + "$" + email;
    }
    
}
