package money.tracker.component;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import money.tracker.entity.Item;
import money.tracker.entity.User;
import money.tracker.repo.ItemRepo;
import money.tracker.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import money.tracker.entity.Item;
import money.tracker.repo.ItemRepo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;


/** Editor для изменения данных в записях */
@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout implements KeyNotifier {
    private final UserRepo repo;
    private User user;

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public UserEditor(UserRepo repo) {
        this.repo = repo;

        // Добавляем интервалы
        setSpacing(true);

        // Делаем форму невидимой
        setVisible(false);
    }
}
