public class Plates {


// BAD
    public static int naiveSolution(int breakingFloor){
        boolean firstPlateBroke = false;       // i just need to keep track on if it broke or not.
        boolean secondPlateBroke = false;
        int currentFloor = 0;
        int upperBound = 100;   // where to stop the search of our exact floor.
        int lowerBound = 0; // where to start our search for the exact floor
        int ans = 0;

        int operations = 0;
        while(!firstPlateBroke) {       // first loop is to find the bound.
            currentFloor = lowerBound + (upperBound - lowerBound)/2;
            if(currentFloor >= breakingFloor){
                upperBound = currentFloor;  // it broke, lower the upper bound.
                firstPlateBroke = true;
            }else{
                lowerBound = currentFloor;
                firstPlateBroke = true;
            }
            operations++;
        }
        while(lowerBound < upperBound){
            if(lowerBound == breakingFloor){
                secondPlateBroke = true;
                ans = lowerBound;
                operations++;
                break;
            }
            lowerBound++;
            operations++;
        }
        System.out.println("took: "+ operations + " operations");
        return ans;
    }

/// BETTER
    public static int squareRoot(int breakingFloor, int floors){

        //short circuits (corner cases)
        if(floors <= 0 || breakingFloor <= 0){
            System.out.println("invalid values! number of floor has to be at least 1 ");
            return -1;
        }
        if(floors == 1 && breakingFloor == 1 ) return 1;

        boolean firstPlateBroke = false;       // i just need to keep track on if it broke or not.
        boolean secondPlateBroke = false;      // don't really need it but it help to visualise.
   //    int currentFloor = 0;
        int floorIncrement = (int) Math.sqrt(floors);      //number floor we will move every time.
        int upperBound = floorIncrement;    // where to stop the search of our exact floor.
        int lowerBound = 0; // where to start our search for the exact floor
        int ans = 0;

        while(!firstPlateBroke) {       // first loop is to find the bound.
            if (upperBound >= breakingFloor) {
                firstPlateBroke = true;
            } else {
                lowerBound = upperBound + 1;
                upperBound += floorIncrement;
            }
        }
        System.out.println("it broke on: " + upperBound);
        while(lowerBound <= upperBound){         // find the exact one
            if(lowerBound == breakingFloor){
                secondPlateBroke = true;          // useless :>
                ans = lowerBound;
                break;
            }
            lowerBound++;
            //operations++;
        }
      //  System.out.println("took: "+ operations + " operations");
        return ans;
    }

//----------------- SOLUTION STARTS HERE -----------------\\
   public static int calculateIncrement(int floors){
    /* this function will solve the formula and return the right number.
       @params: int floors - number for the equation
       @returns: the first floorIncrement.
     */
        int a = 1;
        int b = 1;
        int c = -floors*2;
        int  root1 = 0, root2 = 0;
       double determinant = b * b - 4 * a * c;
       // condition for real and different roots
       if(determinant > 0) {
           root1 = (int) Math.ceil((-b + Math.sqrt(determinant)) / (2 * a));        // round up
           root2 = (int) (-b - Math.sqrt(determinant)) / (2 * a);
       }
       // Condition for real and equal roots
       else if(determinant == 0) {
           root1 = root2 = -b / (2 * a);
            return root1;
       }
        return -1;
   }

    // that's the best one
    public static int sumOFNumbers(int breakingFloor, int floors){
        /* final solution
            @params:     breakingFloor - the floor that will break the plate,
                         floors - how many floors in our building
            @returns:    the minimal floor the plate breaking in.
                         ,-1 if somethings wrong.
         */

        //short circuits (corner cases)
        if(floors <= 0 || breakingFloor <= 0){
            System.out.println("invalid values! number of floor has to be at least 1 ");
            return -1;
        }
        if(breakingFloor > floors){
            System.out.println("the breaking floor can't be higher than the building! ");
            return -1;
        }
        if(floors == 1 && breakingFloor == 1 ) return 1;



        boolean firstPlateBroke = false;       // i just need to keep track on if it broke or not.
        boolean secondPlateBroke = false;      // don't really need it but it help to visualise.
        //    int currentFloor = 0;
        int floorIncrement = calculateIncrement(floors);      //number floor we will move every time.
        int upperBound = floorIncrement;    // where to stop the search of our exact floor.
        int lowerBound = 0; // where to start our search for the exact floor
        int ans = 0;

        while(!firstPlateBroke) {       // first loop is to find the bound.
            if (upperBound >= breakingFloor) {
                firstPlateBroke = true;
            } else {
                lowerBound = upperBound + 1;
                upperBound += floorIncrement;
                floorIncrement--;           // update the dynamic increment!!
            }
        }

        while(lowerBound < upperBound){         // go all the way until it breaks, then return the floor.
            if(lowerBound == breakingFloor){
                secondPlateBroke = true;          // useless :>
                ans = lowerBound;
                break;
            }
            lowerBound++;
            //operations++;
        }
        //  System.out.println("took: "+ operations + " operations");
        return ans;
    }


