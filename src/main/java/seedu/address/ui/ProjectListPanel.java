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
    private static final String PROJECTS_FOR_LABEL = "Projects for: ";
    private static final String ALL_PROJECTS_LABEL = "All Projects";
    private static final String NO_PROJECTS_MESSAGE = "None";
    private static final String MEMBER_LABEL_STYLE = "-fx-text-fill: white; -fx-font-size: 13px;";

    private final Logger logger = LogsCenter.getLogger(ProjectListPanel.class);

    private final ObservableList<Project> allProjects;
    private boolean isShowingAllProjects = false;
    private String currentlyDisplayedProjectName = null; // Track project being displayed for auto-refresh
    private Person currentlySelectedPerson = null; // Track person whose projects are being shown

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

        // Disable mouse clicking for selection but allow scrolling
        projectListView.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
            event.consume(); // Block mouse clicks (prevents selection)
        });
        projectListView.setFocusTraversable(false); // Disable keyboard navigation

        // Listen to project selection to show details
        projectListView.getSelectionModel().selectedItemProperty().addListener((
                observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showProjectDetails(newValue);
                    } else {
                        hideProjectDetails();
                    }
                });

        // Listen to changes in the projects list to auto-refresh displayed project
        allProjects.addListener((javafx.collections.ListChangeListener<Project>) change -> {
            refreshDisplayedProjectIfNeeded();
        });

        showPlaceholder();
    }

    /**
     * Shows the placeholder when no person is selected.
     */
    private void showPlaceholder() {
        this.currentlyDisplayedProjectName = null; // Clear tracked project
        this.currentlySelectedPerson = null; // Clear tracked person

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

        this.isShowingAllProjects = false;
        this.currentlyDisplayedProjectName = null;
        this.currentlySelectedPerson = person;

        showContentArea();
        configureHeaderForPerson(person.getName().fullName);

        List<Project> personProjects = getProjectsForPerson(person);

        if (personProjects.isEmpty()) {
            showEmptyProjectList();
        } else {
            showProjectList(personProjects);
            autoSelectSingleProject(personProjects);
        }
    }

    /**
     * Displays detailed information about the selected project.
     *
     * @param project The project to display. Must not be null.
     */
    private void showProjectDetails(Project project) {
        requireNonNull(project, "Project cannot be null");

        setContainerVisibility(projectDetailsContainer, true);

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
                memberLabel.setStyle(MEMBER_LABEL_STYLE);
                membersContainer.getChildren().add(memberLabel);
            }
        }
    }

    /**
     * Hides the project details section.
     */
    private void hideProjectDetails() {
        setContainerVisibility(projectDetailsContainer, false);
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
     * Selects a project by its name in the currently displayed project list.
     * Used when pdetails is called while a person is selected.
     *
     * @param projectName The name of the project to select
     * @return true if project was found and selected, false otherwise
     */
    public boolean selectProjectByName(String projectName) {
        if (projectListView == null || projectListView.getItems() == null) {
            logger.warning("Cannot select project: project list view not initialized");
            return false;
        }

        ObservableList<Project> items = projectListView.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(projectName)) {
                projectListView.getSelectionModel().select(i);
                projectListView.scrollTo(i);
                logger.info("Selected project by name: " + projectName);
                return true;
            }
        }

        logger.warning("Project not found in current list: " + projectName);
        return false;
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

        this.isShowingAllProjects = false;
        this.currentlyDisplayedProjectName = project.getName();
        this.currentlySelectedPerson = null;

        showContentArea();
        hideAllHeaders();
        setListViewVisibility(false);
        showProjectDetails(project);
    }

    /**
     * Shows all projects in the system without selecting a person.
     * Used for pshow all command.
     */
    public void showAllProjects() {
        this.currentlyDisplayedProjectName = null;
        this.currentlySelectedPerson = null;

        showContentArea();
        configureHeaderForAllProjects();

        if (allProjects.isEmpty()) {
            showEmptyProjectList();
        } else {
            setLabelVisibility(projectsStatusLabel, false);
            setListViewVisibility(true);
            projectListView.setItems(allProjects);
            projectListView.getSelectionModel().clearSelection();
            hideProjectDetails();
        }

        this.isShowingAllProjects = true;
    }

    /**
     * Returns whether the panel is currently showing all projects.
     */
    public boolean isShowingAllProjects() {
        return isShowingAllProjects;
    }

    /**
     * Refreshes the displayed content when the projects list changes.
     * Handles two cases:
     * 1. If showing a person's project list, re-filter and update
     * 2. If showing a single project's details, refresh the details
     */
    private void refreshDisplayedProjectIfNeeded() {
        // Case 1: Refresh person's project list
        if (currentlySelectedPerson != null) {
            logger.info("Auto-refreshing project list for person: "
                    + currentlySelectedPerson.getName().fullName);
            showProjectsForPerson(currentlySelectedPerson);
            return;
        }

        // Case 2: Refresh single project details
        if (currentlyDisplayedProjectName != null) {
            // Find the updated project by name
            Project updatedProject = allProjects.stream()
                    .filter(p -> p.getName().equals(currentlyDisplayedProjectName))
                    .findFirst()
                    .orElse(null);

            if (updatedProject == null) {
                // Project was deleted, clear the display
                logger.info("Displayed project no longer exists: " + currentlyDisplayedProjectName);
                hideProjectDetails();
                currentlyDisplayedProjectName = null;
            } else {
                // Project still exists, refresh the details
                logger.info("Auto-refreshing displayed project: " + currentlyDisplayedProjectName);
                showProjectDetails(updatedProject);
            }
        }
    }

    // Helper Methods for visibility management

    /**
     * Shows the content area and hides the placeholder.
     */
    private void showContentArea() {
        setContainerVisibility(placeholderContainer, false);
        setContainerVisibility(contentContainer, true);
    }

    /**
     * Sets visibility and managed state for a container.
     */
    private void setContainerVisibility(VBox container, boolean visible) {
        if (container != null) {
            container.setVisible(visible);
            container.setManaged(visible);
        }
    }

    /**
     * Sets visibility and managed state for a label.
     */
    private void setLabelVisibility(Label label, boolean visible) {
        if (label != null) {
            label.setVisible(visible);
            label.setManaged(visible);
        }
    }

    /**
     * Sets text, visibility and managed state for a label.
     */
    private void setLabelVisibility(Label label, boolean visible, String text) {
        if (label != null) {
            label.setText(text);
            label.setVisible(visible);
            label.setManaged(visible);
        }
    }

    /**
     * Sets visibility for the project list view.
     */
    private void setListViewVisibility(boolean visible) {
        if (projectListView != null) {
            projectListView.setVisible(visible);
            projectListView.setManaged(visible);
        }
    }

    // Helper Methods for list management

    /**
     * Shows empty state when no projects are available.
     */
    private void showEmptyProjectList() {
        setLabelVisibility(projectsStatusLabel, true, NO_PROJECTS_MESSAGE);
        setListViewVisibility(false);
        hideProjectDetails();
    }

    /**
     * Shows the project list with the given projects.
     */
    private void showProjectList(List<Project> projects) {
        setLabelVisibility(projectsStatusLabel, false);
        setListViewVisibility(true);
        projectListView.setItems(FXCollections.observableArrayList(projects));
        projectListView.getSelectionModel().clearSelection();
        hideProjectDetails();
    }

    /**
     * Auto-selects the only project if the list has exactly one project.
     */
    private void autoSelectSingleProject(List<Project> projects) {
        if (projects.size() == 1) {
            projectListView.getSelectionModel().select(0);
        }
    }

    // Helper Methods for filtering

    /**
     * Filters all projects to return only those where the given person is a member.
     */
    private List<Project> getProjectsForPerson(Person person) {
        return allProjects.stream()
                .filter(project -> project != null)
                .filter(project -> project.getMembers() != null)
                .filter(project -> project.getMembers().contains(person))
                .collect(Collectors.toList());
    }

    // Helper Methods for header config

    /**
     * Configures header labels for displaying a person's projects.
     */
    private void configureHeaderForPerson(String personName) {
        setLabelVisibility(personNameLabel, true, PROJECTS_FOR_LABEL + personName);
        setLabelVisibility(projectsSectionLabel, true);
    }

    /**
     * Configures header labels for displaying all projects.
     */
    private void configureHeaderForAllProjects() {
        setLabelVisibility(personNameLabel, true, ALL_PROJECTS_LABEL);
        setLabelVisibility(projectsSectionLabel, true);
    }

    /**
     * Hides all header and section labels (for single project details view).
     */
    private void hideAllHeaders() {
        setLabelVisibility(personNameLabel, false);
        setLabelVisibility(projectsSectionLabel, false);
        setLabelVisibility(projectsStatusLabel, false);
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
