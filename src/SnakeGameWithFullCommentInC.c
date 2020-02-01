// <--IMPORT-->
#include <stdio.h> //  <--Standard Input and Output Library-->
#include <time.h> //  <--four variable types, two macro and various functions for manipulating date and time-->
#include <stdlib.h> //  <--defines four variable types, several macros, and various functions for performing general functions-->
#include <conio.h> //  performing "console input and output"-->
#include<time.h> //  -> Why need to import so many times
#include<ctype.h> //  -> Why need to import so many times
#include <time.h> // -> Why need to import so many times
#include <windows.h> //  <--contains declarations for all of the functions in the Windows API-->
#include <process.h> //  <--contains function declarations and macros used in working with threads and processes-->


//  <--DECLARE CONSTANT-->
#define UP 72
#define DOWN 80
#define LEFT 75
#define RIGHT 77



// <--DECLARE METHOD-->
int length; // The length of the snake, if <**** (means length = 5)
int bend_no; // bending count
int len; // len => ori is 0, but will increase when bend?
char key; // keyboard key
void record(); // Get player name and change the first letter of word to capital letter & save it in the txt file
// If want view pass record, display txt file in console
void load(); // load the loading bar
int life; // Storing life result
void Delay(long double); // Delay the time by looping 10000000
void Move();  // Consider the main method that keep looping when no key is press, when key is press will check what key is it, and recursion back to the Move()
void Food(); // Create new food position [and make sure is in border ] and also check snake eat food or not, yes -> score++
int Score(); // Print the score marks & life result above the border
void Print(); // Print the instruction, Esc for exit
void gotoxy(int x, int y); //Set the cursor to (x,y)
void GotoXY(int x,int y); //Set the cursor to (x,y) with flush
void Bend(); // Handle bending condition
void Boarder(); //Print the border [Go to particular point to print the food(coordinate get from Food method)]
void Down();
void Left();
void Up();
void Right();
void ExitGame(); // Check whether hit wall/body, if yes minus life, check life -> >=0 reset position else end game & show record
int Scoreonly();


//  struct => <--defines a physically grouped list of variables under one name in a block of memory-->
struct coordinate
{
    int x;
    int y;
    int direction;
};

typedef struct coordinate coordinate;

coordinate head, bend[500],food,body[30];

int main(){

    char key;

    Print(); // Invoke Print() method at 454

    system("cls"); //clear the screen

    load(); // Invoke load() method at 226

    length=5;

    head.x=25;  // Set snake starting coordinate

    head.y=20;

    head.direction=RIGHT; // Head to right

    Boarder(); // Invoke Boarder() method at 433

    Food(); //to generate food coordinates initially

    life=3; //number of extra lives

    bend[0]=head;

    Move();   //initialing initial bend coordinate

    return 0;

}

void Move(){
    int a,i;

    do{

        Food(); // Generate food
        fflush(stdin); // used to clear the buffer and accept the next string

        len=0;

        for(i=0; i<30; i++)

        {

            body[i].x=0; // set body coordinate to (0,0)

            body[i].y=0;

            if(i==length) // the length = 5 (set during main) no idea for wat

                break;

        }

        Delay(length); // Just wanna delay the time (The delay will for loop for 100000000 times, the parameter pass it is useless)

        Boarder(); // Draw border

        if(head.direction==RIGHT)

            Right();

        else if(head.direction==LEFT)

            Left();

        else if(head.direction==DOWN)

            Down();

        else if(head.direction==UP)

            Up();

        ExitGame(); // Wait until hit any wall/body

    }
    while(!kbhit()); //kbhit() => keyboard got hit or not [0 -> no hit ; 1-> hit] Will loop if no hit
    //When is it, will go in check what is hit, see what is the result

    a=getch();

    if(a==27) // 27 = ESC

    {

        system("cls"); //Exit

        exit(0);

    }
    key=getch(); // get input

    if((key==RIGHT&&head.direction!=LEFT&&head.direction!=RIGHT)||(key==LEFT&&head.direction!=RIGHT&&head.direction!=LEFT)||(key==UP&&head.direction!=DOWN&&head.direction!=UP)||(key==DOWN&&head.direction!=UP&&head.direction!=DOWN))
// if (key = > && head != > & < )|| (key = < && head != < & > ) || (key = ^ && head != ^ & v ) || (key = v && head != v & ^ )
    {

        bend_no++; // increase bend_no

        bend[bend_no]=head; // seems like storing head coordinate (aka bending coordinate) into bend list

        head.direction=key; // Change head direction symbol

        if(key==UP)

            head.y--; // (The staring point is (0,0)) The y axis key is getting smaller when we up

        if(key==DOWN)

            head.y++;   // (The staring point is (0,0)) The y axis key is getting bigger when we down

        if(key==RIGHT)

            head.x++; // (The staring point is (0,0)) The x axis key is getting bigger when we right

        if(key==LEFT)

            head.x--; // (The staring point is (0,0)) The x axis key is getting bigger when we left

        Move(); // call back this method (kind of recursion)

    }

    else if(key==27) // 27 = ESC

    {

        system("cls"); //exit

        exit(0);

    }

    else

    {

        printf("\a"); // Beep sound

        Move(); // call back this function (actually is a recursion)

    }
}

