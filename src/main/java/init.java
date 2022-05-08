import java.io.File;
import java.util.List;

public class init {
    public static void main(String[] args) {
        Crack crack = new Crack();
        String sourcePath = crack.readPath();
        crack.crackFiles(sourcePath);
        System.out.println("破解成功");
    }

    private static void testCrackFile(Crack crack) {
        String sourcePath = "";
        String targetPath = "";
        String result = crack.crackFile(new File(sourcePath), new File(targetPath));
        System.out.println(result);
    }

    private static void testSearchFile(Crack crack) {
        String rootPath = "";
        List<File> fileList = crack.searchFiles(rootPath);
        System.out.println(fileList);
    }
}