    // I LIKED THIS ONE BUT MIGHT NOT COUNT
    public static int binarySearch(int arr[]){
        //short circuits (corner cases)
        if(arr.length == 0 ){       // the "building has no floors!"
            System.out.println("no floors! ");
            return -1;
        }
        int  lowerBound = 0;
        int  upperBound = arr.length;

        while( lowerBound <= upperBound){
            int middle = lowerBound + (upperBound - lowerBound)/2;
            if( (arr[middle] == 0) && (arr[middle +1] == 1) ){
                return middle;
            }else if(arr[middle] == 0 ){
                lowerBound = middle+1;
            }else{
                upperBound = middle-1;
            }
        }
        return -1;
    }
    public static void main(String[] args) {

    /*   1) naive solution BAD : O(50)
            throw the first plate form 50 floor and then we if it will break
            throw the second one from the 0-50 until it breaks else divide by 2 again.
            BAD - worst case is O(50).
            but now i understand that by throwing the first one from floor 50,
            1.iv'e got a bound of possible floors (from 0 - 50).
            2. every time the ball wont break it will cost O(1) and in general O(+1) + O(upperbound - lowerbound).

            so by throwing the first plate i get a bound of possible floors that the second plate will break in.
            so the actual question is: from where should i drop it and how will i drop it?

         2) square root approach BETTER: i will square root the number (√100 for example)
            and then i'll jump that square root every time so the max drop will be 10
            for the first plate and then 9 for the exact floor so 10+9 = 19.

         3) i'm sure there's a way to optimize it! so now i'm thinking where can i cut?
            a. maybe from the fact that every time the ball doesn't break the complexity going up by 1?
            b. maybe the lower/ upper bound no tight enough?
            c. maybe something dynamic changing increment??

           Final Solution - BEST - Sum Of Numbers!
            i thought what did i do with the other approaches? i somehow divided/ broke it into smaller gaps
            lets try another method to break a number the formula of sum numbers! Sn = n(n+1)/2!
            we will use the numbers that eventually will give me something close to 100
            100 = n(n+1)/2 will give us 14 then we will jump 14 then 13 then 12 then 11 ........ 1.
            from 100 floors the worst case is: 14! because every time the plate not breaking we decrement the floor we jump
            and it will result in less values for the second plate.

            ALGORITHM:
            1. solve the sum of numbers equation: floors = n(n+1)/2 and round up
            2. this is your initial increment. (100 example is 14).
            3. throw the plate and check if the upperbound breaks here:
               a. if he is: go to the second loop and where exactly it breaks
               b. if not: decrement the floorIncrement by 1, increment bounds and stay in loop.
            4. iterate from the lower bound to the upper bound and find exactly where it breaks.
            5. return that number.

         4) OK this one might consider a cheat but i thought why not!
            Binary search approach if we will consider the floors that the plate does not break in as 0's
            and the floors of that the plate does break as 1's we can binary search the solution!
            we can binary search the floor such that the the zero we are looking for will be next just under 1!
            then the index of the array will be the floor ewe need! and we'll get O(log(n)).
        */
//
//        //1. naive solution - BAD SOLUTION O(n/2)
//        System.out.println("NAIVE: it will break on the: " +naiveSolution(60) + " floor");
//        //2. square root approach - BETTER SOLUTION
//        System.out.println("SQUARE ROOT: it will break on the: " +squareRoot(60,100) + " floor");
          //3. sum of numbers approach - FINAL ANSWER
            System.out.println(sumOFNumbers(99, 100));
//        //4. binary search - MIGHT BE BEST MIGHT BE ILLEGAL O(log(n))
//        int floorsArray [] = createFloorsArr(100);      //create and array the simulate the floors with 0 as non-breaking floors and 1 as breaking floors.
//        System.out.println("BINARY SEARCH: it will break on: " + binarySearch(floorsArray));
    }


    //UTILITY FOR THE BINARY SEARCH APPROACH - FILLING THE ARRAY WITH 0'S AS NON BREAKING FLOORS AND 1'S AS BREAKING FLOORS.

    public static int[]  createFloorsArr(int numberOfFloors){
        int arr[] = new int[numberOfFloors];
        for (int i = 0; i < 67; i++) {
            arr[i] = 0;
        }
        for (int i = 68; i < arr.length; i++) {
            arr[i] = 1;
        }
        return arr;
    }
}
