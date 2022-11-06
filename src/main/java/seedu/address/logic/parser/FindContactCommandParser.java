package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CanHelpWithTaskPredicate;
import seedu.address.model.person.ModuleTakenPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindContactCommand object
 */
public class FindContactCommandParser implements Parser<FindContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindContactCommand
     * and returns a FindContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MODULE, PREFIX_TASK);
        Prefix searchPrefix = getSearchPrefix(argMultimap);

        if (searchPrefix.equals(PREFIX_NAME)) {

            List<String> strings = argMultimap.getAllValues(PREFIX_NAME);
            List<String> keywordsSpaceSeparated = new ArrayList<>();
            for (String string : strings) {
                for (String keyword : string.split("\\s+")) {
                    keywordsSpaceSeparated.add(keyword);
                }
            }
            // ["name", "name name"] -> ["name", "name", "name"]
            return new FindContactCommand(new NameContainsKeywordsPredicate(keywordsSpaceSeparated));

        } else if (searchPrefix.equals(PREFIX_MODULE)) {

            List<String> strings = argMultimap.getAllValues(PREFIX_MODULE);
            List<String> keywordsSpaceSeparated = new ArrayList<>();
            for (String string : strings) {
                for (String keyword : string.split("\\s+")) {
                    keywordsSpaceSeparated.add(keyword);
                }
            }
            // ["mod1", "mod2 mod3"] -> ["mod1", "mod2", "mod3"]
            return new FindContactCommand(new ModuleTakenPredicate(keywordsSpaceSeparated));

        } else if (searchPrefix.equals(PREFIX_TASK)) {

            try {
                Index taskIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK).get());
                return new FindContactCommand(new CanHelpWithTaskPredicate(taskIndex));
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContactCommand.MESSAGE_USAGE));
            }

        } else {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContactCommand.MESSAGE_USAGE));

        }
    }

    private static Prefix getSearchPrefix(ArgumentMultimap argumentMultimap) throws ParseException {
        List<Prefix> searchablePrefixes = new ArrayList<>();
        searchablePrefixes.add(PREFIX_NAME);
        searchablePrefixes.add(PREFIX_MODULE);
        searchablePrefixes.add(PREFIX_TASK);

        List<Prefix> prefixesInArgs = new ArrayList<>();

        for (Prefix prefix : searchablePrefixes) {
            if (!argumentMultimap.getAllValues(prefix).isEmpty()) {
                prefixesInArgs.add(prefix);
            }
        }

        // if number of prefixes in arguments is not 1, the arguments are invalid
        if (prefixesInArgs.size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContactCommand.MESSAGE_USAGE));
        }

        return prefixesInArgs.get(0);
    }

}
