package cowradio.microservicesongs.exceptions;

public class DuplicateAlbumException extends RuntimeException {
    public DuplicateAlbumException(String message, String code) {
        super(message);
    }
}
