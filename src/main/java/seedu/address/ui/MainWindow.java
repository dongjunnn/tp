package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ProjectListPanel projectListPanel;
    private DueSoonWindow dueSoonWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane projectPanelPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     * Creates and initializes all UI components.
     * Person selection triggers project display in the right panel.
     */
    void fillInnerParts() {
        // Create ProjectListPanel first
        projectListPanel = new ProjectListPanel(
                logic.getFilteredProjectList(),
                logic.getFilteredPersonList());
        projectPanelPlaceholder.getChildren().add(projectListPanel.getRoot());

        // Create PersonListPanel with selection callback
        personListPanel = new PersonListPanel(
                logic.getFilteredPersonList(),
                projectListPanel::showProjectsForPerson
        );
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        logic.getFilteredProjectList().addListener((javafx.collections.ListChangeListener<Project>) change -> {
            if (dueSoonWindow != null && dueSoonWindow.isShowing()) {
                List<Project> allProjects = logic.getFilteredProjectList()
                        .stream()
                        .collect(Collectors.toList());
                List<Project> dueSoonProjects = DueSoonWindow.findProjectsDueSoon(allProjects);
                dueSoonWindow.updateProjects(dueSoonProjects);
            }
        });

    }

    /**
     * Checks all projects and, if any are due within the next 7 days,
     * shows a popup listing them.
     */
    public void showDueSoonPopupIfNeeded(boolean firstShow) {
        List<Project> allProjects = logic.getFilteredProjectList()
                .stream()
                .collect(Collectors.toList());

        List<Project> dueSoonProjects = DueSoonWindow.findProjectsDueSoon(allProjects);

        if (dueSoonProjects.isEmpty() && firstShow) {
            return;
        }

        if (dueSoonWindow == null) {
            dueSoonWindow = new DueSoonWindow(dueSoonProjects);
            dueSoonWindow.show();
        } else {
            dueSoonWindow.updateProjects(dueSoonProjects); // refresh content
            if (!dueSoonWindow.isShowing()) {
                dueSoonWindow.show();
            }
            dueSoonWindow.focus();
        }

    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML

    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the deadline window.
     */

    @FXML
    public void handleDeadline() {
        showDueSoonPopupIfNeeded(false);
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();

        if (dueSoonWindow != null && dueSoonWindow.isShowing()) {
            dueSoonWindow.close();
        }

        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowDeadline()) {
                handleDeadline();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            // Handle person selection for pshow command
            if (commandResult.hasPersonIndexToSelect()) {
                personListPanel.selectPerson(commandResult.getPersonIndexToSelect());
            }

            // Handle project details display for pdetails command
            if (commandResult.hasProjectToShow()) {
                handleProjectDetailsDisplay(commandResult.getProjectToShow());
            }

            // Handle show all projects for pshow all command
            if (commandResult.isShowAllProjects()) {
                personListPanel.clearSelection();
                projectListPanel.showAllProjects();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles project details display for pdetails command.
     * Shows project in context if user is viewing projects, otherwise shows standalone.
     */
    private void handleProjectDetailsDisplay(Project project) {
        if (shouldShowProjectInCurrentContext(project)) {
            selectProjectInCurrentList(project);
        } else {
            showStandaloneProjectDetails(project);
        }
    }

    /**
     * Determines if the project should be shown in the current context
     * (either in "all projects" view or in selected person's project list).
     */
    private boolean shouldShowProjectInCurrentContext(Project project) {
        return projectListPanel.isShowingAllProjects()
                || currentPersonHasProject(project);
    }

    /**
     * Checks if the currently selected person is a member of the given project.
     */
    private boolean currentPersonHasProject(Project project) {
        Person selectedPerson = personListPanel.getSelectedPerson();
        return selectedPerson != null && project.getMembers().contains(selectedPerson);
    }

    /**
     * Selects the project in the current list view.
     * Falls back to standalone view if project is not found in list.
     */
    private void selectProjectInCurrentList(Project project) {
        boolean found = projectListPanel.selectProjectByName(project.getName());
        if (!found) {
            logger.warning("Project not found in current list, showing standalone: "
                    + project.getName());
            showStandaloneProjectDetails(project);
        }
    }

    /**
     * Shows a single project without any person/list context.
     */
    private void showStandaloneProjectDetails(Project project) {
        personListPanel.clearSelection();
        projectListPanel.showSingleProjectDetails(project);
    }
}
