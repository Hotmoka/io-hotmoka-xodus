[![Java-Build Action Status](https://github.com/Hotmoka/io-hotmoka-xodus/actions/workflows/java_build.yml/badge.svg)](https://github.com/Hotmoka/io-hotmoka-xodus/actions)
[![Maven Central](https://img.shields.io/maven-central/v/io.hotmoka.xodus/io-hotmoka-xodus.svg?label=Maven%20Central)](https://central.sonatype.com/search?smo=true&q=g:io.hotmoka.xodus)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

# A Java automatic module that adapts some classes of the Xodus database classes.

The [Xodus database](https://github.com/JetBrains/xodus)
is a Kotlin-based database, hence compiled into Java bytecode.
Its jars are unnamed, hence their integration into a modularized Java project is problematic.
This automatic module is the classical adaptor towards modularized Java projects.
Only a few classes of the library have been adapted, namely environments, stores and transactions.

<p align="center"><img width="100" src="https://mirrors.creativecommons.org/presskit/buttons/88x31/png/by.png" alt="This documentation is licensed under a Creative Commons Attribution 4.0 International License"></p><p align="center">This document is licensed under a Creative Commons Attribution 4.0 International License.</p>

<p align="center">Copyright 2024 by Fausto Spoto (fausto.spoto@hotmoka.io)</p>