package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.constructs.Construct;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class AwsServiceStack extends Stack {
    public AwsServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public AwsServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        // Create a load-balanced Fargate service and make it public
        ApplicationLoadBalancedFargateService.Builder.create(this, "EstudoService")
                .serviceName("estudos-service-ola")
                .cluster(cluster)           // Required
                .cpu(512)                   // Default is 256
                .desiredCount(1)
                .listenerPort(8080)
                .assignPublicIp(true)// Default is 1
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .image(ContainerImage.fromRegistry("amazon/amazon-ecs-sample"))
                                .build())
                .memoryLimitMiB(2048)       // Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();
    }
}
