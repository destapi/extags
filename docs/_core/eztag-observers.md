## EzTag Observer/Subscriber Feature

### Backdrop 

On the client side of web applications, it has become popular nowadays to use the single-page application (SPA) architecture. 
To achieve this, a blank page is served to the browser from the backend and then any further rendering is achieved by way of 
some javascript view library or framework, which use data received from json responses.

Now this json data is managed through some client-side state library as well, initially popularized by the flux architecture, 
with other libraries subsequently evolving with the goal of reducing the complexity of client side state management. Some 
frameworks even went as far as baking in themselves state management functionality, like React's _useContext_ feature. 

A particularly interesting pattern for client-state management has been the _signals_ pattern. The goal is to take advantage
of the view library compiler to target DOM updates at the very point where the state is being used. This effectively targets
the re-rendering to where it is needed with clinical precision. It thus avoids the pitfalls that come with dirty checking a
component's state, which frameworks like React perform employ to trigger virtual DOM diffing and subsequent re-rendering.

### Observing data changes

There are three primary ways data can be mutate:

1. Add/Put - this is when a new element is added to a collection or an new entry added to a dictionary
2. Update - this is when an element in a collection or an entry in a dictionary is located and it's value changed
3. Delete - this is when an element in a collection or en entry in a dictionary is located and detached from its parent, 
and subsequently garbage-collected by the _GC_ 

When using _eztags_, it helps to know that there are two hierarchies which one is dealing with and both have the same common 
ancestor, the __JNode__.

1. JElement hierarchy: every page element is a JObject, which is a type of JNode.
2. JContext hierarchy: every data model collection is a JArray and every dictionary type is a JObject, both of which
are also type of JNode

Both bases sub-classes, JObject and JArray, contain a _JObserver_ instance. This is the link that connects the two hierarchies.
The _JObserver_ type gathers _observables_ on one hand and _subscribers_ on the other hand.

_JSubscribe_ describes the type of interest (add- update, remove) a node is interested in. _JReceiver_ is where custom behavior
for events generated following data changes is implemented.

### Subscribing

_EzTags_ remove the need to explicitly subscribe a _JElement's_ interest in _data change_ events happening in the _JContext_ 
object by targeting several places where it makes sense to implicitly add a subscription.

1. x-text: this controls the _text content_ of the element
   - _add_ makes no sense, so there's no need for registering interest in it
   - _update_ is necessary to replace the text content of a node in the DOM
   - _delete_ is necessary to remove the text content of an element in the DOM
2. x-eval: everything that applies to _x-text_ applies here as well
3. x-show: this controls the presence or visibility of an element
   - _add_ makes no sense, so there's no need for registering interest in it
   - _update_ is necessary to toggle visibility of a DOM element
   - _delete_ makes no sense here as well
4. x-if: everything that applies to _x-text_ applies here as well
5. x-items: this controls a loop which renders a particular number of elements
    - _add_ is necessary to insert a new element into the DOM
    - _update_ is necessary to replace an element in the DOM
    - _delete_ is necessary to remove an element from the DOM

