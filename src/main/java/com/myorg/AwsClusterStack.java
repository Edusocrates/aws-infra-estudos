package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.constructs.Construct;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class AwsClusterStack extends Stack {

    private Cluster cluster;
    public AwsClusterStack(final Construct scope, final String id, final Vpc vpc) {
        this(scope, id, null, vpc);
    }

    public AwsClusterStack(final Construct scope, final String id, final StackProps props, final Vpc vpc) {
        super(scope, id, props);

        cluster = Cluster.Builder.create(this, "MyCluster")
                .clusterName("cluster-estudos")
                .vpc(vpc).build();

    }

    public Cluster getCluster() {
        return cluster;
    }
}