void gotoxy(int x, int y)
{

    COORD coord;

    coord.X = x;

    coord.Y = y;

    SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), coord); // Set the cursor to the particular position

}

void GotoXY(int x, int y){
    HANDLE a;
    COORD b;
    fflush(stdout);
    b.X = x;
    b.Y = y;
    a = GetStdHandle(STD_OUTPUT_HANDLE);
    SetConsoleCursorPosition(a,b);
}

void load(){
    int row,col,r,c,q;
    gotoxy(36,14);
    printf("loading...");
    gotoxy(30,15);
    for(r=1; r<=20; r++)
    {
        for(q=0; q<=100000000; q++); //to display the character slowly
        printf("%c",177);
    }
    getch();
}

void Down(){ // almost same concept with up
    int i;
    for(i=0; i<=(head.y-bend[bend_no].y)&&len<length; i++) // length = snake length (will increase when eat food)
    {
        GotoXY(head.x,head.y-i);
        {
            if(len==0)
                printf("v");
            else
                printf("*");
        }
        body[len].x=head.x;
        body[len].y=head.y-i;
        len++;
    }
    Bend();
    if(!kbhit())
        head.y++;
}

void Delay(long double k){
    Score();
    long double i;
    for(i=0; i<=(10000000); i++);
}

void ExitGame(){
    int i,check=0;
    for(i=4; i<length; i++) //starts with 4 because it needs minimum 4 element to touch its own body
    {
        if(body[0].x==body[i].x&&body[0].y==body[i].y) // Check got hit own body/head
        {
            check++;    //check's value increases as the coordinates of head is equal to any other body coordinate
        }
        if(i==length||check!=0) // Break the program because snake is hitting itself
            break;
    }
    if(head.x<=10||head.x>=70||head.y<=10||head.y>=30||check!=0) //Check got hit border(wall) or not [x:10 & 70, y:10 & 30]
    {
        life--; // Deduct mark
        if(life>=0) // If life still >= 0
        {
            head.x=25;      // reset snake starting point
            head.y=20;
            bend_no=0;
            head.direction=RIGHT;
            Move();
        }
        else
        {
            system("cls");      // Exit the game move to record part, End Game already
            printf("All lives completed\nBetter Luck Next Time!!!\nPress any key to quit the game\n");
            record();
            exit(0);
        }
    }
}

void Food(){

    if(head.x==food.x&&head.y==food.y) { // if hit the head coor == food coor
        length++;
        time_t a;  // defined for storing system time values
        a=time(0);  // store system time
        srand(a);   /* srand() function sets the starting point for producing a series of pseudo-random integers.
        If srand() is not called, the rand() seed is set as if srand(1) were called at program start.*/
        food.x=rand()%70; // rand() -> generate random numbers
        if(food.x<=10) // Make sure it push inside the border
            food.x+=11;
        food.y=rand()%30;
        if(food.y<=10)
            food.y+=11;
    }
    else if(food.x==0){/*to create food for the first time coz global variable are initialized with 0*/
        food.x=rand()%70;
        if(food.x<=10)
            food.x+=11;
        food.y=rand()%30;
        if(food.y<=10)
            food.y+=11;
    }
}

