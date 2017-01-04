import com.github.arkinator.splora.importer.SploraImporter;
import com.github.arkinator.splora.model.SploraProject;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SpringImporterTest {
    private SploraProject project;

    @Before
    public void setUp() {
        project = SploraImporter.importFromDirectory("../portal-delivery-service");
    }

    @Test
    public void shouldFindWelcomeControllerAsBean() {
        assertThat(project.getPackage("de.zdf.service.portaldelivery.controller").getClasses())
                .filteredOn("className", "WelcomeController")
                .hasSize(1);
    }

    @Test
    public void shouldFindDependenciesOfWelcomeController() {
        assertThat(project.getClass("WelcomeController").getDependencies())
                .containsExactly("PdRequestExecutorService");
    }
}
