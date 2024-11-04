package org.example;

import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;

public class Main {
    public static void main(String[] args) {

        Model model = ModelFactory.createDefaultModel();

        String finkiURI = "http://finki.ukim.mk/resource#";
        String myURI = "https://www.instagram.com/olgicaupcheva/";

        Resource myProfile = model.createResource(myURI)
                .addProperty(RDFS.label, "Olgica")
                .addProperty(RDFS.comment, "This is my profile")
                .addProperty(VCARD.EMAIL, "olgica.upcheva@students.finki.ukim.mk")
                .addProperty(VCARD.TEL, "078-510-520")
                .addProperty(FOAF.birthday, "2002-02-21")
                .addProperty(VCARD.FN, "Olgica Upcheva")
                .addProperty(FOAF.based_near, "Skopje,Macedonia")
                .addProperty(FOAF.gender, "Female")
                .addProperty(FOAF.interest, "Semantic Web");

        Resource friendProfile = model.createResource("https://www.instagram.com/vicentijevic_i/")
                .addProperty(RDFS.label, "Friend")
                .addProperty(FOAF.knows, myProfile)
                .addProperty(VCARD.TEL, "071-750-250");

        System.out.println("RDF Graph in Turtle Format:");
        model.write(System.out, "TURTLE");

        System.out.println("\nPrinting with model.listStatements():");
        StmtIterator iterator = model.listStatements();
        while (iterator.hasNext()) {
            Statement stmt = iterator.nextStatement();  // Земете ја следната тројка
            Resource subject = stmt.getSubject();       // Субјект
            Property predicate = stmt.getPredicate();   // Предикат
            RDFNode object = stmt.getObject();          // Објект

            // Печатење на форматиран начин
            String formattedObject = object.isLiteral() ? "\"" + object.asLiteral().getString() + "\"" : object.toString();
            System.out.println(subject + " - " + predicate + " - " + formattedObject);
        }
        System.out.println("\nPrinting with model.write(), in RDF/XML:");
        model.write(System.out, "RDF/XML");

        System.out.println("\nPrinting with model.write(), in Pretty RDF/XML:");
        model.write(System.out, "RDF/XML-ABBREV");

        System.out.println("\nPrinting with model.write(), in N-Triples:");
        model.write(System.out, "N-TRIPLES");
    }
}
