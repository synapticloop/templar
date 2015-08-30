templar
=======

(Documentation now lives here: [http://synapticloop.github.io/templar/](http://synapticloop.github.io/templar/))

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


## What's with the whitespace (tabs and newlines)?

Sometimes it matters, sometimes it doesn't - so by default we don't output tabs and newlines unless explicitly asked to (i.e. ```{\n}``` or ```{\t}```)

you can change this behaviour by setting it in the templar context object.

```
  TemplarConfiguration templarConfiguration = new TemplarConfiguration();
  templarConfiguration.setExplicitNewLines(false);
  templarConfiguration.setExplicitTabs(false)
  TemplarContext templarContext = new TemplarContext(templarConfiguration);
```