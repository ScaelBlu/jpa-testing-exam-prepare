package videos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserRepository {

    private EntityManagerFactory factory;

    public UserRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public User saveUser(User user) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(user);
            manager.getTransaction().commit();
            return user;
        } finally {
            manager.close();
        }
    }

    public User findUserWithVideos(long userId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.videos WHERE u.id = ?1",
                    User.class)
                    .setParameter(1, userId)
                    .getSingleResult();
        } finally {
            manager.close();
        }
    }

    public void updateUserWithVideo(long userId, Video video) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            User user = manager.getReference(User.class, userId);
            user.addVideo(video);
            manager.persist(video);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

    public User updateUserStatus(long userId, UserStatus status) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            User user = manager.getReference(User.class, userId);
            user.setUserStatus(status);
            manager.getTransaction().commit();
            return user;
        } finally {
            manager.close();
        }
    }

    public List<User> findUsersWithMoreVideosThan(int amount) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.videos WHERE SIZE(u.videos) > ?1",
                    User.class)
                    .setParameter(1, amount)
                    .getResultList();
        } finally {
            manager.close();
        }
    }
}
