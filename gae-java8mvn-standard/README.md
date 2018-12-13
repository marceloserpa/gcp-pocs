appengine-standard-archetype
============================


## Setup

    gcloud init
    gcloud auth application-default login


## Maven

### Creating project

```
-mvn archetype:generate -Dappengine-version=1.9.59 -Dapplication-id=my-gcp-poc -Dfilter=com.google.appengine.archetypes:
```

### Preparing to use eclipse

```
mvn eclipse:eclipse
```


### Running locally
```
mvn appengine:run
```

### Deploying
```
mvn appengine:deploy
```

