package name.koflerdavid.timetracking;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(glue = {"name.koflerdavid.timetracking"}, tags = "@timetracking")
public class CucumberJunitSuite {
}
