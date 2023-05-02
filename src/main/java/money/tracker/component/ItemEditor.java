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
import money.tracker.domain.Item;
import money.tracker.repo.ItemRepo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class ItemEditor  extends VerticalLayout implements KeyNotifier {
    private final ItemRepo repo;
    private Item item;

    private final TextField
            rowCost = new TextField("", "Сумма"),
            date = new TextField("", "Дата"),
            type = new TextField("", "Тип"),
            dest = new TextField("", "Назничение");

    private final Button
            save = new Button("Сохранить"),
            cancel = new Button("Отменить"),
            delete = new Button("Удалить");

    private final HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);

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

        /* Добавляем интервалы */
        setSpacing(true);

        /* Изменяем внешний вид кнопок */
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        /* Слушаем события на кнопках */
        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(item));
        rowCost.addValueChangeListener(e -> setCost(rowCost.getValue()));

        /* Делаем форму невидимой */
        setVisible(false);
    }

    private  void setCost(String rowCostValue) {
        double result;
        if (rowCostValue.equals("")){
            return;
        }

        try {
            result = Double.parseDouble(rowCostValue);
            item.setCost(result);
            rowCost.getStyle().set("color", "#283646");
            save.getElement().setEnabled(true);
        }
        catch (NumberFormatException nfe) {
            rowCost.getStyle().set("color", "#F52518");
            save.getElement().setEnabled(false);
        }
    }

    /** Функционал сохранения */
    private void save() {
        repo.save(item);
        changeHandler.onChange();
    }

    /** Функционал удаления */
    private void delete() {
        repo.delete(item);
        changeHandler.onChange();
    }

    /** Функционал изменения */
    public void edit(Item it) {
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
