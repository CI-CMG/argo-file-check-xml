package edu.colorado.cires.argonaut.xml.filecheck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import org.junit.jupiter.api.Test;

public class ArgoFileTest {

  @Test
  public void testSimpleRead() throws Exception {
    FileCheckResults checkResults;
    try (Reader reader = Files.newBufferedReader(Paths.get("src/test/resources/file1.xml"), StandardCharsets.UTF_8)) {
      checkResults = (FileCheckResults) JAXBContext.newInstance(FileCheckResults.class).createUnmarshaller().unmarshal(reader);
    }
    assertEquals("-r1259", checkResults.getSpecVersion());
    assertEquals("v2.8.14", checkResults.getFilecheckerVersion());
    assertEquals("1901830_meta.nc", checkResults.getFile());
    assertEquals("FILE-ACCEPTED", checkResults.getStatus());
    assertEquals("FILE-NAME-CHECK", checkResults.getPhase());
    assertEquals("aoml", checkResults.getMetadata().getDac());
    assertEquals("Argo meta-data", checkResults.getMetadata().getDATATYPE());
    assertEquals("3.1", checkResults.getMetadata().getFORMATVERSION());
    assertEquals("20250402201200", checkResults.getMetadata().getDATEUPDATE());
    assertEquals("AO", checkResults.getMetadata().getDATACENTRE());
    assertEquals("1901830", checkResults.getMetadata().getPLATFORMNUMBER());
    assertEquals("BRECK OWENS, STEVEN JAYNE, P.E. ROBBINS", checkResults.getMetadata().getPINAME());
    assertEquals("854", checkResults.getMetadata().getWMOINSTTYPE());
    assertEquals("D", checkResults.getMetadata().getDATAMODE());
    assertEquals("A", checkResults.getMetadata().getDIRECTION());
    assertEquals(2, checkResults.getMetadata().getNPROF());
    assertEquals(23, checkResults.getMetadata().getNLEVELS());
    assertEquals("1", checkResults.getMetadata().getCYCLENUMBER());
    assertEquals("23.88", checkResults.getMetadata().getLATITUDE());
    assertEquals("10.45", checkResults.getMetadata().getLONGITUDE());
    assertEquals("2", checkResults.getMetadata().getJULDQC());
    assertEquals("3", checkResults.getMetadata().getPOSITIONQC());
    assertEquals("4", checkResults.getMetadata().getPROFILETEMPQC());
    assertEquals("5", checkResults.getMetadata().getPROFILEPSALQC());
    assertEquals("8", checkResults.getMetadata().getPROFILEDOXYQC());
    assertEquals("DOXY,TEMP", checkResults.getMetadata().getSTATIONPARAMETERS());
    assertEquals("R", checkResults.getMetadata().getPARAMETERDATAMODE());
    assertEquals(2, checkResults.getErrors().getNumber());
    assertEquals(2, checkResults.getWarnings().getNumber());
    assertEquals(Arrays.asList("error1","error2"), checkResults.getErrors().getErrors());
    assertEquals(Arrays.asList("warn1","warn2"), checkResults.getWarnings().getWarnings());
  }

