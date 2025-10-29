package seedu.address.ui;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.model.project.Project;

/**
 * A small popup window that shows projects with deadlines in the next 7 days.
 */
public class DueSoonWindow extends UiPart<Stage> {

    private static final String FXML = "DueSoonWindow.fxml";

    @FXML
    private ListView<String> dueSoonListView;

    /**
     * Creates the window, populates data, but does NOT show it yet.
     */
    public DueSoonWindow(List<Project> dueSoonProjects) {
        super(FXML, new Stage());

        ObservableList<String> displayItems = FXCollections.observableArrayList(
                dueSoonProjects.stream()
                        .sorted(
                                Comparator
                                        .comparing(Project::getDeadline)
                                        .thenComparing(DueSoonWindow::priorityRank)
                        )
                        .map(p -> String.format(
                                "%s  —  due %s  (Priority: %s)",
                                p.getName(),
                                p.getDeadline(),
                                p.getPriority()
                        ))
                        .collect(Collectors.toList())
        );

        dueSoonListView.setItems(displayItems);


        if (!displayItems.isEmpty()) {
            dueSoonListView.getSelectionModel().select(0);
        }
    }

    /**
     * Orders priority from HIGH to LOW.
     */

    private static int priorityRank(Project p) {
        return switch (p.getPriority()) {
        case HIGH -> 0;
        case MEDIUM -> 1;
        case LOW -> 2;
        };
    }

    /**
     * Shows the window.
     */
    public void show() {
        getRoot().show();
        getRoot().requestFocus();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Static helper to build the "due within 7 days" subset.
     */
    public static List<Project> findProjectsDueSoon(List<Project> allProjects) {
        LocalDate now = LocalDate.now();
        LocalDate cutoff = now.plusDays(7);

        return allProjects.stream()
                .filter(p -> {
                    LocalDate d = p.getDeadline();
                    if (d == null) {
                        return false;
                    }

                    boolean notPast = !d.isBefore(now);
                    boolean withinWeek = !d.isAfter(cutoff);
                    return notPast && withinWeek;
                })
                .collect(Collectors.toList());
    }

    public void updateProjects(List<Project> updatedProjects) {
        if (updatedProjects == null) {
            return;
        }

        ObservableList<String> displayItems = FXCollections.observableArrayList(
                updatedProjects.stream()
                        .sorted(
                                Comparator
                                        .comparing(Project::getDeadline)
                                        .thenComparing(DueSoonWindow::priorityRank)
                        )
                        .map(p -> String.format(
                                "%s  —  due %s  (Priority: %s)",
                                p.getName(),
                                p.getDeadline(),
                                p.getPriority()
                        ))
                        .collect(Collectors.toList())
        );

        dueSoonListView.setItems(displayItems);

        if (!displayItems.isEmpty()) {
            dueSoonListView.getSelectionModel().select(0);
        }
    }

    public void close() {
        if (getRoot() != null && getRoot().isShowing()) {
            getRoot().close();
        }
    }
}
