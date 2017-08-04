package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {

        if (repository.delete(id, userId) == false) throw new NotFoundException("Meal are not yours or doesn't exist");

    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {

        Meal meal = repository.get(id, userId);
        if (meal == null) throw new NotFoundException("Meal are not yours or doesn't exist");
        return meal;
    }

    @Override
    public void update(Meal meal, int userId) {
            repository.save(meal,userId);

    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public Collection<Meal> getAllFilteredByTime(int userId, LocalTime first, LocalTime second) {


        return repository.getAllFilteredByTime(userId, first, second);
    }

    @Override
    public Collection<Meal> getAllFilteredByDate(int userId, LocalDate first, LocalDate second) {

        return repository.getAllFilteredByDate(userId, first, second);
    }
}