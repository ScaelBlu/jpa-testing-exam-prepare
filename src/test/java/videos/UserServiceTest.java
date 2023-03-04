package videos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository uRep;

    @InjectMocks
    private UserService service;

    @Test
    public void testUploadVideo() {
        when(uRep.findUserWithVideos(anyLong()))
                .thenReturn(new User("John Doe", Set.of(
                        new Video("1"),
                        new Video("2"),
                        new Video("3"),
                        new Video("4"),
                        new Video("5"),
                        new Video("6"),
                        new Video("7"),
                        new Video("8"),
                        new Video("9"),
                        new Video("10")
                )));

        assertThrows(IllegalStateException.class,
                () -> service.uploadVideo(3L, new Video("11")),
                "The user with id: 3 have reached the upload limit.");

        verify(uRep).findUserWithVideos(3L);
        verify(uRep, never()).updateUserWithVideo(anyLong(), any());
    }
}