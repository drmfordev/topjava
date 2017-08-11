package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MealTestData {

    public static final int USER_MEAL_ID = 100002;

    public static final Meal USER_MEAL =  new Meal(100002 ,LocalDateTime.of(2017,  Month.FEBRUARY, 1, 2, 0), "Завтрак", 1000);

    public static final List<Meal> MEALS_USER = Arrays.asList(
            new Meal(100002 ,LocalDateTime.of(2017,  Month.FEBRUARY, 1, 2, 0), "Завтрак", 1000),
            new Meal(100003, LocalDateTime.of(2017,  Month.JANUARY, 1, 13, 0), "Обед", 500),
            new Meal(100004, LocalDateTime.of(2017,  Month.JANUARY, 1, 22, 0), "Ужин", 1500)

    );

    public static final List<Meal> MEALS_ADMIN = Arrays.asList(

            new Meal(LocalDateTime.of(2017,  Month.JANUARY, 1, 9, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2017,  Month.JANUARY, 1, 14, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2017,  Month.JANUARY, 1, 21, 0), "Ужин", 1500)
    );

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>(
            ((expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                    && (Objects.equals(expected.getDescription(), actual.getDescription()))
                    && (Objects.equals(expected.getCalories(), actual.getCalories()))
                    && (Objects.equals(expected.getDateTime(), actual.getDateTime())))
            )
    );

}
