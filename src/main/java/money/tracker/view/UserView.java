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

/** Отображение главной страницы */
@Route("")
public class UserView extends VerticalLayout {
    private final UserRepo repo;
    Button loginBtn = new Button("Вход");
    private final TextField username = new TextField("", "Имя пользователя (больше 4 символов)");
    private final PasswordField password = new PasswordField("", "Пароль (больше 4 символов)");

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

        add(uiLayout);

        loginBtn.addClickListener(e -> login());

        editor.setChangeHandler(() -> editor.setVisible(false));
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
