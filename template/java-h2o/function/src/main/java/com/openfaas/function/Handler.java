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

// Your function imported from GitHub
import in.co.dermatologist.YourClass;

public class Handler implements com.openfaas.model.IHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();
        Gson gson = new Gson();

        try{

            // Request Bundle
            // Bundle bundle_in = parser.parseResource(Bundle.class,req.getBody());
            Received received = gson.fromJson(req.getBody(), Received.class);
            String model = System.getenv("MODEL_PATH") + "model.zip";

            EasyPredictModelWrapper.Config config = new EasyPredictModelWrapper.Config()
                    //.setModel(MojoModel.load("/home/bell/Documents/work/temp/2019-11-04_13:55:10.zip"))
                    .setModel(MojoModel.load(model))
                    .setEnableContributions(true);
            //.setEnableLeafAssignment(true);
            EasyPredictModelWrapper model = new EasyPredictModelWrapper(config);

            RowData row = new RowData();
            Iterator payloadIterator = received.payload();
            while(payloadIterator.hasNext()) {
                row.put(iterator.next(), iterator.next());
            }
            BinomialModelPrediction p = model.predictBinomial(row);
            received.setPrediction(p.label);
            for (int i = 0; i < p.classProbabilities.length; i++) {
                received.setProbability(p.classProbabilities[i]);
            }
//            ArrayList<String> list = new ArrayList<String>();


        }
    	catch(Exception e){
 
            // res.setBody(parser.encodeResourceToString(endpoint));        
    	}

	    return res;
    }
}

// Request is mapped into an instance of this class
// Create a class for response like this.
// This template uses the same class for both.
class Received {
    private String Prediction;
    private ArrayList<long> Probability = new ArrayList<long>();

    private ArrayList<String> Payload;

    Received (){}

    public int prediction() { return this.Prediction; }
    public ArrayList<String> payload() { return this.Payload; }
    public void setPayload(ArrayList<String> payload){
        this.Payload = payload;
    }
    public void setPrediction(String prediction){
        this.Prediction = prediction;
    }
    public void setProbability(long probability){
        this.Probability.add(probability);
    }

}