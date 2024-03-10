# EzTag Page Layout

This project started out as a way of stitching together different markup templates, with the goal of trying out [htmx](https://htmx.org/). 
As I completed each feature that I had in mind, I kept bending to the temptation to add another one, and the list of features just kept 
growing. At some point, I had to take a hard pause so that I could take inventory and start documenting how everything worked together. 
In so doing, I would be able to keep track of what was done and also keep track of the new features' wish list.

## Architecture, a view from 10,000 feet

The _template files_ are organized in the project's source code, inside the _resources' folder_. The generated markup is written to files 
in a destination folder outside the project, a folder that is designated to serve _static content_ for the web application. Alternatively,
the generated content can also be written directly to the client's socket as response.

Request handlers are able to send back an entire page in response to a full-page refresh request by a client, or send back just fragments 
of document markup in response to client requests for those fragments.

When using a _layout page_ to decorate a requested page resource, the _requested resource_ can provide as many _slot content_ fragments as the
_layout page_ can take. This makes it possible to have, for example, just one _layout page_ that can decorate wildly different requested pages.

Some general rules of thumb are:
- all markup written must be valid _XML_ content using _HTML tags_
- to target any _html tag_ for processing, you need to prefix it with __x-__. Any tag without an __x-__ prefix is treated simply as a literal.
- custom predefined attributes that are used to provide additional context to the parser must be prefixed with __x-__ 
- the custom attributes have semantic meaning, and therefore they only make sense when used in the right circumstances. 
- decorator tags are those which are only visible by the browser, and not discernible by a human, for example, _doctype_, _meta_, _link_ and _script_. 
These are given a different kind of treatment from just regular markup tags

## Current features

1. Use MVEL templating engine to integrate with any Java backend
2. Prepend any regular html tag with __x-__ to point out that it needs special treatment from the parser, and similarly prepend custom tag attributes with
__x-__ to point out that they have pertinent information for the parser
3. Use well-formed __xhtml__ to describe the html elements, to skip reinventing parsing of DOM content. This also eliminates the need for any special IDE 
plugins for syntax highlighting, since _XML_ is a universally supported format

## Features wish list

1. Detect data change at the element level or document fragment level
2. Stream markup changes to the client using JavaScript's EventSource
3. Load template from other sources in addition to the default _classpath_