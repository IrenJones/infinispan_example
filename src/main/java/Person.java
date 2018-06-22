import lombok.Data;

import java.util.Date;

@Data
public class Person {
    String name;
    String surname;
    Date birthday;

    public Person(String name, String surname, Date birthday){
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }
}