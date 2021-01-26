package com.example.zpo_projekt.layout;

import com.example.zpo_projekt.controller.AppUserController;
import com.example.zpo_projekt.controller.ScheduleController;
import com.example.zpo_projekt.model.AppUser;
import com.example.zpo_projekt.model.Schedule;
import com.example.zpo_projekt.repository.ScheduleRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class ScheduleLayout extends VerticalLayout {

    @Autowired
    private ScheduleController scheduleController;

    @Autowired
    private AppUserController appUserController;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private final Grid<Schedule> scheduleGrid = new Grid<>();
    private final Grid<AppUser> appUserGrid = new Grid<>();
    public Label dateLabel = new Label();

    @PostConstruct
    void init(){
        dateLabel.setValue(scheduleController.getSingleSchedule("date").getModificationDate());
        dateLabel.setCaption("Data ostatniej modyfikacji:");
        HorizontalLayout horizontalLayout = new HorizontalLayout(setAppUserGrid(),setScheduleGrid());
        addComponents(dateLabel,horizontalLayout);
    }

    public void update(){
        scheduleGrid.setItems(scheduleRepository.findByNameNotLike("date"));
        appUserGrid.setItems(appUserController.getAll());
        dateLabel.setValue(scheduleController.getSingleSchedule("date").getModificationDate());
    }

    private Grid<Schedule> setScheduleGrid(){
        scheduleGrid.setItems(scheduleRepository.findByNameNotLike("date"));
        scheduleGrid.removeAllColumns();
        scheduleGrid.addColumn(Schedule::getName).setCaption("Imię:").getId();
        scheduleGrid.addColumn(Schedule::getMonday).setCaption("Poniedziałek:");
        scheduleGrid.addColumn(Schedule::getTuesday).setCaption("Wtorek:");
        scheduleGrid.addColumn(Schedule::getWednesday).setCaption("Środa");
        scheduleGrid.addColumn(Schedule::getThursday).setCaption("Czwartek");
        scheduleGrid.addColumn(Schedule::getFriday).setCaption("Piątek:");
        scheduleGrid.addColumn(Schedule::getSaturday).setCaption("Sobota");
        scheduleGrid.addColumn(Schedule::getSunday).setCaption("Niedziela:");
        scheduleGrid.setWidth("1050px");
        scheduleGrid.getEditor().setEnabled(true);

        return scheduleGrid;
    }

    private Grid<AppUser> setAppUserGrid(){
        appUserGrid.setItems(appUserController.getAll());
        appUserGrid.removeAllColumns();
        appUserGrid.addColumn(AppUser::getName).setCaption("Pracownik:");
        appUserGrid.setWidth("130px");

        return appUserGrid;
    }



}
