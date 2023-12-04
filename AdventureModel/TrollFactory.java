package AdventureModel;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

public class TrollFactory implements Serializable {
    private final HashSet<String> availableTrolls;

    public TrollFactory() {
        availableTrolls = new HashSet<>();
        parseTrolls();
    }

    /*
     * Returns a Troll object matching the given String
     * Throws a ClassNotFoundException if the given Troll is not found
     */
    public Troll createTroll(String trollType) throws ClassNotFoundException {
        if (!availableTrolls.contains(trollType)) {
            throw new ClassNotFoundException("Troll not in directory");
        }
        String className = "AdventureModel.Trolls." + trollType + "." + trollType;
        Class<?> trollClass = Class.forName(className);
        return getTroll(trollClass);
    }

    /*
     * Given a Class that implements the Troll interface, return an instance of that class
     */
    private static Troll getTroll(Class<?> trollClass) throws ClassNotFoundException {
        Troll troll;
        try {
            troll = (Troll) trollClass.getDeclaredConstructor().newInstance();
        } catch(ClassCastException e) {
            throw new ClassNotFoundException("Not a Troll!");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException();
        }
        return troll;
    }

    /*
     * Looks for Troll classes in the AdventureModel.Trolls folder
     * Each Troll class name is added to availableTrolls
     */
    private void parseTrolls() {
        File dir = new File("AdventureModel" + File.separator + "Trolls");
        File[] directoryListing = dir.listFiles();
        if(directoryListing == null) { return; }

        for(File f : directoryListing) { processFile(f); }
    }

    /*
     * Takes a Directory and tries to find a Troll class with the same name.
     * Adds the class name to availableTrolls
     */
    private void processFile(File directory) {
        if(!directory.isDirectory()) { return; }
        String className = directory.getName();
        File[] includedFiles = directory.listFiles();
        if(includedFiles == null) { return; }

        for(File f : includedFiles) {
            checkName(f, className);
        }
    }

    /*
     * Given a file, check if that file matches the given className
     * If so, add name to a HashMap availableTrolls
     */
    private void checkName(File f, String className) {
        String fileName = f.getName();
        if(fileName.startsWith(className)) {
            availableTrolls.add(className);
        }
    }
}
