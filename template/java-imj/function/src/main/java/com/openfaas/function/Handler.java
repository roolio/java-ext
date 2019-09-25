package com.openfaas.function;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.*;

// Your function imported from GitHub
import in.co.dermatologist.YourClass;

public class Handler implements com.openfaas.model.IHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();
        Gson gson = new Gson();
        String inpI = "";
        try{

            Received received = gson.fromJson(req.getBody(), Received.class);
            // Add Processing logic
            YourClass shdSegment = new YourClass(received.payload());
    	    received.setPayload(shdSegment.yourFunction());

            res.setBody(gson.toJson(received));
        }
    	catch(Exception e){
                // GET throws as exception as req is blank.
                // Then show an html page that POST to this function.
                inpI = "<!DOCTYPE html>"+
                "<html lang='en'>"+
                "<head>"+
                "  <meta charset='UTF-8'>"+
                "  <title>SHD</title>"+
                "  <script"+
                "  src='https://code.jquery.com/jquery-3.3.1.min.js'"+
                "  integrity='sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8='"+
                "  crossorigin='anonymous'></script>"+
                "</head>"+
                "<body>"+
                "   <h1>File Upload</h1>"+
                "   <input id='fileinput' type='file' accept='image/jpeg' onchange='readURL(this);' /> <br><br>"+
                "   <img id='image1' src='' />"+
                "  <script type='text/javascript'>"+
                "      function readURL(input) {"+
                "            if (input.files && input.files[0]) {"+
                "               var reader = new FileReader();"+
                "                reader.onload = function(e) {"+
                "                    var base64Img = e.target.result.replace('data:image/jpeg;base64,', '');"+
                "                    var returnImg = '';"+
                "                    var inputString = '{\"Service\": \"\", \"ValueCode\": 0, \"Payload\":' + '\"' + base64Img + '\"}';"+
                "                    $.ajax({"+
                "                        url:'./shd-image-template',"+
                "                        type:'POST',"+
                "                        contentType:'text/plain',"+
                "                        data: inputString"+
                "                    })"+
                "                    .done(function(data) {"+
                "                        var jsonImg = JSON.parse(data);"+
                "                      returnImg = 'data:image/jpeg;base64,' + jsonImg.Payload;"+
                "                      console.log(returnImg);"+
                "                      $('#image1').attr('src',returnImg);"+
                "                    });"+
                "                };"+
                "               reader.readAsDataURL(input.files[0]);"+
                "           }"+
                ""+
                "      }"+
                "  </script>"+
                "</body>"+
                "</html>";
                    


                res.setContentType("text/html");
                res.setBody(inpI);        
    	}

	    return res;
    }
}

// Request is mapped into an instance of this class
// Create a class for response like this.
// This template uses the same class for both.
class Received {
    private String Service = "";
    private String Payload = "";
    private int ValueCode = 0;

    Received (){}

    public String service() { return this.Service; }
    public String payload() { return this.Payload; }
    public void setPayload(String payload){
    	this.Payload = payload;
    }
}