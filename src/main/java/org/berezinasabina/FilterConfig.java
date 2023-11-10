package org.berezinasabina;

public class FilterConfig {
    private String inputFilePath;

    private String outputFilePath;

    private Integer topRecords;

    private Integer lastRecords;

    private Boolean malesOnly;

    private Boolean femalesOnly;

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public Integer getTopRecords() {
        return topRecords;
    }

    public void setTopRecords(Integer topRecords) {
        this.topRecords = topRecords;
    }

    public Integer getLastRecords() {
        return lastRecords;
    }

    public void setLastRecords(Integer lastRecords) {
        this.lastRecords = lastRecords;
    }

    public Boolean getMalesOnly() {
        return malesOnly;
    }

    public void setMalesOnly(Boolean malesOnly) {
        this.malesOnly = malesOnly;
    }

    public Boolean getFemalesOnly() {
        return femalesOnly;
    }

    public void setFemalesOnly(Boolean femalesOnly) {
        this.femalesOnly = femalesOnly;
    }

    @Override
    public String toString() {
        return "FilterConfig{" +
                "inputFilePath='" + inputFilePath + '\'' +
                ", outputFilePath='" + outputFilePath + '\'' +
                ", topRecords=" + topRecords +
                ", lastRecords=" + lastRecords +
                ", malesOnly=" + malesOnly +
                ", femalesOnly=" + femalesOnly +
                '}';
    }
}

