# java-ext

Java8 OpenFaaS Template that pulls GitHub Modules using JitPack

## Templates available in this repository:

- java-imj  - Image Analytics with ImageJ
- java-fhir - FHIR based ML Model deployments for digital health. *paper in print*
- java-h2o - Template for deploying [h2o](http://h2o.ai) MOJO models for predictions.[[Details](http://docs.h2o.ai/h2o/latest-stable/h2o-docs/productionizing.html)]

## Downloading the templates
```
faas-cli template pull https://github.com/dermatologist/java-ext
```

## Citation
* Please cite the following article:
```
@misc{eapen2020serverless,
    title={Serverless on FHIR: Deploying machine learning models for healthcare on the cloud},
    author={Bell Raj Eapen and Kamran Sartipi and Norm Archer},
    year={2020},
    eprint={2006.04748},
    archivePrefix={arXiv},
    primaryClass={cs.CY}
}

```

# Using the template
Create a new function (Check the templates available above)
```
faas-cli new --lang java-imj <fn-name> -p <docker-profile>
```
Build, push, and deploy
```
faas-cli up -f <fn-name>.yml
```
Test the new function
```
echo -n content | faas-cli invoke <fn-name>
```
## Contributors

* [Bell Eapen](https://nuchange.ca) |  [Contact](https://nuchange.ca/contact)
