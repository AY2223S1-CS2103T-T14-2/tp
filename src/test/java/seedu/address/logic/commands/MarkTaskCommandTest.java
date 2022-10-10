package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
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
 * {@code MarkTaskCommand}.
 */
public class MarkTaskCommandTest {

    //should be getTypicalTaskList()
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToMark = model.getFilteredTaskList().get(0);
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(MarkTaskCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getTaskList(), new UserPrefs());
        Task markedTask = new Task(
                taskToMark.getName(), taskToMark.getModule(), taskToMark.getDeadline()).setStatus(true);
        expectedModel.setTask(taskToMark, markedTask);

        assertCommandSuccess(markTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(outOfBoundIndex);

        assertCommandFailure(markTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, Index.fromZeroBased(0));

        Task taskToMark = model.getFilteredTaskList().get(0);
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(MarkTaskCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getTaskList(), new UserPrefs());
        Task markedTask = new Task(
                taskToMark.getName(), taskToMark.getModule(), taskToMark.getDeadline()).setStatus(true);
        expectedModel.setTask(taskToMark, markedTask);

        assertCommandSuccess(markTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, Index.fromZeroBased(0));

        Index outOfBoundIndex = Index.fromZeroBased(1);

        MarkTaskCommand markTaskCommand = new MarkTaskCommand(outOfBoundIndex);

        assertCommandFailure(markTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void excecute_markUnmarkedTask_success() {
        Task taskToMark = model.getFilteredTaskList().get(0).setStatus(false);
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(MarkTaskCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getTaskList(), new UserPrefs());
        Task markedTask = new Task(
                taskToMark.getName(), taskToMark.getModule(), taskToMark.getDeadline()).setStatus(true);
        expectedModel.setTask(taskToMark, markedTask);

        assertCommandSuccess(markTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markMarkedTask_throwsCommandException() {
        model.getFilteredTaskList().get(0).setStatus(true);
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(Index.fromZeroBased(0));

        assertCommandFailure(markTaskCommand, model, MarkTaskCommand.MESSAGE_ALREADY_COMPLETED);
    }

    @Test
    public void equals() {
        MarkTaskCommand markTaskFirstCommand = new MarkTaskCommand(Index.fromZeroBased(0));
        MarkTaskCommand markTaskSecondCommand = new MarkTaskCommand(Index.fromZeroBased(1));

        // same object -> returns true
        assertTrue(markTaskFirstCommand.equals(markTaskFirstCommand));

        // same values -> returns true
        MarkTaskCommand markTaskFirstCommandCopy = new MarkTaskCommand(Index.fromZeroBased(0));
        assertTrue(markTaskFirstCommand.equals(markTaskFirstCommandCopy));

        // different types -> returns false
        assertFalse(markTaskFirstCommand.equals(0));

        // null -> returns false
        assertFalse(markTaskFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(markTaskFirstCommand.equals(markTaskSecondCommand));
    }

}
