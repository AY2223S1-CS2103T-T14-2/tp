package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Marks a task as done.
 */
public class UnmarkTaskCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarks the task identified by the index number used in the displayed person list as not complete.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Task unmarked as not complete: %1$s";
    public static final String MESSAGE_ALREADY_NOT_COMPLETED = "This task is already marked as not complete.";

    private final Index targetIndex;

    public UnmarkTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUnmark = lastShownList.get(targetIndex.getZeroBased());

        if (!taskToUnmark.getStatus().getIsComplete()) {
            throw new CommandException(MESSAGE_ALREADY_NOT_COMPLETED);
        }

        Task unmarkedTask = new Task(taskToUnmark.getName(), taskToUnmark.getModule(), taskToUnmark.getDeadline());
        unmarkedTask.setStatus(false);
        model.setTask(taskToUnmark, unmarkedTask);
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, unmarkedTask));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnmarkTaskCommand // instanceof handles nulls
                && targetIndex.equals(((UnmarkTaskCommand) other).targetIndex)); // state check
    }
}
