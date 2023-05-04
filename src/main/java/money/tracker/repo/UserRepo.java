package money.tracker.repo;

import money.tracker.entity.Item;
import money.tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/** JPA Репозиторий */
public interface UserRepo extends JpaRepository<User, Long> {
    /** Поиск записи */
    @Query("from User u where u.username=:username")
    List<User> findByUsername(@Param("username") String username);
}
