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
import money.tracker.entity.Item;
import money.tracker.entity.User;
import money.tracker.repo.ItemRepo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;


/** Editor для изменения данных в записях */
@SpringComponent
@UIScope
public class ItemEditor extends VerticalLayout implements KeyNotifier {
    private final ItemRepo repo;
    private Item item;
    private User user;

    private final TextField
            rowCost = new TextField("", "Сумма"),
            date = new TextField("", "Дата ГГГГ-ММ-ДД"),
            type = new TextField("", "Тип"),
            dest = new TextField("", "Назничение");

    private final Button
            save = new Button("Сохранить"),
            delete = new Button("Удалить");

    private final HorizontalLayout buttons = new HorizontalLayout(save, delete);

    private final Binder<Item> binder = new Binder<>(Item.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public ItemEditor(ItemRepo repo) {
        this.repo = repo;

        add(rowCost, date, type, dest, buttons);

        binder.bindInstanceFields(this);

        // Добавляем интервалы
        setSpacing(true);

        // Изменяем внешний вид кнопок
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        // Слушаем события на кнопках
        addKeyPressListener(Key.ENTER, e -> save());

        // Слушаем поле и проверяем на валидность
        rowCost.addValueChangeListener(e -> setValues(rowCost.getValue()));
        date.addValueChangeListener(e -> setValues(rowCost.getValue()));
        type.addValueChangeListener(e -> setValues(rowCost.getValue()));
        dest.addValueChangeListener(e -> setValues(rowCost.getValue()));

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());

        // Делаем форму невидимой
        setVisible(false);

        // Отключаем кнопку, когда пустые поля
        save.setEnabled(false);
    }


    /** Проверка на ввод всех полей */
    private void updateEnable() {
        if (
                !rowCost.getValue().equals("") &&
                !date.getValue().equals("") &&
                !type.getValue().equals("") &&
                !dest.getValue().equals("")
        ) {
            save.setEnabled(true);
            return;
        }
        save.setEnabled(false);
    }

    /**
     * Устанавливаем Cost если валидный
     * @param rowCostValue - значение в ячейке с суммой
     */
    private void setValues(String rowCostValue) {
        double result;
        boolean isEnable = true;
        if (rowCostValue.equals("")) {
            return;
        }
        try {
            result = Math.round(Double.parseDouble(rowCostValue) * 100.0) / 100.0;
            item.setCost(result);
            rowCost.getStyle().set("color", "#283646");
            isEnable = true;
        }
        catch (Exception e) {
            rowCost.getStyle().set("color", "#F52518");
            isEnable = false;
        }

        String dt = date.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        try {
            sdf.parse(dt);
            date.getStyle().set("color", "#283646");
            isEnable = true;
        } catch (Exception e) {
            date.getStyle().set("color", "#F52518");
            isEnable = false;
        }

        if (isEnable) {
            updateEnable();
            return;
        }
        save.getElement().setEnabled(isEnable);
    }

    /** Функционал сохранения */
    private void save() {
        item.setUsername(user.getUsername());
        repo.save(item);
        changeHandler.onChange();
    }

    /** Функционал удаления */
    private void delete() {
        repo.delete(item);
        changeHandler.onChange();
    }

    /**
     * Функционал изменения
     * обработка данных из таблицы или создание новой записи
     * @param it - объект записи
     */
    public void edit(Item it, User u) {
        this.user = u;
        /* Если запись пустая то форма невидимая */
        if (it == null) {
            rowCost.setValue("");
            rowCost.setPlaceholder("Сумма");
            setVisible(false);
            return;
        }

        /* Обрабатываем записи */
        if (it.getId() != null) {
            this.item = repo.findById(it.getId()).orElse(it);
            rowCost.setValue(Double.toString(this.item.getCost()));
        } else {
            this.item = it;
            rowCost.setValue("");
            rowCost.setPlaceholder("Сумма");
        }

        binder.setBean(this.item);

        /* Показываем форму */
        setVisible(true);

        type.focus();
    }
}
