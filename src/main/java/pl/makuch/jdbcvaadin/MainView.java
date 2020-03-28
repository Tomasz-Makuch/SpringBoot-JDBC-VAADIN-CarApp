package pl.makuch.jdbcvaadin;

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
    private String[] carEntities = new String[]{"id", "mark", "model", "color"};
    private HorizontalLayout searchLayout = new HorizontalLayout();


    public MainView(CarDAO carDAO) {
        this.carDAO = carDAO;
        carForm = new CarForm();
        searchLayout.setHeight("30vh");
        setClassName("cars-view");
        setSizeFull();
        configureGrid();
        updateCarsList();
        cofigureFilter();
        cofigureFilterCombo();

        Div content = new Div(carGrid, carForm);
        content.setSizeFull();
        content.addClassName("content");

        searchLayout.add(filterCombo, carTextFilter);
        searchLayout.setHeight("10vh");
        add(searchLayout, content);

    }

    private void cofigureFilterCombo() {


        filterCombo.setPlaceholder("Select filter type");
        filterCombo.setItems(carEntities);
        filterCombo.setValue("mark");
        filterCombo.addValueChangeListener(e -> updateCarsList());
    }

    private void cofigureFilter() {
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
    }

}
