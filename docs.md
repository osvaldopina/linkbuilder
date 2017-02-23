

# **Documentation**

[**1. Motivation**](#1-motivation)  
[**2. Classic Link Builder**](#2-classic-link-builder)  
[**2.1. Methods With Template**](#21-methods-with-template)   
[**2.2. Simple Links**](#22-simple-links)  
[**2.3. Templated Links**](#23-templated-links)  
[**2.4. Controlling link rendering via Spring SpEL**](#24-controlling-link-rendering-via-spring-spel)  
[**2.5. Generating Self Link from Current Controoler Call**](#25-generating-self-link-from-current-controoler-call)  
[**3. Creating Links Using Annotations**](#3-creating-links-using-annotations)  
[**3.1. Controller Links**](#31-controller-links)  
[**3.2. Resource Links**](#32-resource-links)  
[**3.3. Composed Annotations**](#33-composed-annotations)  
[**4. Hal extensions to Spring Hateoas Links**](#4-hal-extensions-to-spring-hateoas-links)  
[**4.1. HAL specific link properties**](#41-hal-specific-link-properties)  
[**4.2. knowledge of HAL document structure**](#42-knowledge-of-hal-document-structure)  
[**4.3. How to render HAL links**](#43-how-to-render-hal-links)  
[**5. Extension points**](#5-extension-points)  
[**5.1. User defined parameter types**](#51-user-defined-parameter-types)   
[**5.2. Base uri**](#52-base-uri)   
[**5.2.1. Base uri discover**](#521-base-uri-discover)   
[**5.2.2. Request parts factory**](#522-request-parts-factory)




### 1. Motivation
The framework was created initially because we were desperate to use Uri tempates. Since
then several other features have been added, notably the ability to create links via 
annotations and extensions to HAL documents.

### 2. Classic Link Builder

Initially we chose to create a builder. A little different from what comes with Spring 
Hateoas in terms of use, but functionally similar

The main idea is to use the controller calls to generate links that correspond to the call made

Because we use calls to controller methods there is no way to chain multiple link creations
into a single builder. Each link creation ends on a dead end.

### 2.1. Methods With Template
To indicate that a method will have an associated template you should use the `@GenerateUriTemplateFor`
annotation. Each annotated method will have an associated template and will be available for
link and link template generation. Initially, for link generation via classic linkbuilder,
you can use the empty annotation or inform a rel link that will be the default. Later on we 
will see other ways of using this annotation when link generation is done via annotations

### 2.2. Simple Links

For a simple (non templated) link a simple exmple would be:

Given this Controller:

```java
@RestController
public class ResourcesRestController {

    @RequestMapping("/resource1/{path_param}")
    @GenerateUriTemplateFor
    public ResourceSupport oneResource(
            @PathVariable("path_param") String pathParam,@RequestParam("query_param") String queryParam) {
        return null;
    }

    @RequestMapping("/resource2/{path_param}")
    @GenerateUriTemplateFor
    public ResourceSupport otherResource(
            @PathVariable("path_param") String pathParam,@RequestParam("query_param") String queryParam) {
        return null;
    }
}
```
To generate links for resource1 and resource2 you have to:

* Inject the `LinksBuilderFactory`

```java
    @Autowired
    private LinksBuilderFactory linksBuilderFactory;
```

* Create the `LinksBuilder` which is a builder for a set of links.

```java
    LinksBuilder linksBuilder = linksBuilderFactory.create(resource);
```
Notice that you should pass the `Resource` instance that will hold the links.

* For each link call `link()` on `LinksBulder` to create a builder for a link.

```java
   ResourceSupport resource = new ResourceSupport();

   LinksBuilder linksBuilder = linksBuilderFactory.create(resource);

   linksBuilder.link()
               .withRel("first-rel")
               .fromControllerCall(ResourcesRestController.class)
               .oneResource("path", "query")
               
   linksBuilder.link()
               .withRel("second-rel")
               .fromControllerCall(ResourcesRestController.class)
               .otherResource("path", "query");

                
   linksBuilder.buildAndSetAll();
```

Notice that after `link()` call a builder for a single link will be created and added to the
`LinksBuilder`

* Then when you call `buildAndSetAll()` in the `LinksBuilder` all links will be created and
added to `Resouce` instance.

```java
       linksBuilder.buildAndSetAll();
```

The following json will be generated:

```json
{
    "_links":{
        "first-rel":{
            "href":"http://localhost/resource1/path?query_param=query"
         },
        "second-rel":{
            "href":"http://localhost/resource2/path?query_param=query"
        }
    }
}
```
> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/builder/simple) you can find the complete code for this example.
(The files are:
[controller methods to link to](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/builder/simple/ResourcesRestController.java), 
[controller using link builder](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/builder/simple/RootRestController.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/builder/simple/BuilderSimpleApplication.java) and a 
[integration test](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/builder/simple/BuilderSimpleApplicationTest.java)
)


### 2.3. Templated Links

For template links you should just call `templated()` on `LinkBuilder` and indicate which 
parameters are not going to be replaced and will be left as template parameters. 
The substitution control is done by the following methods:  

* `dontSubstituteParameterIndex(int paramIndex)`  
Indicates that the nth parameter should not be kept as a variable in the uri template

* `dontSubstitute(String variableName)`  
Indicates that the variable with the given name must be kept as a variable in the template

* `dontSubstituteNullValues()`  
Indicates that all parameters with null values must be kept as variables in the template

* `dontSubstituteAny()`
Indicates that all parameters must be kept as variables in the template

For a non templated link a simple templated exmple would be:

Given this Controller:
```java
@RestController
public class ResourcesRestController {

    @RequestMapping("/resource1/{path_param}")
    @GenerateUriTemplateFor
    public ResourceSupport oneResource(
            @PathVariable("path_param") String pathParam,@RequestParam("query_param") String queryParam) {
        return null;
    }

    @RequestMapping("/resource2/{path_param}")
    @GenerateUriTemplateFor
    public ResourceSupport otherResource(
            @PathVariable("path_param") String pathParam,@RequestParam("query_param") String queryParam) {
        return null;
    }
}
```
And the following use of linksBuilder:

```java
        ResourceSupport resource = new ResourceSupport();

        LinksBuilder linksBuilder = linksBuilderFactory.create(resource);

        linksBuilder.link()
                .withRel("templated-first-rel")
                .templated()
                .dontSubstituteNullValues()
                .fromControllerCall(ResourcesRestController.class)
                .oneResource("path", null);

        linksBuilder.link()
                .withRel("templated-second-rel")
                .templated()
                .dontSubstituteNullValues()
                .fromControllerCall(ResourcesRestController.class)
                .otherResource("path", null);

        linksBuilder.buildAndSetAll();
```

The following json will be generated:

```json
{
    "_links":{
        "templated-first-rel":{
            "href":"http://localhost/resource1/path{?query_param}",
            "templated":true
        },
        "templated-second-rel":{
            "href":"http://localhost/resource2/path{?query_param}",
            "templated":true
        }
    }
}
```
> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/builder/templated) you can find the complete code for this example.
(The files are:
[controller methods to link to](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/builder/templated/ResourcesRestController.java), 
[controller using link builder](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/builder/templated/RootRestController.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/builder/templated/BuilderTemplatedApplication.java) and a 
[integration test](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/builder/templated/BuilderTemplatedApplicationTest.java)
)


### 2.4. Controlling link rendering via Spring SpEL

Each link can have its rendering controlled through a SpEL expression. 
The link builder has the `when()` method for defining the expression. 
Only if the expression evaluates to true will the link be included in the resource.

### 2.5. Generating Self Link from Current Controoler Call

If the controller is annotated with @SelfFromCurrentCall a self link will be generated and 
included in the resource returned by the controller.

The following controller:

```java
@RestController
public class RootRestController {

    @RequestMapping("/")
    @SelfFromCurrentCall
    public ResourceSupport root() {
        return new ResourceSupport();
    }

 }
```

Will generate de following json:

```json
{
    "_links":{
        "self":{
            "href":"http://localhost/"
        }
    }
}
```
> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/selffromcurrentcall) you can find the complete code for this example.
(The files are:
[controller with @SelfFromCurrentCall annotation](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/selffromcurrentcall/RootRestController.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/selffromcurrentcall/EnableSelfFromCurrentCallApplication) and a 
[integration test](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/selffromcurrentcall/SelfFromCurrentCallApplicationTest.java)
)

### 3. Creating Links Using Annotations
The framework followed its natural evolution and allowed links to be created via annotations.
Both controller and resource annotations are possible.

### 3.1. Controller Links
To create links via annotations you must use `@Links` as containing annotation for 
`@Link` that is used to generate each link individually.

To point to a method in a controller you must use `controller()`  
and `rel()` properties of the @link annotation.

Other `@Link` properties are:

* `String overridedRel()`  
Use to overload link rel property

* `String when()`  
Boolean SpEL expression that will be evaluated when generating the link. 
If its value is false, the link will not be generated.

* `boolean templated()`  
Indicates whether the link will be a Uri Template or simple link.

* `Variable[] variables()`  
Used to configure each link parameter variable subustitution.

Each `@Variable`  contains the following properties:

* `String when()`  
Indicates whether or not the variable is to be replaced. If the link is a template and the 
indication is non-substitution this variable will be left in the uri template.

* `String name()`  
Variable name

* `String value()`  
SpEL expression that will be evaluated to be used in the substitution of the variable

It is important to note that when the template has a variable that is not set in a `@Variable`
the effect is the same as when the variable's `when()` property is evaluated as false.
It will not appear in the link if it is not a template or it will appear as a uri template variable 
if the link is a template.

A complete example would be:


Given these controller methods:

```java
    @RequestMapping("/direct-link/{path}")
    @GenerateUriTemplateFor(rel = "direct-link")
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }

    @RequestMapping("/direct-link/templated")
    @GenerateUriTemplateFor(rel = "direct-link-templated")
    public void directLinkTemplated(
            @RequestParam(value = "non_templated", required = false) String nonTemplated,
            @RequestParam(value = "templated", required = false) String templated) {
    }

```

And a controller method with the following annotations:

```java
    @RequestMapping("/")
    @SelfFromCurrentCall
    @Links({
            @Link(controller = RootRestController.class, rel = "direct-link", variables = {
                    @Variable(name = "query", value = "#resource.queryValue"),
                    @Variable(name = "path", value = "#resource.pathValue")
            }),
            @Link(controller = RootRestController.class, rel = "direct-link", overridedRel = "direct-link-overrided",
                    variables = {
                            @Variable(name = "query", value = "#resource.queryValue"),
                            @Variable(name = "path", value = "#resource.pathValue")
                    }),
            @Link(controller = RootRestController.class,
                    rel = "direct-link-templated",
                    templated = true,
                    variables = {
                            @Variable(name = "templated", value = "'templated-value'")
                    })
    })
    public Resource root() {
        Resource resource = new Resource();

        resource.setQueryValue("anyQueryValue");
        resource.setPathValue("anyPathValue");

        return resource;
    }

```

The following json will be generated:

```json
 {
     "queryValue":"anyQueryValue",
     "pathValue":"anyPathValue",
     "_links":{
         "direct-link":{
             "href":"http://localhost/direct-link/anyPathValue?query=anyQueryValue"
         },
         "direct-link-overrided":{
             "href":"http://localhost/direct-link/anyPathValue?query=anyQueryValue"
         },
         "direct-link-templated":{
             "href":"http://localhost/direct-link/templated?templated=templated-value{&non_templated}",
             "templated":true
         },
         "self":{
             "href":"http://localhost/"
         }
    }
}
```

> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/simple) you can find the complete code for this example.
(The files are:
[annotated controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/simple/RootRestController.java), 
[resource returned by the controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/simple/Resource.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/simple/AnnotationControllerSimpleApplication.java) and a 
[integration test](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/simple/AnnotationControllerSimpleTest.java)
)


### 3.2. Resource Links
Another option is to annotate resources rather than controllers. The great advantage of 
annotating the controllers is that it stays independent of the method that returns it. 
Note that only the resources returned by controller methods annotated with `@GenerateUriTemplateFor`
are inspected.

Considering the previous example, where the links are annotated in the controller, 
if you want the same previous result but want to note the resource you should:

Leave the controller methods you want to point in the same way:

```java
    @RequestMapping("/direct-link/{path}")
    @GenerateUriTemplateFor(rel = "direct-link")
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }

    @RequestMapping("/direct-link/templated")
    @GenerateUriTemplateFor(rel = "direct-link-templated")
    public void directLinkTemplated(
            @RequestParam(value = "non_templated", required = false) String nonTemplated,
            @RequestParam(value = "templated", required = false) String templated) {
    }

```

Annotate the controller method with `@GenerateUriTemplateFor`:

```java
    @RequestMapping("/")
    @GenerateUriTemplateFor(rel = "root")
    public Resource root() {
        Resource resource = new Resource();

        resource.setQueryValue("anyQueryValue");
        resource.setPathValue("anyPathValue");

        return resource;
    }

```

And annotate the resource:

```java
@Links({
	@Link(controller = RootRestController.class, rel = "root", overridedRel = "self"),
	@Link(controller = RootRestController.class, rel = "direct-link",
		variables = {
			@Variable(name = "query", value = "#resource.queryValue"),
			@Variable(name = "path", value = "#resource.pathValue")
		}),
	@Link(controller = RootRestController.class, rel = "direct-link", overridedRel = "direct-link-overrided",
		variables = {
			@Variable(name = "query", value = "#resource.queryValue"),
			@Variable(name = "path", value = "#resource.pathValue")
		}),
	@Link(controller = RootRestController.class, rel = "direct-link-templated", 
                templated = true,
                variables = {
			@Variable(name = "templated", value = "'templated-value'")
	    })
})
public class Resource extends ResourceSupport {

	private String queryValue;

	private String pathValue;

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public String getPathValue() {
		return pathValue;
	}

	public void setPathValue(String pathValue) {
		this.pathValue = pathValue;
	}
}


```
> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/simple) you can find the complete code for this example.
(The files are:
[controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/simple/RootRestController.java), 
[annotated resource returned by the controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/simple/Resource.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/simple/AnnotationControllerSimpleApplication.java) and a 
[integration test](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/simple/AnnotationResourceSimpleTest.java)
)


### 3.3. Composed Annotations

Annotations are a tremendous innovation in java but it has some limitations. The ideal would 
be that all link destinations could be in enums, but how to make reference in the 
annotation to an enum that will be created by you and a posteriori?

The framework response was to create what we call "composite annotations". The basic idea is 
that you create your own annotations, with the same parameters of the annotation of the 
framework, and use these annotations.

An example will make this approach clearer:

* Create a annotation to map the link destinations 

```java
public enum Destination {

    DIRECT_LINK("direct-link"),

    DIRECT_LINK_TEMPLATED("direct-link-templated");

    private String rel;

    Destination(String rel) {
        this.rel = rel;
    }

    public String getRel() {
        return rel;
    }
}

```
It is mandatory that the enum elements have a `getRel` method with no parameters. A `toString()`
will be called on the return of this method and used as link rel attribute.

* Create a "composed" annotation to replace `@GenerateUriTemplateFor`

```java
@GenerateUriTemplateFor
@Retention(RetentionPolicy.RUNTIME)
public @interface MyGenerateUriTemplateFor {

    Destination destination();
}
```
This annotation should be annotated with `@GenerateUriTemplateFor` and have a destionation 
property with the created enum type

* Create a "composed" annotation to replace `@Links` to be used as a container for "composed"
`@Link` annotation. 

```java
@Links
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLinks {

    MyLink[] value() default {};

}
```
This annotation should be annotated with `@Links` and must have a array of "composed" 
`@Link` annotation.

* Create a "composed" annotaion to replace `@Link`

```java
@Link
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLink {

    Destination destination();

    String overridedRel() default "";

    String when() default "";

    boolean templated() default false;

    Variable[] variables() default {};

}
``` 
This annotation should be annotated with `@Link` and must have a `destination` property with 
enum `Destination` as its type. It is madatory also to have `overridedRel`, `when`, `templated`
and `variables` as parameters 

* Use the newly created "composed" annotations on destination controller methods:

```java
    @RequestMapping("/direct-link/{path}")
    @MyGenerateUriTemplateFor(destination = Destination.DIRECT_LINK)
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }

    @RequestMapping("/direct-link/templated")
    @MyGenerateUriTemplateFor(destination = Destination.DIRECT_LINK_TEMPLATED)
    public void directLinkTemplated(
            @RequestParam(value = "non_templated", required = false) String nonTemplated,
            @RequestParam(value = "templated", required = false) String templated) {
    }

```
* And use the newly created "composed" annotations to control link rendering:

```java
   @RequestMapping("/")
    @SelfFromCurrentCall
    @MyLinks({
            @MyLink(destination = Destination.DIRECT_LINK, variables = {
                    @Variable(name = "query", value = "#resource.queryValue"),
                    @Variable(name = "path", value = "#resource.pathValue")
            }),
            @MyLink(destination = Destination.DIRECT_LINK, overridedRel = "direct-link-overrided",
                    variables = {
                            @Variable(name = "query", value = "#resource.queryValue"),
                            @Variable(name = "path", value = "#resource.pathValue")
                    }),
            @MyLink(destination = Destination.DIRECT_LINK_TEMPLATED,
                    templated = true,
                    variables = {
                            @Variable(name = "templated", value = "'templated-value'")
             })
    })
    public Resource root() {
        Resource resource = new Resource();

        resource.setQueryValue("anyQueryValue");
        resource.setPathValue("anyPathValue");

        return resource;
    }

```

> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/link) you can find the 
code that create composed annotations
(The files are:
[annotation with all link destintations](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/link/Destination.java), 
[composed annotation for @GenerateUriTemplateFor](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/link/MyGenerateUriTemplateFor.java), 
[composed annotation for @Links](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/link/MyLinks.java), 
[composed annotation for @Link](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/link/MyLink.java), 
) and in this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed) you can find the 
complete code example for a composed annotation used in a controller 
(The files are:
[annotated controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/RootRestController.java), 
[resource returned by the controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/Resource.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/AnnotationControllerComposedApplication.java) and a 
[integration test](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/AnnotationControllerComposedTest.java)
)

> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/link) you can find the 
code that create composed annotations
(The files are:
[annotation with all link destintations](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/link/Destination.java), 
[composed annotation for @GenerateUriTemplateFor](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/link/MyGenerateUriTemplateFor.java), 
[composed annotation for @Links](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/link/MyLinks.java), 
[composed annotation for @Link](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/link/MyLink.java), 
) and in this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/link) you can find the 
complete code example for a composed annotation used in a resource
(The files are:
[controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/RootRestController.java), 
[anotated resource returned by the controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/Resource.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/AnnotationControllerComposedApplication.java) and a 
[integration test](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/AnnotationControllerComposedTest.java)
)


### 4. Hal extensions to Spring Hateoas Links

Although spring hateoas is a generic framework and, in theory, it can support several 
formats, it currently supports only HAL. This framework has been designed to be able to 
support several formats. In this first version was created a specific extension for HAL.

This extension comes on 2 fronts: HAL-specific link properties and knowledge of HAL document
structure

###  4.1. HAL specific link properties
This feature is currently under development and the only implemented HAL link property is 
`hreflang`. Other properties will be included in the next releases

###  4.2. knowledge of HAL document structure
Hal documents have a defined structure with 2 properties: `_links` and `_embedded` whereas 
_embedded contains other resources which, in turn, can have their `_links` and `_embedded`
sections and so on. 

When creating HAL links, the framework will recursively search resources within _embedded 
sessions and, if they are annotated, generate the respective links

###  4.3. How to render HAL links
To create HAL Links the `@HalLinks` and `@HalLink` annotations must be used. For HAL Links
are available the features of "composed" annotations and the possibility of annotation in 
both controllers and resources.


                    
> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/simple) you can find the complete
code for a exemple of a controller annotated with HAL link extension  
(The files are:
[annotated controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/simple/RootRestController.java), 
[resource returned by the controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/simple/Resource.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/simple/HalAnnotationControllerSimpleApplication.java) and a 
[integration test](examples/src/main/test/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/simple/HalAnnotationControllerSimpleTest.java)
)

> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/simple) you can find the complete
code for a exemple of a resource annotated with HAL link extension  
(The files are:
[controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/simple/RootRestController.java), 
[annotated resource returned by the controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/simple/Resource.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/simple/HalAnnotationControllerSimpleApplication.java) and a 
[integration test](examples/src/main/test/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/simple/HalAnnotationControllerSimpleTest.java)
)

> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/composed/link) you can find the 
code that create composed annotations for HAL links 
(The files are:
[annotation with all link destintations](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/composed/link/Destination.java), 
[composed annotation for @GenerateUriTemplateFor](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/composed/link/MyGenerateUriTemplateFor.java), 
[composed annotation for @HalLinks](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/composed/link/MyHalLinks.java), 
[composed annotation for @HalLink](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed/link/MyHalLink.java), 
) and in this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/controller/composed) you can find the 
complete code example for a composed annotation used in a controller 
(The files are:
[annotated controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/composed/RootRestController.java), 
[resource returned by the controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/composed/Resource.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/composed/HalAnnotationControllerComposedApplication.java) and a 
[integration test](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/controller/composed/HalAnnotationControllerComposedApplication.java)
)

> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/composed/link) you can find the 
code that create composed annotations for HAL links 
(The files are:
[annotation with all link destintations](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/composed/link/Destination.java), 
[composed annotation for @GenerateUriTemplateFor](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/composed/link/MyGenerateUriTemplateFor.java), 
[composed annotation for @HalLinks](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/composed/link/MyHalLinks.java), 
[composed annotation for @HalLink](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed/link/MyHalLink.java), 
) and in this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/annotation/resource/composed) you can find the 
complete code example for a composed annotation used in a resource 
(The files are:
[annotated controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/composed/RootRestController.java), 
[resource returned by the controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/composed/Resource.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/composed/HalAnnotationControllerComposedApplication.java) and a 
[integration test](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/hal/annotation/resource/composed/HalAnnotationResourceComposedApplication.java)
)

### 5. Extension points

### 5.1. User defined parameter types
The framework recognizes annotations that allow methods on controllers to respond to HTTP 
calls (@RequestParam, @RequestParam), but Spring allows user-created types to be used as 
parameters in the controller. A simple example is the Pageable type used for pagination.   

There is no way to know how to use these types to create the templates associated with the 
methods, nor how to know how to replace these types in the uri templates.

In order for the framework to understand new user-defined types 2 interfaces must be 
implemented:

1. ArgumentResolver

```java
package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver;

public interface ArgumentResolver {

    boolean resolveFor(Method method, int parameterIndex);

    Variables create(Method method, int parameterIndex);
}
```
It is used to, given a parameter, create a list of variables to be included in the 
uri template.   

The `resolveFor` method defines whether this class knows how to create template 
variables for a given parameter 

2. 

```java
package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue;

public interface ParameterVariableValueDiscover {


    List<VariableValue> getVariableValues(
            Variables variables, MethodCall methodCall, Object resource, int parameterIndex,
            ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies);

    boolean canDiscover(MethodCall methodCall, int parameterIndex);

}
```
It is used, given a list of variables of a template, the call of the methods, the resource 
to be returned, the index parameter and the substitution strategy of the variables in the 
template, create a list with the values for the variables to be Replaced.   

The canDiscover method defines whether this class knows how to create the values of the 
variables to be overridden in the template.   

For example, consider the folowing user defined type:

```java
public class UserDefinedType {

    private String value1;

    private String value2;

    public UserDefinedType(String value1, String value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }
}
```

This `ArgumentResolver` includes 2 query parameters, with the names "variable1" and 
"variable2", in the template variable list:

```java
@Component
public class UserDefinedTypeArgumentResolver implements ArgumentResolver{

    @Override
    public boolean resolveFor(Method method, int parameterIndex) {
        return UserDefinedType.class.isAssignableFrom(method.getParameterTypes()[parameterIndex]);
    }

    @Override
    public Variables create(Method method, int parameterIndex) {
        return VariablesFactory.INSTANCE.create(Arrays.asList(
                new Variable("value1", VariableType.QUERY, method, parameterIndex),
                new Variable("value2", VariableType.QUERY, method, parameterIndex)
        ));
    }

}

```

And this This `ParameterVariableValueDiscover` retrieves the template variables corresponding 
to the parameter, the value of the method parameter, tests whether the substitution strategy 
indicates whether this parameter should be replaced or not, and returns a list with the values 
for the template variables to be replaced.

```java
@Component
public class UserDefinedTypeVariableValueDiscover implements ParameterVariableValueDiscover {

    @Override
    public List<VariableValue> getVariableValues(Variables variables, MethodCall methodCall, Object resource, int parameterIndex, ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies) {

        List<VariableValue> variableValues = new ArrayList<VariableValue>();

        UserDefinedType userDefinedType = (UserDefinedType) methodCall.getParam(parameterIndex);

        Variable variable = variables.get("value1");

        if (conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, methodCall.getParam(parameterIndex))) {
            variableValues.add(new VariableValue(variable, userDefinedType.getValue1()));
        }

        variable = variables.get("value2");

        if (conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, methodCall.getParam(parameterIndex))) {
            variableValues.add(new VariableValue(variable, userDefinedType.getValue2()));
        }

        return variableValues;
    }

    @Override
    public boolean canDiscover(MethodCall methodCall, int parameterIndex) {
        return UserDefinedType.class.isAssignableFrom(methodCall.getMethod().getParameterTypes()[parameterIndex]);
    }
}

```

Note that you just need to include these classes in the spring context so that the framework 
can create templates for methods that use this type.   


> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/userdefinedtype) you can find the complete code for this example.
(The files are:
[controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/userdefinedtype/RootRestController.java), 
[user defined type](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/userdefinedtype/UserDefinedType.java), 
[argument resolver](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/userdefinedtype/UserDefinedTypeArgumentResolver.java),
[variable value discover](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/userdefinedtype/UserDefinedTypeVariableValueDiscover.java),
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/userdefinedtype/Application.java) and a 
[integration test](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/userdefinedtype/UserDefinedTypeTest.java)
)


### 5.2. Base uri
HTTP requests often go through multiple network elements and may have many of their 
characteristics changed by them. One aspect that is especially sensitive is the definition 
of the base url.   
The framework offers 2 mechanisms to act on the definition of the base url: url base discover
and request parts factory

### 5.2.1. Base uri discover
The simplest mechanism is the ability to provide the framework with an implementation of the 
`BaseUriDiscover` interface.

```java
package com.github.osvaldopina.linkbuilder.urigeneration.base;

public interface BaseUriDiscover {

    String getBaseUri();

}
```

For example, to set the base url to "fixed-url":


Implement BaseUriDiscover:

```java
public class FixedUrlBaseUriDiscover implements BaseUriDiscover {

    @Override
    public String getBaseUri() {
        return "fixed-url";
    }
}
```

Extends `CustomLinkBuilderConfigurer` to provide a implementation of `FixedUrlBaseUriDiscover`:

```java
@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Configuration
public class Application extends CustomLinkBuilderConfigurer {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }


    @Override
    public BaseUriDiscover baseUriDiscover() {
        return new FixedUrlBaseUriDiscover();
    }

}
```

And considering the following controller:

```java
@RestController
public class RootRestController {

    @RequestMapping("/")
    @SelfFromCurrentCall
    public ResourceSupport root() {
        return new ResourceSupport();
    }

}
```

The following json will be generated:

```json
{
    "_links":{
        "self":{
            "href":"fixed-url/"
        }
    }
}
```

> In this [package](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/extensions/baseuridiscover) you can find the complete code for this example.
(The files are:
[controller](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/extensions/baseuridiscover/RootRestController.java), 
[url base discover](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/extensions/baseuridiscover/FixedUrlBaseUriDiscover.java), 
[main class run the application](examples/src/main/java/com/github/osvaldopina/linkbuilder/example/extensions/baseuridiscover/Application.java) and a 
[integration test](examples/src/test/java/com/github/osvaldopina/linkbuilder/example/extensions/baseuridiscover/BaseUriDiscoverExtensionTest.java)
)

### 5.2.2. Request parts factory
One way to adjust the base url in a more specialized way is to act on an internal structure of 
the framework responsible for inspection of the HTTP request and generate all parts of the base 
url.

The framework creates a list of `RequestPartsFactory`, each one is responsable for inspecting 
the `HttpServletRequest` and return a `ChainedRequestParts`. Each `ChainedRequestParts` holds
information necessary to create the base url. All `ChainedRequestParts` are chained in a way
that each of then is responsable for extracting one of `RequestParts` (the properties are: 
scheme, host, port, contextPath) or delegatin to the next `ChainedRequestParts` until you reach
the last one in the chain. Then it creates the base uri just by contatenating its properties 
(the concatenation code is: `new URI(getScheme(),null, getHost(), normalizedPort,getContextPath(), null, null)).toString();`)   

The framework ofers a possibility of replacing the default `RequestPartsFactoryList`.   

For example, to use a HTTP request reader `my-custom-header` to create the base url you have to:

Create a RequestPartsFactory that looks for HTTP reader `my-custom-header` and use its value 
to create a `ChainedRequestParts`:

```java
   @Override
    public ChainedRequestParts create(HttpServletRequest request) {

        String protocolHeader = request.getHeader("my-custom-header")==null?
                "":
                request.getHeaders("my-custom-header").nextElement().toString();

        if (StringUtils.hasText(protocolHeader)) {
            Map<String, String> headers = fromStringToMap(protocolHeader);

            return new ChainedRequestParts(
                    headers.get("scheme"),
                    headers.get("host"),
                    headers.get("port") == null?-1:Integer.valueOf(headers.get("port")),
                    headers.get("contextPath")
            );

        }
        else {
            return new ChainedRequestParts();
        }

    }
```

Note that if the reader is not in HTTP request a empty `ChainedRequestParts` is returned. The 
chain is develped in a way that if a `ChainedRequestParts` property is empty the next element in 
the chain is used to provide the property.

Create a `RequestPartsFactoryList` with the former 



acertar o chain...




