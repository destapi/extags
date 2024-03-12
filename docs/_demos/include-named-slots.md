## EzTag - &lt;include&gt; also being a named slot

In addition to applying an _x-named_ attribute to components inside the _&lt;layout&gt;_, it is also possible to extract these components into
their own standalone files, and then importing any one of them into any _&lt;layout&gt;_ component using the  _&lt;include&gt;_ tag.

> Layout for pages (phase2/basic-template-wrapper.xml)

```xml
<body>
    <x-header x-slot="header"/>
    <x-main x-slot="content"/>
    <x-footer x-slot="footer"/>
</body>
```

This can be used to decorate two different pages

> Scenario 1: Target page not using any  _&lt;include&gt;_ tag

```xml
<x-layout x-template="/phase2/basic-template-wrapper.xml">
    <x-section id="header-slot" x-named="header">
        <div>Preferred header content</div>
    </x-section>
    <x-article id="content-slot" x-named="content">
        <div>Preferred content content</div>
    </x-article>
    <x-section id="footer-slot" x-named="footer">
        <div>Preferred footer content</div>
    </x-section>
</x-layout>
```

The _#header-slot, #content-slot and #footer-slot_ can be extracted into their own files and therefore become reusable in other parts of the application.

> header section

```xml
<x-section id="header-slot">
    <div>Preferred header content</div>
</x-section>
```

> content section

```xml
<x-article id="content-slot">
    <div>Preferred content content</div>
</x-article>
```

> footer section

```xml
<x-section id="footer-slot">
    <div>Preferred footer content</div>
</x-section>
```

> Scenario 2: Target page using  _&lt;include&gt;_ tags

```xml
<x-layout x-template="/phase2/basic-template-wrapper.xml">
    <x-include x-path="/phase2/basic-header-section.xml" x-named="header" />
    <x-article id="content-slot" x-named="content"><div>Preferred content content</div></x-article>
    <x-include x-path="/phase2/basic-footer-section.xml" x-named="footer" />
</x-layout>
```

In this manner, the unnecessary repetition of page fragments is effectively eliminated.