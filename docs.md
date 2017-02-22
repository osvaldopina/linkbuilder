

# **Documentation**

[**1. Motivation**](#1-motivation)  
[**2. Classic Link Builder**](#2---Classic-Link-Builder)  
[**2.1. Methods With Template**](#2.1---Methods-With-Template)  
[**2.2. Simple Links**](#2.2---Simple-Links)  
[**2.3. Templated Links**](#2.3---Templated-Links)  
[**2.4. Controlling link rendering via Spring SpEL**](#2.4---Controlling-link-rendering-via-Spring-SpEL)  
[**2.5. Generating Self Link from Current Controoler Call**](#2.5---Generating-Self-Link-from-Current-Controoler-Call)  
[**3. Creating Links Using Annotations**](#3---Creating-Links-Using-Annotations)  
[**3.1. Controller Links**](#3.1---Controller-Links)  
[**3.2. Resource Links**](#3.2---Resource-Links)  
[**3.3. Composed Annotations**](#3.3---Composed-Annotations)  
[**4. Hal extensions to Spring Hateoas Links**](#4---Hal-extensions-to-Spring-Hateoas-Links)  
[**4.1. HAL specific link properties**](#4.1---HAL-specific-link-properties)  
[**4.2. knowledge of HAL document structure**](#4.2---knowledge-of-HAL-document-structure)  
[**4.3. How to render HAL links**](#4.3---How-to-render-HAL-links)  


## 1. Motivation
The framework was created initially because we were desperate to use Uri tempates. Since
then several other features have been added, notably the ability to create links via 
annotations and extensions to HAL documents.

## 2. Classic Link Builder

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
### 2.4. Controlling link rendering via Spring SpEL

Each link can have its rendering controlled through a SpEL expression. 
The link builder has the `when()` method for defining the expression. 
Only if the expression evaluates to true will the link be included in the resource.

###2.5. Generating Self Link from Current Controoler Call

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
##**3 - Creating Links Using Annotations**
The framework followed its natural evolution and allowed links to be created via annotations.
Both controller and resource annotations are possible.

###**3.1 - Controller Links**
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

###**3.2 - Resource Links**
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

###**3.3 - Composed Annotations**

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
##**4 - Hal extensions to Spring Hateoas Links**

Although spring hateoas is a generic framework and, in theory, it can support several 
formats, it currently supports only HAL. This framework has been designed to be able to 
support several formats. In this first version was created a specific extension for HAL.

This extension comes on 2 fronts: HAL-specific link properties and knowledge of HAL document
structure

###**4.1 - HAL specific link properties**
This feature is currently in development and the only implemented HAL link property is 
`hreflang`. Other properties will be included in the next releases

###**4.2 - knowledge of HAL document structure**
Hal documents have a defined structure with 2 properties: `_links` and `_embedded` whereas 
_embedded contains other resources which, in turn, can have their `_links` and `_embedded`
sections and so on. 

When creating HAL links, the framework will recursively search resources within _embedded 
sessions and, if they are annotated, generate the respective links

###**4.3 - How to render HAL links**
To create HAL Links the `@HalLinks` and `@HalLink` annotations must be used. For HAL Links
are available the features of "composed" annotations and the possibility of annotation in 
both controllers and resources.