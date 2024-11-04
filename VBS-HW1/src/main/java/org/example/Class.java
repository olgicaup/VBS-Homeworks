package org.example;

import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.VCARD;

import java.io.FileInputStream;
import java.io.IOException;

public class Class {
    public static void main(String[] args) {

        Model model = ModelFactory.createDefaultModel();
        String filePath = "C:\\Users\\olgic\\Downloads\\VBS-HW1\\rdfRDF.xml";
        String myURI = "https://www.instagram.com/olgicaupcheva/";

        try {
            model.read(new FileInputStream(filePath), null, "RDF/XML");
            //model.write(System.out, "TURTLE");
            Resource myProfile = model.getResource(myURI);

            if (myProfile != null) {
                System.out.println("Resource found: " + myProfile.getURI());

                StmtIterator iterator = myProfile.listProperties();
                while (iterator.hasNext()) {
                    Statement stmt = iterator.nextStatement();
                    Property predicate = stmt.getPredicate();
                    RDFNode object = stmt.getObject();

                    String formattedObject = object.isLiteral() ? "\"" + object.asLiteral().getString() + "\"" : object.toString();
                    System.out.println(predicate + " - " + formattedObject);
                }
                Statement fullNameStmt = myProfile.getProperty(VCARD.FN);
                if (fullNameStmt != null) {
                    String fullName = fullNameStmt.getObject().asLiteral().getString();
                    System.out.println("Full Name: " + fullName);
                } else {
                    System.out.println("Full Name not found.");
                }

                Statement lastNameStmt = myProfile.getProperty(FOAF.lastName);
                if (lastNameStmt != null) {
                    String lastName = lastNameStmt.getObject().asLiteral().getString();
                    System.out.println("Last Name: " + lastName);
                } else {
                    System.out.println("Last Name not found.");
                }

                Statement emailStmt = myProfile.getProperty(VCARD.EMAIL);
                if (emailStmt != null) {
                    String email = emailStmt.getObject().asLiteral().getString();
                    System.out.println("Email: " + email);
                } else {
                    System.out.println("Email not found.");
                }
            }
            else {
                System.out.println("Resource not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the RDF file.");
        }
    }
}
