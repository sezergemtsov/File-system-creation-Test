import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int health;
    private final int weapons;
    private final int lvl;
    private final double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    protected static void zipSavedGame() {

        File file = new File("Games/savegames");
        File fileZip = new File("Games/savegames/Save.zip");

        if (!fileZip.exists()) {
            Main.newFile(fileZip.getPath());
        }

        FilenameFilter f1 = (File dir, String name) -> name.contains(".dat");
        File[] saveGamesList = file.listFiles(f1);

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(fileZip))) {
            for (File fileN :
                    saveGamesList) {
                FileInputStream fis = new FileInputStream(fileN);
                ZipEntry entry = new ZipEntry("zipped_" + fileN.getName());
                zout.putNextEntry(entry);
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                zout.write(bytes);
                zout.closeEntry();
                fis.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File fileN :
                saveGamesList) {
            Main.delete(fileN.getPath());
        }
    }

    protected static void unZipSavedGame() {

        File fileZip = new File("Games/savegames/Save.zip");

        if (fileZip.exists()) {
            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip))) {
                ZipEntry entry = zis.getNextEntry();
                String name = "Games/savegames/" + entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fout.write(c);
                }
                fout.flush();
                zis.closeEntry();
                fout.close();
                System.out.println(readZip(name));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("There is no saved games");
        }


    }

    protected static GameProgress readZip(String path) {
        try (FileInputStream fis = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameProgress) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    protected void saveGame() {
        File file = new File("Games/savegames");

        if (file.exists()) {
            int n = 1;
            while (true) {
                String saveGamePath = "Games/savegames/" + n + "save.dat";
                File save = new File(saveGamePath);
                if (!save.exists()) {
                    Main.newFile(saveGamePath);
                    try (FileOutputStream fos = new FileOutputStream(save)) {
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(this);
                    } catch (FileNotFoundException e) {
                    } catch (IOException e) {
                    }
                    break;
                }
                n++;
            }
        } else {
            System.out.println("Save not completed. Game directory is incomplete");
        }
    }

}
