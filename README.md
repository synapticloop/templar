templar
=======
A lightweight java templating engine

Munin plugins for various things which may or may not be handy to you

## how fast is it??

No idea! - I guess the answer is 'fast-enough' depending on what you want to do with it.

We use it as the basis of our code generation tool ```h2zero``` which may not be open-sourced yet...

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
  1. ```{set __something__ as __somethingElse}``` ```something``` is the thing to be evaluated and ```somethingElse``` is the variable name to be placed in the context.
  1. ```{loop __something__ as __somethingElse__}``` loop over stuff placing a variable named ```somethingElse``` in each iteration - don't forget the ```{endloop}``` token
  1. ```{dumpcontext}``` dump all of the key-value pairs in the context - useful for debugging.
  1. ```{nl}``` or ```{\n}``` a new line character
  1. ```{tab}``` or ```{\t}``` a tab character

and that should be about it.

for the ```{if}``` and ```{set}``` commands can also take functions - or you can just print out the value of the function itself a la ```{fn:length[array]}```

The functions are as follows:

  1. ```fn:null``` if something is null 
  1. ```fn:notNull``` if something is not null
  1. ```fn:=",``` equal
  1. ```fn:equal``` equal
  1. ```fn:<>",``` not equal
  1. ```fn:not``` not
  1. ```fn:noEqual``` not equal
  1. ```fn:>",``` greater than
  1. ```fn:gt``` greater than
  1. ```fn:>=",``` greater than or equal
  1. ```fn:gte``` as above
  1. ```fn:<",``` less than
  1. ```fn:lt``` as above
  1. ```fn:<=",``` less than or equal
  1. ```fn:lte``` as above
  1. ```fn:length``` length of an obect
  1. ```fn:size``` same as above - I can never remember which one I should use
  1. ```fn:fmtDate``` format a date

They can be used inline.

## What's with the whitespace (tabs and newlines)?

Sometimes it matters, sometimes it doesn't - so by default we don't output tabs and newlines unless explicitly asked to (i.e. ```{\n}``` or ```{\t}```)

you can change this behaviour by setting it in the templar context object.

```
  TemplarConfiguration templarConfiguration = new TemplarConfiguration();
  templarConfiguration.setExplicitNewLines(false);
  templarConfiguration.setExplicitTabs(false)
  TemplarContext templarContext = new TemplarContext(templarConfiguration);
```