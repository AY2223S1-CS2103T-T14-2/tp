package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * Unmarks a task as not done.
 */
public class UnmarkTaskCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the task identified by the index number used in the displayed task list as not complete.\n"
            + "Parameters: {task_index} (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Task unmarked as not complete: %1$s";
    public static final String MESSAGE_TASK_ALREADY_NOT_COMPLETED = "This task is already unmarked as not complete.";

    private final Index targetIndex;

    public UnmarkTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getSortedTaskList();

        assert(targetIndex.getZeroBased() >= 0);
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUnmark = lastShownList.get(targetIndex.getZeroBased());

        if (!taskToUnmark.isTaskComplete()) {
            throw new CommandException(MESSAGE_TASK_ALREADY_NOT_COMPLETED);
        }

        Task unmarkedTask = createUnmarkedTask(taskToUnmark);
        model.setTask(taskToUnmark, unmarkedTask);
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, unmarkedTask), true);
    }

    /**
     * Creates and returns a {@code Task} with the status of {@code taskToUnmark} set to incomplete.
     */
    private static Task createUnmarkedTask(Task taskToUnmark) {
        assert taskToUnmark != null;

        TaskName name = taskToUnmark.getName();
        Module module = taskToUnmark.getModule();
        Deadline deadline = taskToUnmark.getDeadline();
        Status updatedStatus = new Status(false);

        return new Task(name, module, deadline, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnmarkTaskCommand // instanceof handles nulls
                && targetIndex.equals(((UnmarkTaskCommand) other).targetIndex)); // state check
    }
}
