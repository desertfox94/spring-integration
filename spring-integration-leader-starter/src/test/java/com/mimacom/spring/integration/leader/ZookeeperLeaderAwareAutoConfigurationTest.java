package com.mimacom.spring.integration.leader;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.curator.test.TestingServer;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.zookeeper.ZookeeperProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.zookeeper.leader.LeaderInitiator;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperLeaderAwareAutoConfigurationTest extends AbstractLeaderAwareAutoConfigurationTest {

    @Autowired(required = false)
    private LeaderInitiator leaderInitiator;

    @Test
    public void testZookeeperBasedLeaderInitiator() {
        assertThat(leaderInitiator).isNotNull();
    }

    @SpringBootApplication
    static class TestConfigUsingZookeeper {

        @Bean
        ZookeeperProperties zookeeperProperties() throws Exception {
            ZookeeperProperties zookeeperProperties = new ZookeeperProperties();
            zookeeperProperties.setConnectString(this.testingServer().getConnectString());
            return zookeeperProperties;
        }

        @Bean(destroyMethod = "close")
        TestingServer testingServer() throws Exception {
            return new TestingServer();
        }

    }

}