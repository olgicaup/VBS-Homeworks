package org.example;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.apache.jena.vocabulary.AS.object;
import static org.apache.jena.vocabulary.RDF.Nodes.predicate;

public class NewClass {
    public static void main(String[] args) {

        Model model = ModelFactory.createDefaultModel();

        String filePath = "C:\\Users\\olgic\\Downloads\\VBS-HW1\\hifm-dataset.ttl";

        try {
            model.read(new FileInputStream(filePath), null, "TURTLE");

            String medicineURI = "http://purl.org/net/hifm/data#82481";
            //Resource medicine = model.getResource(medicineURI);
            Resource drugType = model.getResource("http://purl.org/net/hifm/ontology#Drug");
            ResIterator resIterator = model.listResourcesWithProperty(RDF.type, drugType);

            if (!resIterator.hasNext()) {
                System.out.println("No resources of type 'Drug' found. Please check the URI or dataset.");
                return;
            }

            Resource selectedMedicine = resIterator.nextResource();
            String selectedMedicineName = getLabel(selectedMedicine);
            String selectedMedicinePrice = getPrice(selectedMedicine);

            System.out.println("Selected Medicine: " + selectedMedicineName);
            System.out.println("Price: " + selectedMedicinePrice);

            Property similarTo = model.getProperty("http://purl.org/net/hifm/ontology#similarTo");
            StmtIterator stmtIterator = selectedMedicine.listProperties(similarTo);

            Set<String> similarMedicines = new HashSet<>();

            while (stmtIterator.hasNext()) {
                Statement stmt = stmtIterator.nextStatement();
                Resource similarMedicine = stmt.getObject().asResource();
                String name = getLabel(similarMedicine);
                String price = getPrice(similarMedicine);
                similarMedicines.add(name + " | Price: " + price);
            }

            System.out.println("Similar Medicines:");
            if (similarMedicines.isEmpty()) {
                System.out.println("No similar medicines found.");
            } else {
                for (String medicine : similarMedicines) {
                    System.out.println(" - " + medicine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the Turtle file.");
        }

    }
    private static String getLabel(Resource resource) {
        Statement labelStmt = resource.getProperty(RDFS.label);
        return (labelStmt != null) ? labelStmt.getString() : "No name available";
    }

    private static String getPrice(Resource resource) {
        Property priceProperty = resource.getModel().getProperty("http://purl.org/net/hifm/ontology#refPriceWithVAT");
        Statement priceStmt = resource.getProperty(priceProperty);
        return (priceStmt != null) ? priceStmt.getString() : "No price available";
    }
}
