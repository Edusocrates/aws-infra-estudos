package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class AwsInfraApp {
    public static void main(final String[] args) {
        App app = new App();

        AwsVpcStack vpcStack = new AwsVpcStack(app, "Vpc");
        AwsClusterStack clusterStack = new AwsClusterStack(app, "Cluster", vpcStack.getVpc());
        clusterStack.addDependency(vpcStack);
        AwsServiceStack serviceStack = new AwsServiceStack(app,"Service",clusterStack.getCluster());
        app.synth();
    }
}

