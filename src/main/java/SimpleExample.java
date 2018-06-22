import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class SimpleExample {
    public static void main(String[] args) throws IOException {

        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        ConfigurationBuilder config = new ConfigurationBuilder();
        config.clustering().cacheMode(CacheMode.DIST_SYNC);
        DefaultCacheManager m = new DefaultCacheManager(global.build(), config.build());

        Cache<String, Person> cache = m.getCache();

        cache.put("1", new Person("Ann", "Jones", Date.valueOf(LocalDate.now())));
        cache.put("2", new Person("Matt", "Williams", Date.valueOf(LocalDate.now())));
        cache.put("3", new Person("Alfred", "Jones", Date.valueOf(LocalDate.now())));

        QueryFactory qf = Search.getQueryFactory(cache);
        Query query = qf.from(Person.class)
                .having("surname").like("J%")
                .build();
        List<Person> list = query.list();
        System.out.println(list);

        cache.clear();
        m.close();
    }
}
