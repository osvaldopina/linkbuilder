# linkbuilder

[![Build Status](https://travis-ci.org/osvaldopina/linkbuilder.svg?branch=master)](https://travis-ci.org/osvaldopina/linkbuilder)

[![Join the chat at https://gitter.im/osvaldopina/linkbuilder](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/osvaldopina/linkbuilder?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

# How to use it.

## Include the dependency

Add the following dependency to your project:

```xml
<dependency>
    <groupId>com.github.osvaldopina</groupId>
    <artifactId>linkbuilder</artifactId>
    <version>0.2.0</version>
</dependency>

```

Or the following dependency for the version under development:

```xml
<dependency>
    <groupId>com.github.osvaldopina</groupId>
    <artifactId>linkbuilder</artifactId>
    <version>0.3.0-SNAPSHOT</version>
</dependency>

```
## New in 0.2.0

### Generating links using only annotations

Indicate the  target method  using ```@LinkTarget```:

```java
    @RequestMapping("/direct-link/{path}")
    @LinkTarget("direct")
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }
```
Then  use ```@Links, @Link``` to point to a target method:
```java
    @RequestMapping("/")
    @EnableSelfFromCurrentCall
    @Links({
            @Link(destination = RootRestController.class, target = "direct", relation = "direct", params = {
                    @Param(name = "query",value = "#resource.queryValue"),
                    @Param(name = "path",value = "#resource.pathValue")
            })
    })
    public Resource root() {
...
```
Then when perform a GET on previous controller (GET http://localhost:8080/ calling ```public Resource root()```)
you get the following reponse:
```json
{
    "queryValue": "anyQueryValue",
    "pathValue": "anyPathValue",
    "_links": {
        "self": {
            "href": "http://localhost:8080/"
        },
        "direct": {
            "href": "http://localhost:8080/direct-link/anyPathValue?query=anyQueryValue"
        }
    }
}
```

[Here](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/directlink) the code for a complete example and
[here](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/userdefinedtype) the example integration test


### New in 0.1.3

## Spel expressions for link conditional rendering

 ```java
         ResourceSupport resource = new ResourceSupport();

         LinksBuilder  linksBuilder = linksBuilderFactory.create(resource);

         linksBuilder.link()
                 .withRel("link-for-authenticated-users")
                 .when("isAuthenticated()" )
                 .fromControllerCall(ARestController.class)
                 .someControllerMethod();

         linksBuilder.buildAndSetAll());

         return reosurce;
 ```

## Resource acessible via Spel expressions

 ```java
         ResourceSupport resource = new ResourceSupport();

         LinksBuilder  linksBuilder = linksBuilderFactory.create(resource);

         linksBuilder.link()
                 .withRel("link-for-authenticated-users")
                 .when("#resource.someProperty && isAuthenticated()" )
                 .fromControllerCall(ARestController.class)
                 .someControllerMethod();

         linksBuilder.buildAndSetAll());

         return resource;
 ```


### Annotate controller methods

```@EnableSelfFromCurrentCall``` If you want to use the annotated method to generate links, link templates and use the current call to generate a self link:

```java
@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory linksBuilderFactory;

    @RequestMapping("/")
    @EnableSelfFromCurrentCall
    public ResourceSupport root(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

```

Or ```@GenerateUriTemplateFor``` If you just want to use the annotated method to generate links and link templates:

```java
@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory linksBuilderFactory;

    @RequestMapping("/")
    @GenerateUriTemplateFor
    public ResourceSupport root(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

```

### Inject ```LinksBuilderFactory```

```java

    @Autowired
    private LinksBuilderFactory linksBuilderFactory;

```

### Create the ```LinksBuilder``` than the ```LinkBuilder```, configure them, and then build the links.

```java
        LinksBuilder  linksBuilder = linksBuilderFactory.create(resource);

        linksBuilder
              .link()
              .withSelfRel()
              .fromCurrentCall();

        linksBuilder
              .link()
              .withRel("no-query-parameter")
              .fromControllerCall(RootRestController.class)
              .methodWithoutQueryParameter("value", null);

        linksBuilder
              .link()
              .withRel("user-defined-type")
              .fromControllerCall(RootRestController.class)
              .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

        linksBuilder.buildAndSetAll());
 ```

