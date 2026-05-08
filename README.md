# argo-file-check-xml

The argo-file-check-xml contains an XML schema for the output of the Argo NetCDF file format checker (https://github.com/OneArgo/ArgoFormatChecker).

This schema is used to generate JAXB classes to interact with data in an object-oriented way.

Additional project information, javadocs, and test coverage is located at https://ci-cmg.github.io/project-documentation/argo-file-check-xml/

## Adding To Your Project

Add the following dependency to your Maven pom.xml

```xml
    <dependency>
      <groupId>io.github.ci-cmg</groupId>
      <artifactId>argo-file-check-xml</artifactId>
      <version>1.0.0</version>
    </dependency>
```

## Usage

To read an XML file:
```java
FileCheckResults checkResults;
try (Reader reader = Files.newBufferedReader(Paths.get("file1.xml"), StandardCharsets.UTF_8)) {
  checkResults = (FileCheckResults) JAXBContext.newInstance(FileCheckResults.class).createUnmarshaller().unmarshal(reader);
}

System.out.println(checkResults.getSpecVersion());
System.out.println(checkResults.getMetadata().getDac());
```

To write to an XML file:
```java
FileCheckResults checkResults = new FileCheckResults();
checkResults.setSpecVersion("-r1259");
Metadata metadata = new Metadata();
metadata.setDac("aoml");
checkResults.setMetadata(metadata);

try(Writer writer = Files.newBufferedWriter(Paths.get("file1.xml"), StandardCharsets.UTF_8)) {
    JAXBContext.newInstance(FileCheckResults.class).createMarshaller().marshal(checkResults, writer);
}
```





