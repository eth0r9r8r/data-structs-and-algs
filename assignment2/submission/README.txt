To compile Assign2 program:

  1.Place Assign2.java, Vecta.java, and LinkedList.java into
	the same directory.
	
  2.Ensure no other .java files are in the directory.
  
  3.Open Windows Powershell, or other command-line shell and cd into directory.
  
  4.Type: "javac *.java"
  
To run Assign2 program:

  1.Place .txt file containing words separated only by new line characters into
    the same directory as the Assign2.java and Assign2.class files.
	
  2.Type into the command-line: "java Assign2 exampleIn.txt exampleOut.txt",
    exampleIn.txt should be replaced with the name of the .txt file from 
	step one. exampleOut.txt can be replaced with any filename ending in .txt.
	
The program will create a new .txt file with the second filename specified in
the command line. This file will contain the words from the input .txt file, 
sorted so that words that are anagrams are placed on the same line. Each line
will be alphabetically organised, and the file will alphabetically organised
based on the first word on each line.

Example input:

	race
	place
	kiss
	skis
	ouch
	care
	ssik
	
Example output:

	care race
	kiss skis ssik
	ouch
	place