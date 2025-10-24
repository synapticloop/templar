<img src="project-logo.png" alt="Description" width="300" style="display:block; margin:auto;">

# templar

> Templar templating engine



(Documentation now lives here: [https://synapticloop.github.io/templar/](http://synapticloop.github.io/templar/))

A lightweight java templating engine - and by lightweight we mean a small number 
of lines of code... (that weights in around 90k):

## how fast is it??

No idea! - I guess the answer is 'fast-enough' depending on what you want to do 
with it.

We use it as the basis of our code generation tool ```h2zero``` which has now 
been [open-sourced](https://github.com/synapticloop/h2zero).  

Code generation is done on an ad-hoc basis, so the speed is not sufficiently 
important.  If you are looking to generate millions of requests per second, 
this is probably not for you.

No parsing/rendering speed testing or comparisons have been done, and will 
not be done.


## What's with the whitespace (tabs and newlines)?

Sometimes it matters, sometimes it doesn't - so by default we don't output tabs 
and newlines unless explicitly asked to (i.e. ```{\\n}``` or ```{\\t}```)

All whitespace is stripped from the beginning (yet **NOT** the end) of a line 
should you wish to have whitespace at the start of the line, you will need to 
use either the pre (`{pre   `) syntax or the escape (`{\\   `) syntax.

You may change this behaviour by creating a `TemplarConfiguration` object and 
setting it in the templar context object.

```
  TemplarConfiguration templarConfiguration = new TemplarConfiguration();
  templarConfiguration.setExplicitNewLines(false);
  templarConfiguration.setExplicitTabs(false)
  TemplarContext templarContext = new TemplarContext(templarConfiguration);
```

## Conversion of existing text

The file `quick-convert.html` at the root of the project is an all-in-one html 
file which will allow you to convert your existing text into templar formatted 
text.


# In-Build functions

Templar consists of a lot of in-built functions which will always exist in 
the `TemplarContext`:

```
// null operators
functionMap.put("null", new FunctionIsNull()); // test whether the passed in parameter is null
functionMap.put("notNull", new FunctionIsNotNull()); // test whether the passed in parameter is not null

// boolean function operators
functionMap.put("=", new FunctionEqual()); // test whether the passed in parameters are equal
functionMap.put("<>", new FunctionNotEqual()); // test whether the passed in parameters are not equal
functionMap.put(">", new FunctionGreaterThan()); // test whether the the first parameter is greater than the second
functionMap.put(">=", new FunctionGreaterThanEqual()); // test whether the the first parameter is greater than or equal to the second
functionMap.put("<", new FunctionLessThan());  // test whether the the first parameter is less than the second
functionMap.put("<=", new FunctionLessThanEqual());  // test whether the the first parameter is less than or equal to the second

// size operators
functionMap.put("length", new FunctionLength()); // return the length/size of the passed in parameter

// date operators
functionMap.put("fmtDate", new FunctionFormatDate()); // format the date with the two parameters date, and format as a string

// boolean test operators
functionMap.put("false", new FunctionFalse()); // test whether the parameter is false
functionMap.put("true", new FunctionTrue()); // test whether the parameter is true

// logical operators
functionMap.put("and", new FunctionAnd()); // logical AND function for the two parameters
functionMap.put("&", new FunctionAnd()); // logical AND function for the two parameters
functionMap.put("or", new FunctionOr()); // logical OR function for the two parameters
functionMap.put("|", new FunctionOr()); // logical OR function for the two parameters

// mathematical operators
functionMap.put("+", new FunctionAdd()); // Mathematical ADDITION of two numbers
functionMap.put("-", new FunctionSubtract()); // Mathematical SUBTRACTION of two numbers
functionMap.put("*", new FunctionMultiply()); // Mathematical MULTIPLICATION of two numbers
functionMap.put("/", new FunctionDivide()); // Mathematical DIVISION of two numbers
functionMap.put("^", new FunctionPower()); // Mathematical EXPONENT of two numbers
functionMap.put("%", new FunctionModulus()); // Mathematical MODULUS of two numbers

// even and odd
functionMap.put("even", new FunctionEven()); // Test whether the passed in number is even
functionMap.put("odd", new FunctionOdd()); // Test whether the passed in number is odd

// utilties
functionMap.put("instanceOf", new FunctionInstanceOf()); // Test whether it is an instance of

// string
functionMap.put("indexOf", new FunctionIndexOf()); // get the index of strings
functionMap.put("toJson", new FunctionToJson()); // convert a string into a JSON object
functionMap.put("startsWith", new FunctionStartsWith()); // determine whether a string starts with another string
functionMap.put("substring", new FunctionSubString()); // return a substring of a string
functionMap.put("uppercase", new FunctionUppercase()); // return the uppercase of the string
functionMap.put("lowercase", new FunctionLowercase()); // return the lowercase of the string
functionMap.put("pluralise", new FunctionPluralise()); // return the lowercase of the string
```

And some aliases for the above

```
functionAliasMap.put("isnull", "null");
functionAliasMap.put("isNull", "null");

functionAliasMap.put("!Null", "notNull");
functionAliasMap.put("!null", "notNull");
functionAliasMap.put("isnotnull", "notNull");
functionAliasMap.put("isNotNull", "notNull");
functionAliasMap.put("notnull", "notNull");

functionAliasMap.put("equal", "=");
functionAliasMap.put("eq", "=");

functionAliasMap.put("not=", "<>");
functionAliasMap.put("ne", "<>");
functionAliasMap.put("notEqual", "<>");

functionAliasMap.put("gt", ">");
functionAliasMap.put("gt=", ">=");
functionAliasMap.put("lt", "<");
functionAliasMap.put("lte", "<=");

functionAliasMap.put("size", "length");

functionAliasMap.put("lowerCase", "lowercase");
functionAliasMap.put("upperCase", "uppercase");
functionAliasMap.put("pluralize", "pluralise");
```
