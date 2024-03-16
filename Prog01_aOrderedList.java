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

public class Prog01_aOrderedList {
    public static void main(String[] args) {
        aOrderedList carList = new aOrderedList();
        try {
            Scanner scanner = getInputFile("Enter input filename: ");
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line[0].equals("A")) {
                    String make = line[1];
                    int year = Integer.parseInt(line[2]);
                    int price = Integer.parseInt(line[3]);
                    carList.add(new Car(make, year, price));
                } else if (line[0].equals("D")) {
                    if (line.length == 4) {
                        String make = line[1];
                        int year = Integer.parseInt(line[2]);
                        carList.delete(make, year);
                    } else if (line.length == 2) {
                        int index = Integer.parseInt(line[1]);
                        carList.delete(index);
                    }
                }
            }
            scanner.close();

            PrintWriter writer = getOutputFile("Enter output filename: ");
            writer.println("Number of cars: " + carList.size());
            for (int i = 0; i < carList.size(); i++) {
                Car car = (Car) carList.get(i);
                writer.println(car.toString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public static Scanner getInputFile(String userPrompt) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String filename;
        do {
            System.out.print(userPrompt);
            filename = scanner.nextLine();
            File file = new File(filename);
            if (file.exists())
                return new Scanner(file);
            else {
                System.out.println("File specified <" + filename + "> does not exist. Would you like to continue? <Y/N>");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("N"))
                    throw new FileNotFoundException("User canceled program execution.");
            }
        } while (true);
    }

    public static PrintWriter getOutputFile(String userPrompt) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String filename;
        do {
            System.out.print(userPrompt);
            filename = scanner.nextLine();
            File file = new File(filename);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    return new PrintWriter(file);
                } catch (Exception e) {
                    System.err.println("Error creating file: " + e.getMessage());
                }
            } else {
                System.out.println("File already exists. Would you like to continue? <Y/N>");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("N"))
                    throw new FileNotFoundException("User canceled program execution.");
            }
        } while (true);
    }
}

class Car implements Comparable<Car> {
    private String make;
    private int year;
    private int price;

    public Car(String make, int year, int price) {
        this.make = make;
        this.year = year;
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int compareTo(Car other) {
        if (!make.equals(other.make))
            return make.compareTo(other.make);
        else
            return Integer.compare(year, other.year);
    }

    @Override
    public String toString() {
        return "Make: " + make + ", Year: " + year + ", Price: $" + price;
    }
}

class aOrderedList {
    private static final int SIZEINCREMENTS = 20;
    private Comparable[] oList;
    private int listSize;
    private int numObjects;

    public aOrderedList() {
        numObjects = 0;
        listSize = SIZEINCREMENTS;
        oList = new Comparable[SIZEINCREMENTS];
    }

    public void add(Comparable newObject) {
        if (numObjects == listSize)
            resize();
        int i;
        for (i = numObjects - 1; (i >= 0 && oList[i].compareTo(newObject) > 0); i--) {
            oList[i + 1] = oList[i];
        }
        oList[i + 1] = newObject;
        numObjects++;
    }

    public void delete(String make, int year) {
        for (int i = 0; i < numObjects; i++) {
            Car car = (Car) oList[i];
            if (car.getMake().equals(make) && car.getYear() == year) {
                for (int j = i; j < numObjects - 1; j++) {
                    oList[j] = oList[j + 1];
                }
                numObjects--;
                break;
            }
        }
    }

    public void delete(int index) {
        if (index >= 0 && index < numObjects) {
            for (int i = index; i < numObjects - 1; i++) {
                oList[i] = oList[i + 1];
            }
            numObjects--;
        }
    }

    private void resize() {
        Comparable[] newList = new Comparable[listSize + SIZEINCREMENTS];
        for (int i = 0; i < listSize; i++) {
            newList[i] = oList[i];
        }
        oList = newList;
        listSize += SIZEINCREMENTS;
    }

    public int size() {
        return numObjects;
    }

    public Comparable get(int index) {
        if (index >= 0 && index < numObjects) {
            return oList[index];
        } else {
            return null;
        }
    }
}