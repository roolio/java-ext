"""
* Template by Bell Eapen
* https://nuchange.ca
"""
package com.openfaas.function;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

import hex.genmodel.easy.RowData;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.prediction.*;
import hex.genmodel.MojoModel;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.*;

// Your function imported from GitHub
// import in.co.dermatologist.YourClass;

public class Handler implements com.openfaas.model.IHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();
        Gson gson = new Gson();

        try{

            """
            Example Input:
            curl -X POST -H "Content-Type: text/plain" --data "{Payload: ["Z71.0", "0"]}" http://localhost:8089/
            """
            Received received = gson.fromJson(req.getBody(), Received.class);
            """
            Change model filename here.
            Add the model to the root folder with the function name
            in the same folder as build.gradle and settings.gradle.
            """
            String h2oModel = System.getenv("MODEL_PATH") + "model.zip";

            EasyPredictModelWrapper.Config config = new EasyPredictModelWrapper.Config()
                    //.setModel(MojoModel.load("/home/bell/Documents/work/temp/2019-11-04_13:55:10.zip"))
                    .setModel(MojoModel.load(h2oModel))
                    .setEnableContributions(true);
            //.setEnableLeafAssignment(true);
            EasyPredictModelWrapper model = new EasyPredictModelWrapper(config);

            RowData row = new RowData();
            Iterator payloadIterator = received.payload().iterator();
            while(payloadIterator.hasNext()) {
                row.put(payloadIterator.next().toString(), payloadIterator.next().toString());
            }
            BinomialModelPrediction p = model.predictBinomial(row);
            received.setPrediction(p.label);
            for (int i = 0; i < p.classProbabilities.length; i++) {
                received.setProbability(p.classProbabilities[i]);
            }

            """
            Example output:
            {"Prediction":"0","Probability":[0.9368596161735153,0.06314038382648467],"Payload":["Z71.0","0"]}
            """
            res.setBody(gson.toJson(received));
        }
    	catch(Exception e){
 
            // Display HTML form.
    	}

	    return res;
    }
}

"""
Request is mapped into an instance of this class
Create a class for response like this.
This template uses the same class for both.
"""
class Received {
    private String Prediction;
    private ArrayList<Double> Probability = new ArrayList<>();            ;

    private ArrayList<String> Payload;

    Received (){}

    public String prediction() { return this.Prediction; }
    public ArrayList<String> payload() { return this.Payload; }
    public void setPayload(ArrayList<String> payload){
        this.Payload = payload;
    }
    public void setPrediction(String prediction){
        this.Prediction = prediction;
    }
    public void setProbability(Double probability){
        this.Probability.add(probability);
    }

}