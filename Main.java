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
            setGoodLog(file, "create");
        } else {
            setBadLog(file, "create");
        }
    }

    protected static void delete(String path) {
        File file = new File(path);
        tempCheck();
        if (file.delete()) {
            setGoodLog(file, "delete");
        } else {
            setBadLog(file, "delete");
        }
    }

    protected static void newFile(String name) {
        tempCheck();
        File file = new File(name);
        try {
            if (file.createNewFile()) {
                setGoodLog(file, "create");
            } else {
                setBadLog(file, "create");
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

    protected static void setGoodLog(File file, String action) {
        String success = getTime() + " ... " + file + " " + action + "d\n";
        byte[] bytes = success.getBytes(StandardCharsets.UTF_8);
        recordLog(bytes);
    }

    protected static void setBadLog(File file, String action) {
        String fail = getTime() + " ... " + file + " failed to " + action + "\n";
        byte[] bytes = fail.getBytes(StandardCharsets.UTF_8);
        recordLog(bytes);
    }

    public static void tempCheck() {
        File file = new File("Games/temp/temp.txt");
        File file1 = new File("Games/temp");
        File file2 = new File("Games");
        if (file.exists()) {
        } else if (file1.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
            setGoodLog(file, "create");
        } else if (file2.exists()) {
            file1.mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
            setGoodLog(file1, "create");
            setGoodLog(file, "create");
        } else {
            file2.mkdir();
            file1.mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
            setGoodLog(file2, "create");
            setGoodLog(file1, "create");
            setGoodLog(file, "create");
        }
    }

    public static void main(String[] args) throws Exception {

        // ???????????? 1: ?????????????????? (?????????????? ????????????????????, ???????????? ?? Main. ?????? ???? ?????????????????????? ?????????????????????? ?? Games/temp/temp.txt)

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

        // ???????????? 2: ???????????????????? ?? ?????????????????????????? (?????????????? ???????????? ?? ?????????????????? ????????????????. ???????????? ?? GameProgress)

        GameProgress player1 = new GameProgress(100, 1, 1, 0);
        GameProgress player2 = new GameProgress(80, 3, 5, 200);
        GameProgress player3 = new GameProgress(35, 12, 20, 1200);
        player1.saveGame();
        player2.saveGame();
        player3.saveGame();

        GameProgress.zipSavedGame();

        // ???????????? 3: ???????????????? (?????????????????? ???????????? ???????????????????? ?? ?????????????? ???????????? ?? ??????????????. ???????????? ?? GameProgress)

        GameProgress.unZipSavedGame();

    }
}