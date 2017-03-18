# linkbuilder

[![Build Status](https://travis-ci.org/osvaldopina/linkbuilder.svg?branch=master)](https://travis-ci.org/osvaldopina/linkbuilder)

[![Join the chat at https://gitter.im/osvaldopina/linkbuilder](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/osvaldopina/linkbuilder?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)


# Changes from 0.3.0

Before 0.4.0-SNAPSHOT the annotation @LinkDestination was named @GenerateUriTemplateFor

# How to use it.

## Include the dependency

Add the following dependency to your project:

```xml
<dependency>
    <groupId>com.github.osvaldopina</groupId>
    <artifactId>linkbuilder</artifactId>
    <version>0.3.0</version>
</dependency>

```

Or the following dependency for the version under development:

```xml
<dependency>
    <groupId>com.github.osvaldopina</groupId>
    <artifactId>linkbuilder</artifactId>
    <version>0.4.0-SNAPSHOT</version>
</dependency>

```

### Purpose

The framework was created to generate links and links templates for Spring Hateoas web 
applications. In this new version, besides the builder, you can create the links using only annotations.


In the [complete documentation](https://github.com/osvaldopina/linkbuilder/wiki/Documentation) 
you will find instructions for 
[creating links via builder](https://github.com/osvaldopina/linkbuilder/wiki/Documentation#2-classic-link-builder), 
via annotations made on the 
[controller](https://github.com/osvaldopina/linkbuilder/wiki/Documentation#31-controller-links) and / or 
[resources](https://github.com/osvaldopina/linkbuilder/wiki/Documentation#32-resource-links).

Below is a summary of link building via builder and resource annotation





### Creating links with a classic builder

* Indicate the methods that will be destiny of the links with ``@LinkDestination`` (or ``@GanerateUriTemplateFor`` in 0.3.0 and before)
```java
@RestController
public class ResourcesRestController {

    @RequestMapping("/resource1/{path_param}")
    @LinkDestination
    public ResourceSupport oneResource(
            @PathVariable("path_param") String pathParam,@RequestParam("query_param") String queryParam) {
        return null;
    }

    @RequestMapping("/resource2/{path_param}")
    @LinkDestination
    public ResourceSupport otherResource(
            @PathVariable("path_param") String pathParam,@RequestParam("query_param") String queryParam) {
        return null;
    }
}
```

* Inject the `LinksBuilderFactory`

```java
    @Autowired
    private LinksBuilderFactory linksBuilderFactory;
```

* Create the `LinksBuilder` which is a builder for a set of links.

```java
    LinksBuilder linksBuilder = linksBuilderFactory.create(resource);
```


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


### Use annotations to generate links

* Leave the controller methods you want to point in the same way:

```java
    @RequestMapping("/direct-link/{path}")
    @LinkDestination(rel = "direct-link")
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }

    @RequestMapping("/direct-link/templated")
    @LinkDestination(rel = "direct-link-templated")
    public void directLinkTemplated(
            @RequestParam(value = "non_templated", required = false) String nonTemplated,
            @RequestParam(value = "templated", required = false) String templated) {
    }

```

* Annotate the controller method with `@LinkDestination` (or ``@GanerateUriTemplateFor`` in 0.3.0 and before):

```java
    @RequestMapping("/")
    @LinkDestination(rel = "root")
    public Resource root() {
        Resource resource = new Resource();

        resource.setQueryValue("anyQueryValue");
        resource.setPathValue("anyPathValue");

        return resource;
    }

```

* And annotate the resource:

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