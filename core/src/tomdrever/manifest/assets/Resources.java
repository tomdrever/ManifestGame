package tomdrever.manifest.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class Resources {
    private static HashMap<String, Resource> resources;

    private static final HashMap<String, ResourceType> resourceTypes;
    static {
        resourceTypes = new HashMap<String, ResourceType>();
        resourceTypes.put("png", ResourceType.TEXTURE);
        resourceTypes.put("jpg", ResourceType.TEXTURE);
        resourceTypes.put("mp3", ResourceType.AUDIO);
        resourceTypes.put("txt", ResourceType.TEXT);
        resourceTypes.put("json", ResourceType.TEXT);
        resourceTypes.put("fnt", ResourceType.FONT);
    }

    private enum ResourceType { TEXTURE, AUDIO, TEXT, FONT }

    public static void loadAssets() {
        // Get all files in resources
        // Texture, Font, JSON,
        // Store in dict: "$FILENAME" + "_" + type
        // e.g. "levels.json" -> "LEVELS_TEXT",
        // "debug/fleet.png" -> "DEBUG_FLEET_TEXTURE"
        resources = new HashMap<String, Resource>();

        // Get current directory (including lone files)
        loadResourceFromDirectory(Gdx.files.internal(".").toString());
    }

    private static void loadResourceFromDirectory(String directoryName) {
        // get all files & loadLevels
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    // Load asset from each file
                    String fileType = Files.getFileExtension(file.getName());
                    String resourceNameSuffix = "";
                    Resource resource = null;
                    switch (resourceTypes.get(fileType)) {
                        case TEXTURE:
                            resourceNameSuffix = "_TEXTURE";
                            resource = new Resource<Texture>(new Texture(file.getPath()));
                            break;
                        case AUDIO:
                            resourceNameSuffix = "_AUDIO";
                            resource = new Resource<Sound>(Gdx.audio.newSound(Gdx.files.absolute(file.getPath())));
                            break;
                        case TEXT:
                            resourceNameSuffix = "_TEXT";
                            byte[] encodedText = new byte[0];
                            try {
                                encodedText = java.nio.file.Files.readAllBytes(Paths.get(file.getPath()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            resource = new Resource<String>(new String(encodedText));
                            break;
                        case FONT:
                            resourceNameSuffix = "_FONT";
                            resource = new Resource<BitmapFont>(new BitmapFont(Gdx.files.absolute(file.getPath())));
                            break;
                    }

                    // If the filetype has a resource name extension attached to it...
                    if (!(resourceNameSuffix.isEmpty())) {
                        // Format resource name, removing file extension and adding type identifier.

                        // Get the name of all files above, between this file and Gdx.files.internal(".").toString()
                        File tempFileForIterating = file;
                        String directoryPrefix = "";
                        while (!tempFileForIterating.getParentFile().getName().equals(".")) {
                            directoryPrefix += tempFileForIterating.getParentFile().getName().toUpperCase()
                                    + "_";

                            tempFileForIterating = tempFileForIterating.getParentFile();
                        }

                        String resourceName = directoryPrefix + file.getName().replace("." + fileType, "").toUpperCase()
                                + resourceNameSuffix;

                        resources.put(resourceName, resource);
                    }
                } else if (file.isDirectory()) {
                    // Load resources from sub dir
                    loadResourceFromDirectory(file.getAbsolutePath());
                }
            }
        }
    }

    public static Resource loadResource(String resourceName) {
        Resource resource = resources.get(resourceName);

        if (resource == null) {
            try {
                throw new Exception("Could not find resource with name: " + resourceName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        else {
            return resource;
        }
    }

    public static void disposeOfResources() {
        for (Resource resource: resources.values()) {
            resource.dispose();
        }
    }
}
