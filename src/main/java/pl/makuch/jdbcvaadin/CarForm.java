package pl.makuch.jdbcvaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class CarForm extends FormLayout {

    private TextField mark = new TextField("Mark");
    private TextField model = new TextField("Model");
    private TextField color = new TextField("Color");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button cancel = new Button("Cancel");

    Binder<Car> binder = new BeanValidationBinder<>(Car.class);

    public CarForm() {

        binder.bindInstanceFields(this);
        addClassName("form-view");
        add(
                mark,
                model,
                color,
                configButonLayout()
        );
    }

    private Component configButonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save,delete,cancel);
    }


    public void setCar(Car car){
        binder.setBean(car);
    }

    public Button getSaveButton() {
        return save;
    }

    public Button getDeleteButton() {
        return delete;
    }

    public Button getCancelButton() {
        return cancel;
    }

    public Car getCarFromForm(){
        return new Car(mark.getValue(), model.getValue(), color.getValue());
    }
}
