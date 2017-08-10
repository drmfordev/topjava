package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2017,  Month.JANUARY, 1, 2, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2017,  Month.JANUARY, 1, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2017,  Month.JANUARY, 1, 22, 0), "Ужин", 1500),
            new Meal(LocalDateTime.of(2017,  Month.JANUARY, 1, 9, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2017,  Month.JANUARY, 1, 14, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2017,  Month.JANUARY, 1, 21, 0), "Ужин", 1500)
    );

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>();

}
