package videos;

public class UserService {

    private UserRepository uRep;

    public UserService(UserRepository uRep) {
        this.uRep = uRep;
    }

    public void uploadVideo(long id, Video video) {
        User user = uRep.findUserWithVideos(id);
        int countOfVideos = user.getVideos().size();
        if(countOfVideos < 10) {
            uRep.updateUserWithVideo(id, video); //nem növeli a user példány videóinak számát!
            updateUserStatus(id, countOfVideos);
        } else {
            throw new IllegalStateException("The user with id: " + id + " have reached the upload limit.");
        }
    }

    private void updateUserStatus(long id, int countOfVideos) {
        if(countOfVideos == 4) {
            uRep.updateUserStatus(id, UserStatus.ADVANCED);
        }
    }
}
