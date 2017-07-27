package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CRUDService;
import ru.javawebinar.topjava.dao.InMemoryDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "meal.jsp";
    private static final String LIST_MEAL = "listMeal.jsp";
    private CRUDService crudService;


    public MealServlet(){
        super();
        crudService = new InMemoryDAO();
    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String forward = "";
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            crudService.deleteMeal(mealId);
            forward = LIST_MEAL;
            List<Meal> listMeals = crudService.getAllMeals();
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(listMeals, LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("meals", mealWithExceeds);

        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = crudService.getMealById(mealId);

            req.setAttribute("meal",meal);
        } else if (action.equalsIgnoreCase("listMeal")){
            forward = LIST_MEAL;
            List<Meal> listMeals = crudService.getAllMeals();
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(listMeals, LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("meals", mealWithExceeds);
        } else {
            forward = INSERT_OR_EDIT;
        }
        if (action.equalsIgnoreCase("delete")){
            resp.sendRedirect( "meals?action=listMeal");
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(forward);
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDescription(req.getParameter("description"));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));
        meal.setDateTime(LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        String mealId = req.getParameter("mealId");

        if (mealId == null || mealId.isEmpty() ){
            crudService.addMeal(meal);
        }else {
            meal.setId(Integer.parseInt(mealId));
            crudService.updateMeal(meal);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(LIST_MEAL);
        List<Meal> listMeals = crudService.getAllMeals();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(listMeals, LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", mealWithExceeds);
        requestDispatcher.forward(req,resp);

    }
}
