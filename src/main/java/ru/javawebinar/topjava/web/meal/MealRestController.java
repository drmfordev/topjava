package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;


@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Collection<MealWithExceed> getAll(){
         log.info("getAll");
         Collection<Meal> meals = service.getAll(AuthorizedUser.id());
         return MealsUtil.getWithExceeded(meals, AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id){
        log.info("get {}", id);
        return service.get(id, AuthorizedUser.id());
    }


    public Meal create(Meal meal){
        log.info("create {}", meal);
        checkNew(meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id){
        log.info("delete {}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int id){
        log.info("update {} with id = {}" , meal , id);
        checkIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }

    public Collection<MealWithExceed> getAllByTime(String firstTime, String secondTime){
        log.info("get all filtered by time {} , {}", firstTime, secondTime);
        LocalTime firstLT = LocalTime.parse(firstTime, DateTimeUtil.TIME_FORMATTER);
        LocalTime secondLT = LocalTime.parse(secondTime, DateTimeUtil.TIME_FORMATTER);
        log.info("{} {}" ,firstLT, secondLT);
        Collection<Meal> meals = service.getAllFilteredByTime(AuthorizedUser.id(), firstLT, secondLT);
        return MealsUtil.getWithExceeded(meals, AuthorizedUser.getCaloriesPerDay());
    }

    public Collection<MealWithExceed> getAllByDate(String firstDate, String secondDate){
        log.info("get all filtered by date {} , {}", firstDate, secondDate);
        LocalDate firstLD = LocalDate.parse(firstDate, DateTimeUtil.DATE_FORMATTER);
        LocalDate secondLD = LocalDate.parse(secondDate, DateTimeUtil.DATE_FORMATTER);
        log.info("{} {}" ,firstLD, secondLD);
        Collection<Meal> meals = service.getAllFilteredByDate(AuthorizedUser.id(), firstLD, secondLD);
        return MealsUtil.getWithExceeded(meals, AuthorizedUser.getCaloriesPerDay());
    }

}