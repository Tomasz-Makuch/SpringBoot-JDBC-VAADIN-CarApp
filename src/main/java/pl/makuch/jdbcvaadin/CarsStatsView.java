package pl.makuch.jdbcvaadin;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Statistics | CAR APP")
@Route(value = "statistics", layout = MainView.class)
public class CarsStatsView extends VerticalLayout {

    private CarDAO carDAO;
    private H1 statInfo = new H1("Select mark to display number of cars");
    private ComboBox<String> comboBox = new ComboBox<>();
    private H3 carInfo = new H3();

    @Autowired
    public CarsStatsView(CarDAO carDAO) {
        this.carDAO = carDAO;
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        configComboBox();
        add(statInfo, comboBox, carInfo);
    }

    private void configComboBox() {
        comboBox.setItems(carDAO.getAllCarMarks());
        comboBox.setPlaceholder("Select mark...");
        comboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                carInfo.setClassName("carNum");
                carInfo.setText("number of cars not available");
            } else {
                carInfo.setClassName("carNum");
                carInfo.setText("Total cars number with mark "+event.getValue()+" : "+carDAO.getCarsNumByMark(event.getValue()));
            }
        });
    }



}