void Left(){ // Almost same concept with UP() Look back tat
    int i;
    for(i=0; i<=(bend[bend_no].x-head.x)&&len<length; i++)
    {
        GotoXY((head.x+i),head.y); // Go to the coordinate
        {
            if(len==0)
                printf("<"); // Print the first element as "<"
            else
                printf("*"); // others  print *
        }
        body[len].x=head.x+i;
        body[len].y=head.y;
        len++;
    }
    Bend();
    if(!kbhit())
        head.x--;

}

void Right(){ // Almost same concept with UP() Look back tat

    int i;
    for(i=0; i<=(head.x-bend[bend_no].x)&&len<length; i++)
    {
        //GotoXY((head.x-i),head.y);
        body[len].x=head.x-i;
        body[len].y=head.y;
        GotoXY(body[len].x,body[len].y);
        {
            if(len==0)
                printf(">");
            else
                printf("*");
        }
        /*body[len].x=head.x-i;
        body[len].y=head.y;*/
        len++;
    }
    Bend();
    if(!kbhit())
        head.x++;
}

void Bend(){
    int i,j,diff;
    for(i=bend_no; i>=0&&len<length; i--) //length = snake length (will increase when eat food), bend_no (current bending count)
        //len = ori is 0 (But will increase when newly printed the body after bend, len < length, make sure wont print the len more than original length
    {
        if(bend[i].x==bend[i-1].x) // [At the same vertical line] current bend coordinate x == previous bend coordinate x
        {
            diff=bend[i].y-bend[i-1].y; // get the y difference (current - previous)
            if(diff<0) // Means the current bending point is at below
                for(j=1; j<=(-diff); j++)
                {
                    body[len].x=bend[i].x;
                    body[len].y=bend[i].y+j;
                    GotoXY(body[len].x,body[len].y);
                    printf("*");
                    len++; // After print one * , increment the len (len is just a temp var)
                    if(len==length) // Make sure the new len print == the length the snake should have
                        break;
                }
            else if(diff>0) // Means the current bending point is at above
                for(j=1; j<=diff; j++)
                {
                    /*GotoXY(bend[i].x,(bend[i].y-j)); // Not From Yan , ori ady commented, so ignore
                    printf("*");*/
                    body[len].x=bend[i].x;
                    body[len].y=bend[i].y-j;
                    GotoXY(body[len].x,body[len].y);
                    printf("*");
                    len++; // After print one * , increment the len (len is just a temp var)
                    if(len==length) // Make sure the new len print == the length the snake should have
                        break;
                }
        }
        else if(bend[i].y==bend[i-1].y) // [At the same horizontal] current bend coordinate y == previous bend coordinate y
        {
            diff=bend[i].x-bend[i-1].x; // get the y difference (current - previous)
            if(diff<0) // Means the current bending point is at left
                for(j=1; j<=(-diff)&&len<length; j++)
                {
                    /*GotoXY((bend[i].x+j),bend[i].y); // Not From Yan , ori ady commented, so ignore
                    printf("*");*/
                    body[len].x=bend[i].x+j;
                    body[len].y=bend[i].y;
                    GotoXY(body[len].x,body[len].y);
                    printf("*");
                    len++; // After print one * , increment the len (len is just a temp var)
                    if(len==length) // Make sure the new len print == the length the snake should have
                        break;
                }
            else if(diff>0) // Means the current bending point is at right
                for(j=1; j<=diff&&len<length; j++)
                {
                    /*GotoXY((bend[i].x-j),bend[i].y); // Not From Yan , ori ady commented, so ignore
                    printf("*");*/
                    body[len].x=bend[i].x-j;
                    body[len].y=bend[i].y;
                    GotoXY(body[len].x,body[len].y);
                    printf("*");
                    len++; // After print one * , increment the len (len is just a temp var)
                    if(len==length) // Make sure the new len print == the length the snake should have
                        break;
                }
        }
    }
}
void Boarder()
{
    system("cls");
    int i;
    GotoXY(food.x,food.y);   /*displaying food*/
    printf("F");
    for(i=10; i<71; i++)
    {
        GotoXY(i,10);
        printf("!");
        GotoXY(i,30);
        printf("!");
    }
    for(i=10; i<31; i++)
    {
        GotoXY(10,i);
        printf("!");
        GotoXY(70,i);
        printf("!");
    }
}
void Print() // Print the instruction, Esc for exit
{
    //GotoXY(10,12);
    printf("\tWelcome to the mini Snake game.(press any key to continue)\n");
    getch(); // get any input to show game instruction
    system("cls");
    printf("\tGame instructions:\n");
    printf("\n-> Use arrow keys to move the snake.\n\n-> You will be provided foods at the several coordinates of the screen which you have to eat. Everytime you eat a food the length of the snake will be increased by 1 element and thus the score.\n\n-> Here you are provided with three lives. Your life will decrease as you hit the wall or snake's body.\n\n-> YOu can pause the game in its middle by pressing any key. To continue the paused game press any other key once again\n\n-> If you want to exit press esc. \n");
    printf("\n\nPress any key to play game...");
    if(getch()==27)  //Detect esc key
        exit(0);
}

