## EzTag Attributes

### x-if

This tag is used with any __x-__ tag to hint that it should optionally be rendered, based on the evaluation of its
expression value, which should
result in either true or false.

```xml

<x-p id="done" x-text="name">name</x-p>
```

The parser will evaluate the _done_ expression, and if the result is _true_, the component will be rendered in the DOM,
otherwise it will be skipped.

### x-show

This tag is used with any __x-__ tag to hint that it can be hidden by applying a css style using the _
_[data-x-show=<true|false>]__ selector, based on
the evaluation of its expression value, which should result in either true or false.

```xml
<li>
    <x-i x-show="done" class="fa fa-square" title="done"></x-i>
    <x-i x-show="!done" class="fa fa-check-square" title="done"></x-i>
    <x-span x-text="title">title</x-span>
    <i class="fa fa-times-circle" title="remove"></i>
</li>
```

The parser will evaluate the _done_ expression, and depending on the result, the component will have an additional
attribute, __data-x-show__, whose value
is either _true_ or _false_. The decision was made not to add an inline style directly to the element, but to instead
use a css styling selector attribute.
The css below can then be used to toggle the visibility of the resulting component.

```css
[data-x-show=false]{
    display: none;
}
```

### x-items

This tag is used with any __x-__ tag to hint that it will be used in a _for-loop_ to generate markup for multiple
related elements. It can optionally
be accompanied by the __x-key__ attribute which points to the unique field which identifies each object in the iterator.

```xml
<x-ul x-items="items" x-key="id">
  <li>
    <x-i x-if="done" class="fa fa-square-check" title="done"></x-i>
    <x-span x-text="title">title</x-span>
    <i class="fa fa-times-circle" title="remove"></i>
  </li>
</x-ul>
```

### x-key

This is only valid in the same context as __x-items__. It's optional, and its purpose is to inform about the field which
uniquely identified each of
the objects in the iterator. Each child element will have a __data-x-id__ attribute added to it. This value can then be
used by javascript in the
client when figuring out the necessary updates to the generated list of items

```xml

<x-ul x-items="items" x-key="id">...</x-ul>
```

### x-text

This tag should only be used with elements that are intended to display text content only. This tag, however, constrains
the text content to the result
of evaluating its expression value.

```xml

<x-span x-text="page.title">title</x-span>
```

This will result is evaluating the expression _page.title_ from the parser's context. If any text content exists, it
will be overwritten by the evaluation
result.

### x-eval

Very closely related to the __x-text__ tag, this tag is also used with elements that are intended to display text
content only. This tag, however, replaces
the existing text content with the result of evaluating it first. In other words, it treats the existing _text content_
as an expression, whse result it will
use to replace the _expression_.

```xml

<x-p class="fa fa-memo" x-eval="true">My name is @{name}</x-p>
```

### x-path

