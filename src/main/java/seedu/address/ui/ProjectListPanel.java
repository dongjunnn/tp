package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * Panel that displays projects associated with a selected person.
 * Shows a list of projects on top and detailed information about the selected project below.
 */
public class ProjectListPanel extends UiPart<Region> {

    private static final String FXML = "ProjectListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ProjectListPanel.class);

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
    private Label projectsSectionLabel;

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
     * Programmatically selects the project at the specified index in the currently displayed project list.
     * This will trigger the selection listener which automatically displays the project details.
     *
     * @param index The index of the project to select (zero-based via Index.getZeroBased())
     */
    public void selectProject(Index index) {
        requireNonNull(index);
        int zeroBasedIndex = index.getZeroBased();

        if (projectListView == null || projectListView.getItems() == null) {
            logger.warning("Cannot select project: project list view not initialized");
            return;
        }

        if (zeroBasedIndex >= 0 && zeroBasedIndex < projectListView.getItems().size()) {
            projectListView.getSelectionModel().select(zeroBasedIndex);
            projectListView.scrollTo(zeroBasedIndex);
            logger.info("Selected project at index: " + (zeroBasedIndex + 1));
        } else {
            logger.warning("Attempted to select invalid project index: " + (zeroBasedIndex + 1)
                    + " (total projects: " + projectListView.getItems().size() + ")");
        }
    }

    /**
     * Shows details for a single project without selecting a person.
     * Used for pdetails command to display only the requested project.
     * Hides the project list view and directly shows the details section.
     *
     * @param project The project to display. Must not be null.
     */
    public void showSingleProjectDetails(Project project) {
        requireNonNull(project, "Project cannot be null");

        // Hide placeholder, show content
        if (placeholderContainer != null) {
            placeholderContainer.setVisible(false);
            placeholderContainer.setManaged(false);
        }

        if (contentContainer != null) {
            contentContainer.setVisible(true);
            contentContainer.setManaged(true);
        }

        // Hide the person name header label - we don't need it for pdetails
        if (personNameLabel != null) {
            personNameLabel.setVisible(false);
            personNameLabel.setManaged(false);
        }

        // Hide the "Projects:" section label
        if (projectsSectionLabel != null) {
            projectsSectionLabel.setVisible(false);
            projectsSectionLabel.setManaged(false);
        }

        // Hide the status label
        if (projectsStatusLabel != null) {
            projectsStatusLabel.setVisible(false);
            projectsStatusLabel.setManaged(false);
        }

        // Hide the project list view - we don't need it for pdetails
        if (projectListView != null) {
            projectListView.setVisible(false);
            projectListView.setManaged(false);
        }

        // Directly show the project details
        showProjectDetails(project);
    }

    /**
     * Shows all projects in the system without selecting a person.
     * Used for pshow all command.
     */
    public void showAllProjects() {
        // Hide placeholder, show content
        if (placeholderContainer != null) {
            placeholderContainer.setVisible(false);
            placeholderContainer.setManaged(false);
        }

        if (contentContainer != null) {
            contentContainer.setVisible(true);
            contentContainer.setManaged(true);
        }

        // Update header
        if (personNameLabel != null) {
            personNameLabel.setText("All Projects");
            personNameLabel.setVisible(true);
            personNameLabel.setManaged(true);
        }

        // Show "Projects:" label if it exists (might be null if FXML was reverted)
        if (projectsSectionLabel != null) {
            projectsSectionLabel.setVisible(true);
            projectsSectionLabel.setManaged(true);
        }

        // Check if there are any projects
        if (allProjects.isEmpty()) {
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
            // Hide status label
            if (projectsStatusLabel != null) {
                projectsStatusLabel.setVisible(false);
                projectsStatusLabel.setManaged(false);
            }

            // Show all projects in the list
            if (projectListView != null) {
                projectListView.setVisible(true);
                projectListView.setManaged(true);
                projectListView.setItems(allProjects);
            }

            // Clear selection and hide details initially
            projectListView.getSelectionModel().clearSelection();
            hideProjectDetails();
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
