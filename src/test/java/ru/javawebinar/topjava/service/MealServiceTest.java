package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }
    @Autowired
    private MealService service;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MealTestData.USER_MEAL_ID, USER_ID);
        MealTestData.MATCHER.assertEquals(MealTestData.USER_MEAL, meal);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MealTestData.USER_MEAL_ID, USER_ID);
        List<Meal> meals = new ArrayList<>(MealTestData.MEALS_USER);
        Collections.sort(meals, Comparator.comparing(Meal::getDateTime).reversed());
        meals.remove(MealTestData.USER_MEAL);
        MealTestData.MATCHER.assertCollectionEquals(meals, service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotOwnMeal() throws Exception {
        service.get(MealTestData.USER_MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotOwnMeal() throws Exception {
        service.update(MealTestData.USER_MEAL, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotOwnMeal() throws Exception {
        service.delete(MealTestData.USER_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> meals = new ArrayList<>(MealTestData.MEALS_USER);
        LocalDate from = LocalDate.of(2017,  Month.JANUARY, 1);
        LocalDate to = LocalDate.of(2017,  Month.JANUARY, 2);
        List<Meal> actual =  service.getBetweenDates(from, to , USER_ID);
        List<Meal> expected = meals.stream().filter(e -> DateTimeUtil.isBetween(e.getDate(), from, to)).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
        MealTestData.MATCHER.assertCollectionEquals(actual, expected);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {

        List<Meal> meals = new ArrayList<>(MealTestData.MEALS_USER);
        LocalDateTime from = LocalDateTime.of(2017,  Month.JANUARY, 1, 13, 0);
        LocalDateTime to = LocalDateTime.of(2017,  Month.JANUARY, 2, 13, 0);
        List<Meal> actual =  service.getBetweenDateTimes(from, to , USER_ID);
        List<Meal> expected = meals.stream().filter(e -> DateTimeUtil.isBetween(e.getDateTime(), from, to)).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
        MealTestData.MATCHER.assertCollectionEquals(actual, expected);

    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> all = service.getAll(USER_ID);
        List<Meal> meals = new ArrayList<>();
        meals.addAll(MealTestData.MEALS_USER);
        Collections.sort(meals, Comparator.comparing(Meal::getDateTime).reversed());
        MealTestData.MATCHER.assertCollectionEquals(meals, all);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MealTestData.MEALS_USER.get(0));
        updated.setDescription("TEST");
        updated.setCalories(300);
        updated.setDateTime(LocalDateTime.MAX);
        service.update(updated, USER_ID);
        MealTestData.MATCHER.assertEquals(updated, service.get(updated.getId(),USER_ID));
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "Ланч", 2000 );
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        List<Meal> meals = new ArrayList<>();
        meals.addAll(MealTestData.MEALS_USER);
        meals.add(created);
        Collections.sort(meals, Comparator.comparing(Meal::getDateTime).reversed());
        MealTestData.MATCHER.assertCollectionEquals(meals, service.getAll(USER_ID));
    }

}