This is only useful in the context of the __x-include__ tag. It provides the path (constrained by Java's classpath) to
the target file whose content should
replace the __x-include__ tag

```xml
<p class="me">
  <x-include x-path="../another.html" />
</p>
```

### x-slot

This is used to designate positional elements in a layout page, where actual content will be swapped during rendering.
These are important in retaining the
ordering of elements in the final generated markup.

> If the _target page_ does not have markup for the slot, then the existing content in the slot will be used in the
> resulting markup.

```xml
<html lang="en">
    <head>
    <meta charset="UTF-8" />
    <x-title x-slot="title">Default Title</x-title>
    </head>
    <body>
        <x-main x-slot="content"></x-main>
        <x-footer x-slot="footer">
            <div><span class="sticky">&amp;copy; 2024 EzTag</span></div>
        </x-footer>
    </body>
</html>
```

### x-named

This attribute is intended for use with tags only inside _x-layout_ components, and is used to indicate that the __x-__
tag where it has been added, can
be used as a document fragment for __ANY__ named _slot_ in a _layout template document_. If used inside a _x-include_
tag, the _imported_ element can be
used as content for the named slot. Typical usage of the attribute is shown below (assuming it's called
_basic-template.xml_)

```xml
<x-layout x-template="/basic-template.xml">
    <x-title x-named="title" x-text="page.title">The good title</x-title>
    <!-- OR perhaps -->
    <x-include x-path="/another/page/fragment.xml" x-named="another-slot" />

    <x-div id="todo-list" x-named="content">Main content</x-div>
</x-layout>
```

### x-fragment

Sometimes, breaking up a page into slot definitions and putting them in standalone files may sound like a great strategy
because it employs the
__separation of concerns__ principle. In theory, it sounds good indeed, but in practice, it's not all sunshine and
rainbows. It does inadvertently
introduce another kind of coupling which means that it raises the cognitive impedance needed to understand what purpose
each standalone file serves
when isolated from the other collaborating files. One would require intimate knowledge of the files and the part they
play in concert with each
other, which is a kind of tight coupling.

There is another principle that comes into play here, and which perhaps can become more significant under certain
circumstances. This is the
__locality of behavior__ principal. The idea here is that the different concerns which together address a single problem
should be collocated,
which lowers the cognitive impedance needed to understand the solution and also increases the cohesion for that
solution. This is what __fragments__
provides. You can therefore think of _slots_ as enforcing _separation of concerns_, while _fragments_ on the other hand
can be viewed as enforcing
_locality_of_behavior_.

A fragment's content is stored in memory, inside its _parent element_ using its name as a map key. To retrieve the
fragment's content, the parent
element should first be located, so that the fragment can then be obtained from the memory map using its name.

```xml
<x-layout x-template="/phase2/basic-template-wrapper.xml">
    <x-section id="header-slot" x-fragment="header">
        <div>Preferred header content</div>
    </x-section>
    <x-article id="content-slot" x-fragment="content">
        <div>Preferred content content</div>
    </x-article>
    <x-section id="footer-slot" x-fragment="footer">
        <div>Preferred footer content</div>
    </x-section>
</x-layout>
```

### x-template

This is only useful in the context of the __x-layout__ tag. It provides the path (constrained by Java's classpath) to
the target file whose content should
be used to decorate the _targeted page_.

### x-doctype

Since the _<!DOCTYPE html>_ tag is not a valid _xml_ tag, the choice was made to use this tag purely for specifying the
_doctype_ tag content, which is then
inserted into the generated markup when the markup generated begins.

> Note that _x-doctype_ is used as both an _attribute_ and a special _tag_

```xml
<x-layout x-template="/basic-template.xml">
    
    <x-doctype x-doctype="&lt;!doctype html&gt;" />
    <!--...rest of it...-->
</x-layout>
```

## Special cases

### Orb expressions for attributes

Attributes on any __x-__ tag can have an _Orb expression (@{expr})_ as their value, and this will be evaluated and
reassigned to the attribute instead of being
treated simply as a string literal. This feature is only useful for standard _html tag_ attributes, because having this
feature on _x-_ attributes is pointless.
_x-_ attributes are there only to inform the parser and are not recognized by the browser.

```xml

<label>
    <x-input type="checkbox" checked="@{todo.done}"/>
</label>
```

### Attributes having boolean values

In _HTML_, some attributes are boolean attributes, which basically means they can be true or false. They are the case,
or they are not the case. They compute to one,
or they compute to zero. Examples of these attributes include checked, required, disabled and open. When using
expressions to define values for these attributes, make
sure the result is either true or false, and the parser will omit the attribute if the value is false.

A sample generated result may look like this.

```html
<ul id="todo-list" class="listing">
    <li data-x-id="1"><label><input type="checkbox"/></label><span>Read book</span></li>
    <li data-x-id="2"><label><input checked type="checkbox"/></label><span>Make dinner</span></li>
</ul>
```
