package com.example.zpo_projekt.window;

import com.example.zpo_projekt.controller.AppUserController;
import com.example.zpo_projekt.controller.ScheduleController;
import com.example.zpo_projekt.layout.ScheduleLayout;
import com.example.zpo_projekt.model.AppUser;
import com.example.zpo_projekt.model.Schedule;
import com.example.zpo_projekt.repository.ScheduleRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@UIScope
@SpringComponent
public class NewScheduleWindow extends Window {

    @Autowired
    AppUserController appUserController;

    @Autowired
    ScheduleController scheduleController;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleLayout scheduleLayout;

    private final ComboBox<String> appUsersComboBox = new ComboBox<>("Pracownik:");
    private final ComboBox<String> startTimeMonday = new ComboBox<>("Od:");
    private final ComboBox<String> endTimeMonday = new ComboBox<>("Do:");
    private final ComboBox<String> startTimeTuesday = new ComboBox<>("Od:");
    private final ComboBox<String> endTimeTuesday = new ComboBox<>("Do:");
    private final ComboBox<String> startTimeWednesday = new ComboBox<>("Od:");
    private final ComboBox<String> endTimeWednesday = new ComboBox<>("Do:");
    private final ComboBox<String> startTimeThursday = new ComboBox<>("Od:");
    private final ComboBox<String> endTimeThursday = new ComboBox<>("Do:");
    private final ComboBox<String> startTimeFriday = new ComboBox<>("Od:");
    private final ComboBox<String> endTimeFriday = new ComboBox<>("Do:");
    private final ComboBox<String> startTimeSaturday = new ComboBox<>("Od:");
    private final ComboBox<String> endTimeSaturday = new ComboBox<>("Do:");
    private final ComboBox<String> startTimeSunday = new ComboBox<>("Od:");
    private final ComboBox<String> endTimeSunday = new ComboBox<>("Do:");
    private final Button saveChangesButton = saveChangesButton();

