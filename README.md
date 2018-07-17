# Yari Editor 2

This revamp project started as a learning adventure into what JavaFX can provide the modern world such as extremely simple and fluid UI/UX utilizing material design-like patterns and the latest concepts in UX.

## Description

Yari Editor, in it's simplest form, is an XML editor developed specifically for the creation and editing of XML decision table files utilized by the Yari rules engine.

### New Features:

* All-new modern UI/UX
 * Splash screen: results in less user confusion when first launching the application
 * Toast messages: instantly see important messages such as validation issues and file save status
 * Data validation indicator: quickly see the current validation status of the table at a glance
* Asynchronous tasks
 * Continuous data validation on any edit 
 * Opening and saving files is now performed off FX thread resulting in a much smoother experience
* Editing enhancements
 * Pressing TAB while editing cells will commit the current cell and select the next cell to the right
 * Pressing ESC will cancel the edit and deselect the cell
 * Pressing ENTER or unfocusing the cell will commit the edit
* Keyboard shortcuts
 * CTRL + S to save
 * CTRL + P to print
 * CTRL + O to open
* Printing of table data  

### Usage
To get further assistance using the application, see the wiki guide.

## Project

This project is made entirely in JavaFX and Java 10 utilizing Maven for build and dependency managenent.

The UI/UX of the project is intended to be reminiscent of material design.