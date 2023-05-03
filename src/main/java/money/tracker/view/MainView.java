package money.tracker.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import money.tracker.component.ItemEditor;
import money.tracker.domain.Item;
import money.tracker.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/** Отображение главной страницы */
@Route
public class MainView extends VerticalLayout {
    private final ItemRepo repo;
    private final TextField filter = new TextField("", "Поиск по записям");
    private final Button addBtn = new Button("+ Добавить новую запись");
    private final Anchor aboutAnchor = new Anchor("https://mtvy.github.io/", "Об авторе");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addBtn);
    private final Grid<Item> grid = new Grid<>(Item.class);
    private final TextField sumCostTxt = new TextField("", "Суммарный перерасчёт: 0");

    @Autowired
    public MainView(ItemRepo repo, ItemEditor editor) {
        this.repo = repo;

        add(toolbar, aboutAnchor, grid, sumCostTxt, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showItem(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> editor.edit(e.getValue()));

        addBtn.addClickListener(e -> editor.edit(new Item()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showItem(filter.getValue());
        });

        aboutAnchor.getElement().setAttribute("target", "_blank");
        sumCostTxt.setEnabled(false);
        showItem(filter.getValue());
    }

    /**
     * Подсчёт суммарных цен в записях
     * @return суммырный cost всех записей
     */
    public double countSum(List<Item> items) {
        double summer = 0;
        for (Item item : items) {
            summer += item.getCost();
        }
        return summer;
    }

    /**
     * Вывод записей
     * @param type - если не пустой выберет записи только с этим типом
     */
    public void showItem(String type) {
        /* Если без фильтрации берём все иначе фильтруем */
        List<Item> items = (type.isEmpty()) ? repo.findAll() : repo.findByType(type);
        grid.setItems(items);
        sumCostTxt.setValue("Сумма: "+countSum(items));
    }
}
