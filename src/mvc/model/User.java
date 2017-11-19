package mvc.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {
    
    //El atributo "name" será usado como clave primaria en la tabla "user".
    @DatabaseField(id = true)
    private String name;
    //El atributo "password" tendrá una columna en la tabla "user".
    @DatabaseField
    private String password;
    
    public User() {}
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
}
