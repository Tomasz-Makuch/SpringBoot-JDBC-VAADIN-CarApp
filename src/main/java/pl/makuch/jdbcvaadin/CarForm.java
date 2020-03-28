package pl.makuch.jdbcvaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CarForm extends FormLayout {

    private TextField textMark = new TextField("Mark");
    private TextField textModel = new TextField("Model");
    private TextField textColor = new TextField("Color");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button cancel = new Button("CAncel");

    public CarForm() {
        addClassName("form-view");
        add(
                textMark,
                textModel,
                textColor,
                configButonLayout()
        );
    }

    private Component configButonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save,delete,cancel);
    }
}
