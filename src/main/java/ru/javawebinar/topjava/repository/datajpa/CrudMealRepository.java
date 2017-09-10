package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {


    @Override
    @Transactional
    Meal save(Meal meal);


    Meal getByIdAndUserId(int id, int userId);

    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id =:id AND m.user.id= :userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    List<Meal> findAll(Sort sort);

    List<Meal> findByDateTimeBetweenAndUserIdOOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
