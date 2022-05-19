package fruitanalyser;

// Imports
import javafx.scene.image.*;
import javafx.scene.paint.*;

public class BlackAndWhite {

    Image bwImage, colourImage;

    PixelReader pr;
    PixelWriter pw;
    WritableImage wi;

    double hueDiff, satDiff, brightDiff;

    ArrayForFruit fruitArray;


    public BlackAndWhite(Image image, Color fruitColour, int width, int height ){
        this.bwImage = image;
        this.pr = bwImage.getPixelReader(); // Pixel reader enabled to read the selected portion
        this.wi = new WritableImage(pr, width, height);
        this.pw = wi.getPixelWriter();
        this.hueDiff = 360;
        this.satDiff = 0.4;
        this.brightDiff = 0.3;
        this.fruitArray = new ArrayForFruit(width,height);
        this.bwImage = createBWImage(fruitColour);
        this.colourImage = bwImage;
    }

    public Image createBWImage(Color fruitColour) {
        boolean moreRed = decideRGBofFruit(fruitColour.getRed(), fruitColour.getGreen(), fruitColour.getBlue());
        for (int y= 0; y<bwImage.getHeight(); y++){
            for (int x= 0; x< bwImage.getWidth(); x++){
                Color pixelColour = pr.getColor(x,y);
                if (moreRed){
                    moreRedFruitRecog(fruitColour, pixelColour, x,y);
                }
                else{
                    moreBlueFruitRecog(fruitColour, pixelColour, x,y);
                }
            }
        }
        fruitArray.unionFruitPixels();
        colourImage = bwImage;
        return wi;
    }

    public void moreRedFruitRecog(Color fruitColour, Color pixelColour, int x, int y){
        double r = pixelColour.getRed();
        if(pixelIsPartOfFruit(fruitColour, pixelColour) && r > pixelColour.getGreen() && r > pixelColour.getBlue()){
            pw.setColor(x,y,Color.WHITE);
            fruitArray.addFruitToArray(x,y);
        }
        else {
            pw.setColor(x,y,Color.BLACK);
            fruitArray.addNonFruitToArray(x,y);
        }
    }

    public void moreBlueFruitRecog(Color fruitColour, Color pixelColour, int x, int y){
        double b = pixelColour.getBlue();
        if(pixelIsPartOfFruit(fruitColour, pixelColour) && b > pixelColour.getGreen() && b > pixelColour.getBlue()){
            pw.setColor(x,y,Color.WHITE);
            fruitArray.addFruitToArray(x,y);
        }
        else {
            pw.setColor(x,y,Color.BLACK);
            fruitArray.addNonFruitToArray(x,y);
        }
    }

    public boolean decideRGBofFruit(double r, double g, double b){  //This allows to interpret the proportion of red as a boolean response.
        return r > g && r > b;
    }

    public boolean pixelIsPartOfFruit(Color fruitColour, Color pixelColour){  //this allows for the initial selection to be confirmed as part of fruit by getting the relative diff.
        return compareHue(fruitColour.getHue(), pixelColour.getHue(), hueDiff) &&
                compareSat(fruitColour.getSaturation(), pixelColour.getSaturation(), satDiff) &&
                compareBrightness(fruitColour.getBrightness(), pixelColour.getBrightness(), brightDiff);
    }

    public boolean compareHue(double firstHue, double secondHue, double diff){
        return (Math.abs(firstHue-secondHue) < diff);
    }

    public boolean compareSat(double firstSat, double secondSat, double diff){
        return (Math.abs(firstSat-secondSat) < diff);
    }

    public boolean compareBrightness(double firstBrightness, double secondBrightness, double diff){
        return (Math.abs(firstBrightness-secondBrightness) < diff);
    }


    //auto generated getter and setter methods:

    public double getHueDiff() {
        return hueDiff;
    }

    public void setHueDiff(double hueDiff) {
        this.hueDiff = hueDiff;
    }

    public double getSatDiff() {
        return satDiff;
    }

    public void setSatDiff(double satDiff) {
        this.satDiff = satDiff;
    }

    public double getBrightDiff() {
        return brightDiff;
    }

    public void setBrightDiff(double brightDiff) {
        this.brightDiff = brightDiff;
    }
}
