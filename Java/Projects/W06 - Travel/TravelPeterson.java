/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 *    ASSIGNMENT:    "Travel" - CSCI 0012
 *      DUE DATE:    29 Sep 24
 *    SELF GRADE:    100 points (100%)
 * JUSTIFICATION:    This program meets all the listed requirements of the assignment and the output matches the given
 * example. Additionally, I reduced redundancies by creating methods for repeated code segments. I also fixed an
 * incorrect formula in the given code: for calculating the travel time in an electric car, the number of stops can be
 * changed slightly if you assume that the driver starts out with a fully charged vehicle (the cost remains unchanged
 * though, as the driver will need to charge their car after the trip).
 */

import java.util.*;

/**
 * This code calculates the cost of travel as well as how long it might take to get there based on if an electric
 * or gas-powered car is used.
 */
public class TravelPeterson {
    public static final int WIDTH = 80;

    public static void main(String[] args) {
        // setup
        description();
        Scanner kb = new Scanner(System.in);
        // get initial input
        System.out.print("\nEnter the amount of times you would like to run this program: ");
        int count = kb.nextInt();
        PP.lb(' ');
        PP.lb(WIDTH);
        // run program
        double distance, speed;
        for (int j = 1; j <= count; j++) {
            System.out.print("\nEnter the total number of the miles you will be travelling [mi]: ");
            distance = kb.nextDouble();
            System.out.print("\nEnter the average speed you will be travelling [mph]: ");
            speed = kb.nextDouble();
            PP.lb(' ');
            gTravel(kb, distance, speed);
            PP.lb(WIDTH);
            eTravel(kb, distance, speed);
            PP.lb(WIDTH);
        }
        // exit
        PP.lb(' ');
        PP.pl("~ Enjoy your trip! ~", WIDTH / 2);
        PP.pl(":)", WIDTH / 2);
    }

    /**
     * This method calculates the cost and the time to travel with a gas car.
     */
    public static void gTravel(Scanner kb, double mileage, double speed) {
        PP.pl("TRAVELLING WITH A GAS CAR", WIDTH, '-');
        // get inputs
        System.out.print("\nEnter the average miles per gallon of your vehicle [mpg]: ");
        double mpg = kb.nextDouble();
        System.out.print("\nEnter the current cost of gas per gallon [$]: ");
        double gasPrice = kb.nextDouble();
        // calculate
        double gTime = travelHoursGasCar(mileage, speed);
        double gCost = gasCost(mileage, mpg, gasPrice);
        //print results
        showOutput("gas", gTime, gCost);
    }

    /**
     * This method calculates the number of hours to travel in a gas car.
     */
    public static double travelHoursGasCar(double distance, double speed) {
        return distance / speed;
    }

    /**
     * This method calculates the total amount of money spent on gas for the trip.
     */
    public static double gasCost(double distance, double milesPerGallon, double costPerGallon) {
        return costPerGallon * (distance / milesPerGallon);
    }

    /**
     * This method calculates the cost and the time to travel with an electric car.
     */
    public static void eTravel(Scanner kb, double mileage, double speed) {
        PP.pl("TRAVELLING WITH AN ELECTRIC CAR", WIDTH, '-');
        // get inputs
        System.out.print("\nEnter the average miles your vehicle can drive on a full charge [mi]: ");
        double range = kb.nextDouble();
        System.out.print("\nEnter how long it takes your vehicle fully charge [hr]: ");
        double timeToFull = kb.nextDouble();
        System.out.print("\nEnter the current cost of a full charge [$]: ");
        double chargePrice = kb.nextDouble();
        // calculate
        int numStops = stops(mileage, range);
        double eTime = travelHoursWithElectricCar(numStops, timeToFull, mileage, speed);
        double eCost = chargeCost(numStops, chargePrice);
        // print results
        showOutput("electric", eTime, eCost);
    }

    /**
     * This method calculates the number of stops needed to charge the battery.
     * milePerCharge: the number of the miles driven from a fully charged battery
     */
    public static int stops(double distance, double milePerCharge) {
        return (int) Math.floor(distance / milePerCharge);
    }

    /**
     * This method calculates the number of hours to travel in an electric car.
     * stops:        the number of the times the car needs to be charged
     * hoursPerStop: time it takes to fully charge your car
     * distance:     the total distance travelled
     * speed:        speed of the car over the course of the trip
     */
    public static double travelHoursWithElectricCar(int stops, double hoursPerStop, double distance, double speed) {
        return (distance / speed) + (stops * hoursPerStop);
    }

    /**
     * This method calculates the total cost of charging the battery.
     * stops: the number of the charges
     */
    public static double chargeCost(int stops, double pricePerCharge) {
        return pricePerCharge * stops;
    }

    /**
     * This method is a printout that describes what is happening in this program.
     */
    public static void showOutput(String carType, double time, double cost) {
        PP.lb(' ');
        PP.pl("v".repeat(WIDTH / 2), WIDTH);
        PP.pl(String.format("%s CAR TRAVEL TIME: %.2f [hr]", carType.toUpperCase(), time), WIDTH);
        PP.pl(String.format("%s CAR TRAVEL COST: $%.2f", carType.toUpperCase(), cost), WIDTH);
        PP.pl("^".repeat(WIDTH / 2), WIDTH);
        PP.lb(' ');
    }

    /**
     * This method is a printout that describes what is happening in this program.
     */
    public static void description() {
        String description = """
                Welcome!
                
                This program calculates the cost of travel as well as
                how long it might take to complete the trip based on if an
                electric or gas-powered car is used for travel.
                
                For best results, please answer the following
                questions as best you can.
                """;
        String[] lines = description.split("\n");
        // output
        PP.lb(WIDTH);
        for (String line : lines) PP.pl(line, WIDTH);
        PP.lb(WIDTH);
    }
}