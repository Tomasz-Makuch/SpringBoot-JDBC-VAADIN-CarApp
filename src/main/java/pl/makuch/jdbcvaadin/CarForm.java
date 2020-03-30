package pl.makuch.jdbcvaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

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

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())) );
        cancel.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save,delete,cancel);
    }

    private void validateAndSave() {
        if(binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<CarForm> {
        private Car car;

        protected ContactFormEvent(CarForm source, Car car) {
            super(source, false);
            this.car = car;
        }

        public Car getCar() {
            return car;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(CarForm source, Car car) {
            super(source, car);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(CarForm source, Car car) {
            super(source, car);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(CarForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void setCar(Car car){
        binder.setBean(car);
    }
}
