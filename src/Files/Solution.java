package Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static java.nio.file.Files.write;

/* 
Продвинутый поиск файлов
*/

public class Solution extends SimpleFileVisitor<Path> {

    public static void main(String[] args) {
        List<String> list = new CustomTree();

        for (int i = 1; i < 16; i++) {
            list.add(String.valueOf(i));
        }

        System.out.println("The list size is " + list.size());
        System.out.println("The expected parent is 3. The actual parent is " + ((CustomTree) list).getParent("8"));
        System.out.println("The expected parent is null. The actual parent is " + ((CustomTree) list).getParent("20"));

        list.remove("3");
        System.out.println("The list size is " + list.size());
        System.out.println("The expected parent is null. The actual parent is " + ((CustomTree) list).getParent("8"));

        list.add("16");
        System.out.println("The expected parent is 9. The actual parent is " + ((CustomTree) list).getParent("16"));
        System.out.println("The list size is " + list.size());

        list.remove("4");
        System.out.println("Удалил 4");
        System.out.println("The list size is " + list.size());
        list.remove("5");
        System.out.println("Удалил 5");
        System.out.println("The list size is " + list.size());
        list.remove("6");
        System.out.println("Удалил 6");
        System.out.println("The list size is " + list.size());
        System.out.println("Expected: true. Actual: " + list.add("20"));
        System.out.println("The list size is " + list.size());
        System.out.println("The expected parent is 1. The actual parent is " + ((CustomTree) list).getParent("20"));
        System.out.println("The expected parent is null. The actual parent is " + ((CustomTree) list).getParent("129"));
        System.out.println("The expected parent is null. The actual parent is " + ((CustomTree) list).getParent("200"));
    }
/*
    public static void main(String[] args) throws IOException {
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        final Solution solution = new Solution();
        Files.walkFileTree(Paths.get("g:\\Java\\"), options, 20, solution);

        List<String> result = solution.getArchived();
        System.out.println("All archived files:");
        for (String path : result) {
            System.out.println("\t" + path);
        }

        List<String> failed = solution.getFailed();
        System.out.println("All failed files:");
        for (String path : failed) {
            System.out.println("\t" + path);
        }
    }

    private List<String> archived = new ArrayList<>();
    private List<String> failed = new ArrayList<>();

    public List<String> getArchived() {
        return archived;
    }

    public List<String> getFailed() {
        return failed;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String fileName = file.getFileName().toString();
        if (fileName.substring(fileName.length()-4).toUpperCase().equals(".RAR") ||
                fileName.substring(fileName.length()-4).toUpperCase().equals(".ZIP"))
            archived.add(file.toString());

        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        archived.add(file.toString());
        return FileVisitResult.SKIP_SUBTREE;
    }
*/
}
