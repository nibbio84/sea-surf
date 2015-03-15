# sea-surf
Sea-Surf is a Java EE 6 filter that protects against CSRF (Sea-Surf) attacks.

## Configuration
You need to put the library and a configuration file into a Java EE Web application.

You can download the jar from this site or using maven:

```
<dependency>
      <groupId>it.nerdammer</groupId>
      <artifactId>sea-surf</artifactId>
      <version>1.0.1</version>
</dependency>
```

Sea-Surf allows you choosing the protection methods on every page:

* Synchronizer token pattern
* HTTP referer check

### Synchronizer Token
The synchronizer token can be stored in *SESSION* or *COOKIE*: you can make a choice using the configuration file.
Sea-Surf provides two convenience JSTL tags for simplifying this task:

```
<%@ taglib uri="http://www.nerdammer.it/seasurf" prefix="csrf"%>

....
Write the token in a specific position in the page:
<csrf:token /> 

Save the token in a specific variable on page:
<csrf:token var="myVar" />

Create an hidden input field (this has to be put inside an html form):
<csrf:token-input />

```

### HTTP Referer Check
You can configure some pages to be accessed only if the user comes from particular pages. *Note* that the "Referer" http header can be missing,  so you have to specify to *BLOCK* or *ALLOW* the connection in these  circumstances.

## Sample Configuration
The configuration file must be named "sea-surf-config.xml" and placed into a source folder (at the root level).

Here is an example:

```
<?xml version="1.0" encoding="UTF-8"?>
<sea-surf-config xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.nerdammer.it/sea-surf-config_1_0"
	xsi:schemaLocation="http://www.nerdammer.it/sea-surf-config_1_0 http://sea-surf.googlecode.com/files/sea-surf-config_1_0.xsd">
	
    <referer-constraints>
		<referer-constraint>

                    <on-missing-referer>BLOCK</on-missing-referer>

		    <include>
		        <request-type>
		            <pattern>/*</pattern>
		            <methods>
		                <method>POST</method>
		                <method>PUT</method>
		            </methods>
		        </request-type>
		    </include>
		    
		    <exclude>
		        <request-type>
				<pattern>/myPage</pattern>
		    	</request-type>
		    </exclude>
		    
		    <include-origin>
		        <domain>www.mydomain.*</domain>
		        <page>http://www2.aaa.it/page</page>
		    </include-origin>
		    
		</referer-constraint>        
    </referer-constraints>
    
    <security-token-constraints>
        <security-token-constraint>
            <include>
                <request-type>
                    <pattern>/page2.jsp</pattern>
                </request-type>
            </include>
        </security-token-constraint>
    </security-token-constraints>
        
    <preferences>
        <token-storage>SESSION</token-storage>
        
    </preferences>
</sea-surf-config>
```

Automatically exported from code.google.com/p/sea-surf
