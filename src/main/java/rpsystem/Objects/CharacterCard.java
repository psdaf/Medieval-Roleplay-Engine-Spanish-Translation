package rpsystem.Objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import static rpsystem.Subsystems.UtilitySubsystem.findUUIDBasedOnPlayerName;

public class CharacterCard {
    private UUID playerUUID = null;
    private String name = "defaultName";
    private String race = "defaultRace";
    private String subculture = "defaultSubculture";
    private int age = 0;
    private String gender = "defaultGender";
    private String religion = "defaultReligion";

    public CharacterCard(UUID uuid) {
        playerUUID = uuid;
    }

    // storage constructor
    public CharacterCard() {

    }

    void setPlayerUUID(UUID newUUID) {
        playerUUID = newUUID;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setName(String newUUID) {
        name = newUUID;
    }

    public String getName() {
        return name;
    }

    public void setRace(String newRace) {
        race = newRace;
    }

    public String getRace() {
        return race;
    }

    public void setSubculture(String newSubculture) {
        subculture = newSubculture;
    }

    public String getSubculture() {
        return subculture;
    }

    public void setAge(int newAge) {
        age = newAge;
    }

    public int getAge() {
        return age;
    }

    public void setGender(String newGender) {
        gender = newGender;
    }

    public String getGender() {
        return gender;
    }

    public void setReligion(String newReligion) {
        religion = newReligion;
    }

    public String getReligion() {
        return religion;
    }

    public boolean save() {
        try {
            File saveFolder = new File("./plugins/MedievalRoleplayEngine/");
            if (!saveFolder.exists()) {
                saveFolder.mkdir();
            }
            File saveFile = new File("./plugins/MedievalRoleplayEngine/" + playerUUID + ".txt");
            if (saveFile.createNewFile()) {
                System.out.println("Archivo de guardado de la tarjeta de personaje de " + playerUUID + " fue creada.");
            } else {
                System.out.println("Archivo de guardado de la tarjeta de personaje de " + playerUUID + " ya existe. Alterando.");
            }

            FileWriter saveWriter = new FileWriter("./plugins/MedievalRoleplayEngine/" + playerUUID + ".txt");

            // actual saving takes place here
            saveWriter.write(playerUUID.toString() + "\n");
            saveWriter.write(name + "\n");
            saveWriter.write(race + "\n");
            saveWriter.write(subculture + "\n");
            saveWriter.write(age + "\n");
            saveWriter.write(gender + "\n");
            saveWriter.write(religion + "\n");

            saveWriter.close();

            return true;

        } catch (IOException e) {
            System.out.println("Ocurrió un error tratando de guardar la tarjeta de personaje de " + playerUUID);
            e.printStackTrace();
            return false;
        }
    }

    public boolean load(String filename) {
        try {
            File loadFile = new File("./plugins/MedievalRoleplayEngine/" + filename);
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            if (loadReader.hasNextLine()) {
                setPlayerUUID(UUID.fromString(loadReader.nextLine()));
            }
            if (loadReader.hasNextLine()) {
                setName(loadReader.nextLine());
            }
            if (loadReader.hasNextLine()) {
                setRace(loadReader.nextLine());
            }
            if (loadReader.hasNextLine()) {
                setSubculture(loadReader.nextLine());
            }
            if (loadReader.hasNextLine()) {
                setAge(Integer.parseInt(loadReader.nextLine()));
            }
            if (loadReader.hasNextLine()) {
                setGender(loadReader.nextLine());
            }
            if (loadReader.hasNextLine()) {
                setReligion(loadReader.nextLine());
            }

            loadReader.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Ocurrió un error tratando de cargar el archivo " + filename + ".");
            e.printStackTrace();
            return false;
        }
    }

    public boolean legacyLoad(String filename) {
        try {
            File loadFile = new File("./plugins/medieval-roleplay-engine/" + filename);
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            if (loadReader.hasNextLine()) {
                setPlayerUUID(findUUIDBasedOnPlayerName(loadReader.nextLine()));
            }
            if (loadReader.hasNextLine()) {
                setName(loadReader.nextLine());
            }
            if (loadReader.hasNextLine()) {
                setRace(loadReader.nextLine());
            }
            if (loadReader.hasNextLine()) {
                setSubculture(loadReader.nextLine());
            }
            if (loadReader.hasNextLine()) {
                setAge(Integer.parseInt(loadReader.nextLine()));
            }
            if (loadReader.hasNextLine()) {
                setGender(loadReader.nextLine());
            }
            if (loadReader.hasNextLine()) {
                setReligion(loadReader.nextLine());
            }

            loadReader.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Ocurrió un error tratando de cargar el archivo " + filename + ".");
            e.printStackTrace();
            return false;
        }
    }

}
