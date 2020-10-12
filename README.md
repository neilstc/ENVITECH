# ENVITECH
Two Plates From A Building

that's exactly how i thought of the answer: 
Solutions 1 and 2 are the the solutions that made me understand the problem and what i need to do (i implemented them to).
Solution 3 is the best and right ONE!
solution 4 is just a bonus that i wanted to write because i tought about it.


 1) naive solution BAD : O(50)
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
            from 100 floors the worst case is: 14 because every time the plate not breaking we decrement the floor we jump
            and it will result in less values for the second plate.

         ALGORITHM: O(sumOfNumbersEquation)
            1. solve the sum of numbers equation: floors = n(n+1)/2 and round it up.
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
