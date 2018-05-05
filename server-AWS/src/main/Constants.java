package main;

import java.awt.*;

public final class Constants {

    private Constants() {
        // private constructor so cant be instantiated as all variables are public static final
    }

    // Investment property price constants - 24 total - each row is a new colour group
    public static final int[] INVESTMENT_PRICES = {
        60,60,60,
        100,100,120,
        140,140,160,
        180,180,200,
        220,220,240,
        260,260,280,
        300,300,320,
        350,350,400
    };

    // Utilities price constants
    public static final int[] UTILITY_PRICES = {
        150,150
    };

    // Station price constants
    public static final int[] STATION_PRICES = {
        200,200,200,200
    };

    // Investment property rent constants - 24 total - each row is a new colour group
    public static final int[][] INVESTMENT_RENTS = {
        {2,10,30,90,160,250},{2,10,30,90,160,250},{4,20,60,180,320,450},
        {6,30,90,270,400,550},{6,30,90,270,400,550},{8,40,100,300,450,600},
        {10,50,150,450,625,750},{10,50,150,450,625,750},{12,60,180,500,700,900},
        {14,70,200,550,750,950},{14,70,200,550,750,950},{16,80,220,600,800,1000},
        {18,90,250,700,875,1050},{18,90,250,700,875,1050},{20,100,300,750,925,1100},
        {22,110,330,800,975,1150},{22,110,330,800,975,1150},{22,120,360,850,1025,1200},
        {26,130,390,900,1100,1275},{26,130,390,900,1100,1275},{28,150,450,1000,1200,1400},
        {35,175,500,1100,1300,1500},{35,175,500,1100,1300,1500},{50,200,600,1400,1700,2000}
    };

    // Utilities rent constants - these are the dice multipliers
    public static final int[][] UTILITY_RENTS = {
        {4,10},{4,10}
    };

    // Station rent constants  - 0-3 indices for 1-4 stations possible
    public static final int[][] STATION_RENTS = {
        {25,50,100,200},{25,50,100,200},
        {25,50,100,200},{25,50,100,200}
    };


    // Investment property house prices(hotels same price) - 24 total - each row is a new colour group
    public static final int[] HOUSE_PRICES = 	{
        30,30,30,
        50,50,50,
        100,100,100,
        100,100,100,
        150,150,150,
        150,150,150,
        150,150,160,
        200,200,200
    };


    // Investment property mortgage constants - 24 total - each row is a new colour group
    public static final int[] INVESTMENT_MORTGAGE_VALUE = {
        50,50,50,
        50,50,60,
        70,70,80,
        90,90,100,
        110,110,120,
        150,150,150,
        200,200,200,
        175,175,200
    };

    // Station mortgage value constants
    public static final int[] STATION_MORTGAGE_VALUE = {
        100,100,100,100
    };


    // Utility mortgage value constants
    public static final int[] UTILITY_MORTGAGE_VALUE = {
        75,75
    };


    // Player colours
    public static final Color[] playerColours = {
        new Color(66,229,244),
        new Color(26,224,59),
        new Color(242, 50, 226),
        new Color(255, 244, 43)
    };

    // Investment property colour groups
    public static final Color[] INVESTMENT_COLOUR_GROUPS = new Color[]{
        Color.CYAN, Color.GREEN,
        Color.MAGENTA, Color.YELLOW,
        Color.WHITE, Color.ORANGE,
        Color.BLUE, Color.PINK
    };
    
    //Special colours
    public static final Color[] SPECIAL_COLOURS = new Color[]{
    	Color.RED, //Tax
    	Color.BLACK, //Stations
    	Color.MAGENTA, //Utilities
    	Color.YELLOW, //Chance
    	Color.LIGHT_GRAY //Special tiles	
    };
    

    public static final Color[] OTHER_TILE_COLOURS = new Color[] {

    };
}
