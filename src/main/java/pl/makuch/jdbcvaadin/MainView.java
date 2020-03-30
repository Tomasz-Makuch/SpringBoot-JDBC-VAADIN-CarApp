package pl.makuch.jdbcvaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private CarDAO carDAO;
    private CarForm carForm;
    private Grid<Car> carGrid = new Grid<>(Car.class);
    private TextField carTextFilter = new TextField();
    private ComboBox<String> filterCombo = new ComboBox<>();
    private Button addButton;

    private String[] carEntities = new String[]{"id", "mark", "model", "color"};
    private HorizontalLayout searchLayout = new HorizontalLayout();


    public MainView(CarDAO carDAO) {
        this.carDAO = carDAO;
        carForm = new CarForm();
        carForm.addListener(CarForm.SaveEvent.class, this::saveCar);
        carForm.addListener(CarForm.DeleteEvent.class, this::deleteCar);
        carForm.addListener(CarForm.CloseEvent.class, e-> closeEditor());
        searchLayout.setHeight("30vh");
        setClassName("cars-view");
        setSizeFull();
        configureGrid();
        updateCarsList();
        getToolbar();
        cofigureFilterCombo();
        closeEditor();

        Div content = new Div(carGrid, carForm);
        content.setSizeFull();
        content.addClassName("content");

        addButton = new Button("Add car", click -> addCar());


        searchLayout.add(filterCombo, carTextFilter, addButton);
        searchLayout.setHeight("10vh");
        searchLayout.setSpacing(true);
        add(searchLayout, content);


    }

    private void addCar() {
        carGrid.asSingleSelect().clear();
        editCar(new Car());
    }

    private void deleteCar(CarForm.DeleteEvent evt) {
        carDAO.deleteCar(evt.getCar());
        updateCarsList();
    }

    private void saveCar(CarForm.SaveEvent evt) {
        carDAO.updateCar(evt.getCar());
        updateCarsList();
    }

    private void closeEditor() {
        carForm.setCar(null);
        carForm.setVisible(false);
        removeClassName("editing");
    }

    private void cofigureFilterCombo() {

        filterCombo.setPlaceholder("Select filter type");
        filterCombo.setItems(carEntities);
        filterCombo.setValue("mark");
        filterCombo.addValueChangeListener(e -> updateCarsList());
    }

    private void getToolbar() {
        carTextFilter.setPlaceholder("Insert words...");
        carTextFilter.setClearButtonVisible(true);
        carTextFilter.setValueChangeMode(ValueChangeMode.LAZY);
        carTextFilter.addValueChangeListener(e -> updateCarsList());
    }

    private void updateCarsList() {
        List<Car> allCars = carDAO.getAllCars(filterCombo.getValue(), carTextFilter.getValue());
        carGrid.setItems(allCars);

    }

    private void configureGrid() {
        carGrid.setClassName("cars-grid");
        carGrid.setHeight("60vh");
        carGrid.setColumns(carEntities);
        carGrid.getColumns().forEach(carColumn -> carColumn.setAutoWidth(true));
        carGrid.asSingleSelect().addValueChangeListener(event -> editCar(event.getValue()));
    }

    private void editCar(Car car) {
        if(car==null){
            closeEditor();
        }
        else{
            carForm.setCar(car);
            carForm.setVisible(true);
            addClassName("editing");
        }
    }

}
