package com.gorlah.kappabot.command;

import com.gorlah.kappabot.function.BotRequestMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandPayloadBuilder {

    private final Pattern REGEX = Pattern.compile("\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\"|\\S+");

    public CommandPayload parseMessageAndBuild(BotRequestMetadata metadata, String commandPrefix) {
        return new CommandPayload(parseMessage(metadata.getMessage(), commandPrefix), metadata);
    }

    private List<String> parseMessage(String message, String commandPrefix) {
        ArrayList<String> messageList = new ArrayList<>();
        Matcher regexMatcher = REGEX.matcher(stripCommandPrefix(message, commandPrefix));

        while (regexMatcher.find()) {
            messageList.add(sanitizeMessageToAdd(regexMatcher.group()));
        }

        return messageList;
    }

    private String stripCommandPrefix(String message, String commandPrefix) {
        if (message.length() == commandPrefix.length()) {
            return "";
        }

        return message.substring(commandPrefix.length() + 1);
    }

    private String sanitizeMessageToAdd(String message) {
        message = removeLeadingQuoteIfNecessary(message);
        message = removeTrailingQuoteIfNecessary(message);
        message = replaceEscapedQuoteWithRegularQuote(message);

        return message;
    }

    private String removeLeadingQuoteIfNecessary(String message) {
        return message.startsWith("\"") ? message.substring(1) : message;
    }

    private String removeTrailingQuoteIfNecessary(String message) {
        return message.endsWith("\"") ? message.substring(0, message.length() - 1) : message;
    }

    private String replaceEscapedQuoteWithRegularQuote(String message) {
        return message.replaceAll("\\\\\"", "\"");
    }
}
