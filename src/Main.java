import org.test.file.FileCommentProcessor;
import org.test.file.FileCommentProcessorImpl;
import org.test.mime.MimeTypeExtractor;
import org.test.mime.MimeTypeExtractorImpl;

public class Main {

    private static final String FILE_NAMES[] = {"index1.html", "image1.png", "index.index", "home/index.html/",
            "node/image.png/dir", "", "ddd"};

    public static void main(String[] args) {
        //getMimeTypes();
        removeComments();
    }

    private static void getMimeTypes() {
        System.out.println("Mime types");
        MimeTypeExtractor extractor = new MimeTypeExtractorImpl();
        for(String fileName : FILE_NAMES) {
            try {
                String mime = extractor.getMimeType(fileName);
                System.out.println("fileName: [" + fileName + "] mime type: [" + mime + "]");
            } catch(Exception e) {
                System.out.println("fileName: [" + fileName + "] mime type: [unknown]: exception message: " + e.getMessage());
            }
        }
    }

    private static void removeComments() {
        FileCommentProcessor processor = new FileCommentProcessorImpl();
        processor.processFile("resources/one-row-comment.txt");
        processor.processFile("resources/multi-row-comment1.txt");
        processor.processFile("resources/multi-row-comment2.txt");
    }
}
