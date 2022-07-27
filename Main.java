import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    protected static void newDir(String path) {
        tempCheck();
        File file = new File(path);
        if (file.mkdir()) {
            setGoodLog(file);
        } else {
            setBadLog(file);
        }
    }

    protected static void delete(String path) {
        File file = new File(path);
        if (path.equals("Games/temp/temp.txt")|path.equals("Games/temp")|path.equals("Games")) {
            if (file.delete()) {
                setGoodLog(file);
            } else {
                setBadLog(file);
            }
        } else {
            tempCheck();
            if (file.delete()) {
                setGoodLog(file);
            } else {
                setBadLog(file);
            }
        }
    }

    protected static void newFile(String name) {
        tempCheck();
        File file = new File(name);
        try {
            if (file.createNewFile()) {
                setGoodLog(file);
            } else {
                setBadLog(file);
            }
        } catch (IOException e) {
            System.out.println(file + " creation issue");
        }
    }

    protected static String getTime() {
        return String.valueOf(java.time.LocalDateTime.now());
    }

    protected static void recordLog(byte[] bytes) {
        try (FileOutputStream ous = new FileOutputStream("Games/temp/temp.txt", true)) {
            ous.write(bytes);
        } catch (FileNotFoundException e) {
            System.out.println("File temp.txt not found");
        } catch (IOException e) {
            System.out.println("File temp.txt issue");
        }
    }

    protected static void setGoodLog(File file) {
        String success = getTime() + " ... " + file + " created\n";
        byte[] bytes = success.getBytes(StandardCharsets.UTF_8);
        recordLog(bytes);
    }

    protected static void setBadLog(File file) {
        String fail = getTime() + " ... " + file + " failed to create\n";
        byte[] bytes = fail.getBytes(StandardCharsets.UTF_8);
        recordLog(bytes);
    }

    public static void tempCheck() {
        File file = new File("Games/temp/temp.txt");
        File file1 = new File("Games/temp");
        File file2 = new File("Games");
        if (file.exists()) {
        } else if (file1.exists()) {
            file.mkdir();
            setGoodLog(file);
        } else if (file2.exists()) {
            file1.mkdir();
            file.mkdir();
            setGoodLog(file1);
            setGoodLog(file);
        } else {
            file2.mkdir();
            file1.mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
            setGoodLog(file2);
            setGoodLog(file1);
            setGoodLog(file);
        }
    }

    public static void main(String[] args) {
        newDir("Games/src");
        newDir("Games/res");
        newDir("Games/savegames");
        newDir("Games/src/main");
        newDir("Games/src/test");
        newFile("Games/src/main/Main.java");
        newFile("Games/src/main/Utils.java");
        newDir("Games/res/drawables");
        newDir("Games/res/vectors");
        newDir("Games/res/icons");
    }

}