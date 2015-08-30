templar
=======
A lightweight java templating engine - and by lightweight we mean a small number of lines of code... (that weights in around 90k):

```
 
 
 Line number report (NumberTextReporter)
 =======================================
   File type     #    Code(      %)    Comment(      %)    Blank(      %)    Total(      %)  
 -----------  ----  ---------------  ------------------  ----------------  ----------------  
       .java    72    4077( 79.40%)        336(  6.54%)      722( 14.06%)     5135(100.00%)  
 -----------  ----  ---------------  ------------------  ----------------  ----------------  
     1 types    72    4077( 79.40%)        336(  6.54%)      722( 14.06%)     5135(100.00%)  
 ===========  ====  ===============  ==================  ================  ================  
 
 Line number report (CumulativeBarTextReporter)
 ==============================================
   File type  
 -----------  0                   25                  50                   75                  100
              +----------------------------------------------------------------+-----+-----------+
       .java  |################################################################|:::::|           |
              +----------------------------------------------------------------+-----+-----------+
 
 Key:
 ----
   '#' code
   ':' comment
   ' ' blank
  
```


## how fast is it??

No idea! - I guess the answer is 'fast-enough' depending on what you want to do with it.

We use it as the basis of our code generation tool ```h2zero``` which has now been [open-sourced](https://github.com/synapticloop/h2zero)

## To Build

```ant dist```

This will build and run all of the tests, leaving you with a distributable located: here -> ```dist/templar.jar```

## To Use:

This example was taken from [sab-spot-comment](http://synapticloop.github.io/sab-spot-comment/):

In a very small nutshell

  1. Create a new templarContext
  1. Add stuff to it
  1. Create a parser (and pass in the templar file)
  1. parser.render(templarContext)

```
TemplarContext templarContext = new TemplarContext();

templarContext.add("setupManager", SetupManager.INSTANCE);

templarContext.add("dummySuccessComment", new Download("Dummy Success Download", "http://www.example.com/", "SAB_NZB_ID", "", System.currentTimeMillis(), "GUID", false).getComment());
templarContext.add("dummyFailedComment", new Download("Dummy Failed Download", "http://www.example.com/", "SAB_NZB_ID", "Unpacking failed, archive requires a password", System.currentTimeMillis(), "GUID", false).getComment());
templarContext.add("dummyIgnoredDownload", new Download("Dummy Ignored Download", "http://www.example.com/", "SAB_NZB_ID", "Unpacking failed, archive requires a password", System.currentTimeMillis(), "GUID", true));

try {
	Parser parser = new Parser(this.getClass().getResourceAsStream("/synapticloop/ssc/template/admin.templar"));
	return(HttpUtils.okResponse(NanoHTTPD.MIME_HTML, parser.render(templarContext)));
} catch (ParseException pex) {
	pex.printStackTrace();
	return(HttpUtils.internalServerErrorResponse());
} catch (RenderException rex) {
	rex.printStackTrace();
	return(HttpUtils.internalServerErrorResponse());
}
```

## What are the templating commands?

This is a very brief overview:

All commands start with a ```{``` and end with ```}```.

If you want an actual ```{``` character then you will need to do ```{{```.

here goes:

  1. ```{-- some comment here }``` a comment - can be multilined, but the comment cannot contain a ```}``` character - this designates the end of the comment
  1. ```{if ...}``` do something ```{else}``` do something else ```{endif}``` the else token is not necessary
  1. ```{import some/file/here}``` a nice little inclusion method - can also be ```{import classpath:/some/classpath/file/here}``` note that it is from the current working directory and is not relative... (will maybe add this)
  1. ```{set __something__ as __somethingElse}``` ```something``` is the thing to be evaluated and ```somethingElse``` is the variable name to be placed in the context.  If you want to set a string as a variable then you need to quote it with either a single (') or double (") quotation mark.
  1. ```{loop __something__ as __somethingElse__}``` loop over stuff placing a variable named ```somethingElse``` in each iteration - don't forget the ```{endloop}``` token
  1. ```{dumpcontext}``` dump all of the key-value pairs in the context - useful for debugging.
  1. ```{dumpfunctions}``` dump all of the functions and their number of arguments - useful for debugging.
  1. ```{nl}``` or ```{\n}``` a new line character
  1. ```{tab}``` or ```{\t}``` a tab character

and that should be about it.

for the ```{if}``` and ```{set}``` commands can also take functions - or you can just print out the value of the function itself a la ```{fn:length[array]}```

The functions are as follows:

```

// null operators
functionMap.put("null", new FunctionIsNull()); // test whether the passed in parameter is null
functionMap.put("notNull", new FunctionIsNotNull()); // test whether the passed in parameter is not null
functionMap.put("!Null", new FunctionIsNotNull()); // test whether the passed in parameter is not null
functionMap.put("!null", new FunctionIsNotNull());// test whether the passed in parameter is not null

// boolean function operators
functionMap.put("=", new FunctionEqual()); // test whether the passed in parameters are equal
functionMap.put("equal", new FunctionEqual()); // test whether the passed in parameters are equal
functionMap.put("<>", new FunctionNotEqual()); // test whether the passed in parameters are not equal
functionMap.put("not=", new FunctionNotEqual()); // test whether the passed in parameters are not equal
functionMap.put("!=", new FunctionNotEqual()); // test whether the passed in parameters are not equal
functionMap.put("notEqual", new FunctionNotEqual()); // test whether the passed in parameters are not equal
functionMap.put(">", new FunctionGreaterThan()); // test whether the the first parameter is greater than the second
functionMap.put("gt", new FunctionGreaterThan()); // test whether the the first parameter is greater than the second
functionMap.put(">=", new FunctionGreaterThanEqual()); // test whether the the first parameter is greater than or equal to the second
functionMap.put("gte", new FunctionGreaterThanEqual());  // test whether the the first parameter is greater than or equal to the second
functionMap.put("<", new FunctionLessThan());  // test whether the the first parameter is less than the second
functionMap.put("lt", new FunctionLessThan());  // test whether the the first parameter is less than the second
functionMap.put("<=", new FunctionLessThanEqual());  // test whether the the first parameter is less than or equal to the second
functionMap.put("lte", new FunctionLessThanEqual());  // test whether the the first parameter is less than or equal to than the second

// size operators
functionMap.put("length", new FunctionLength()); // return the length/size of the passed in parameter
functionMap.put("size", new FunctionSize()); // return the length/size of the passed in parameter

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
```

See [src/main/java/synapticloop/templar/function](https://github.com/synapticloop/templar/tree/master/src/main/java/synapticloop/templar/function) for all of the in-built functions and [TemplarContext.java](https://github.com/synapticloop/templar/blob/master/src/main/java/synapticloop/templar/utils/TemplarContext.java) for all of the registrations and aliases.


## What's with the whitespace (tabs and newlines)?

Sometimes it matters, sometimes it doesn't - so by default we don't output tabs and newlines unless explicitly asked to (i.e. ```{\n}``` or ```{\t}```)

you can change this behaviour by setting it in the templar context object.

```
  TemplarConfiguration templarConfiguration = new TemplarConfiguration();
  templarConfiguration.setExplicitNewLines(false);
  templarConfiguration.setExplicitTabs(false)
  TemplarContext templarContext = new TemplarContext(templarConfiguration);
```