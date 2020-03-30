package pl.makuch.jdbcvaadin;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

@StyleSheet("styles.css")
public class MainView extends AppLayout {

    public MainView() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {

        Icon icon = new Icon(VaadinIcon.CAR);
        H2 logo = new H2("Car application");
        logo.setClassName("logo");
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setWidth("100%");
        header.setHeight("8vh");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.add(new DrawerToggle(), icon, logo);
        addToNavbar(header);
    }

    private void createDrawer() {

        RouterLink carList = new RouterLink("Car List", CarListView.class);
        carList.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink dashboardLink = new RouterLink("Car statistics", CarsStatsView.class);

        addToDrawer(new VerticalLayout(carList, dashboardLink));
    }
}
