package biz.cits.search.neo4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Value("${solr.host.url}")
    String solrHostUrl;

    @Value("${solr.cloud.solrUrls}")
    List<String> solrCloudSolrUrls;

    @Value("${solr.cloud.zkHosts}")
    List<String> solrCloudZkHosts;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    @Bean
    public HttpSolrClient httpSolrClient() {
        HttpSolrClient client = new HttpSolrClient.Builder(solrHostUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        return client;
    }

    @Bean
    public CloudSolrClient cloudSolrClient() {
        CloudSolrClient client = new CloudSolrClient.Builder(solrCloudZkHosts, Optional.empty())
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        return client;
    }

}