package seedu.codeConnect.logic.commands;

import static seedu.codeConnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.codeConnect.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.codeConnect.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.codeConnect.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.codeConnect.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.codeConnect.model.Model;
import seedu.codeConnect.model.ModelManager;
import seedu.codeConnect.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListContactCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), getTypicalTaskList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST);
        Assertions.assertTrue(model.getAddressBook().equals(expectedModel.getAddressBook()));
        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
