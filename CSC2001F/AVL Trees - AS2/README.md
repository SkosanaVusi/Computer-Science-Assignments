CSC2001F 2021 Assignment 2
Instructions
The goal of this assignment is to test the performance of the AVL Tree, using a real-world application to check if a student is on a pre-approved list for access to campus during the lockdown.

# Dataset
The dataset to be used is the same as in Assignment 1.

# Part 1: Program
Create an application as described below to store and retrieve data using the AVL data structures.

## AccessAVLApp

Write an application AccessAVLApp to read in the attached text file (oklist.txt) and store the data items within an AVL Tree.

Your AVL Tree implementation can be created from scratch or re-used from anywhere.  You may use the sample code provided.  You may NOT replace the AVL Tree with a different data structure.

Include the following methods in your code:

printStudent (studentID) -  to print out the student name for the first matching student ID; or "Access denied!" if there is no match.
printAllStudents () - to print out the student numbers and names in any order. 
You should be able to invoke your application using commands such as

java AccessAVLApp "MNGREA015"

to print out the name for this student, thereby confirming that they have access.

java AccessAVLApp

to print all student details.  You may use quotes in your parameters or not - it is up to you.

Test your application with 3 known parameters that work, 3 invalid parameters and without any parameters.  Use output redirection in Unix to save the output in each case to different files.

# Part 2: Experiment
Conduct an experiment with AccessAVLApp to compare the performance obtained with the theoretical performance of the algorithms.

## Instrumentation

Add additional code to your programs from Part 1 to discretely count the number of comparison operations (<, >, =) you are performing in the code.  Only count where you are comparing the keys.  This is called instrumentation.  There are 3 basic steps.

First, create a variable/object (e.g., opCount=0) somewhere in your code to track the counter; maybe use an instance variable in the data structure class.

Secondly, wherever there is an operation you want to count, increment the counter (opCount++).  For example:

opCount++;   // instrumentation

if (queryString == theKey)
...

Finally, report the value of the counter before the program terminates.  Maybe add a method to write the value to a file before the program terminates or print it to the screen.  Note that you will probably need 2 variables to count the find and insert operations separately.

## Experiment

Vary the size of the dataset (n) and measure the number of comparison operations in the best/average/worst case for different values of n.  Use 10 values of n that are spaced approximately equally apart (n=500, 1000, ..., 5000).  For each value of n:

Create a subset of n entries from the sample data (if it is not random, randomise the data).
Run the instrumented application for every one of the n parameters corresponding to the subset of the data file.  Store all operation count values.
Determine the minimum (best case), maximum (worst case) and average of these count values.
Use graphs to compare the experimental values obtained with the theoretical complexity analysis for insert and find operations.

It is recommended that you use Python/Java programs to automate this process.

# Report
Write a report (of up to 6 pages) that includes the following:

What your OO design is: what classes you created and how they interact.
What the goal of the experiment is and how you executed the experiment.
What test values you used in the trial runs (Part 1) and what the output was in each case.  Only show the first 5 and last 5 lines for the trial run where you invoke printAllStudents ().
What your final results are (use one or more graphs), showing best, average and worst cases for the application and the theoretical expectation.  Discuss what the results mean.
A statement of what you included in your application(s) that constitutes creativity - how you went beyond the basic requirements of the assignment.
Summary statistics from your use of git to demonstrate usage.  Print out the first 10 lines and last 10 lines from "git log" , with line numbers added.  You can use a Unix command such as:

git log | (ln=0; while read l; do echo $ln\: $l; ln=$((ln+1)); done) | (head -10; echo ...; tail -10)

# Dev requirements
As a software developer, you are required to make appropriate use of the following tools:

- git, for source code management
- javadoc, for documentation generation
- make, for automation of compilation and documentation generation