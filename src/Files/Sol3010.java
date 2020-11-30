package Files;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

//import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class Sol3010 {
    public static void main(String[] args) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(args[1]));
        ZipOutputStream zipOutputStream = null;
        Path path = Paths.get(args[0]);
        HashMap<ZipEntry, ByteArrayOutputStream> map = new HashMap<ZipEntry, ByteArrayOutputStream>();
        ZipEntry zipBbuffer;
        String sFutureFileName = "NEW/" + path.getFileName().toString().toUpperCase();
            //пишем zip архив в мапу
        while ((zipBbuffer = zipInputStream.getNextEntry()) != null) {
            if (!(zipBbuffer.getName().toUpperCase().equals(sFutureFileName))) { // таким образом я не буду запоминать то, что потом я писать не буду.
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int length = 0;
                byte[] buffer = new byte[1024 * 1024];
                try {
                    while ((length = zipInputStream.read(buffer)) != -1) { // Заполняю поток байтами
                        baos.write(buffer, 0, length);
                    }
                    map.put(zipBbuffer, baos);
                } catch (Exception e) {
                    System.out.println("Ошибка");
                }
            }
        }
        zipInputStream.close();

        //Начинаем запись в новый архив
        zipOutputStream = new ZipOutputStream(new FileOutputStream(args[1]));

        //Пишем все имеющиеся рание в архиве файлы в архив
         for (HashMap.Entry<ZipEntry, ByteArrayOutputStream> current : map.entrySet()) {
             zipOutputStream.putNextEntry(new ZipEntry(current.getKey().getName()));
             zipOutputStream.write(current.getValue().toByteArray());
             System.out.println(current.getKey().getName() + " размер " + current.getKey().getSize() ) ;
             try {
                 zipOutputStream.closeEntry();
             } catch (Exception e) {
                 System.out.println(e.getCause());
             }
         }

                //String newFileNameWithDirectory = "new" + File.separator + path.getFileName().toString();
        ZipEntry zipEntry = new ZipEntry(sFutureFileName);
        zipOutputStream.putNextEntry(zipEntry);
        Files.copy(Paths.get(args[0]), zipOutputStream);

        zipOutputStream.close();

    }
}
