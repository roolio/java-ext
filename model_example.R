library(h2o)
h2o.init(nthreads = -1)
path <- system.file("extdata", "prostate.csv", package="h2o")
h2o_df <- h2o.importFile(path)
h2o_df$CAPSULE <- as.factor(h2o_df$CAPSULE)
model <- h2o.gbm(y = "CAPSULE",
                 x = c("AGE", "RACE", "PSA", "GLEASON"),
                 training_frame = h2o_df,
                 distribution = "bernoulli",
                 ntrees = 100,
                 max_depth = 4,
                 learn_rate = 0.1)

# Download the MOJO and the resulting h2o-genmodel.jar file
# to a new **experiment** folder. Note that the ``h2o-genmodel.jar`` file
# is a library that supports scoring and contains the required readers
# and interpreters. This file is required when MOJO models are deployed
# to production. Be sure to specify the entire path, not just the relative path.
# modelfile <- h2o.download_mojo(model, path="/tmp/", get_genmodel_jar=TRUE)
modelfile <- h2o.download_mojo(model, path="/tmp/", get_genmodel_jar=FALSE, filename = "model.zip")
print(modelfile)