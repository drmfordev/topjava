package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles("datajpa")
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void testGetWithMeals() throws Exception {
        Meal actual = service.getWithUser(ADMIN_MEAL1.getId());
        MATCHER.assertEquals(ADMIN_MEAL1, actual);
    }
}
