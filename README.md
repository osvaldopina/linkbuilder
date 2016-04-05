# linkbuilder

[![Build Status](https://travis-ci.org/osvaldopina/linkbuilder.svg?branch=master)](https://travis-ci.org/osvaldopina/linkbuilder)

## How to use it.

### Include the dependency

Add the following dependency to your project:

```xml
<dependency>
    <groupId>com.github.osvaldopina</groupId>
    <artifactId>linkbuilder</artifactId>
    <version>0.1.3-SNAPSHOT</version>
</dependency>
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
        LinksBuilder  linksBuilder = linksBuilderFactory.create();

        linksBuilder.link().withSelfRel().fromCurrentCall();
        linksBuilder.link().withRel("no-query-parameter").
                fromControllerCall(RootRestController.class).methodWithoutQueryParameter("value", null);

        linksBuilder.link().withRel("user-defined-type").
                fromControllerCall(RootRestController.class).queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

        payload.add(linksBuilder.buildAll());
 ```
 
 