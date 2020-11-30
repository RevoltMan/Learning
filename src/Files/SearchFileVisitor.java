package Files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {

    private String partOfName = null;
    private String partOfContent = null;
    private int MinSize = 0;
    private int MaxSize = Integer.MAX_VALUE;
    private List<Path> foundFiles = new ArrayList<>();

    public SearchFileVisitor () {  // Для нового экземпляра зададим значение по умолчанию.
        partOfName = null;
        partOfContent = null;
        MinSize = 0;
        MaxSize = Integer.MAX_VALUE;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.MinSize = minSize;
    }

    public void setMaxSize(int maxSize) {
         this.MaxSize = maxSize;
    }



    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length
        String fileContent;
        boolean bcondit = true; //Считаем, что условия выполненны
        if (content.length >= MinSize && content.length < MaxSize) {
            if (partOfName != null) {
                if (file.getFileName().toString().indexOf(partOfName) == -1)
                    bcondit = false;
            }
            if (partOfContent != null && bcondit) {
                fileContent = new String(content);
                if (fileContent.indexOf(partOfContent) == -1)
                    bcondit = false;
            }
            if (bcondit)
                foundFiles.add(file);
        }


        return super.visitFile(file, attrs);
    }


}
