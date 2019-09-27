package com.openfaas.function;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.dstu3.model.*;
import java.util.List;

// Your function imported from GitHub
import in.co.dermatologist.YourClass;

public class Handler implements com.openfaas.model.IHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();
        FhirContext ctx = FhirContext.forDstu3();
        IParser parser = ctx.newJsonParser();

        Patient patient = new Patient();
        Observation observation = new Observation();
        Subscription subscription = new Subscription();
        CarePlan carePlan = new CarePlan();        
        try{

            // Request Bundle
            Bundle bundle_in = parser.parseResource(Bundle.class,req.getBody());
            List<Bundle.BundleEntryComponent> components = bundle_in.getEntry();

            // Process CarePlan
            for (Bundle.BundleEntryComponent component: components){
                Resource resource = component.getResource();
                if(resource.getResourceType() == ResourceType.Patient){
                    patient = (Patient)resource;
                }
                if(resource.getResourceType() == ResourceType.Observation){
                    observation = (Observation)resource;
                }
                if(resource.getResourceType() == ResourceType.Subscription){
                    subscription = (Subscription)resource;
                }
                if(resource.getResourceType() == ResourceType.CarePlan){
                    carePlan = (CarePlan)resource;
                    carePlan.setId("NewId");
                }
            }

            // Response Bundle
            Bundle bundle = new Bundle();
            bundle.setType(Bundle.BundleType.MESSAGE);

            Bundle.BundleEntryComponent bundleEntryComponent = new Bundle.BundleEntryComponent();
            bundleEntryComponent.setResource(patient);
            bundle.addEntry(bundleEntryComponent);

            Bundle.BundleEntryComponent bundleEntryComponent1 = new Bundle.BundleEntryComponent();
            bundleEntryComponent1.setResource(observation);
            bundle.addEntry(bundleEntryComponent1);

            Bundle.BundleEntryComponent bundleEntryComponent2 = new Bundle.BundleEntryComponent();
            bundleEntryComponent2.setResource(subscription);
            bundle.addEntry(bundleEntryComponent2);

            Bundle.BundleEntryComponent bundleEntryComponent3 = new Bundle.BundleEntryComponent();
            bundleEntryComponent3.setResource(carePlan);
            bundle.addEntry(bundleEntryComponent3);

            res.setBody(parser.encodeResourceToString(bundle));
        }
    	catch(Exception e){
            Endpoint endpoint = new Endpoint();
            endpoint.setId("MyId");

            res.setBody(parser.encodeResourceToString(endpoint));        
    	}

	    return res;
    }
}

