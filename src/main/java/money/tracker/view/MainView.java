package money.tracker.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import money.tracker.component.ItemEditor;
import money.tracker.entity.Item;
import money.tracker.entity.User;
import money.tracker.repo.ItemRepo;
import money.tracker.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/** Отображение главной страницы */
@Route("table")
public class MainView extends VerticalLayout implements HasUrlParameter<String> {
    private User user;
    private final ItemRepo repo;
    private final UserRepo urepo;
    private final ItemEditor editor;
    private final TextField filter = new TextField("", "Поиск по записям");
    private final Button addBtn = new Button("+ Добавить новую запись");
    private final Anchor aboutAnchor = new Anchor("https://mtvy.github.io/", "Об авторе");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addBtn);
    private final Grid<Item> grid = new Grid<>(Item.class);
    private final TextField sumCostTxt = new TextField("", "Суммарный перерасчёт: 0");

    @Autowired
    public MainView(ItemRepo repo, ItemEditor editor, UserRepo userRepo) {
        this.repo = repo;
        this.editor = editor;
        this.urepo = userRepo;
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
    public void showItem(String type, String username) {
        /* Если без фильтрации берём все иначе фильтруем */
        List<Item> items = (type.isEmpty()) ? repo.findByUser(username) : repo.findByType(type, username);
        grid.setItems(items);
        sumCostTxt.setValue("Сумма: "+countSum(items));
    }

    @Override
    public void setParameter(BeforeEvent event, String userpass) {
        System.out.println(userpass);
        String[] data = userpass.split("p=");
        if (data.length < 2) {
            Text txt = new Text("Нет доступа!");
            add(txt);
            return;
        }

        this.user = new User(data[0], data[1]);
        List<User> users = urepo.findByUsername(this.user.getUsername());
        if (users.size() < 1 || !users.get(0).getPassword().equals(this.user.getPassword())) {
            Text txt = new Text("Нет доступа!");
            add(txt);
            return;
        }

        add(toolbar, aboutAnchor, grid, sumCostTxt, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showItem(e.getValue(), this.user.getUsername()));

        grid.asSingleSelect().addValueChangeListener(e -> editor.edit(e.getValue(), this.user));

        addBtn.addClickListener(e -> editor.edit(new Item(), this.user));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showItem(filter.getValue(), this.user.getUsername());
        });

        aboutAnchor.getElement().setAttribute("target", "_blank");
        sumCostTxt.setEnabled(false);
        showItem(filter.getValue(), this.user.getUsername());
    }
}
