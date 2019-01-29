package thrift.generated;

import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2019/1/13
 */
public class PersonServiceImpl implements PersonService.Iface {

    private static List<Person> personList = new ArrayList<>();
    static {
        Person p = new Person();
        p.setUserName("Jabari");
        p.setAge(32);
        p.setMarried(true);
        personList.add(p);
        p = new Person();
        p.setUserName("LGJ");
        p.setAge(22);
        p.setMarried(false);
        personList.add(p);
    }

    @Override
    public Person getPersonByUserName(String userName) throws DataException, TException {
        System.out.println("------getPersonByUserName()...userName:" + userName);
        Person p = new Person();
        for (Person person : personList) {
            if (userName.trim().equalsIgnoreCase(person.getUserName().trim())) {
                p = person;
                break;
            }
        }
        System.out.println("------getPersonByUserName()...end!");
        return p;
    }

    @Override
    public int savePerson(Person p) throws DataException, TException {
        System.out.println("-------savePerson()....begin:");
        personList.add(p);
        System.out.println("-------savePerson()....end!");
        return 1;
    }
}
