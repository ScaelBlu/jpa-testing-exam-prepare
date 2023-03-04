package videos;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
    private UserRepository uRep = new UserRepository(factory);

    @Test
    public void testSaveAndFindUser() {
        User user = new User("John Doe");

        uRep.saveUser(user);

        User found = uRep.findUserWithVideos(user.getId());

        assertThat(user.getId()).isNotNull();
        assertThat(found)
                .isNotNull()
                .extracting(User::getName, User::getUserStatus)
                .containsExactly("John Doe", UserStatus.BEGINNER);
    }

    @Test
    public void testUpdateUserWithVideo() {
        User user = new User("John Doe");

        uRep.saveUser(user);
        uRep.updateUserWithVideo(user.getId(), new Video("Kristof's favourite movie"));

        User found = uRep.findUserWithVideos(user.getId());

        assertThat(found)
                .isNotNull()
                .extracting(User::getUserStatus, u -> u.getVideos().size())
                .containsExactly(UserStatus.BEGINNER, 1);
    }

    @Test
    public void testUpdateUserStatus() {
        User user = new User("John Doe");

        uRep.saveUser(user);
        uRep.updateUserStatus(user.getId(), UserStatus.ADVANCED);

        User found = uRep.findUserWithVideos(user.getId());

        assertThat(found.getUserStatus()).isEqualTo(UserStatus.ADVANCED);
    }

    @Test
    public void testFindUsersWithMoreVideosThan() {
        User john = new User("John Doe");
        User jack = new User("Jack Doe");
        User jim = new User("Jim Doe");

        uRep.saveUser(john);
        uRep.saveUser(jack);
        uRep.saveUser(jim);

        uRep.updateUserWithVideo(john.getId(), new Video("Best Video In The World"));
        uRep.updateUserWithVideo(john.getId(), new Video("Better Than Best Video"));
        uRep.updateUserWithVideo(jack.getId(), new Video("Better Than Better Than Best Video"));
        uRep.updateUserWithVideo(jack.getId(), new Video("Must see this before you die"));
        uRep.updateUserWithVideo(jim.getId(), new Video("Kristof's favourite movie"));

        List<User> result = uRep.findUsersWithMoreVideosThan(1);

        assertThat(result)
                .hasSize(2)
                .extracting(User::getName)
                .containsOnly("John Doe", "Jack Doe");
    }
}