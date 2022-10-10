package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.task.Task;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnmarkTaskCommand}.
 */
public class UnmarkTaskCommandTest {

    //should be getTypicalTaskList()
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToUnmark = model.getFilteredTaskList().get(0);
        taskToUnmark.setStatus(true);
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(UnmarkTaskCommand.MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getTaskList(), new UserPrefs());
        Task unmarkedTask = new Task(taskToUnmark.getName(), taskToUnmark.getModule(), taskToUnmark.getDeadline());
        unmarkedTask.setStatus(false);
        expectedModel.setTask(taskToUnmark, unmarkedTask);

        assertCommandSuccess(unmarkTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(outOfBoundIndex);

        assertCommandFailure(unmarkTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void excecute_unmarMarkedTask_success() {
        Task taskToMark = model.getFilteredTaskList().get(0);
        taskToMark.setStatus(true);
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(UnmarkTaskCommand.MESSAGE_UNMARK_TASK_SUCCESS, taskToMark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getTaskList(), new UserPrefs());
        Task unmarkedTask = new Task(taskToMark.getName(), taskToMark.getModule(), taskToMark.getDeadline());
        unmarkedTask.setStatus(false);
        expectedModel.setTask(taskToMark, unmarkedTask);

        assertCommandSuccess(unmarkTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unmarkUnmarkedTask_throwsCommandException() {
        model.getFilteredTaskList().get(0).setStatus(false);
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(Index.fromZeroBased(0));

        assertCommandFailure(unmarkTaskCommand, model, UnmarkTaskCommand.MESSAGE_ALREADY_NOT_COMPLETED);
    }

    @Test
    public void equals() {
        UnmarkTaskCommand unmarkTaskFirstCommand = new UnmarkTaskCommand(Index.fromZeroBased(0));
        UnmarkTaskCommand unmarkTaskSecondCommand = new UnmarkTaskCommand(Index.fromZeroBased(1));

        // same object -> returns true
        assertTrue(unmarkTaskFirstCommand.equals(unmarkTaskFirstCommand));

        // same values -> returns true
        UnmarkTaskCommand unmarkTaskFirstCommandCopy = new UnmarkTaskCommand(Index.fromZeroBased(0));
        assertTrue(unmarkTaskFirstCommand.equals(unmarkTaskFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkTaskFirstCommand.equals(0));

        // null -> returns false
        assertFalse(unmarkTaskFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(unmarkTaskFirstCommand.equals(unmarkTaskSecondCommand));
    }

}
