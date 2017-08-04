package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

//    private MealRepository repository;
    private MealRestController controller;
    private ConfigurableApplicationContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
//        repository = new InMemoryMealRepositoryImpl();
        context = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = context.getBean(MealRestController.class);

    }

    @Override
    public void destroy() {
        super.destroy();
        context.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));


        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()){
            controller.create(meal);
        }else{
            meal.setUserId(Integer.parseInt(request.getParameter("userId")));
            controller.update(meal, Integer.parseInt(id));

        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                String filter = request.getParameter("filter");
                boolean flag = false;
                if (filter != null){
                    Collection<MealWithExceed> mealWithExceeds = Collections.EMPTY_LIST;
                    if (filter.equalsIgnoreCase("time")){
                        String firstTime = request.getParameter("firstTime");
                        String secondTime = request.getParameter("secondTime");

                        if (firstTime != null && !firstTime.isEmpty() && secondTime != null && !secondTime.isEmpty() ){
                          mealWithExceeds =  controller.getAllByTime(firstTime, secondTime);
                          flag = true;
                        }

                    }else if (filter.equalsIgnoreCase("date")){
                        String firstDate = request.getParameter("firstDate");
                        String secondDate = request.getParameter("secondDate");

                        if (firstDate != null && !firstDate.isEmpty() && secondDate != null && !secondDate.isEmpty() ){
                            mealWithExceeds = controller.getAllByDate(firstDate, secondDate);
                            flag = true;
                        }
                    }
                    if (flag) {
                        request.setAttribute("meals",
                                mealWithExceeds);
                        request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    }
                }
                request.setAttribute("meals",
                        controller.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
