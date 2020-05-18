package constant;

/**
 * Enum to represent all supported MIME types. This list is not exhaustive.
 * A full(er) list of MIME types (https://www.sitepoint.com/mime-types-complete-list/).
 */
public enum MimeType {
    AVI("video/avi"),
    BIN("application/octet-stream"),
    BMP("image/bmp"),
    BOOK("application/book"),
    CLASS("application/java"),
    COM("application/octet-stream"),
    CPP("text/x-c"),
    CSS("text/css"),
    DL("video/dl"),
    DOC("application/msword"),
    EXE("application/octet-stream"),
    HTM("text/html"),
    HTML("text/html"),
    HTMLS("text/html"),
    JAVA("text/x-java-source"),
    JPEG("image/jpeg"),
    JPG("image/jpg"),
    JS("application/javascript"),
    MOVIE("video/x-sgi-movie"),
    MP2("audio/mpeg"),
    MP3("audio/mpeg3"),
    MPA("audio/mpeg"),
    MPEG("video/mpeg"),
    PS("application/postscript"),
    PY("text/x-script.python"),
    TEXT("application/plain"),
    DEFAULT("text/plain");

    private final String contentType;

    MimeType(String contentType) {
        this.contentType = contentType;
    }

    public String contentType() {
        return contentType;
    }
}
