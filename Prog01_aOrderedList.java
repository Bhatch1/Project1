/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prog01_aorderedlist;

//Student Name: Brady Hatcher 
//Student ID:890523778

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
* This class represents a Car object with make, year, and price.
* 
* CSC 1351 Programming Project No 1
* Section 
* 
* @author Brady Hatcher
* @since 3/17/2024
*/

class Car implements Comparable<Car> {
    private String make;
    private int year;
    private int price;

    /**
     * Constructor for Car class.
     * 
     * @param make  the make of the car
     * @param year  the year of the car
     * @param price the price of the car
     */
    
    public Car(String make, int year, int price) {
        this.make = make;
        this.year = year;
        this.price = price;
    }

    // Getter methods for Car class

    /**
     * Returns the make of the car.
     * 
     * @return the make of the car
     */
    
    public String getMake() {
        return make;
    }

    /**
     * Returns the year of the car.
     * 
     * @return the year of the car
     */
    
    public int getYear() {
        return year;
    }

    /**
     * Returns the price of the car.
     * 
     * @return the price of the car
     */
    
    public int getPrice() {
        return price;
    }

    // CompareTo method to compare Car objects
    @Override
    public int compareTo(Car other) {
        if (!make.equals(other.make))
            return make.compareTo(other.make);
        else if (year != other.year)
            return Integer.compare(year, other.year);
        else
            return 0;
    }

    // Override toString method for Car class
    @Override
    public String toString() {
        return "Make: " + make + ", Year: " + year + ", Price: $" + price;
    }
}

/**
 * This class represents an ordered list of Car objects.
 * 
 * CSC 1351 Programming Project No 1
 * Section 
 * 
 * @author Brady Hatcher
 * @since 3/17/2024
 */

class aOrderedList {
    private final int SIZE_INCREMENTS = 20;
    private Car[] oList;
    private int listSize;
    private int numObjects;

    /**
     * Constructor for aOrderedList class.
     */
    
    public aOrderedList() {
        numObjects = 0;
        listSize = SIZE_INCREMENTS;
        oList = new Car[listSize];
    }

    /**
     * Adds a Car object to the sorted array in the correct position to maintain sorted order.
     * 
     * @param newCar the Car object to add
     */
    
    public void add(Car newCar) {
        if (numObjects == listSize)
            increaseSize();
        
        int i = numObjects - 1;
        while (i >= 0 && oList[i].compareTo(newCar) > 0) {
            oList[i + 1] = oList[i];
            i--;
        }
        oList[i + 1] = newCar;
        numObjects++;
    }

    // Method to increase the size of the ordered list
    private void increaseSize() {
        listSize += SIZE_INCREMENTS;
        Car[] newList = new Car[listSize];
        System.arraycopy(oList, 0, newList, 0, numObjects);
        oList = newList;
    }

    // Override toString method for aOrderedList class
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < numObjects; i++) {
            result.append(oList[i]);
            if (i < numObjects - 1)
                result.append(", ");
        }
        result.append("]");
        return result.toString();
    }

    /**
     * Returns the number of elements in this list.
     * 
     * @return the number of elements in this list
     */
    
    public int size() {
        return numObjects;
    }

    /**
     * Returns the element at the specified position in this list.
     * 
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     */
    
    public Car get(int index) {
        return oList[index];
    }

    /**
     * Returns true if the array is empty and false otherwise.
     * 
     * @return true if the array is empty and false otherwise
     */
    
    public boolean isEmpty() {
        return numObjects == 0;
    }

    /**
     * Removes the element at the specified position in this list.
     * 
     * @param index the index of the element to remove
     */
    
    public void remove(int index) {
        for (int i = index; i < numObjects - 1; i++) {
            oList[i] = oList[i + 1];
        }
        numObjects--;
    }
}

/**
 * This class contains the main method to run the program.
 * 
 * CSC 1351 Programming Project No 1
 * Section 
 * 
 * @author Brady Hatcher
 * @since 3/17/2024
 */

public class Prog01_aOrderedList {
    /**
     * Main method to run the program.
     * 
     * @param args command-line arguments
     */
    
    public static void main(String[] args) {
        Scanner scanner = getInputFile("Enter input filename: ");
        aOrderedList orderedList = new aOrderedList();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split(",");
            if (tokens.length > 0) {
                String operation = tokens[0];
                switch (operation) {
                    case "A":
                        if (tokens.length == 4) {
                            String make = tokens[1];
                            int year = Integer.parseInt(tokens[2]);
                            int price = Integer.parseInt(tokens[3]);
                            Car newCar = new Car(make, year, price);
                            orderedList.add(newCar);
                        }
                        break;
                    case "D":
                        if (tokens.length == 4) {
                            String makeToDelete = tokens[1];
                            int yearToDelete = Integer.parseInt(tokens[2]);
                            int indexToDelete = findIndexToDelete(orderedList, makeToDelete, yearToDelete);
                            if (indexToDelete != -1)
                                orderedList.remove(indexToDelete);
                        }
                        break;
                }
            }
        }
        scanner.close();

        PrintWriter writer = null;
        try {
            writer = getOutputFile("Enter output filename: ");
            writer.println("Number of cars: " + orderedList.size());
            for (int i = 0; i < orderedList.size(); i++) {
                Car car = orderedList.get(i);
                writer.println(car);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     * Prompts the user for the input filename and returns a Scanner object for reading the file.
     * 
     * @param userPrompt the prompt to display to the user
     * @return a Scanner object for reading the input file
     */
    
    private static Scanner getInputFile(String userPrompt) {
        Scanner scanner = new Scanner(System.in);
        File file = null;
        do {
            System.out.print(userPrompt);
            String filename = scanner.nextLine();
            file = new File(filename);
            if (!file.exists()) {
                System.out.println("File specified <" + filename + "> does not exist. Would you like to continue? <Y/N> ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("Y")) {
                    scanner.close();
                    System.exit(0);
                }
            }
        } while (!file.exists());
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Prompts the user for the output filename and returns a PrintWriter object for writing to the file.
     * 
     * @param userPrompt the prompt to display to the user
     * @return a PrintWriter object for writing to the output file
     * @throws FileNotFoundException if the specified file is not found
     */
    
    private static PrintWriter getOutputFile(String userPrompt) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        PrintWriter writer = null;
        boolean validFile = false;
        do {
            System.out.print(userPrompt);
            String filename = scanner.nextLine();
            File file = new File(filename);
            try {
                writer = new PrintWriter(file);
                validFile = true;
            } catch (FileNotFoundException e) {
                System.out.println("Invalid filename. Would you like to continue? <Y/N> ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("Y")) {
                    scanner.close();
                    System.exit(0);
                }
            }
        } while (!validFile);
        return writer;
    }

    /**
     * Finds the index of the Car object to delete.
     * 
     * @param orderedList  the ordered list of Car objects
     * @param makeToDelete the make of the car to delete
     * @param yearToDelete the year of the car to delete
     * @return the index of the Car object to delete, or -1 if not found
     */
    
    private static int findIndexToDelete(aOrderedList orderedList, String makeToDelete, int yearToDelete) {
        for (int i = 0; i < orderedList.size(); i++) {
            Car car = orderedList.get(i);
            if (car.getMake().equals(makeToDelete) && car.getYear() == yearToDelete)
                return i;
        }
        return -1;
    }
}


