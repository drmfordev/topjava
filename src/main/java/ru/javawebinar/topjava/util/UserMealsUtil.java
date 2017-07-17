package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);


    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();


        Map<LocalDate, Integer> caloriesSumPerDay = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            caloriesSumPerDay.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (prevValue, newValue) -> (prevValue + newValue));
        }

        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {

                userMealWithExceeds.add(new UserMealWithExceed(userMeal, caloriesSumPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));

            }
        }

        return userMealWithExceeds;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {


        Map<LocalDate, IntSummaryStatistics> caloriesSumPerDay = mealList
                .stream()
                .collect(Collectors.groupingBy((m) -> m.getDateTime().toLocalDate(), Collectors.summarizingInt(UserMeal::getCalories)));


        return mealList.stream()
                .filter(e -> TimeUtil.isBetween(e.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal, caloriesSumPerDay.get(meal.getDateTime().toLocalDate()).getSum() > caloriesPerDay))
                .collect(Collectors.toList());
    }

}
