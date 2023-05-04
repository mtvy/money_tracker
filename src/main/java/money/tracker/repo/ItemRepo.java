package money.tracker.repo;

import money.tracker.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** JPA Репозиторий */
public interface ItemRepo extends JpaRepository<Item, Long> {
    /** Поиск записи */
    @Query(
            "from Item i " +
                    "where concat(i.id, ' ', i.type, ' ', i.cost, ' ', i.dest, ' ', i.date) " +
                    "like concat('%', :type, '%', '%', '%') and i.username=:username"
    )
    List<Item> findByType(@Param("type") String type, @Param("username") String username);
}