    @PostConstruct
    public void init(){
        update();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(appUsersComboBox,horizontalLayoutForAllLayouts(),saveChangesButton);
        verticalLayout.setComponentAlignment(appUsersComboBox,Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(saveChangesButton,Alignment.MIDDLE_CENTER);
        setContent(verticalLayout);
        setSettingsForWindow();
    }

    public void update(){
        appUsersComboBox.setItems(appUserController.getAll().stream().map(AppUser::getName));
        scheduleLayout.update();

    }

    private void setSettingsForWindow(){
        setResizable(false);
        setDraggable(false);
        center();
    }

    private VerticalLayout verticalLayoutMonday(){
        VerticalLayout verticalLayoutMonday = new VerticalLayout();
        Label monday = new Label("Poniedziałek");
        startTimeMonday.setItems(timeList());
        endTimeMonday.setItems(timeList());
        verticalLayoutMonday.addComponents(monday,startTimeMonday,endTimeMonday);

        return verticalLayoutMonday;
    }

    private VerticalLayout verticalLayoutTuesday(){
        VerticalLayout verticalLayoutTuesday = new VerticalLayout();
        Label tuesday = new Label("Wtorek");
        startTimeTuesday.setItems(timeList());
        endTimeTuesday.setItems(timeList());
        verticalLayoutTuesday.addComponents(tuesday,startTimeTuesday,endTimeTuesday);

        return verticalLayoutTuesday;
    }

    private VerticalLayout verticalLayoutWednesday(){
        VerticalLayout verticalLayoutWednesday = new VerticalLayout();
        Label wednesday = new Label("Środa");
        startTimeWednesday.setItems(timeList());
        endTimeWednesday.setItems(timeList());
        verticalLayoutWednesday.addComponents(wednesday,startTimeWednesday,endTimeWednesday);

        return verticalLayoutWednesday;
    }

    private VerticalLayout verticalLayoutThursday(){
        VerticalLayout verticalLayoutThursday = new VerticalLayout();
        Label thursday = new Label("Czwartek");
        startTimeThursday.setItems(timeList());
        endTimeThursday.setItems(timeList());
        verticalLayoutThursday.addComponents(thursday,startTimeThursday,endTimeThursday);

        return verticalLayoutThursday;
    }

    private VerticalLayout verticalLayoutFriday(){
        VerticalLayout verticalLayoutFriday = new VerticalLayout();
        Label friday = new Label("Piątek");
        startTimeFriday.setItems(timeList());
        endTimeFriday.setItems(timeList());
        verticalLayoutFriday.addComponents(friday,startTimeFriday,endTimeFriday);

        return verticalLayoutFriday;
    }

    private VerticalLayout verticalLayoutSaturday(){
        VerticalLayout verticalLayoutSaturday = new VerticalLayout();
        Label saturday = new Label("Sobota");
        startTimeSaturday.setItems(timeList());
        endTimeSaturday.setItems(timeList());
        verticalLayoutSaturday.addComponents(saturday,startTimeSaturday,endTimeSaturday);

        return verticalLayoutSaturday;
    }

    private VerticalLayout verticalLayoutSunday(){
        VerticalLayout verticalLayoutSunday = new VerticalLayout();
        Label sunday = new Label("Niedziela");
        startTimeSunday.setItems(timeList());
        endTimeSunday.setItems(timeList());
        verticalLayoutSunday.addComponents(sunday,startTimeSunday,endTimeSunday);

        return verticalLayoutSunday;
    }

    private HorizontalLayout horizontalLayoutForAllLayouts(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(verticalLayoutMonday(),verticalLayoutTuesday(),verticalLayoutWednesday(),
                verticalLayoutThursday(),verticalLayoutFriday(),verticalLayoutSaturday(),verticalLayoutSunday());

        return horizontalLayout;
    }

    private Button saveChangesButton(){
        Button saveChangesButton = new Button("Zapisz zmiany");
        saveChangesButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveChangesButton.addClickListener(v->{
            String monday,tuesday,wednesday;
            String thursday,friday,saturday,sunday;
            if (!startTimeMonday.isEmpty() && !endTimeMonday.isEmpty()){
                monday = startTimeMonday.getValue() + " - " + endTimeMonday.getValue();
            }
            else {
                monday = "";
            }
            if (!startTimeTuesday.isEmpty() && !endTimeTuesday.isEmpty()){
                tuesday = startTimeTuesday.getValue() + " - " + endTimeTuesday.getValue();
            }
            else {
                tuesday = "";
            }
            if (!startTimeWednesday.isEmpty() && !endTimeWednesday.isEmpty()){
                wednesday = startTimeWednesday.getValue() + " - " + endTimeWednesday.getValue();
            }
            else {
                wednesday = "";
            }
            if (!startTimeThursday.isEmpty() && !endTimeThursday.isEmpty()){
                thursday = startTimeThursday.getValue() + " - " + endTimeThursday.getValue();

            }
            else {
                thursday = "";
            }
            if (!startTimeFriday.isEmpty() && !endTimeFriday.isEmpty()){
                friday = startTimeFriday.getValue() + " - " + endTimeFriday.getValue();
            }
            else {
                friday = "";
            }
            if (!startTimeSaturday.isEmpty() && !endTimeSaturday.isEmpty()){
                saturday = startTimeSaturday.getValue() + " - " + endTimeSaturday.getValue();
            }
            else {
                saturday = "";
            }
            if (!startTimeSunday.isEmpty() && !endTimeSunday.isEmpty()){
                sunday = startTimeSunday.getValue() + " - " + endTimeSunday.getValue();
            }
            else {
                sunday = "";
            }
            if (!appUsersComboBox.isEmpty()){
                Schedule scheduleToEdit = scheduleController.getSingleSchedule(appUsersComboBox.getValue());
                scheduleController.updateSchedule(scheduleToEdit,monday,tuesday,wednesday,thursday,friday,saturday,sunday);
                update();
                close();
                scheduleRepository.updateDate(LocalDate.now().toString(),"date");
                scheduleLayout.dateLabel.setValue(scheduleController.getSingleSchedule("date").getModificationDate());
                Notification.show("Pomyślnie edytowano grafik", Notification.Type.HUMANIZED_MESSAGE);
            }
            else
            Notification.show("Wybierz pracownika!", Notification.Type.ERROR_MESSAGE);

        });

        return saveChangesButton;
    }

    private List<String> timeList(){
        List<String> timeList = new ArrayList<>();
        timeList.add("12:00");
        timeList.add("12:30");
        timeList.add("13:00");
        timeList.add("13:30");
        timeList.add("14:00");
        timeList.add("14:30");
        timeList.add("15:00");
        timeList.add("15:30");
        timeList.add("16:00");
        timeList.add("16:30");
        timeList.add("17:00");
        timeList.add("17:30");
        timeList.add("18:00");
        timeList.add("18:30");
        timeList.add("19:00");
        timeList.add("19:30");
        timeList.add("20:00");
        timeList.add("20:30");
        timeList.add("21:00");
        timeList.add("21:30");
        timeList.add("22:00");
        return timeList;
    }


}
