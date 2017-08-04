package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {

    Meal save(Meal meal, int userId);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal meal, int userId);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getAllFilteredByTime(int userId, LocalTime first, LocalTime second);

    Collection<Meal> getAllFilteredByDate(int userID, LocalDate first, LocalDate second);
}