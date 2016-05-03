package com.test.websocket.auth.core.bootstrap;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.RuntimeConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.io.NullProcessor;
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
    protected static MongodProcess mongoProcess;
    protected static Mongo mongo;
    public static final String MONGO_HOST = "localhost";
    public static final int MONGO_PORT = 27028;
    public static final String MONGO_DB_NAME = "test_auth_db";

    @PostConstruct
    public void init(){
        try {
            RuntimeConfig config = new RuntimeConfig();
            config.setExecutableNaming(new UserTempNaming());
            //suppress mongo logging
            config.setProcessOutput(new ProcessOutput(new NullProcessor(), new NullProcessor(), new NullProcessor()));

            MongodStarter starter = MongodStarter.getInstance(config);

            MongodExecutable mongoExecutable = starter.prepare(new MongodConfig(Version.V2_2_0, MONGO_PORT, false));
            mongoProcess = mongoExecutable.start();

            mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
            mongo.getDB(MONGO_DB_NAME);
        } catch (IOException e) {
            String err = "Can't init mongodb";
            log.error(err, e);
            throw new RuntimeException(err);
        }
    }

    @PreDestroy
    public void destroy(){
        try {
            mongo.close();
            mongoProcess.stop();
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
