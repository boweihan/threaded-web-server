package constant;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum to represent all supported file extensions. This list is not exhaustive.
 * A full(er) list of file extensions (https://www.sitepoint.com/mime-types-complete-list/).
 */
public enum FileExtension {
    AVI(".avi"),
    BIN(".bin"),
    BMP(".bmp"),
    BOOK(".book"),
    CLASS(".class"),
    COM(".com"),
    CPP(".cpp"),
    CSS(".css"),
    DL(".dl"),
    DOC(".doc"),
    EXE(".exe"),
    HTM(".htm"),
    HTML(".html"),
    HTMLS(".htmls"),
    JAVA(".java"),
    JPEG(".jpeg"),
    JPG(".jpg"),
    JS(".js"),
    MOVIE(".movie"),
    MP2(".mp2"),
    MP3(".mp3"),
    MPA(".mpa"),
    MPEG(".mpeg"),
    PS(".ps"),
    PY(".py"),
    TEXT(".text");

    private final String extension;

    FileExtension(String extension) {
        this.extension = extension;
    }

    public String extension() {
        return extension;
    }

    public static Optional<FileExtension> fromString(String value) {
        return Arrays.stream(values())
                .filter(e -> e.extension().equals(value))
                .findFirst();
    }
}
