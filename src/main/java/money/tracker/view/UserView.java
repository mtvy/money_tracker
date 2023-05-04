package money.tracker.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import money.tracker.component.UserEditor;
import money.tracker.entity.User;
import money.tracker.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
//@Route("/users")
//public class UserView {
//
//    public UserRepo repo;
//
//    @
//    public Status registerUser() {
//        List<User> users = repo.findAll();
//        System.out.println("New user: " + newUser.toString());
//        for (User user : users) {
//            System.out.println("Registered user: " + newUser.toString());
//            if (user.equals(newUser)) {
//                System.out.println("User Already exists!");
//                return Status.USER_ALREADY_EXISTS;
//            }
//        }
//        repo.save(newUser);
//        return Status.SUCCESS;
//    }
//    @PostMapping("/users/login")
//    public Status loginUser(@Valid @RequestBody User user) {
//        List<User> users = repo.findAll();
//        for (User other : users) {
//            if (other.equals(user)) {
//                user.setLoggedIn(true);
//                repo.save(user);
//                return Status.SUCCESS;
//            }
//        }
//        return Status.FAILURE;
//    }
//    @PostMapping("/users/logout")
//    public Status logUserOut(@Valid @RequestBody User user) {
//        List<User> users = repo.findAll();
//        for (User other : users) {
//            if (other.equals(user)) {
//                user.setLoggedIn(false);
//                repo.save(user);
//                return Status.SUCCESS;
//            }
//        }
//        return Status.FAILURE;
//    }
//    @DeleteMapping("/users/all")
//    public Status deleteUsers() {
//        repo.deleteAll();
//        return Status.SUCCESS;
//    }
//}

/** Отображение главной страницы */
@Route("")
public class UserView extends VerticalLayout {
    private final UserRepo repo;
    Button loginBtn = new Button("Вход");
    private final TextField username = new TextField("", "Имя пользователя (больше 4 символов)");
    private final PasswordField password = new PasswordField("", "Пароль (больше 4 символов)");

//    private final TextField filter = new TextField("", "Поиск по записям");
//    private final Button addBtn = new Button("+ Добавить новую запись");
//    private final Anchor aboutAnchor = new Anchor("https://mtvy.github.io/", "Об авторе");
//    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addBtn);
//    private final Grid<Item> grid = new Grid<>(Item.class);
//    private final TextField sumCostTxt = new TextField("", "Суммарный перерасчёт: 0");

    @Autowired
    public UserView(UserRepo repo, UserEditor editor) {
        this.repo = repo;
        setSizeFull();

        username.setWidth("300px");
        username.setRequired(true);

        password.setWidth("300px");
        password.setRequired(true);
        password.setValue("");

        loginBtn.addClickListener(e -> {
            if (username.getValue().length() < 4) {
                username.getStyle().set("color", "#F52518");
                return;
            }
            if (password.getValue().length() < 4) {
                password.getStyle().set("color", "#F52518");
                return;
            }
            List<User> users = repo.findByUsername(username.getValue());
            if (users.size() < 1) {
                repo.save(new User(username.getValue(), password.getValue()));
            } else if (!users.get(0).getPassword().equals(password.getValue())) {
                password.getStyle().set("color", "#F52518");
                return;
            }

            loginBtn.getUI().ifPresent(ui -> ui.navigate(
                    MainView.class, username.getValue() + "p=" + password.getValue()));
        });

        VerticalLayout fields = new VerticalLayout(username, password, loginBtn);
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.setSizeUndefined();

        VerticalLayout uiLayout = new VerticalLayout(fields);
        uiLayout.setSizeFull();
        uiLayout.setHorizontalComponentAlignment(Alignment.CENTER, fields);

//        add(toolbar, aboutAnchor, grid, sumCostTxt, editor);

//        filter.setValueChangeMode(ValueChangeMode.EAGER);
//        filter.addValueChangeListener(e -> showItem(e.getValue()));

//        grid.asSingleSelect().addValueChangeListener(e -> editor.edit(e.getValue()));

//        addBtn.addClickListener(e -> editor.edit(new Item()));

        add(uiLayout);

        loginBtn.addClickListener(e -> login());

        editor.setChangeHandler(() -> editor.setVisible(false));

//        aboutAnchor.getElement().setAttribute("target", "_blank");
//        sumCostTxt.setEnabled(false);
//        showItem(filter.getValue());
    }

    public void login() {
        if (username.getValue().equals("")) {
            username.getElement().getStyle().set("color", "#F52518");
            return;
        }
        if (password.getValue().equals("")) {
            password.getElement().getStyle().set("color", "#F52518");
            return;
        }
        List<User> users = repo.findByUsername(username.getValue());


    }
}
