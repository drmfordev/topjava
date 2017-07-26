package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.debug("Redirect to meals");
        String calories  = req.getParameter("caloriesPerDay");
        int caloriesPerDay = Integer.parseInt(calories);
        List<Meal> mealList = MealsUtil.getMockMeals();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX,caloriesPerDay);
        req.setAttribute("mealsListWithExceeds", mealWithExceeds);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);

    }
}
