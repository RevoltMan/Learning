package Files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Test3202 {
    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Khoor#Dpljr#&C,₷B'3");
        System.out.println(decode(reader, -3));  //Hello Amigo #@)₴?$0
    }

    public static String decode(StringReader reader, int key) throws IOException {
        String result = "";
        if (reader != null) {
            BufferedReader br = new BufferedReader(reader);
            char[] ch = new char[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = br.read(ch)) != -1) {
                for (int i = 0; i < len; i++)
                    sb.append((char) (ch[i] + key));
            }
            result = sb.toString();
        }
        return result;
    }
}
