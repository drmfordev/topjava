package ru.javawebinar.topjava.service.jpa;

import org.junit.Assume;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        Assume.assumeFalse(environment.acceptsProfiles(Profiles.JDBC));
        service.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}