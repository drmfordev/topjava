package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import java.util.Collection;


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

}