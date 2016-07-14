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

public class Assets {
    private static HashMap<String, Resource> assets;

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
        // Get all files in assets
        // Load (based on type TODO - list types))
        // Texture, Font, JSON,
        // Store in dict: "$FILENAME" + "_" + type
        // e.g. "debug.png" -> "DEBUG_TEXTURE"
        assets = new HashMap<String, Resource>();

        // Get current directory (including lone files)
        loadAssetsFromDirectory(Gdx.files.internal(".").toString());
    }

    private static void loadAssetsFromDirectory(String directoryName) {
        // get all files
        // load
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    // Load asset from each file
                    String fileType = Files.getFileExtension(file.getName());
                    String resourceNameExtension = "";
                    Resource resource = null;
                    switch(resourceTypes.get(fileType)){
                        case TEXTURE:
                            resourceNameExtension = "_TEXTURE";
                            resource = new Resource<Texture>(new Texture(file.getPath()));
                            break;
                        case AUDIO:
                            resourceNameExtension = "_AUDIO";
                            resource = new Resource<Sound>(Gdx.audio.newSound(Gdx.files.absolute(file.getPath())));
                            break;
                        case TEXT:
                            resourceNameExtension = "_TEXT";
                            byte[] encodedText = new byte[0];
                            try {
                                encodedText = java.nio.file.Files.readAllBytes(Paths.get(file.getPath()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            resource = new Resource<String>(new String(encodedText));
                            break;
                        case FONT:
                            resourceNameExtension = "_FONT";
                            resource = new Resource<BitmapFont>(new BitmapFont(Gdx.files.absolute(file.getPath())));
                            break;
                    }

                    // If the filetype has a resource name extension attached to it...
                    if (!(resourceNameExtension.isEmpty())) {
                        // Format resource name, removing file extension and adding type identifier.
                        String resourceName = file.getName().replace("." + fileType, "").toUpperCase()
                                + resourceNameExtension;

                        assets.put(resourceName, resource);
                    }
                } else if (file.isDirectory()) {
                    // Load assets from sub dir
                    loadAssetsFromDirectory(file.getAbsolutePath());
                }
            }
        }
    }

    public static Resource getAsset(String assetName) {
        Resource asset = assets.get(assetName);

        if (asset == null) {
            try {
                throw new Exception("Could not find asset with name: " + assetName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        else {
            return asset;
        }
    }
}
