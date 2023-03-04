package videos;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTestIT {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
    private UserRepository uRep = new UserRepository(factory);
    private UserService service = new UserService(uRep);

    @Test
    public void testUploadVideo() {
        User user = new User("John Doe");

        uRep.saveUser(user);

        service.uploadVideo(user.getId(), new Video("Kristof's favourite movie"));

        User found = uRep.findUserWithVideos(user.getId());

        assertThat(found.getUserStatus()).isEqualTo(UserStatus.BEGINNER);
        assertThat(found.getVideos())
                .hasSize(1)
                .extracting(Video::getTitle)
                .containsOnly("Kristof's favourite movie");
    }

    @Test
    public void testUploadTheFifthVideo() {
        User user = new User("John Doe");

        uRep.saveUser(user);

        service.uploadVideo(user.getId(), new Video("Best Video In The World"));
        service.uploadVideo(user.getId(), new Video("Better Than Best Video"));
        service.uploadVideo(user.getId(), new Video("Better Than Better Than Best Video"));
        service.uploadVideo(user.getId(), new Video("Must see this before you die"));

        assertThat(uRep.findUserWithVideos(user.getId()).getUserStatus()).isEqualTo(UserStatus.BEGINNER);

        service.uploadVideo(user.getId(), new Video("Kristof's favourite movie"));

        assertThat(uRep.findUserWithVideos(user.getId()).getUserStatus()).isEqualTo(UserStatus.ADVANCED);
    }
}