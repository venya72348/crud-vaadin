package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    // implement the view with a grid and a form to add new customers
    private final CustomerRepository repo;
    final Grid<Customer> grid;
    final TextField filter;
    private final Button addNewBtnn;
    // Vladyslav do you best
    public MainView(CustomerRepository repo) {
        this.repo = repo;
        this.grid = new Grid<>(Customer.class);
        this.filter = new TextField();
        this.addNewBtnn = new Button("Add your people");

        filter.setPlaceholder("Filter by last name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomersFunction(e.getValue()));

        addNewBtnn.addClickListener(e -> {
            repo.save(new Customer("Alex", "Novak"));
            listCustomersFunction(filter.getValue());
        });

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtnn);
        add(actions, grid);

        grid.setHeight("300px");
        grid.setColumns("id", "firstName", "lastName");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        listCustomersFunction("");
    }

    private void listCustomersFunction(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}

// to do: Implement the rest of the functionality