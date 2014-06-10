/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.nnet.learning.MomentumBackpropagation;

/**
 *
 * @author Hanzo
 */
public class SymptomIA {

    private NeuralNetwork loadedMlPerceptron;

    public SymptomIA() {
        loadNetSymptom();
    }
    
    public void loadNetSymptom() {

        // Crear vector de entrenamiento
        DataSet trainingSet = new DataSet(14, 7);

        trainingSet.addRow(new DataSetRow(new double[]{1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0}, new double[]{1, 0, 0, 0, 0, 0, 0}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}, new double[]{0, 1, 0, 0, 0, 0, 0}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0}, new double[]{0, 0, 1, 0, 0, 0, 0}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, new double[]{0, 0, 0, 1, 0, 0, 0}));
        trainingSet.addRow(new DataSetRow(new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, new double[]{0, 0, 0, 0, 1, 0, 0}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, new double[]{0, 0, 0, 0, 0, 1, 0}));
        trainingSet.addRow(new DataSetRow(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new double[]{0, 0, 0, 0, 0, 0, 1}));

        // Crear vector para de comparación
        DataSet PruebaSet = new DataSet(14, 7);
        PruebaSet.addRow(new DataSetRow(new double[]{1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, new double[]{0, 0, 0, 0, 0, 0, 0}));

        // Crear red neuronal tipo Perceptron multicapa
        MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 14, 4, 7);

        MomentumBackpropagation mbk = new MomentumBackpropagation();

        // Crear primer ciclo de entrenamiento     
        mbk.setMomentum(0.4);
        mbk.setMaxError(0.01);
        mbk.setMaxIterations(1000);
        mbk.setLearningRate(0.6);
        myMlPerceptron.setLearningRule(mbk);

        // Ejecutar primer ciclo de entrenamiento
        myMlPerceptron.learn(trainingSet);

        // Crear segundo ciclo de entrenamiento    
        mbk.setMomentum(0.2);
        mbk.setLearningRate(0.7);
        myMlPerceptron.setLearningRule(mbk);

        // Ejecutar segundo ciclo de entrenamiento
        myMlPerceptron.learn(trainingSet);

        // Realizar prueba completa con la matriz de entrenamiento
        //System.out.println("Testing trained neural network");
        //testNeuralNetwork(myMlPerceptron, trainingSet);

        // Guardar la red neuronal
        myMlPerceptron.save("myMlPerceptron.nnet");

        // Cargar la red neuronal
        loadedMlPerceptron = NeuralNetwork.load("myMlPerceptron.nnet");

        // Ejecutar comparación de dato de entrada con el vector de caracteristicas
        // System.out.println("Testing loaded neural network");
        // testNeuralNetwork(loadedMlPerceptron, PruebaSet);

    }

    public void testNeuralNetwork(NeuralNetwork nnet, DataSet tset) {

        for (DataSetRow dataRow : tset.getRows()) {

            nnet.setInput(dataRow.getInput());
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();
            System.out.println(" Output: " + Arrays.toString(networkOutput));

        }

    }

    public Map<String, Double> getSymptom(DataSet tset) {
        double[] networkOutput = new double[]{0, 0, 0, 0, 0, 0, 0};
        for (DataSetRow dataRow : tset.getRows()) {

            loadedMlPerceptron.setInput(dataRow.getInput());
            loadedMlPerceptron.calculate();
            networkOutput = loadedMlPerceptron.getOutput();
            System.out.print("Input: " + Arrays.toString(dataRow.getInput()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));

        }

        Map<String, Double> resultNet = new HashMap<String, Double>();
        resultNet.put("Monilia", networkOutput[0]);
        resultNet.put("Phytoptora", networkOutput[1]);
        resultNet.put("Escoba de Bruja", networkOutput[2]);
        resultNet.put("Machete", networkOutput[3]);
        resultNet.put("Bubas", networkOutput[4]);
        resultNet.put("Carpintero", networkOutput[5]);
        resultNet.put("Sanas", networkOutput[6]);

        return resultNet;

    }
}
