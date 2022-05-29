package Input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import java.net.URL;


public abstract class IO {

    //Given a path (related to resources), this function returns an InputStream object
    public static InputStream file(String path) {

        InputStream stream;
        URL url;

        try {
            url = IO.class.getResource(path);
            assert url != null;
            stream = url.openStream();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return stream;
    }

    //Given a path to a file, this function will return a Scanner that can read the given file
    public static Scanner scan(String path) {

        InputStream inputStream = file(path);

        if (inputStream == null) return null;
        else return new Scanner(inputStream);

    }

    //Given a path to an image, this function will return a BufferedImage that contains the contents of said image
    public static BufferedImage image(String path) {

        BufferedImage bufferedImage;
        URL url;

        try {
            url = IO.class.getResource(path);
            assert url != null;
            bufferedImage = ImageIO.read(url);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return bufferedImage;
    }

    //Given an array of integers, create a text file that represents the array
    public static void arrayToFile(int[] array, String filepath) {
        //Create the file
        try {
            File file = new File(filepath);

            if (file.createNewFile()) {
                if (file.canWrite()) {
                    //Write
                    FileWriter writeFile = new FileWriter(filepath);
                    for (int num : array) {
                        writeFile.write(num);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Given a 2D-array of integers, create a text file that represents the array
    public static void arrayToFile(int[][] array, String filepath) {
        int rows = array.length;
        int cols = array[0].length;
        BufferedWriter writeFile = null;
        //Create the file
        try {
            writeFile = new BufferedWriter(new FileWriter(filepath));

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    writeFile.write(array[r][c] + "");
                }
                writeFile.write("\n");
            }
            writeFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Given a 2D-array of characters, create a text file that represents the array
    public static void arrayToFile(char[][] array, String filepath) {
        int rows = array.length;
        int cols = array[0].length;
        BufferedWriter writeFile = null;
        //Create the file
        try {
            writeFile = new BufferedWriter(new FileWriter(filepath));

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    writeFile.write(array[r][c] + "");
                }
                writeFile.write("\n");
            }
            writeFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String filepath) {
        File file = new File(filepath);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    public static boolean renameFile(String currentFilepath, String newFilepath) {
        File file = new File(currentFilepath);
        if (file.exists())
            return file.renameTo(new File(newFilepath));
        return false;
    }
}
