# CSIRO SVG Toolkit

The rest of this readme will just get the thing running.


What you will need
------------------

* JDK
* The Xerces parser (xml.apache.org) - included in distribution
* The SteadyState CSS2 Parser (www.steadystate.com) - included in distribution
* The Rhino Javascript engine (www.mozilla.org/rhino) - included in distribution

How to run it
-------------

Windows people can just run the "view.bat" batch file.
Have a look at the batch file to see what is needed
in your classpath.

Or if your classpath is already set properly:

   java org.csiro.svg.viewer.Viewer filename.svg

or 

   java org.csiro.svg.viewer.Viewer

and then open a file (hit the browse button)

Scripting
---------

This release includes some support for embedded javascript
in SVG files. Have a look at samples/scripting and
samples/tour/script*. Highlights include:

samples/scripting/adobe_animation.svg 
> the Adobe animated wave

samples/tour/script3.svg 
> a simple attempt at Powerpoint like transitions. Includes
> animated text (with transform), text that slowly becomes
> visible (through style:opacity) and animated gradient patterns.

samples/tour/script4.svg 
> allows you to draw on the canvas, inserting
> elements into the SVG DOM. Make sure mouse is in pointer mode 
> (the pointing finger), choose your element type (circle or line)
> and click in the rectangle

Note about scripting.. at the moment it takes a fair bit of
processing time (you'll see how much!). This is only the first
release, so consider the scripting pre-beta software.

