
(Documentation now lives here: [http://synapticloop.github.io/templar/](http://synapticloop.github.io/templar/))

A lightweight java templating engine - and by lightweight we mean a small number 
of lines of code... (that weights in around 90k):

## how fast is it??

No idea! - I guess the answer is 'fast-enough' depending on what you want to do 
with it.

We use it as the basis of our code generation tool ```h2zero``` which has now 
been [open-sourced](https://github.com/synapticloop/h2zero)


## What's with the whitespace (tabs and newlines)?

Sometimes it matters, sometimes it doesn't - so by default we don't output tabs 
and newlines unless explicitly asked to (i.e. ```{\n}``` or ```{\t}```)

All whitespace is stripped from the beginning (yet **NOT** the end) of a line 
should you wish to have whitespace at the start of the line, you will need to 
use either the pre (`{pre   `) syntax or the escape (`{\   `) syntax.

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
