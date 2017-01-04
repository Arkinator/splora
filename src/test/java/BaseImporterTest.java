import com.github.arkinator.splora.importer.SploraImporter;
import com.github.arkinator.splora.model.SploraClass;
import com.github.arkinator.splora.model.SploraPackage;
import com.github.arkinator.splora.model.SploraProject;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class BaseImporterTest {
    private SploraProject project;

    @Before
    public void setUp() {
        project = SploraImporter.importFromDirectory(".");
    }

    @Test
    public void shouldGiveNonNullResult() {
        assertThat(project).isNotNull();
    }

    @Test
    public void shouldReadInClasses() {
        assertThat(project.getClassList())
                .isNotEmpty()
                .hasAtLeastOneElementOfType(SploraClass.class)
                .filteredOn("className","SploraImporter")
                .hasSize(1);
    }

    @Test
    public void shouldReadInPackages() {
        assertThat(project.getPackages())
                .isNotEmpty()
                .hasAtLeastOneElementOfType(SploraPackage.class)
                .filteredOn("packageName","com.github.arkinator.splora.model")
                .hasSize(1);
        assertThat(project.getPackage("com.github.arkinator.splora.model").getClasses())
                .filteredOn("className","SploraPackage")
                .hasSize(1);
    }
}
