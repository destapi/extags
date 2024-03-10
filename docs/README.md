# ExTag

This project started out as a way of stitching together different markup templates, with the goal of trying out [htmx](https://htmx.org/). 
As I completed each feature that I had in mind, I kept bending to the temptation to add another feature, and the features list kept growing. 
I had to take a hard pause so that I could start documenting how everything works, and that way I would be able to keep track of what is 
done and also keep track of the features' wish list.

## Architecture - view from 10,000 feet

The _template files_ are organized in the project's source code, inside the _resources folder_ and the generated markup is written to files 
in a destination folder outside the project, a folder that is designated to serve _static content_ for the web application.

Request handlers are able to send back an entire page in response to a full-page refresh request by a client, or send back just fragments 
of document markup in response to client requests for those fragments.

When using a _layout page_ to decorate a requested page resource, the _requested resource_ can provide as many _slot content_ fragments as the
_layout page_ can take. This makes it possible to have for example, just one _layout page_ that can decorate wildly different requested pages.

As general rules:
- to target any _html tag_ for processing, you need to prefix it with __x-__. Any tag without an __x-__ prefix is treated simply as a literal.
- custom predefined attributes which provide additional context to the parser must be prefixed with __x-__ 
- the custom attributes have semantic meaning, and therefore they only make sense when used in the right circumstances. 
- decorator tags are those which are only visible by the browser, and not discernible by a human, for example, _meta_, _link_ and _script_. 
These are given a different treatment from regular tags
- 

## Current features

1. Using MVEL templating engine to integrate with a Java backend
2. Prepending any regular html tag with __x-__ to point out that it needs special treatment, and similarly prepending custom tag attributes with
__x-__ to point out that they have pertinent information for the parser
3. Using well-formed __xhtml__ to describe the html elements, to skip reinventing the wheel

## Features wish list

1. Detecting data change at the element level or document fragment level
2. Streaming markup changes to the client using JavaScript's EventSource
3. Add __x-show="expr"__ to complement __x-if="expr"__. It might sometimes be important to just have the element available in the DOM, and only hide
it from view in the meantime using ```style: {display: none}```
4. 