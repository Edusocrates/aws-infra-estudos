package com.myorg;

import software.amazon.awscdk.Fn;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecr.IRepository;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.constructs.Construct;

import java.util.HashMap;
import java.util.Map;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class AwsServiceStack extends Stack {
    public AwsServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public AwsServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        Map<String, String> autenticacao= new HashMap<>();
        autenticacao.put("SPRING_DATASOURCE_URL", "jdbc:mysql://" + Fn.importValue( "pedidos-db-endpoint") + ":3306/alurafood-pedidos?createDatabaseIfNotExist=true");
        autenticacao.put("SPRING_DATASOURCE_USERNAME", "admin");
        autenticacao.put("SPRING_DATASOURCE_PASSWORD", Fn.importValue("pedidos-db-senha"));

        IRepository repositorio = Repository.fromRepositoryName(this, "repositorio", "img-pedidos-ms");


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
                                .image(ContainerImage.fromEcrRepository(repositorio))
                                .containerPort(8080)
                                .containerName("app_ola")
                                .environment(autenticacao)
                                .build())
                .memoryLimitMiB(2048)       // Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();
    }
}
