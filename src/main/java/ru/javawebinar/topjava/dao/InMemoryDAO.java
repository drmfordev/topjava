package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryDAO implements CRUDService {

    private static Map<Integer,Meal> storage = new ConcurrentHashMap<>();
    private static final AtomicInteger nextIdGenerator = new AtomicInteger(1);

    @Override
    public void addMeal(Meal meal) {
         int id = nextIdGenerator.getAndIncrement();
         meal.setId(id);
         storage.putIfAbsent(id,meal);

    }

    @Override
    public void deleteMeal(int mealId) {
          storage.remove(mealId);
    }

    @Override
    public void updateMeal(Meal meal) {
         storage.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return storage.values().stream().collect(Collectors.toList());
    }

    @Override
    public Meal getMealById(int mealId) {
        return storage.get(mealId);
    }
}
