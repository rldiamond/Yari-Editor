## Yari Editor

[![Build Status](https://travis-ci.org/rldiamond/yariEditor.svg?branch=master)](https://travis-ci.org/rldiamond/yariEditor)

Yari Editor is an tool to create and edit decision table XML files for the Yari rules engine. Yari Editor assists by providing users simple and familiar methods to manipulate data, while ensuring that the data entered will correctly run through the engine.

Yari Editor provides additional tools such as Java code template generation for matching ‘’’Rule’’’ objects, printing of decision table data in an easy to understand format, and more.

[View our wiki for guides and documentation.](../../wiki)

### Build

Yari Editor is made entirely in JavaFX and Java 10 utilizing Maven for build and dependency management.

You can build Yari Editor by navigating to the project directory and using:

```
mvn clean install
```

**Latest Supported Version of Yari-Core:** `0.3.1-SNAPSHOT`

### New Features in 2.0

- All-new modern UI/UX
  - Splash screen: get started more quickly by utilizing the new recommended files feature, or choosing to open or create a new decision table.
  - Toast messages: instantly see important information such as validation messages and file save status.
  - Validation indicator: quickly see the current validation status of the decision table at a glance.
- Asynchronous tasks
  - Continuous data validation on any edit.
  - Many tasks have been designed to run asynchronously to provide a much smoother user experience.
- Editing enhancements
  - Pressing TAB while editing cells will commit the current cell and select the next cell to the right.
  - Pressing ESC will cancel edit and deselect the cell.
  - Pressing ENTER or unfocusing the cell will commit the edit.
- Print decision table
- Keyboard shortcuts
  - For a list of currently supported keyboard shortcuts, [visit the wiki](../../wiki#section-3-keyboard-shortcuts).
- Improved validation
  - Additional (useful) detail is provided on validation errors.
  - Validation log can be utilized to see all validation errors.
    - Double-clicking on any validation error will bring the user to the proper tool to correct the issue.
