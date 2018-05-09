package surveyape.models;

import java.util.Set;

public class Options {
    private Set<Option> options;

    public Options(Set<Option> options) {
        this.options = options;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }
}
