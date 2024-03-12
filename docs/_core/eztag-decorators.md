## EzTag Decorator Tags

These are special tags because they are placeholders for content which is used by the browser and not by a person directly.
They include _doctype_, _meta_, _link_ and _script_. It's __important__ to be aware that the _style_ tag is not supported
as a decorator tag. The _style tag_ is treated like regular _HTML content_. Any styling is assumed to be using the _link tag_.
Also, these tags are only relevant if you are using a _HTML layout page_, otherwise, you wouldn't find use for them.

### &lt;x-doctype&gt;

This is only used together with the __<x-layout>__ tag. It is used to inform the parser to include the specified _doctype_
at the beginning of the generated markup. This is an opt-in feature which means that by default, the _doctype_ is not added
to generated content.

```xml
<x-layout x-template="/basic-template.xml">
    <x-doctype x-doctype="&lt;!doctype html&gt;" />
    <!-- The rest of it -->
</x-layout>
```

### &lt;x-meta /&gt;

This tag is used in two places:
- in the _layout page_ to provide positional point to insert _meta tags_

```xml
<html lang="en">
    <head>
    <meta charset="UTF-8" />
    <x-meta />
    <!-- rest of head -->
    </head>
    <!-- rest of body -->
</html>
```

- in the _decorated page_ to provide custom _meta tags_ that need to be injected in the generated markup.

```xml
<x-layout x-template="/basic-template.xml">
    <x-doctype x-doctype="&lt;!doctype html&gt;" />
    <x-meta hobbby="programming" />
    <x-meta skylight="azure" />
    <!-- ret of page -->
</x-layout>
```

### &lt;x-link /&gt;

in the same way as the _meta tags_, this tag is also used in two places:
- in the _layout page_ to provide positional point to insert _link tags_

```xml
<html lang="en">
    <head>
    <meta charset="UTF-8" />
    <x-meta />
    <x-link />
    <!-- rest of head -->
    </head>
    <!-- rest of body -->
</html>
```

- in the _decorated page_ to provide custom _link tags_ that need to be injected in the generated markup.

```xml
<x-layout x-template="/basic-template.xml">
    <x-doctype x-doctype="&lt;!doctype html&gt;" />
    <x-meta hobbby="programming" />
    <x-meta skylight="azure" />
    <x-link hre="css/reset.css" rel="stylesheet" type="text/css" />
    <x-link hre="css/style.css" rel="stylesheet" type="text/css" />
    <!-- ret of page -->
</x-layout>
```

### &lt;x-script /&gt;

in the same way as the _meta tags_, this tag is also used in two places:
- in the _layout page_ to provide positional point to insert _script tags_

```xml
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <x-meta />
        <x-link />
        <x-title x-slot="title">Default Title</x-title>
        <x-script />
    </head>
    <body>
        <x-main x-slot="content"></x-main>
    </body>
</html>
```

- in the _decorated page_ to provide custom _script tags_ that need to be injected in the generated markup.

```xml
<x-layout x-template="/basic-template.xml">
    <x-doctype x-doctype="&lt;!doctype html&gt;" />
    <x-meta hobbby="programming" />
    <x-meta skylight="azure" />
    <x-link hre="css/reset.css" rel="stylesheet" type="text/css" />
    <x-link hre="css/style.css" rel="stylesheet" type="text/css" />
    <x-script src="/js/hot-sauce.js" defer="true"></x-script>
    <x-script src="/js/sweet-sauce.js" defer="true"></x-script>

    <x-title x-named="title" x-text="page.title">The good title</x-title>

    <x-div id="todo-list" x-named="content">I'm here</x-div>
</x-layout>
```

