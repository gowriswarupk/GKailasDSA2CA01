package fruitanalyser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayForFruitTest {
    ArrayForFruit fruitArray;

    @BeforeEach
    void createArray(){
        fruitArray = new ArrayForFruit(5,5);
        fruitArray.array[0] = 4;
        fruitArray.array[1] = 2;
        fruitArray.array[2] = 3;
        fruitArray.array[3] = 1;
        fruitArray.array[4] = -1;
        fruitArray.array[5] = 1;
        fruitArray.array[6] = 3;
        fruitArray.array[7] = 2;
        fruitArray.array[8] = -1;
        fruitArray.array[9] = 1;
        fruitArray.array[10]  =3;
    }

    @BeforeEach


    @Test
    void pixelIsWhite() {
        assertTrue(fruitArray.whitePixel(4));
    }

    @Test
    void find() {
        fruitArray.unionFruitPixels();
        assertEquals(1, fruitArray.find(fruitArray.array, 3));
    }

    @Test
    void calcArrayPosition() {
    }

    @Test
    void addFruitToArray() {
    }

    @Test
    void addNonFruitToArray() {
    }
}