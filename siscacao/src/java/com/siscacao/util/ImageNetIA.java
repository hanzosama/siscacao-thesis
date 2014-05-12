/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;

/**
 *
 * @author Hanzo
 */
public  class ImageNetIA {
    NeuralNetwork nnet ;

    public ImageNetIA() {
        nnet= NeuralNetwork.load("C:\\Users\\Hanzo\\Desktop\\proyecto de grado\\tesis\\siscacao\\redNeuronal\\net_cacao.nnet");
    }
    
    
    
  public Map<String, Double> getImageDiag(String path) throws IOException{
        System.out.println("Analizando imagen: " +path);
        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin)nnet.getPlugin(ImageRecognitionPlugin.class); // get the image recognition plugin from neural network         
         // Ejecutar la función de reconocimiento de imagenes para una ruta especifica
      
        HashMap<String, Double> output = imageRecognition.recognizeImage(new File(path));
        return output;
   
    }
    
}