// plname = player name , nplname = new player name(convert to upper case)
void record() // Get player name and change the first letter of word to capital letter & save it in the txt file
{ // If want view pass record, display txt file in console
    char plname[20],nplname[20],cha,c;
    int i,j,px;
    FILE *info;
    info=fopen("record.txt","a+");
    getch();
    system("cls");
    printf("Enter your name\n");
    scanf("%[^\n]",plname);
    //************************
    //'\0' => means termination of a character string
    for(j=0; plname[j]!='\0'; j++) //to convert the first letter after space to capital
    {
        nplname[0]=toupper(plname[0]);
        if(plname[j-1]==' ') // If before that is a space, now this character is convert to capital
        {
            nplname[j]=toupper(plname[j]);
            nplname[j-1]=plname[j-1];
        }
        else nplname[j]=plname[j];
    }
    nplname[j]='\0'; //'\0' => means termination of a character string
    //*****************************
    //sdfprintf(info,"\t\t\tPlayers List\n");
    fprintf(info,"Player Name :%s\n",nplname);
    //for date and time

    time_t mytime;
    mytime = time(NULL);
    fprintf(info,"Played Date:%s",ctime(&mytime));
    //**************************
    fprintf(info,"Score:%d\n",px=Scoreonly());//call score to display score
    //fprintf(info,"\nLevel:%d\n",10);//call level to display level
    for(i=0; i<=50; i++)
        fprintf(info,"%c",'_');
    fprintf(info,"\n");
    fclose(info);
    printf("Wanna see past records press 'y'\n");
    cha=getch();
    system("cls");
    if(cha=='y')
    {
        info=fopen("record.txt","r");
        do
        {
            putchar(c=getc(info));
        }
        while(c!=EOF);
    }
    fclose(info);
}
int Score()
{
    int score;
    GotoXY(20,8);
    score=length-5;
    printf("SCORE : %d",(length-5));
    score=length-5;
    GotoXY(50,8);
    printf("Life : %d",life);
    return score;
}
int Scoreonly()
{
    int score=Score();
    system("cls");
    return score;
}
void Up()
{
    int i;
    for(i=0; i<=(bend[bend_no].y-head.y)&&len<length; i++) // (bend[bend_no].y-head.y) => make sure is not stay at same point
    {
        GotoXY(head.x,head.y+i);
        {
            if(len==0)
                printf("^");
            else
                printf("*");
        }
        body[len].x=head.x; //Staying at same vertical line , coz action is up]
        body[len].y=head.y+i;
        len++;
    } // Handle no bend
    Bend(); // Handle bend
    if(!kbhit())
        head.y--; // If keyboard no hit, the next head y will decrease coz toward up, the y coor is smaller
}

