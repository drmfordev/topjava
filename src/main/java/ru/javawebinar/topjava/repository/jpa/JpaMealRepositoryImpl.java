package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()){
            meal.setUser(em.getReference(User.class,userId));
            em.persist(meal);
            return meal;
        }else {
            Meal mealFromBase = em.getReference(Meal.class, meal.getId());
            if (mealFromBase.getUser().getId() != userId) return null;
            else {
                meal.setUser(mealFromBase.getUser());
            }
          return   em.merge(meal);
        }

    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {

//        Meal ref = em.getReference(Meal.class, id);
//        em.remove(ref);

//        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id");
//        query.setParameter("id", id);
//        return query.executeUpdate() != 0;

        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id",id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {

        Meal meal = em.find(Meal.class, id);
        if (meal.getUser().getId() != userId) return null;
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {

        List<Meal> meals = em.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("userId", userId)
                .getResultList();

        return meals;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {

        List<Meal> meals = em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();

        return meals;
    }
}