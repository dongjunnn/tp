package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * Panel that displays projects associated with a selected person.
 * Shows a list of projects on top and detailed information about the selected project below.
 */
public class ProjectListPanel extends UiPart<Region> {

    private static final String FXML = "ProjectListPanel.fxml";

    private final ObservableList<Project> allProjects;

    @FXML
    private VBox placeholderContainer;

    @FXML
    private VBox contentContainer;

    @FXML
    private Label personNameLabel;

    @FXML
    private ListView<Project> projectListView;

    @FXML
    private Label projectsStatusLabel;

    @FXML
    private Label projectName;

    @FXML
    private Label priority;

    @FXML
    private Label deadline;

    @FXML
    private VBox membersContainer;

    @FXML
    private VBox projectDetailsContainer;

    /**
     * Creates a {@code ProjectListPanel} with the given list of all projects.
     *
     * @param allProjects The list of all projects. Must not be null.
     */
    public ProjectListPanel(ObservableList<Project> allProjects) {
        super(FXML);
        requireNonNull(allProjects, "Project list cannot be null");

        this.allProjects = allProjects;

        // Setup project list view
        projectListView.setCellFactory(listView -> new ProjectListViewCell());

        // Listen to project selection to show details
        projectListView.getSelectionModel().selectedItemProperty().addListener((
                observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showProjectDetails(newValue);
                    } else {
                        hideProjectDetails();
                    }
                });

        showPlaceholder();
    }

    /**
     * Shows the placeholder when no person is selected.
     */
    private void showPlaceholder() {
        if (placeholderContainer != null) {
            placeholderContainer.setVisible(true);
            placeholderContainer.setManaged(true);
        }

        if (contentContainer != null) {
            contentContainer.setVisible(false);
            contentContainer.setManaged(false);
        }
    }

    /**
     * Displays projects for the given person.
     * Finds all projects where the person is a member using project.getMembers().
     *
     * @param person The person whose projects to display. Must not be null.
     */
    public void showProjectsForPerson(Person person) {
        requireNonNull(person, "Person cannot be null");

        // Hide placeholder, show content
        if (placeholderContainer != null) {
            placeholderContainer.setVisible(false);
            placeholderContainer.setManaged(false);
        }

        if (contentContainer != null) {
            contentContainer.setVisible(true);
            contentContainer.setManaged(true);
        }

        // Update person name label
        if (personNameLabel != null) {
            personNameLabel.setText("Projects for: " + person.getName().fullName);
        }

        // Find all projects where this person is a member
        List<Project> personProjects = allProjects.stream()
                .filter(project -> project != null)
                .filter(project -> project.getMembers() != null)
                .filter(project -> project.getMembers().contains(person))
                .collect(Collectors.toList());

        // Show "None" if no projects, otherwise show project list
        if (personProjects.isEmpty()) {
            if (projectsStatusLabel != null) {
                projectsStatusLabel.setText("None");
                projectsStatusLabel.setVisible(true);
                projectsStatusLabel.setManaged(true);
            }
            if (projectListView != null) {
                projectListView.setVisible(false);
                projectListView.setManaged(false);
            }
            hideProjectDetails();
        } else {
            if (projectsStatusLabel != null) {
                projectsStatusLabel.setVisible(false);
                projectsStatusLabel.setManaged(false);
            }
            if (projectListView != null) {
                projectListView.setVisible(true);
                projectListView.setManaged(true);
                projectListView.setItems(FXCollections.observableArrayList(personProjects));
            }

            // Clear selection and hide details initially
            projectListView.getSelectionModel().clearSelection();
            hideProjectDetails();

            // If only one project, auto-select it
            if (personProjects.size() == 1) {
                projectListView.getSelectionModel().select(0);
            }
        }
    }

    /**
     * Displays detailed information about the selected project.
     *
     * @param project The project to display. Must not be null.
     */
    private void showProjectDetails(Project project) {
        requireNonNull(project, "Project cannot be null");

        if (projectDetailsContainer != null) {
            projectDetailsContainer.setVisible(true);
            projectDetailsContainer.setManaged(true);
        }

        if (projectName != null) {
            projectName.setText(project.getName());
        }

        if (priority != null) {
            priority.setText(project.getPriority().toString());
        }

        if (deadline != null) {
            deadline.setText(project.getDeadline().toString());
        }

        // Display members as non-clickable text labels
        if (membersContainer != null) {
            membersContainer.getChildren().clear();
            for (Person member : project.getMembers()) {
                Label memberLabel = new Label(member.getName().fullName);
                memberLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
                membersContainer.getChildren().add(memberLabel);
            }
        }
    }

    /**
     * Hides the project details section.
     */
    private void hideProjectDetails() {
        if (projectDetailsContainer != null) {
            projectDetailsContainer.setVisible(false);
            projectDetailsContainer.setManaged(false);
        }
    }

    /**
     * Custom cell for displaying project in the list.
     */
    class ProjectListViewCell extends ListCell<Project> {
        @Override
        protected void updateItem(Project project, boolean empty) {
            super.updateItem(project, empty);

            if (empty || project == null) {
                setText(null);
            } else {
                setText(project.getName() + " (" + project.getPriority() + ")");
            }
        }
    }
}
