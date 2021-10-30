/*
  Game name :- Hangman ( A word guessing game)

  Task :- 1. Create a collection of words which will be used in game.
          2. Randomly select a word from collection of words.
          3. create maskword of fill with '*' of similar length of actual word.
          4. Ask user to guess tha character available in word.
          5. Check that character available in word or not .
          6. If avaiable, unmasked that character in mask word .
          7. If already unmasked than show msg of already guessed !
          8. If not available, show msg of not available or wrong guess !
          9. If complete word guess , give output how many time user make wrong guess.
          10. Ask user to play again or not!
          11. If yes , repeat !

*/

import java.util.*;

 public class Hangman{

  /* Function name : randomWord
     return type : String
     parameter type : Array of String
     work : take array of string as a input and return random one string from that array of string !

     variables : n (for store random generate index number)

  */
  public static String randomWord(String[] list){
   int n =(int)(Math.random()*list.length);
   return list[n];
  }

  /* Function name : guessLetter
     return type : void
     parameter : string
     work : game logic start from here,take string as a input,then fetch length of word using 
            string's length() method ,then create a mask word (fill with *) with the same length 
            of word using do-while loop we start user interaction of game. here first store maskword 
            into another string which will be used to compare the user guess correct or not in this function
            (later explained), now take input a char from user and check whether that char is the part of word or
             not using checkLetter . to invoke checkLetter, we have to invoke function with passing three arguments
             (1 letter which guessed by user , 2. Mask word which will be modified if guess is right, 3. word 
             (which in same in single complete round)). and checkLetter return modified maskword if user guess was right. 
             Now we compare old mask word with modified mask word , if they are same, mean user guess was wrong, 
             therefore increment the count variable(which record wrong guess). otherwise guess is correct, therefore
             we store guessword into guessRecord array (Array of char). again this will happend again n again until user completely guess that word , after that print result output and ask user to play again or not !

    variable :- 1. int n (for store length of word)
                2. int count( for recode of wrong guess)
                3. String maskWord( it used as game mask word , continuously modified after coorect guess and used as output )
                4. String oldMask(store maskWord before modification and used to compare with maskWord)
                5. char letter( to store user guess character in every iteration)
                6. char ch( to store y / n as a input for repeat game)
                7. char[] guessRecord ( array of char to store correct guessed character, and used to check input character is previously guessed or not)
                8. int i( supporting index for guessRecord)

  */
  public static void guessLetter(String word){
  Scanner input = new Scanner(System.in);
  int n=word.length(),count=0;
   String maskWord="",oldMask;
   for(int i=0;i<n;i++)
   maskWord += '*';
   char letter,ch;
   char[] guessRecord = new char[word.length()];
   int i=0;
   do{
   System.out.print("\n(Guess) Enter a letter in word "+maskWord + " > " );
   letter = input.next().charAt(0);

   if(checkRepeatGuess(letter,guessRecord))
   {
    System.out.println("\n\t"+letter+" is already in the word ! ");
    continue;
   }
    oldMask=maskWord;
    maskWord = checkLetter(letter,maskWord,word);
    if(oldMask.equals(maskWord))
    count++;
    else{
    guessRecord[i] = letter ;
    i++;
    }
   if(word.equals(maskWord))
   {
     System.out.println("\n\tYes , word is "+word+" ! ");
     System.out.println("\n\tYou missed "+count+ " times !");
     System.out.println("\n\n Do you want to play again ( y for yes / n for no ) : ");
     ch = input.next().charAt(0);
     if(ch=='y' || ch == 'Y')
     main(null);
   }
   } while(!word.equals(maskWord));

  }



  /*
    function name : checkLetter
    return type : String (modified mask word on correct guess, same maskword on wrong guess)
    parameter : 1.char(user's guessed character)
    		2.String(mask word)
		3.String(actual word)

    work :- Create a char array with mask word to be able to modify it t specific index,
            check user's guessed character is avaiable in actual word or not using String's contains method.
            If actual word contains that letter than start process of mofify the mask word , 
    	    else return same maskWord with msg of wrong input(output).
            To modify the mask word , we first check, how many time that character repeat in actual word,
            for this we use another method repeatance which return int( number of repeatance of character in actual word.)
            now we use loop for iteration for number of repeatance of character ( fetch earlier using repeatance method)
            With the help of String's indexOf method we can find the index of actual word which match character, and then change that index * with guessed word in char array of madkword.
            after loop, return char array as a string(modified maskword)


     	   variable :- 1. char[] ch (to store mask word as char array)
			2.int n ( to store repeatance of character in actual word)
  			3.int indx ( to store index of char in actual word)
			4. String mask( to store converted modified char Array(maskWord))
 */
  public static String checkLetter(char c,String maskWord, String word){
   char ch[]=new char[word.length()];
   for(int i=0;i<word.length();i++)
   ch[i] = maskWord.charAt(i);

   int n,indx=-1;;
    if(word.contains(c+""))
   {
    n=repeatance(word,c);
    for(int i=0;i<n;i++){
     indx=word.indexOf(c,indx+1);
     if(indx<0)
     break;
     ch[indx]=c;
    }

    String mask = new String(ch);

    return mask;
   }
   else{
    System.out.println("\n\t "+c+" is not in the word");
    return maskWord;
    }

  }


  /*
    Function name: repeatance
    return type :- int (number of repeatance of char)
    parameter :- 1. String (actual word)
		 2. char (guessed character)

    work :- calculate and return how many time a guessed character is repeated in word.

    variable :- 1. int count

  */
  public static int repeatance(String word,char c){
   int count=0;
    for(int i=0;i<word.length();i++){
      if(word.charAt(i)==c)
      count++;
    }
    return count;
  }


  /*
    main function

    Variable :-1.String[] words ( to store collection of words which will be used in game.
  	       2.String wird (to store random selected word from collection of words)

  */

  public static void main(String[] args){
   String[] words={"program","bytecode","int","char","long","multithread","java","array","string","class","object","inheritance","abstraction"};
   String word = randomWord(words);
   guessLetter(word);

  }


  /*
   Function name : checkRepeatGuess
   return type : boolean
   parameter : 1.char( input character)
	       2.char Array( array which store correct guessed characters)

   Work :- Check whether character is avaiable in char array or not!


   variable:- int i( for loop purpose only)

  */
  public static boolean checkRepeatGuess(char ch, char[] guessData){
   for(int i=0;i<guessData.length;i++){
    if(guessData[i]==ch)
    {
     return true;
    }
   }
    return false;
  }
 }
