package com.test.websocket.auth.core.bootstrap;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.runtime.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * Created by timur on 03.05.16.
 */
@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);
    public static final String MONGO_HOST = "localhost";
    public static final int MONGO_PORT = 27028;
    public static final String MONGO_DB_NAME = "test_auth_db";
    private MongodExecutable mongodExecutable;

    @PostConstruct
    public void init() {
        try {
            Command command = Command.MongoD;

            IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
                    .defaults(command)
                    .artifactStore(new ExtractedArtifactStoreBuilder()
                            .defaults(command)
                            .download(new DownloadConfigBuilder()
                                    .defaultsForCommand(command).build())
                            .executableNaming(new UserTempNaming()))
                    .build();

            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(MONGO_PORT, Network.localhostIsIPv6()))
                    .build();

            MongodStarter runtime = MongodStarter.getInstance(runtimeConfig);

            mongodExecutable = runtime.prepare(mongodConfig);
            mongodExecutable.start();

            MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
            mongo.getDB(MONGO_DB_NAME);
        } catch (IOException e) {
            String err = "Can't init mongodb";
            log.error(err, e);
            throw new RuntimeException(err);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            if (mongodExecutable != null)
                mongodExecutable.stop();
        } catch (Exception e) {
            log.error("Error while closing mongodb", e);
        }
        log.info("done");
    }

    @Override
    protected String getDatabaseName() {
        return MONGO_DB_NAME;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(new ServerAddress(MONGO_HOST, MONGO_PORT));
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

}
