package mapper;

import constant.FileExtension;
import constant.MimeType;

import java.util.HashMap;
import java.util.Map;

/**
 * MimeMapper maps a string file extension (".txt") to a string MIME type ("text/plain").
 */
public class MimeMapper {
    public static Map<FileExtension, MimeType> extensionMimeTypeMap = new HashMap<>();

    {
        extensionMimeTypeMap.put(FileExtension.AVI, MimeType.AVI);
        extensionMimeTypeMap.put(FileExtension.BIN, MimeType.BIN);
        extensionMimeTypeMap.put(FileExtension.BMP, MimeType.BMP);
        extensionMimeTypeMap.put(FileExtension.BOOK, MimeType.BOOK);
        extensionMimeTypeMap.put(FileExtension.CLASS, MimeType.CLASS);
        extensionMimeTypeMap.put(FileExtension.COM, MimeType.COM);
        extensionMimeTypeMap.put(FileExtension.CPP, MimeType.CPP);
        extensionMimeTypeMap.put(FileExtension.CSS, MimeType.CSS);
        extensionMimeTypeMap.put(FileExtension.DL, MimeType.DL);
        extensionMimeTypeMap.put(FileExtension.DOC, MimeType.DOC);
        extensionMimeTypeMap.put(FileExtension.EXE, MimeType.EXE);
        extensionMimeTypeMap.put(FileExtension.HTM, MimeType.HTM);
        extensionMimeTypeMap.put(FileExtension.HTML, MimeType.HTML);
        extensionMimeTypeMap.put(FileExtension.HTMLS, MimeType.HTMLS);
        extensionMimeTypeMap.put(FileExtension.JAVA, MimeType.JAVA);
        extensionMimeTypeMap.put(FileExtension.JPEG, MimeType.JPEG);
        extensionMimeTypeMap.put(FileExtension.JPG, MimeType.JPG);
        extensionMimeTypeMap.put(FileExtension.JS, MimeType.JS);
        extensionMimeTypeMap.put(FileExtension.MOVIE, MimeType.MOVIE);
        extensionMimeTypeMap.put(FileExtension.MP2, MimeType.MP2);
        extensionMimeTypeMap.put(FileExtension.MP3, MimeType.MP3);
        extensionMimeTypeMap.put(FileExtension.MPA, MimeType.MPA);
        extensionMimeTypeMap.put(FileExtension.MPEG, MimeType.MPEG);
        extensionMimeTypeMap.put(FileExtension.PS, MimeType.PS);
        extensionMimeTypeMap.put(FileExtension.PY, MimeType.PY);
        extensionMimeTypeMap.put(FileExtension.TEXT, MimeType.TEXT);
    }

    public String getContentTypeForFileName(String fileExtension) {
        String contentType;

        try {
            FileExtension extension = FileExtension.valueOf(fileExtension);
            MimeType mimeType =  extensionMimeTypeMap.get(extension);
            contentType = mimeType.contentType();

        } catch(IllegalArgumentException | NullPointerException e) {
            // if there is no valid FileExtension or name is null
            e.printStackTrace();
            contentType = MimeType.DEFAULT.contentType();

        }

        return contentType;
    }
}
