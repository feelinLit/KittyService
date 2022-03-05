import com.itmo.kotiki.dao.KittyDao;
import com.itmo.kotiki.dao.KittyFriendshipDao;
import com.itmo.kotiki.dao.PersonDao;
import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.entity.Person;
import com.itmo.kotiki.service.KittyService;
import com.itmo.kotiki.service.PersonService;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import java.time.LocalDate;
import java.time.Month;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }

        PersonService personService = new PersonService(new PersonDao());
        KittyService kittyService = new KittyService(new KittyDao(), new KittyFriendshipDao());

        Kitty kitty1 = new Kitty("Chartreux", "Sharik", Color.Black, LocalDate.of(2002, Month.JULY, 20), null);
        Kitty kitty2 = new Kitty("Maine Coon", "Kvadratik", Color.White, LocalDate.of(2010, Month.DECEMBER, 1), null);
        Kitty kitty3 = new Kitty("Himalayan", "Ryzhik", Color.NonBinary, LocalDate.of(2015, Month.SEPTEMBER, 6), null);
        kittyService.persist(kitty1);
        kittyService.persist(kitty2);
        kittyService.persist(kitty3);

        Person person1 = new Person("Vasya", LocalDate.of(2000, Month.AUGUST, 3), kitty1);
        person1.addKitty(kitty2);
        Person person2 = new Person("Vladimir", LocalDate.of(1950, Month.FEBRUARY, 28), null);
        personService.persist(person1);
        personService.persist(person2);

        kittyService.update(kitty1);
        kittyService.update(kitty2);

        Kitty kitty4 = new Kitty("Obdolbysh", "Fish", Color.Black, LocalDate.of(2021, Month.MARCH, 22), person1);
        kittyService.persist(kitty4);

        kittyService.addFriendship(kitty1, kitty2);
        kittyService.addFriendship(kitty1, kitty3);
    }
}