import com.github.arkinator.splora.importer.SploraImporter;
import com.github.arkinator.splora.model.SploraProject;
import org.junit.Test;

public class PlantUmlExportTest {
    private SploraProject project;

    @Test
    public void shouldReadInPackages() {
        project = SploraImporter.importFromDirectory("../index-service");
        project.deleteNonSpringMembers();
        project.deleteClass("IndexManagerController");
//        project.deletePackage("tracking");
        System.out.println(project.exportToPlantUml());
    }

    @Test
    public void testPd() {
        project = SploraImporter.importFromDirectory("../identity-service");//portal-delivery-service
        project.deleteNonSpringMembers();
//        project.deleteClass("IndexManagerController");
//        project.deletePackage("tracking");
        System.out.println(project.exportToPlantUml());
    }
}
