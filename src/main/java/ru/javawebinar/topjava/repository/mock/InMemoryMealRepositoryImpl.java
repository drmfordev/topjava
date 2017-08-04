package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(e -> save(e, e.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("{} id = {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        } else {
            if (meal.getUserId() != userId) return null;
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
       Meal removedMeal = repository.get(id);
       if (removedMeal.getUserId() == userId){
           repository.remove(id);
           return true;
       }else{
           return false;
       }

    }

    @Override
    public Meal get(int id, int userId) {

        Meal meal = repository.get(id);

        if (meal.getUserId() != userId) return null;
        else return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().filter(e -> e.getUserId() == userId).sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFilteredByTime(int userId, LocalTime first, LocalTime second) {


        return getAll(userId).stream().filter(e -> DateTimeUtil.isBetweenGeneric(e.getTime(), first, second)).collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFilteredByDate(int userId, LocalDate first, LocalDate second) {

        return getAll(userId).stream().filter(e -> DateTimeUtil.isBetweenGeneric(e.getDate(), first, second)).collect(Collectors.toList());
    }
}

