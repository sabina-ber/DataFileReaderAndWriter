package org.berezinasabina;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
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
    }

    public FilterConfig parse() {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        FilterConfig filterConfig = new FilterConfig();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (!cmd.hasOption("in")) {
                throw new ParseException("The input file path (--in) is required.");
            }
            if (!cmd.hasOption("out")) {
                throw new ParseException("The output file path (--out) is required.");
            }

            filterConfig.setInputFilePath(cmd.getOptionValue("in"));
            filterConfig.setOutputFilePath(cmd.getOptionValue("out"));
            filterConfig.setTopRecords(parseIntegerOption(cmd, "top"));
            filterConfig.setLastRecords(parseIntegerOption(cmd, "last"));
            filterConfig.setMalesOnly(parseBooleanOption(cmd, "males-only"));
            filterConfig.setFemalesOnly(parseBooleanOption(cmd, "females-only"));

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
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
            return Boolean.valueOf(cmd.getOptionValue(option));
        }
        return null;
    }
}

