package rpsystem.Subsystems;

import rpsystem.Objects.CharacterCard;
import rpsystem.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import static rpsystem.Subsystems.UtilitySubsystem.findUUIDBasedOnPlayerName;

public class StorageSubsystem {

    Main main = null;

    public StorageSubsystem(Main plugin) {
        main = plugin;
    }

    public void saveCardFileNames() {
        try {
            File saveFolder = new File("./plugins/MedievalRoleplayEngine/");
            if (!saveFolder.exists()) {
                saveFolder.mkdir();
            }
            File saveFile = new File("./plugins/MedievalRoleplayEngine/" + "cards.txt");
            if (saveFile.createNewFile()) {
                System.out.println("El archivo de guardado para los nombres de archivo de las tarjetas de personaje fue creado.");
            } else {
                System.out.println("Archivo de guardado para los nombres de archivo de las tarjetas ya existe. Sobreescribiendo.");
            }

            FileWriter saveWriter = new FileWriter(saveFile);

            // actual saving takes place here
            for (CharacterCard card : main.cards) {
//                System.out.println("[DEBUG] Saving card with UUID: " + card.getPlayerUUID());
                if (card.getPlayerUUID() != null) {
                    saveWriter.write(card.getPlayerUUID().toString() + ".txt" + "\n");
                }
            }

            saveWriter.close();

        } catch (IOException e) {
            System.out.println("Ocurrió un error tratando de guardar los nombres de archivo de las tarjetas de personaje.");
        }
    }

    public void saveCards() {
        for (CharacterCard card : main.cards) {
            if (card.getPlayerUUID() != null) {
                card.save();
            }
        }
    }

    public void loadCards() {
        try {
            System.out.println("Intentando cargar tarjetas de personaje...");
            File loadFile = new File("./plugins/MedievalRoleplayEngine/" + "cards.txt");
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            while (loadReader.hasNextLine()) {
                String nextFilename = loadReader.nextLine();
                CharacterCard temp = new CharacterCard();
                temp.load(nextFilename);

                // existence check
                int index = -1;
                for (int i = 0; i < main.cards.size(); i++) {
                    if (main.cards.get(i).getPlayerUUID().equals(temp.getPlayerUUID())) {
                        index = i;
                    }
                }
                if (index != -1) {
                    main.cards.remove(index);
                }

                main.cards.add(temp);

            }

            loadReader.close();
            System.out.println("Tarjetas de personaje cargadas.");
        } catch (FileNotFoundException e) {
            System.out.println("¡Error tratando de cargar las tarjetas de personaje!");
            e.printStackTrace();
        }
    }

    public void legacyLoadCards() {
        try {
            System.out.println("Intentando cargar tarjetas de personaje...");
            File loadFile = new File("./plugins/medieval-roleplay-engine/" + "card-player-names.txt");
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            while (loadReader.hasNextLine()) {
                String nextName = loadReader.nextLine();
                CharacterCard temp = new CharacterCard();
                temp.legacyLoad(nextName + ".txt");

                main.cards.add(temp);

            }

            loadReader.close();

            System.out.println("Tarjetas de personaje cargadas.");
        } catch (FileNotFoundException e) {
            System.out.println("¡Error tratando de cargar las tarjetas de personaje!");
            e.printStackTrace();
        }
    }

    // Recursive file delete from https://www.baeldung.com/java-delete-directory
    public boolean deleteLegacyFiles(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteLegacyFiles(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public boolean oldSaveFolderPresent() {
        File saveFolder = new File("./plugins/medieval-roleplay-engine/");
        return saveFolder.exists();
    }

}
