# java-ext

Java8 OpenFaaS Template that pulls GitHub Modules using JitPack

## Templates available in this repository:

- java-imj

## Downloading the templates
```
faas-cli template pull https://github.com/dermatologist/java-ext
```

# Using the template
Create a new function (Check the templates available above)
```
faas-cli new --lang java-imj <fn-name>
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
