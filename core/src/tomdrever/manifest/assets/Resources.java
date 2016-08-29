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
    private static HashMap<String, Resource> resources = new HashMap<String, Resource>();

    private static final HashMap<String, ResourceType> resourceTypeMap;
    static {
        resourceTypeMap = new HashMap<String, ResourceType>();
        resourceTypeMap.put("png", ResourceType.TEXTURE);
        resourceTypeMap.put("jpg", ResourceType.TEXTURE);
        resourceTypeMap.put("mp3", ResourceType.AUDIO);
        resourceTypeMap.put("txt", ResourceType.TEXT);
        resourceTypeMap.put("json", ResourceType.TEXT);
        resourceTypeMap.put("fnt", ResourceType.FONT);
    }

    private enum ResourceType { TEXTURE, AUDIO, TEXT, FONT }

    public static void loadResources() {
        resources = new HashMap<String, Resource>();

        // Get current directory (including lone files)
        // This is recursive, looping down all directories in the assets folder
        loadResourceFromDirectory(Gdx.files.internal(".").toString());
    }

    private static void loadResourceFromDirectory(String directoryName) {
        File directory = new File(directoryName);

        File[] fileList = directory.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    // Load asset from each file
                    String fileType = Files.getFileExtension(file.getName());
                    String resourceNameSuffix = "";
                    Resource resource = null;
                    switch (resourceTypeMap.get(fileType)) {
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
                            // Quickly read text from file
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
                        // Get the name of all files above, between this file and the top level of the assets directory
                        File tempFileForIterating = file;
                        String directoryPrefix = "";
                        while (!tempFileForIterating.getParentFile().getName().equals(".")) {
                            directoryPrefix += tempFileForIterating.getParentFile().getName().toUpperCase()
                                    + "_";

                            tempFileForIterating = tempFileForIterating.getParentFile();
                        }

                        // Format resource name, removing file extension and adding type identifier.
                        String resourceName = directoryPrefix + file.getName().replace("." + fileType, "").toUpperCase()
                                + resourceNameSuffix;

                        resources.put(resourceName, resource);
                    }
                } else if (file.isDirectory()) {
                    // Load resources from subdirectory
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
        } else {
            return  resource;
        }
    }

    public static void disposeOfResources() {
        for (Resource resource: resources.values()) {
            resource.dispose();
        }
    }
}
