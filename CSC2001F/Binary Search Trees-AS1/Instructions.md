CSC2001F 2021 Assignment 1
# Instructions
The goal of this assignment is to compare the Binary Search Tree with a traditional unsorted array data structure, both implemented in Java, using a real-world application to check if a student is on a pre-approved list for access to campus during the lockdown.

# Dataset
The attached file (oklist.txt) is a listing of student IDs and student names.

This has been generated as random data that looks like UCT student names but is in fact derived from popular South African names.  There are 5000 entries in this file and the data order has been randomised.

Study the data carefully.  The pre-processing is non-trivial but something you can do yourself as well.

In your application, you MUST write your own code to read in the text file.  Your data structure items must each store 2 values for an entry, such as the ones below.  The key is the student ID.

SHBCAL017
Caleb Shabangu 

# Part 1: Programs
Create 2 applications as described below to store and retrieve data from 2 different data structures.

AccessArrayApp

Write an application AccessArrayApp to read in the attached text file (oklist.txt) and store the data items within a traditional array (a single array of objects).  There are 5000 data items - you may use a fixed-size array or try to determine the size programmatically.  Do not use a LinkedList, ArrayList or other advanced data structure.

Include the following methods in your code:

printStudent (studentID) -  to print out the student name for the first matching student ID; or "Access denied!" if there is no match.
printAllStudents () - to print out the student numbers and names in any order. 
You should be able to invoke your application using commands such as

java AccessArrayApp "MNGREA015"

to print out the name for this student, thereby confirming that they have access.

java AccessArrayApp

to print all student details.  You may use quotes in your parameters or not - it is up to you.

Test your application with 3 known parameters that work, 3 invalid parameters and without any parameters.  Use output redirection in Unix to save the output in each case to different files.

AccessBSTApp

Write an application AccessBSTApp to perform the same tasks, but using a Binary Search Tree (BST) instead of an array.

Your BST implementation can be created from scratch or re-used from anywhere.  You may NOT replace the BST with a different data structure and may not use a balanced tree.

Once again, test your application with 3 known parameters that work, 3 invalid parameters and without any parameters.  Use output redirection in Unix to save the output in each case to different files.

# Part 2: Experiment
Conduct an experiment with AccessArrayApp and AccessBSTApp to demonstrate the speed difference for searching between a BST and a traditional array.

Instrumentation

Add additional code to your programs from Part 1 to discretely count the number of comparison operations (<, >, =) you are performing in the code.  Only count where you are comparing the keys.  This is called instrumentation.  There are 3 basic steps.

First, create a variable/object (e.g., opCount=0) somewhere in your code to track the counter; maybe use an instance variable in the data structure class.

Secondly, wherever there is an operation you want to count, increment the counter (opCount++).  For example:

opCount++;   // instrumentation

if (queryString == theKey)
...

Finally, report the value of the counter before the program terminates.  Maybe add a method to write the value to a file before the program terminates or print it to the screen.

Experiment

Vary the size of the dataset (n) and measure the number of comparison operations in the best/average/worst case for different values of n.  Use 10 values of n that are spaced approximately equally apart (n=500, 1000, ..., 5000).  For each value of n:

Create a subset of n entries from the sample data (preferably use a random subset of lines).
Run both instrumented applications for every one of the n parameters corresponding to the subset of the data file.  Store all operation count values.
Determine the minimum (best case), maximum (worst case) and average of these count values.
It is recommended that you use Unix or Python scripts to automate this process.

# Report
Write a report (of up to 6 pages) that includes the following:

What your OO design is: what classes you created and how they interact.
What the goal of the experiment is and how you executed the experiment.
What test values you used in the trial runs (Part 1) and what the output was in each case.  Only show the first 5 and last 5 lines for the trial run where you invoke printAllStudents ().
What your final results are (use one or more graphs), showing best, average and worst cases for both applications.  Discuss what the results mean.
A statement of what you included in your application(s) that constitutes creativity - how you went beyond the basic requirements of the assignment.
Summary statistics from your use of git to demonstrate usage.  Print out the first 10 lines and last 10 lines from "git log" , with line numbers added.  You can use a Unix command such as:
git log | (ln=0; while read l; do echo $ln\: $l; ln=$((ln+1)); done) | (head -10; echo ...; tail -10)

# Dev requirements
As a software developer, you are required to make appropriate use of the following tools:
- git, for source code management
- javadoc, for documentation generation
- make, for automation of compilation and documentation generation