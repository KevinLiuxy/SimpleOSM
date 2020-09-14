# SimpleOSM
Minimalist API for Accessing Data in OpenStreetMap Files using Java

<h3>Description</h3>
<p>
This repository offers a .jar library <code>OSMDatabase</code> that allows you to extract geographical data
from OpenStreetMap files easily, within only a few lines of code. All that it needs is a <b>PBF</b> format
OpenStreetMap file, and it will read all OSM Nodes, Ways, and Relations data from the file (or input stream).
</p>

<h3>How to Use It</h3>
<p>
1. Download the OSMDatabase.jar library or the source code, and include it in your project.
</p><p>
2. That's all you need! Now you can read PBF OSM files and get access to all its data.
</p><p>
--> please read <a href="https://github.com/KevinLiuxy/SimpleOSM/blob/master/OSMDatabase_example/Main.java">
this example</a> to see how you can quickly extract data from an OSM file.
</p>

<h3>About the Source Code</h3>
<p>
The source code is set up as an IntelliJ project. It requires the OSM4J-PBF library as its dependency. The
OSMDatabase.jar comes with OSM4J-PBF version 0.1.0 built into it, so you <b>don't</b> need to download any
other dependencies separately when using the library.
</p><p>
However, OSM4J's source code is not included in this repository. Therefore it is required to downloading and
linking it with the source code manually (if IDE doesn't do it automatically) in order to compile the source
code. See how you can <a href="https://jaryard.com/projects/osm4j/">download the latest version of OSM4J</a>.
</p><p>
Note: downloading OSM4J from Maven is required since the OSM4J-PBF dependency listed on its
<a href="https://github.com/topobyte/osm4j">Github Page</a> download section does not seem to work.
</p>

<h3>Copyright Disclaimer</h3>
<p>
This work is a combined library based on the OSM4J libraries developed by <i>sebkur Sebastian</i> and
<i>mojodna Seth Fitzsimmons</i>. The aforementioned software is protected under version 3 of the GNU Lesser
General Public License. You can find the source code, uncombined with any library facilities conveyed under
the terms of LGPLv3 license, <a href="https://github.com/KevinLiuxy/SimpleOSM/tree/master/OSMDatabase">
here</a>. You can also find the accompanying uncombined form of OSM4J, the work which OSMDatabase is based
on, <a href="https://github.com/topobyte/osm4j">through this link</a>.
</p>

<h5>Good Luck, and Have Fun!</h5>
