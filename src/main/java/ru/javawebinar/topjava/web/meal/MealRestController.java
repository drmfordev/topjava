package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    @GetMapping("/meals/update/{mealId}")
    public String update(@PathVariable int mealId, Model model) {
        int userId = AuthorizedUser.id();
        log.info("get meal {} for userId={}", mealId, userId);
        model.addAttribute("meal",service.get(mealId, userId));
        return "mealForm";
    }
    @GetMapping("/meals/delete/{mealId}")
    public String delete(@PathVariable int mealId) {
        int userId = AuthorizedUser.id();
        log.info("delete meal {} for userId={}", mealId, userId);
        service.delete(mealId, userId);

        return "redirect:/meals";
    }
    @GetMapping("/meals")
    public String getAll(Model model) {
        int userId = AuthorizedUser.id();
        log.info("getAll for userId={}", userId);
        model.addAttribute("meals",MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/meals/create")
    public String createMeal(Model model) {
        int userId = AuthorizedUser.id();
        log.info("create meal for userId={}", userId);
        model.addAttribute("message","Create meal");
        return "mealForm";
    }

    @PostMapping("/meals")
    public String createOrUpdate(HttpServletRequest request) {
        int userId = AuthorizedUser.id();
        log.info("create {} for userId={}", request, userId);
        Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
        System.out.println(request.getParameter("id"));
            if (request.getParameter("id").isEmpty()) {
                service.create(meal, userId);
            } else {

                service.update(meal, userId);
            }

        return "redirect:/meals";
    }

//    public void update(Meal meal, int id) {
//        int userId = AuthorizedUser.id();
//        log.info("update {} with id={} for userId={}", meal, id, userId);
//        assureIdConsistent(meal, id);
//        service.update(meal, userId);
//    }

    /**
     * <ol>Filter separately
     *   <li>by date</li>
     *   <li>by time for every date</li>
     * </ol>
     */
    @PostMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        int userId = AuthorizedUser.id();
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        log.info("getBetween dates({} - {}) time({} - {}) for userId={}", startDate, endDate, startTime, endTime, userId);

        model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        ));
        return "meals";
    }


}