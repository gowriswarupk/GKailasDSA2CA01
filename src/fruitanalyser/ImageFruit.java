package fruitanalyser;

//Imports
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

import java.io.FileInputStream;
import java.util.*;

public class ImageFruit {

    Image originalImage, finalImage, borderedImage;

    int w, h;

    PixelReader pr;
    PixelWriter pw;
    WritableImage wi;

    public ImageFruit(FileInputStream file, int w, int h){
        this.originalImage = new Image(file, w, h, false, true); //last two bool variables for preserve ratio and smoothness
        this.finalImage = originalImage;
        this.w = (int)finalImage.getWidth();
        this.h = (int)finalImage.getHeight();
        this.pr = finalImage.getPixelReader();
        this.wi = new WritableImage(pr, w, h);
        this.pw = wi.getPixelWriter();
        this.borderedImage = finalImage;

    }

    public void drawCLusterBorder(int root, HashMap<Integer, ArrayList<Integer>> map){

        List<Integer> tmpList = map.get(root);
        int furthestLeftPixel = root;
        int furthestRightPixel = root;
        int bottomPixel = tmpList.get(tmpList.size()-1);
        for (int i : tmpList){
            furthestLeftPixel = i >= tmpList.size() ? calcFurtherLeftPixel(furthestLeftPixel, i) : furthestLeftPixel;
            furthestRightPixel = i >= tmpList.size() ? calcFurtherRightPixel(furthestRightPixel, i) : furthestRightPixel;
        }
        int leftX = calcXFromIndex(furthestLeftPixel),
                rightX = calcXFromIndex(furthestRightPixel),
                topY = calcYFromIndex(root),
                bottomY = calcYFromIndex(bottomPixel);
        drawBorder(leftX, rightX, topY, bottomY);
        borderedImage = wi;
    }

    //Calculating pixel distance follows:

    //furthest left:
    public int calcFurtherLeftPixel(int posA, int posB) {
        return posA %w < posB%w ? posA : posB;
    }

    //furthest right:
    public int calcFurtherRightPixel(int posA, int posB) {
        return posA%w > posB%w ? posA : posB;
    }

    //X from index

    public int calcXFromIndex (int i) {
        return i%w;
    }

    //Y from index

    public int calcYFromIndex(int i){
        return i/w;
    }

    public void drawBorder(int leftX, int rightX, int topY, int bottomY){
        for (int x = leftX; x <= rightX; x++){
            pw.setColor(x, topY, Color.RED);
        }
        for (int x = rightX; x >= leftX; x--){
            pw.setColor(x, bottomY, Color.RED);
        }
        for (int y = topY; y <= bottomY; y++){
            pw.setColor(rightX, y, Color.RED);
        }
        for (int y = bottomY; y >= topY; y--){
            pw.setColor(leftX, y, Color.RED);
        }
    }



    public void setFinalImage(Image imageX){
        this.finalImage = imageX;
    }

    public void setBorderedImage(Image imageY){
        this.borderedImage = imageY;
    }

    public void resetFinalImage(){
        finalImage = originalImage;
        pr = finalImage.getPixelReader();
        wi = new WritableImage(pr, w, h);
        pw = wi.getPixelWriter();
    }

    //WIP::to control hue, saturation and brightness using sliders:

    public void editImage(ColorAdjust ca){
        for (int y = 0; y < h ; y++){
            for (int x=0; x < w ; x++){
                Color colour = pr.getColor(x,y);
                colour = colour.deriveColor(ca.getHue(), ca.getSaturation(), ca.getBrightness(),1);
                pw.setColor(x,y,colour);
            }
        }
    }

}
