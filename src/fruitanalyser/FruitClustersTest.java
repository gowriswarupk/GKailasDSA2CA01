package fruitanalyser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FruitClustersTest {
    FruitClusters Map;
    ArrayList<Integer> arrayList;

    @BeforeEach
    void createNewHashMap() {
        Map = new FruitClusters();
        arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.add(6);
        Map.map.put(1, arrayList);
    }


    @Test
    void totalFruits(){
        assertEquals(1, Map.totalFruits(), "Method totalFruits() in FruitClusters is not calculating the total number of fruits correctly!");
    }

    @BeforeEach

    @Test
    void createHashMap() {
    }

    @Test
    void createSizeArray() {
    }

    @Test
    void createSizeHashMap() {
    }

    @Test
    void sortByValue() {
    }

    @Test
    void sizeRanked() {
    }

    @Test
    void getClusterSize() {
    }

    @Test
    void randomlyColourClusters() {
    }

    @Test
    void colourAll() {
    }
}