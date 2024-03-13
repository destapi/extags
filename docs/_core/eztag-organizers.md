## EzTag Organizer Tags

These are special tags because they are not standard _HTML tags_, and they only serve the purpose of

- importing content
- laying out content

### &lt;x-include x-path="/path/to/file.xml"&gt;

This is used to include content of a different file in the place where this tag appears in the file being parsed.
The same backing data object used in the current page is also used to resolve any expressions or variables that may
exist in the imported content.

An _x-named_ attribute may optionally be used in this element. When this happens, then the _imported_ content is treated
as the markup for a
_named slot_ in the decorator or layout page. It therefore only makes sense to use the _x-named_ attribute when the
_x-include_ is used inside
an _x-layout_ definition.

```xml
<p class="me">
  <x-include x-path="../another.html" />
</p>
```

### &lt;x-layout x-template="/path/to/file.xml"&gt;

This is used to import a _layout template_ which contains _named slots_ that can take on new content. When a target page
is using a _layout_,
it can take advantage of the _named slots_ in the _layout template_ and plug in its own content. If the target page does
not provide content
for a particular slot, then the content already in the _layout page slot_ is retained as the default content.

```xml
<html lang="en">
    <head>
    <meta charset="UTF-8" />
    <x-title x-slot="title">Default Title</x-title>
    </head>
    <body>
        <x-main x-slot="content"></x-main>
    </body>
</html>
```

The _target page_ can therefore look like this:

```xml
<x-layout x-template="/basic-template.xml">
    <x-doctype x-doctype="&lt;!doctype html&gt;" />

    <x-title x-named="title" x-text="page.title">The good title</x-title>

    <x-div id="todo-list" x-named="content">I'm here</x-div>
</x-layout>
```

