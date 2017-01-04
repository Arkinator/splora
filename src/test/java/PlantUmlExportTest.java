import com.github.arkinator.splora.importer.SploraImporter;
import com.github.arkinator.splora.model.SploraProject;
import org.junit.Before;
import org.junit.Test;

public class PlantUmlExportTest {
    private SploraProject project;

    @Before
    public void setUp() {
        project = SploraImporter.importFromDirectory("../index-service");
    }

    @Test
    public void shouldReadInPackages() {
        System.out.println(project.exportToPlantUml());
    }
}
