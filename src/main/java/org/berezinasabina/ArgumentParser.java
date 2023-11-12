package org.berezinasabina;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {
    private final String[] args;

    private final Options options = new Options();

    public ArgumentParser(String[] args) {
        this.args = args;
        options.addOption(Option.builder().longOpt("in").hasArg().argName("path").required(true).desc("Input file path").build());
        options.addOption(Option.builder().longOpt("out").hasArg().argName("path").required(true).desc("Output file path").build());
        options.addOption(Option.builder().longOpt("top").hasArg().argName("number").desc("Select top n records").build());
        options.addOption(Option.builder().longOpt("last").hasArg().argName("number").desc("Select last n records").build());
        options.addOption(Option.builder().longOpt("males-only").hasArg().argName("true | false").desc("Filter only male records").build());
        options.addOption(Option.builder().longOpt("females-only").hasArg().argName("true | false").desc("Filter only female records").build());
        options.addOption(Option.builder().longOpt("name").hasArg().argName("name").desc("Filter by first name").build());
        options.addOption(Option.builder().longOpt("surname").hasArg().argName("surname").desc("Filter by last name").build());
    }

    public FilterConfig parse() throws ParseException {
        CommandLineParser parser = new DefaultParser();
        FilterConfig filterConfig = new FilterConfig();

        CommandLine cmd = parser.parse(options, args);

        if (!cmd.hasOption("in")) {
            throw new ParseException("The input file path (--in) is required.");
        }
        if (!cmd.hasOption("out")) {
            throw new ParseException("The output file path (--out) is required.");
        }

        validateFileExtension(cmd.getOptionValue("in"));
        validateFileExtension(cmd.getOptionValue("out"));

        filterConfig.setInputFilePath(cmd.getOptionValue("in"));
        filterConfig.setOutputFilePath(cmd.getOptionValue("out"));
        filterConfig.setTopRecords(parseIntegerOption(cmd, "top"));
        filterConfig.setLastRecords(parseIntegerOption(cmd, "last"));
        filterConfig.setMalesOnly(parseBooleanOption(cmd, "males-only"));
        filterConfig.setFemalesOnly(parseBooleanOption(cmd, "females-only"));
        filterConfig.setNameFilter(cmd.getOptionValue("name"));
        filterConfig.setLastNameFilter(cmd.getOptionValue("surname"));

        if (filterConfig.getTopRecords() != null && filterConfig.getLastRecords() != null) {
            throw new ParseException("Cannot use --top and --last together. Please specify only one.");
        }
        if (filterConfig.getMalesOnly() != null && filterConfig.getFemalesOnly() != null
                && filterConfig.getMalesOnly() && filterConfig.getFemalesOnly()) {
            throw new ParseException("Cannot use --males-only and --females-only together. Please specify only one.");
        }

        return filterConfig;
    }

    private Integer parseIntegerOption(CommandLine cmd, String option) {
        if (cmd.hasOption(option)) {
            try {
                return Integer.valueOf(cmd.getOptionValue(option));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid format for option " + option, e);
            }
        }
        return null;
    }

    private Boolean parseBooleanOption(CommandLine cmd, String option) {
        if (cmd.hasOption(option)) {
            String value = cmd.getOptionValue(option);
            if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {
                throw new IllegalArgumentException("Invalid boolean value for option " + option);
            }
            return Boolean.valueOf(value);
        }
        return null;
    }

    public Options getOptions() {
        return options;
    }

    private void validateFileExtension(String filePath) throws ParseException {
        if (!(filePath.endsWith(".csv") || filePath.endsWith(".json") || filePath.endsWith(".xml"))) {
            throw new ParseException("Unsupported file format: " + filePath);
        }
    }
}

