package fr.bankapp.architecture;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@Tag ("architecture")
public class ArchitectureCibleTest {
    static  final JavaClasses CLASSES = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("fr.bankapp");

    @Test
    void regle1_lesConsommateursNeConnaissentPasRisque() {
        noClasses()
            .that().resideInAnyPackage(
                "fr.bankapp.prets..",
                "fr.bankapp.crm..",
                "fr.bankapp.mobile.."
            )
            .should().dependOnClassesThat()
            .resideInAPackage("fr.bankapp.risque..")
            .check(CLASSES);
    }

    @Test
    void regle2_seulRisqueLitSesTables() {
        List<String> violations = SourcesJava.occurrences(
            "RISK_DB.",
            "fr/bankapp/risque/"
        );

        assertEquals(List.of(),violations, "Accès aux tables de Risque hors de l'application Risque"); 
    }
    @Test
    void mesure_cyclesEntreApplications() {
        int cycles = slices().matching("fr.bankapp.(*)..")
            .should().beFreeOfCycles()
            .evaluate(CLASSES)
            .getFailureReport()
            .getDetails()
            .size();

        System.out.println(">>> Cycles entre applications : " + cycles);
    }
}