  @Test
  public void testWrite() throws Exception {

    FileCheckResults checkResults = new FileCheckResults();
    checkResults.setSpecVersion("-r1259");
    checkResults.setFilecheckerVersion("v2.8.14");
    checkResults.setFile("1901830_meta.nc");
    checkResults.setStatus("FILE-ACCEPTED");
    checkResults.setPhase("FILE-NAME-CHECK");

    Metadata metadata = new Metadata();
    checkResults.setMetadata(metadata);
    metadata.setDac("aoml");
    metadata.setDATATYPE("Argo meta-data");
    metadata.setFORMATVERSION("3.1");
    metadata.setDATEUPDATE("20250402201200");
    metadata.setDATACENTRE("AO");
    metadata.setPLATFORMNUMBER("1901830");
    metadata.setPINAME("BRECK OWENS, STEVEN JAYNE, P.E. ROBBINS");
    metadata.setWMOINSTTYPE("854");
    metadata.setDATAMODE("D");
    metadata.setDIRECTION("A");
    metadata.setNPROF(2);
    metadata.setNLEVELS(23);
    metadata.setCYCLENUMBER("1");
    metadata.setLATITUDE("23.88");
    metadata.setLONGITUDE("10.45");
    metadata.setJULDQC("2");
    metadata.setPOSITIONQC("3");
    metadata.setPROFILETEMPQC("4");
    metadata.setPROFILEPSALQC("5");
    metadata.setPROFILEDOXYQC("8");
    metadata.setSTATIONPARAMETERS("DOXY,TEMP");
    metadata.setPARAMETERDATAMODE("R");

    Errors errors = new Errors();
    checkResults.setErrors(errors);
    errors.setNumber(2);
    errors.getErrors().add("error1");
    errors.getErrors().add("error2");

    Warnings warnings = new Warnings();
    checkResults.setWarnings(warnings);
    warnings.setNumber(2);
    warnings.getWarnings().add("warn1");
    warnings.getWarnings().add("warn2");

    Path outputFile = Paths.get("target/file2.xml");
    Files.createDirectories(outputFile.getParent());
    Files.deleteIfExists(outputFile);
    try(Writer writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
      JAXBContext.newInstance(FileCheckResults.class).createMarshaller().marshal(checkResults, writer);
    }

    FileCheckResults saved;
    try (Reader reader = Files.newBufferedReader(outputFile, StandardCharsets.UTF_8)) {
      saved = (FileCheckResults) JAXBContext.newInstance(FileCheckResults.class).createUnmarshaller().unmarshal(reader);
    }

    assertEquals("-r1259", saved.getSpecVersion());
    assertEquals("v2.8.14", saved.getFilecheckerVersion());
    assertEquals("1901830_meta.nc", saved.getFile());
    assertEquals("FILE-ACCEPTED", saved.getStatus());
    assertEquals("FILE-NAME-CHECK", saved.getPhase());
    assertEquals("aoml", saved.getMetadata().getDac());
    assertEquals("Argo meta-data", saved.getMetadata().getDATATYPE());
    assertEquals("3.1", saved.getMetadata().getFORMATVERSION());
    assertEquals("20250402201200", saved.getMetadata().getDATEUPDATE());
    assertEquals("AO", saved.getMetadata().getDATACENTRE());
    assertEquals("1901830", saved.getMetadata().getPLATFORMNUMBER());
    assertEquals("BRECK OWENS, STEVEN JAYNE, P.E. ROBBINS", saved.getMetadata().getPINAME());
    assertEquals("854", saved.getMetadata().getWMOINSTTYPE());
    assertEquals("D", saved.getMetadata().getDATAMODE());
    assertEquals("A", saved.getMetadata().getDIRECTION());
    assertEquals(2, saved.getMetadata().getNPROF());
    assertEquals(23, saved.getMetadata().getNLEVELS());
    assertEquals("1", saved.getMetadata().getCYCLENUMBER());
    assertEquals("23.88", saved.getMetadata().getLATITUDE());
    assertEquals("10.45", saved.getMetadata().getLONGITUDE());
    assertEquals("2", saved.getMetadata().getJULDQC());
    assertEquals("3", saved.getMetadata().getPOSITIONQC());
    assertEquals("4", saved.getMetadata().getPROFILETEMPQC());
    assertEquals("5", saved.getMetadata().getPROFILEPSALQC());
    assertEquals("8", saved.getMetadata().getPROFILEDOXYQC());
    assertEquals("DOXY,TEMP", saved.getMetadata().getSTATIONPARAMETERS());
    assertEquals("R", saved.getMetadata().getPARAMETERDATAMODE());
    assertEquals(2, saved.getErrors().getNumber());
    assertEquals(2, saved.getWarnings().getNumber());
    assertEquals(Arrays.asList("error1","error2"), saved.getErrors().getErrors());
    assertEquals(Arrays.asList("warn1","warn2"), saved.getWarnings().getWarnings());

  }

}
