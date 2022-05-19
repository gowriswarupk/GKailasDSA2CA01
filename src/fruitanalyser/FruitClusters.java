package fruitanalyser;

//Imports
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

import java.io.FileInputStream;
import java.util.*;

public class FruitClusters {

    HashMap<Integer, ArrayList<Integer>> map;

    public FruitClusters(){
        this.map = new HashMap<>();
    }

    public void createHashMap(ArrayForFruit fa){
        for (int x=0; x < fa.array.length; x++){
            if (fa.whitePixel(x)){
                int root = fa.find(fa.array, x);
                if (!map.containsKey(root)){
                    ArrayList<Integer> tempList = new ArrayList<>();
                    for (int i = x; i < fa.array.length; i++){
                        if (fa.whitePixel(i) && (fa.find(fa.array, i) == root)){
                            tempList.add(i);
                        }
                    }
                    map.put(root, tempList);
                }
            }
        }
    }

    //creating array with size values to identify:
    //for label with size id, unfinished//

    public ArrayList<Integer> createSizeArray(){
        ArrayList<Integer> arraySizes = new ArrayList<>();
        for (int i : map.keySet()){
            arraySizes.add(map.get(i).size());
        }
        Collections.sort(arraySizes);
        return arraySizes;
    }

    //create Size HashMap:

    public HashMap<Integer, Integer> createSizeHashMap() {
        HashMap<Integer, Integer> createSizeMap = new HashMap<>();
        for (int i : map.keySet()) {
            createSizeMap.put(i, map.get(i).size());
        }
        return createSizeMap;
    }

    //sort by value to be used in the following method:

    public HashMap<Integer, Integer> sortByValue(){
        HashMap<Integer, Integer> valueMap = createSizeHashMap();
        List<HashMap.Entry<Integer, Integer>> list = new LinkedList<>(valueMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        HashMap<Integer, Integer> sortedHashMap = new LinkedHashMap<>();

        for (HashMap.Entry<Integer, Integer> a : list){
            sortedHashMap.put(a.getKey(), a.getValue());
        }
        return sortedHashMap;
    }

    // sorting sets by size:

    public HashMap<Integer, Integer> sizeRanked(){
        HashMap<Integer, Integer> sortedMap = sortByValue();
        int newSize = sortedMap.keySet().size();
        for (int i : sortedMap.keySet()){
            sortedMap.replace(i, newSize);
            newSize--;
        }
        return sortedMap;
    }

    public int totalFruits(){
        return map.keySet().size();
    }

    //get ranks/size:

    public int getClusterSize (int root) {
        return sizeRanked().get(root);
    }


    //todo: colour clusters/individual fruits random colour:

    public void randomlyColourClusters(int root, ImageFruit fruitImage, PixelWriter pw, Color colour){
        ArrayList<Integer> tempArray = map.get(root);
        for (int i : tempArray){
            pw.setColor(fruitImage.calcXFromIndex(i), fruitImage.calcYFromIndex(i), colour);
        }
    }

    //colour all clusters button:

    public void colourAll(BlackAndWhite blackAndWhiteImage, ImageFruit imageFruit){
        PixelReader pixelReader = blackAndWhiteImage.colourImage.getPixelReader();
        WritableImage writableImage = new WritableImage(pixelReader, (int)blackAndWhiteImage.colourImage.getWidth(), (int)blackAndWhiteImage.colourImage.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int i : map.keySet()){
            Random rand = new Random();
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            Color randomColour = new Color (r, g, b, 1);
            randomlyColourClusters(i, imageFruit, pixelWriter, randomColour);
        }
        blackAndWhiteImage.colourImage = writableImage;
    }

}
