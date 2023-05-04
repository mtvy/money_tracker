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

//    private final TextField
//            rowCost = new TextField("", "Сумма"),
//            date = new TextField("", "Дата ГГГГ-ММ-ДД"),
//            type = new TextField("", "Тип"),
//            dest = new TextField("", "Назничение");
//
//    private final Button
//            save = new Button("Сохранить"),
//            delete = new Button("Удалить");

//    private final HorizontalLayout buttons = new HorizontalLayout(save, delete);

//    private final Binder<Item> binder = new Binder<>(Item.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public UserEditor(UserRepo repo) {
        this.repo = repo;

//        add(rowCost, date, type, dest, buttons);

//        binder.bindInstanceFields(this);

        // Добавляем интервалы
        setSpacing(true);

        // Изменяем внешний вид кнопок
//        save.getElement().getThemeList().add("primary");
//        delete.getElement().getThemeList().add("error");

        // Слушаем события на кнопках
//        addKeyPressListener(Key.ENTER, e -> save());

        // Слушаем поле и проверяем на валидность
//        rowCost.addValueChangeListener(e -> setValues(rowCost.getValue()));
//        date.addValueChangeListener(e -> setValues(rowCost.getValue()));
//        type.addValueChangeListener(e -> setValues(rowCost.getValue()));
//        dest.addValueChangeListener(e -> setValues(rowCost.getValue()));

//        save.addClickListener(e -> save());
//        delete.addClickListener(e -> delete());

        // Делаем форму невидимой
        setVisible(false);

        // Отключаем кнопку, когда пустые поля
//        save.setEnabled(false);
    }

//    /** Функционал сохранения */
//    private void save() {
//        repo.save(item);
//        changeHandler.onChange();
//    }
//
//    /** Функционал удаления */
//    private void delete() {
//        repo.delete(item);
//        changeHandler.onChange();
//    }
//
//    /**
//     * Функционал изменения
//     * обработка данных из таблицы или создание новой записи
//     * @param it - объект записи
//     */
//    public void edit(Item it) {
//        /* Если запись пустая то форма невидимая */
//        if (it == null) {
//            rowCost.setValue("");
//            rowCost.setPlaceholder("Сумма");
//            setVisible(false);
//            return;
//        }
//
//        /* Обрабатываем записи */
//        if (it.getId() != null) {
//            this.item = repo.findById(it.getId()).orElse(it);
//            rowCost.setValue(Double.toString(this.item.getCost()));
//        } else {
//            this.item = it;
//            rowCost.setValue("");
//            rowCost.setPlaceholder("Сумма");
//        }
//
//        binder.setBean(this.item);
//
//        /* Показываем форму */
//        setVisible(true);
//
//        type.focus();
//    }
}
