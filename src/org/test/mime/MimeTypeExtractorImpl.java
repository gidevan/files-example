package org.test.mime;

import java.util.HashMap;
import java.util.Map;

public class MimeTypeExtractorImpl implements MimeTypeExtractor {

    private Map<String, String> mimeMaps = new HashMap<>();

    public MimeTypeExtractorImpl() {
        this.mimeMaps.put("html", "text/html");
        this.mimeMaps.put("png", "image/png");
    }

    @Override
    public String getMimeType(String fileName) {
        String extension = getExtension(fileName);
        return getMimeTypeValue(extension);
    }

    private String getMimeTypeValue(String extension) {
        String mimeType = mimeMaps.get(extension);
        if(mimeType != null) {
            return mimeType;
        }
        throw new IllegalArgumentException("It is impossible to get mime type by extension: [" + extension + "]");
    }

    private String getExtension(String fileName) {
        if(fileName.length() > 0) {
            int dotSymbolIndex = fileName.lastIndexOf(".");
            int slashSymbolIndex = fileName.lastIndexOf("/");
            if(dotSymbolIndex == -1) {
                throw new IllegalArgumentException("File [" + fileName + "] doesn't have extension");
            } else if(dotSymbolIndex > slashSymbolIndex) {
                String extension = fileName.substring(dotSymbolIndex + 1);
                return extension;
            } else {
                throw new IllegalArgumentException("Wrong fileName: [" + fileName + "]");
            }

        }
        throw new IllegalArgumentException("Wrong file name. File name couldn't be empty");
    }
}
