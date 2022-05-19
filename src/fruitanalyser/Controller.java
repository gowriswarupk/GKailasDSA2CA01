package fruitanalyser;

import com.sun.javafx.iio.ImageFrame;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.fxml.FXML;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;


public class Controller extends Component {


    // FXML components declarations
    @FXML
    public ImageView originalImage, bwImage;

    @FXML
    public Button imageUpload, findClusters, resetButton;

    @FXML
    public RadioButton colourAllRB;

    @FXML
    public Slider hueSlider, satSlider, brightnessSlider;

    @FXML
    public TextField clusterOutput;

    //other declarations

    Color fruitColour;
    ImageFruit colourImage, blackWhiteImage;
    PixelReader pr;
    PixelWriter pw;
    WritableImage wi;
    BlackAndWhite blackAndWhite;

    ArrayForFruit arrayForFruit;

    FruitClusters clusters;

    //loadImage method allows for user to select an image from system.

    public void loadImage() throws FileNotFoundException {

        resetScene(); //Allows for existing image if any to be cleared as part of reset process
        FileChooser fc = new FileChooser();
        //.getExtensionFilter used to ensure only filetypes accepted are: JPEG/JPG/PNG
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg"), new FileChooser.ExtensionFilter("PNG Files", "*.png"), new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
        File file = fc.showOpenDialog(null);
        colourImage = new ImageFruit(new FileInputStream(file), (int)originalImage.getFitWidth(), (int)originalImage.getFitHeight());
        originalImage.setImage(colourImage.originalImage);

    }

    public void resetScene() {
        //resets scene either at start of application or upon pressing button.
        originalImage.setImage(null);
        bwImage.setImage(null);


        //needs slider reset values once integrated :done
        hueSlider.setValue(0);
        satSlider.setValue(1);
        brightnessSlider.setValue(1);

    }

    public void getColourAtMouse(javafx.scene.input.MouseEvent mouseEvent) {
        if (originalImage != null) {
            fruitColour = originalImage.getImage().getPixelReader().getColor((int) mouseEvent.getX(), (int) mouseEvent.getY());
            if (bwImage.getImage() == null) {
                displayBWImage();
            }
        }
    }

    public void displayBWImage(){
        try{
            initBWImage();
            bwImage.setImage(blackAndWhite.bwImage);
            colourImage.setBorderedImage(colourImage.finalImage);
            originalImage.setImage(colourImage.borderedImage);

        }
        catch (Exception e){
            if (originalImage == null){
                JOptionPane.showMessageDialog(this, "Please choose an Image!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if (fruitColour == null){
                JOptionPane.showMessageDialog(this, "Please click on a fruit!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void initBWImage(){
        blackAndWhite = new BlackAndWhite(colourImage.finalImage, fruitColour, colourImage.w, colourImage.h);
    }



    //WIP:: Slider Config for initial setting and later edits: done

    public void sliderSetting(){
        ColorAdjust ca = new ColorAdjust();
        //set values
        ca.setHue(hueSlider.getValue());
        ca.setSaturation(satSlider.getValue());
        ca.setBrightness(brightnessSlider.getValue());
        colourImage.editImage(ca);
        colourImage.setFinalImage(colourImage.wi);
        originalImage.setImage(colourImage.finalImage);
        displayBWImage();

    }


    //todo: drawing pixel borders around clusters and showing count?
    //WIP:done

    public void drawFrames(){
        try{
            colourImage.resetFinalImage();
            clusters = new FruitClusters();
            clusters.createHashMap(blackAndWhite.fruitArray);

            for (int i : clusters.map.keySet()){
                colourImage.drawCLusterBorder(i, clusters.map);
            }
            originalImage.setImage(colourImage.borderedImage);
            colourAll();
            clusterOutput.setText(clusters.totalFruits() + "");
        }
        catch (Exception e){
            if (originalImage == null){
                JOptionPane.showMessageDialog(this, "Please choose an Image!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    //todo: color individual/all fruits :
    //WIP:

    public void colourAll(){
        try{
            clusters.colourAll(blackAndWhite, colourImage);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "Please identify the fruit!",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showColoured(){
        if(originalImage.getImage()!=null && bwImage.getImage()!=null) {
             if (colourAllRB.isSelected()){
                if (blackAndWhite.colourImage == blackAndWhite.bwImage) {
                    JOptionPane.showMessageDialog(this, "Please select a fruit!",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else bwImage.setImage(blackAndWhite.colourImage);
             }
             else {
                 bwImage.setImage(blackAndWhite.bwImage);
             }
        }
        else {
            JOptionPane.showMessageDialog(this, "Please choose an Image!",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void colourIndividualCluster(javafx.scene.input.MouseEvent event){
        blackAndWhite.colourImage = blackAndWhite.bwImage;
        int x = (int)event.getX();
        int y = (int)event.getY();
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColour = new Color (r, g, b, 1);
        clusters.randomlyColourClusters(blackAndWhite.fruitArray.find(blackAndWhite.fruitArray.array, blackAndWhite.fruitArray.calcArrayPosition(y, x)), colourImage, blackAndWhite.pw, randomColour);
        bwImage.setImage(blackAndWhite.colourImage);
    }

}