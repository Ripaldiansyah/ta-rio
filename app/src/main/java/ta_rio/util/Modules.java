package ta_rio.util;

import java.io.File;

public class Modules {
    // Aktifin kalo
    // mau generate aja

    public static void main(String[] args) {
    generateModule();
    }

    private static void generateModule() {
        String[] modules = {
                "login",
                "user",
                "home",
                "alternative",
                "criteria",
                "spk",
                "report",
                "dashboard",
                "spk_count",
                "spk_result"
        };

        for (String module : modules) {
            createDirectories("app/src/main/java/ta_rio/" + module + "/model");
            createDirectories("app/src/main/java/ta_rio/" + module + "/view");
            createDirectories("app/src/main/java/ta_rio/" + module + "/controller");
            createDirectories("app/src/main/java/ta_rio/" + module + "/dao");
            createDirectories("app/src/main/java/ta_rio/" + module + "/service");
        }

    }

    private static void createDirectories(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directories created: " + path);
            } else {
                System.out.println("Failed to create directories: " + path);
            }
        } else {
            System.out.println("Directories already exist: " + path);
        }
    }
}
