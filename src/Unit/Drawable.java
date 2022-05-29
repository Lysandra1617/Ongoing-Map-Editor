package Unit;

import Physics.V2D;
import Utilities.Draw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static Input.IO.image;

public interface Drawable {
    //This stores the tile images
    final static HashMap<Integer, BufferedImage> TILE_IMAGES = new HashMap<>();
    final static HashMap<Integer, BufferedImage> DECORATION_IMAGES = new HashMap<>();

    //This loads the images
    static void load() {
        loadImages("/Map/Tiles.png", TILE_IMAGES, 16, 16, 2);
        loadImages("/Map/Decorations.png", DECORATION_IMAGES, 16, 16, 2);
    }

    //This function takes a file path to an image, dissects it, and loads all the images into that image into a hash map
    static void loadImages(String path, HashMap<Integer, BufferedImage> storage, int w, int h, int imageGap) {

        //Aggregate Image
        BufferedImage aggImages = image(path);

        //Width and height of the aggregate image
        int aggImagesWidth = aggImages.getWidth(), aggImagesHeight = aggImages.getHeight();

        int x = 0, y = 0;
        int index = storage.size();

        while (x < aggImagesWidth && y < aggImagesHeight) {

            BufferedImage details = aggImages.getSubimage(x, y, w, h);
            storage.put(index, details);

            index++;
            x += w + imageGap;

            //If we've reached the end of the image, start again but go down some
            if (x >= aggImagesWidth) {
                x = 0;
                y += h + imageGap;
            }
        }
    }
    static void draw(BufferedImage image, float x, float y, float w, float h, Graphics2D g) {
        Draw.drawImage((int)x, (int)y, (int)w, (int)h, image, g);
    }
    static void draw(BufferedImage image, V2D position, V2D size, Graphics2D g) {
        Draw.drawImage((int) position.x, (int) position.y, (int) size.x, (int) size.y, image, g);
    }
}
