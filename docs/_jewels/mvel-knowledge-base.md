## MVEL Projection

This section will contain a collection of MVEL references and tips that would be very handy to have rapid access to.

```@{($ in todos if $.done == true).size()}```

This will access each item in the _todos_ collection, assign it a temporary variable _$_, adn then inspect the _.done_ field for _truthiness_, and then count how many matched.

> A good place to see more examples - [MVEL Github](https://github.com/mvel/mvel/tree/master/src/test/java/org/mvel2/tests)