import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameApplication {

    public static Scanner sc = new Scanner(System.in);
    public static int length = 20;
    public static Random r = new Random();
    public static String [] board = new String[length];
    public static Player player1 = new Player("player1");
    public static Player bot = new Player("bot");

    public static Player firstPlayer;
    public static Player secondPlayer;
    public static String [] newBoard;
    public static int newPosition=-1;

    public static int diceRoll=0;
    public static List<Trap> traps = new ArrayList<Trap>();

    public static void main(String[] args) {
        printInitialBoard();
        playCycle();
    }

    public static void setInitialBoard(){
        board[0]="S/&& **/";
        placeInvests();
        placePartyHard();
        placeChance();
        placeSteal();
        placeTraps();
        player1.setMoney();
        player1.setPosition(0);
        player1.setNickname("&&");
        bot.setMoney();
        bot.setPosition(0);
        bot.setNickname("**");

    }


    public static void printInitialBoard(){
        setInitialBoard();
        for(int i=10;i<18;i++)
            System.out.printf("|%s| ", board[i]);
        System.out.printf("\n|%s|                          |%s|", board[9],board[18]);
        System.out.printf("\n|%s|                          |%s|\n", board[8],board[19]);
        for(int i=7;i>=0;i--)
            System.out.printf("|%s| ", board[i]);

        System.out.printf("\nИграчи: %s играе със символ - &&, %s играе със символ - **",player1.getName(),bot.getName());
    }

    public static void printBoard(String [] board){
        board[0]="S";
        for(int i=10;i<18;i++)
            System.out.printf("|%s| ", board[i]);
        System.out.printf("\n|%s|                          |%s|", board[9],board[18]);
        System.out.printf("\n|%s|                          |%s|\n", board[8],board[19]);
        for(int i=7;i>=0;i--)
            System.out.printf("|%s| ", board[i]);
    }

    public static void placeInvests(){
        int randomIndex=0;
        for(int i=0;i<3;i++){
            randomIndex = r.nextInt(length);
            while(board[randomIndex]!=null){
                randomIndex = r.nextInt(length);
            }
            board[randomIndex] = "I";
        }
    }

    public static void placePartyHard(){
        int randomIndex=0;
        for(int i=0;i<3;i++){
            randomIndex = r.nextInt(length);
            while(board[randomIndex]!=null){
                randomIndex = r.nextInt(length);
            }
            board[randomIndex] = "P";
        }
    }

    public static void placeChance(){
        int randomIndex=0;
        for(int i=0;i<3;i++){
            randomIndex = r.nextInt(length);
            while(board[randomIndex]!=null){
                randomIndex = r.nextInt(length);
            }
            board[randomIndex] = "C";
        }
    }

    public static void placeSteal(){
        int randomIndex=0;
        for(int i=0;i<3;i++){
            randomIndex = r.nextInt(length);
            while(board[randomIndex]!=null){
                randomIndex = r.nextInt(length);
            }
            board[randomIndex] = "St";
        }
    }

    //по условие да поставим капани на всички празни места, които са останали
    public static void placeTraps(){
        for(int i=0;i<length;i++){
            if(board[i]==null) {
                Trap trap = new Trap(i);
                traps.add(trap);
                board[i] = "T";
            }
        }
    }

    public static int whoStartsFirst() {
        int choice;
        if (player1.getMoney()==1000 && bot.getMoney()==1000 || player1.getMoney()==bot.getMoney()){
            Random r = new Random();
            choice=r.nextInt(2)+1;
        }
        else if(player1.getMoney()>bot.getMoney())
            choice = 1;
        else choice=2;
        System.out.println(choice);
        return choice;
    }

    public static void printWhoStartsFirst(){
        if(whoStartsFirst()==1) {
            System.out.println("Player1 започва първи");
            firstPlayer = player1;
            secondPlayer = bot;
        }
        else {
            System.out.println("Bot започва първи");
            firstPlayer = bot;
            secondPlayer = player1;
        }
    }

    public static void playCycle(){
        while(true)
        {
            oneIteration();
            if(firstPlayer.getMoney()!=0 && secondPlayer.getMoney()!=0)
                break;
        }
        if(firstPlayer.getMoney()==0){
            System.out.printf("\n%s загуби всичките си пари. %s губи, %s печели",firstPlayer.getName(),secondPlayer.getName(),firstPlayer.getName());
        }
        else System.out.printf("\n%s загуби всичките си пари. %s губи, %s печели",secondPlayer.getName(),firstPlayer.getName(),secondPlayer.getName());

    }


    public static void oneIteration(){
        System.out.println();
        printWhoStartsFirst();
        firstPlayer.setSteal(generateSteal());
        secondPlayer.setSteal(generateSteal());
        for(int i=0;i<length;i++){
            newBoard = board.clone();
            if(playersTurns(newBoard)) {
                System.out.println("Двамата играчи звършват обиколата успешно!");
                break;
            }
        }
    } public static boolean playersTurns(String [] newBoard){
        boolean areTheyOnTheStart=false;
        getPlayerNewPosition(2,firstPlayer);
        if(firstPlayer.getPosition()!=0) {
            while(firstPlayer.getPosition()==secondPlayer.getPosition()){
                System.out.printf("\n%s хвърли %d, но играчите не може да са на едно и също поле, %s хвърля зар отново",firstPlayer.getName(),getDiceRoll(2),firstPlayer.getName());
                firstPlayer.setPosition(firstPlayer.getPosition()-diceRoll);
                getPlayerNewPosition(2,firstPlayer);
            }
            movePlayer(firstPlayer,newBoard);
            isPositionTrap(firstPlayer.getPosition(),firstPlayer,secondPlayer);
            checkPosition(firstPlayer.getPosition(),firstPlayer);
        } else {
            System.out.printf("\n%s се придвижва до Страта.", firstPlayer.getName());
            newBoard[0] = "S/" + firstPlayer.getNickname() + "/";
        }

     //   System.out.println();
        getPlayerNewPosition(2,secondPlayer);
        if(secondPlayer.getPosition()!=0) {
            while (firstPlayer.getPosition() == secondPlayer.getPosition()) {
                System.out.printf("\n%s хвърли %d, но играчите не може да са на едно и също поле, %s хвърля зар отново", secondPlayer.getName(), getDiceRoll(2), secondPlayer.getName());
                secondPlayer.setPosition(secondPlayer.getPosition()-diceRoll);
                getPlayerNewPosition(2, secondPlayer);
            }
            movePlayer(secondPlayer,newBoard);
            isPositionTrap(firstPlayer.getPosition(),secondPlayer,firstPlayer);

            checkPosition(newPosition,secondPlayer);

        }else {
            System.out.printf("\n%s се придвижва до Страта.", secondPlayer.getName());
            newBoard[0] = "S/" + secondPlayer.getNickname() + "/";
        }

        if(firstPlayer.getPosition()==0 && secondPlayer.getPosition()==0)
            areTheyOnTheStart = true;
        System.out.println();
        return areTheyOnTheStart;
    }

    public static void getPlayerNewPosition(int sides, Player player){
        diceRoll = getDiceRoll(sides);
        int initialPosition= player.getPosition();
        if(newPosition !=0) {
            newPosition = diceRoll + initialPosition;
            if (newPosition > 19)
                newPosition=0;
        }
        player.setPosition(newPosition);
    }

    public static int getDiceRoll(int sides){
        Random dice = new Random();
        int diceRoll = dice.nextInt(sides)+1;
        return diceRoll;
    }

    public static void movePlayer(Player player, String [] newBoard){
        System.out.printf("\n%s хвърля зар.", player.getName());
        System.out.printf("\n%s хвърли %d. %s се придвижва.\n", player.getName(), diceRoll, player.getName());
        System.out.println("Позиция на играча " + player.getPosition());
        newBoard[player.getPosition()] = player.getNickname();
        printBoard(newBoard);
    }
    public static void checkPosition(int position, Player player){
        isPositionChance(position,player);
        isPositionPartyHard(position,player);
        isPositionSteal(position,player);
        isPositionInvest(position,player);
    }

    public static void isPositionSteal(int position, Player player){
        if(board[position]=="St"){
            if(player.getSteal().getType()==3 && player.getSteal().getActive())
                player.playerGetsMoney(100);
            if(!player.getSteal().getActive())
            {
                player.getSteal().setActive(true);
                if(player.getSteal().getType()==1)
                    System.out.println("Завладяване на света, ще получавате +100 на всеки шанс до края на цикъла");
                else if(player.getSteal().getType()==2)
                    System.out.println("Зложници, ще получавате +100 на всеки инвест до края на цикъла");
                else System.out.println("Големия банков удар, ще получавате +100 на вски Steal до края на цикъла");
            }
        }

    }

    public static Steal generateSteal(){
        int steal = getDiceRoll(3);//sluchaino chislo ot 1 do 3
        Steal s = new Steal();
        switch (steal){
            case 1:
                s = new Steal(1);
                break;
            case 2:
                s = new Steal(2);
                break;
            case 3:
                s = new Steal(3);
                break;
        }
        return s;
    }

    public static String printTrapQuestion(){
        String answer="";
        System.out.println("Желаете ли да заложите капан?");
        System.out.println("*(1): Данъчна ревизия");
        System.out.println("*(2): Развод по котешки");
        System.out.println("*(3): Пропаганда");
        System.out.println("*(4): Проглеждане");
        System.out.println("*(5): Хазартен бос");
        System.out.println("*(N): Не, мерси, не вярвам в злото");
        answer = sc.nextLine();
        return answer;
    }

    public static void ifTrapIsOpenedInfo(String type, Player setPlayer){
        System.out.println("Капанът е активиран, вашето наказание е: ");
        switch (type){
            case "(1)":
                if(setPlayer.getMoney()>=10)
                    setPlayer.playerLoseMoney(10);
                else System.out.printf("\n%s искаше да постави капан, но няма достатъчно средства", setPlayer.getName());
                System.out.println("Данъчна ревизия, губите 10% от всичките си приходи в края на цикъла");
                break;
            case "(2)":
                if(setPlayer.getMoney()>=20)
                    setPlayer.playerLoseMoney(20);
                else System.out.printf("\n%s искаше да постави капан, но няма достатъчно средства", setPlayer.getName());
                System.out.println("Развод по котешки, загубата ви ще се определи от 10стенен зар в края на цикъла");
                break;
            case "(3)":
                if(setPlayer.getMoney()>=100)
                    setPlayer.playerLoseMoney(100);
                else System.out.printf("\n%s искаше да постави капан, но няма достатъчно средства", setPlayer.getName());
                System.out.println("Пропаганда, не може да поставяте повече капани до края на цикъла");
                break;
            case "(4)":
                if(setPlayer.getMoney()>=50)
                    setPlayer.playerLoseMoney(50);
                else System.out.printf("\n%s искаше да постави капан, но няма достатъчно средства", setPlayer.getName());
                System.out.println("Пропаганда, Не може да поставяте повече капани до края на цикъла");
                break;
            case "(5)":
                if(setPlayer.getMoney()>=100)
                    setPlayer.playerLoseMoney(100);
                else System.out.printf("\n%s искаше да постави капан, но няма достатъчно средства", setPlayer.getName());
                System.out.println("Хазартен бос, Следващото квадратче Шанс ви носи само негативни последици");
                break;
        }
    }
    public static void ifTrapIsOpened(Trap trap, Player openPlayer){
        switch (trap.getType()) {
            case "(1)":
                double openPlayerMoney = openPlayer.getMoney() - 0.1 * openPlayer.getMoney();
                openPlayer.setMoney(openPlayerMoney);
                break;
            case "(2)":
                int diceRoll = getDiceRoll(10);
                if (diceRoll == 2 || diceRoll == 8)
                    System.out.println("Не получихте загуби или печалби от капана");
                break;
        }
    }




    public static Trap isPositionTrap(int position, Player currentPlayer, Player otherPlayer){
        Trap currentTrap = new Trap();
        if(currentPlayer.getName()==firstPlayer.getName())
            otherPlayer = secondPlayer;
        else otherPlayer = firstPlayer;

        if(board[position]=="T"){
            System.out.printf("\n%s стъпи на поле капан.",currentPlayer.getName());
            for(Trap trap:traps){
                if(trap.getPosition()==position)
                    currentTrap = trap;
            }
            if(!currentTrap.getIsActive()){
                String answer = printTrapQuestion();
                if(answer!="(N)") {
                    currentTrap.setType(answer);
                    currentTrap.setSetByPlayer(currentPlayer);
                    currentTrap.setActive(true);
                    currentTrap.setOpened(false);
                }
                else currentTrap.setActive(false);
            }else{
                if(currentTrap.getSetByPlayer()==currentPlayer){
                    System.out.printf("\n%s стъпи на свой капан. Сега ще се провери дали е прецакан. Хвърля се 10-стенен зар",currentPlayer.getName());
                    int diceRoll = getDiceRoll(10);
                    int numbersSum = 0;
                    while(diceRoll>0){
                        numbersSum = numbersSum + diceRoll%10;
                        diceRoll = diceRoll/10;
                    }
                    if(numbersSum%3==0){
                        System.out.println("Вашето число се дели на 3, вие се спасихте!");
                    }else{
                        System.out.println("Вашето число не се дели на 3, вие отваряте капана си!");
                        currentTrap.setOpened(true);
                        ifTrapIsOpenedInfo(currentTrap.getType(),currentPlayer);
                        currentTrap.getOpenedByPlayer(currentPlayer);
                    }
                }else{
                    currentTrap.setOpened(true);
                    ifTrapIsOpenedInfo(currentTrap.getType(),otherPlayer);
                    currentTrap.getOpenedByPlayer(otherPlayer);
                }
            }
        }
        return currentTrap;
    }


    public static void positiveChance(int secondDiceRoll, Player player){
        System.out.println("\nШансът ви е положителен.");
        if (secondDiceRoll>=1 && secondDiceRoll<=39) {
            System.out.println("Големите надежди.\nПолучавате бонус 50");
            player.playerGetsMoney(50);
        }
        else if (secondDiceRoll>=40 && secondDiceRoll<=65) {
            System.out.println("Лолита.\nПолучавате бонус 100");
            player.playerGetsMoney(100);
        }
        else if(secondDiceRoll>=66 && secondDiceRoll<=79) {
            System.out.println("Гордост и предразсъдъци. Получавате 150 бонус");
            player.playerGetsMoney(150);
        }
        else if(secondDiceRoll>=80 && secondDiceRoll<=94) {
            System.out.println("Повелителя на мухите. Получавате 200 бонус");
            player.playerGetsMoney(200);
        }
        else {
            System.out.println("Хобит. Получавате 250 бонус");
            player.playerGetsMoney(250);
        }
    }

    public static void negativeChance(int secondDiceRoll, Player player){
        System.out.println("\nШансът ви е отрицателен");
        if (secondDiceRoll>=1 && secondDiceRoll<=39) {
            System.out.println("1001 нощ. Губите 50");
            player.playerLoseMoney(50);
        }
        else if (secondDiceRoll>=40 && secondDiceRoll<=65) {
            System.out.println("Балът на феите. Губите 100");
            player.playerLoseMoney(100);
        }
        else if(secondDiceRoll>=66 && secondDiceRoll<=79) {
            System.out.println("Война и мир. Губите 150");
            player.playerLoseMoney(150);
        }
        else if(secondDiceRoll>=80 && secondDiceRoll<=94) {
            System.out.println("Пресъпление и наказание. Губите 200");
            player.playerLoseMoney(200);
        }
        else {
            System.out.println("Гроздовете на гнева. Губите 250");
            player.playerLoseMoney(250);
        }
    }

    public static void isPositionChance(int position, Player player){
        boolean isChancePositive = false;
        if(board[position]=="C") {
            if(player.getSteal().getType()==1 && player.getSteal().getActive()){
                player.playerGetsMoney(100);
            }
            System.out.printf("\n%s стъпи на Шанс. Трябва да хвърлите зар, за да разберете дали шансът ви ще е положителен или отрицателен.", player.getName());
            int diceRoll = getDiceRoll(10);
            if (diceRoll % 2 == 0)
                isChancePositive = true;
            int secondDiceRoll = getDiceRoll(100);
            if (isChancePositive)
                positiveChance(secondDiceRoll, player);
            else
                negativeChance(secondDiceRoll, player);
        }
    }

    public static Invest pickInvest(){
        Invest invest = new Invest();
        int num = getDiceRoll(7);
        switch (num){
            case 1:
                invest = new Invest("(1)","Evel Co",500,0.2,-5,100);
                break;
            case 2:
                invest = new Invest("(2)","Bombs Away",400,0.5,-10,50);
                break;
            case 3:
                invest = new Invest("(3)","Clock Work Orange",300,1.5,-15,35);
                break;
            case 4:
                invest = new Invest("(4)","Maroders unated",200,2,-18,50);
                break;
            case 5:
                invest = new Invest("(5)","Fatcat incorporated",100,2.5,-25,100);
                break;
            case 6:
                invest = new Invest("(6)","Macrosoft",50,5.0,-20,10);
                break;
            case 7:
                invest = null;
                break;
        }
        return invest;
    }

    public static Invest chosenInvest(String answer){
        Invest invest = new Invest();
        switch (answer){
            case "(1)":
                invest = new Invest("(1)","Evel Co",500,0.2,-5,100);
                break;
            case "(2)":
                invest = new Invest("(2)","Bombs Away",400,0.5,-10,50);
                break;
            case "(3)":
                invest = new Invest("(3)","Clock Work Orange",300,1.5,-15,35);
                break;
            case "(4)":
                invest = new Invest("(4)","Maroders unated",200,2,-18,50);
                break;
            case "(5)":
                invest = new Invest("(5)","Fatcat incorporated",100,2.5,-25,100);
                break;
            case "(6)":
                invest = new Invest("(6)","Macrosoft",50,5.0,-20,10);
                break;
            case "(N)":
                invest = null;
                break;
        }
        return invest;
    }

    public static void calculateInvest(String answer, double money, Player player,double coeff){
        Random ran = new Random();
        int num=0;
        switch (answer){
            case "(1)":
               num = ran.nextInt(95)-5;
                if(num>0) {
                    player.playerGetsMoney(money * coeff);
                    System.out.printf("\nСпечелихте %.2f от инвестиция",money * coeff);
                }
                else {
                    player.playerLoseMoney(money * coeff);
                    System.out.printf("\nИзгубихте %.2f от инвестиция",money * coeff);
                }
                break;
            case "(2)":
                num = ran.nextInt(40)-10;
                if(num>0) {
                    player.playerGetsMoney(money * coeff);
                    System.out.printf("\nСпечелихте %.2f от инвестиция",money * coeff);
                }
                else {
                    player.playerLoseMoney(money * coeff);
                    System.out.printf("\nИзгубихте %.2f от инвестиция",money * coeff);

                }
                break;
            case "(3)":
                num = ran.nextInt(20)-15;
                if(num>0) {
                    player.playerGetsMoney(money * coeff);
                    System.out.printf("\nСпечелихте %.2f от инвестиция",money * coeff);
                }
                else {
                    player.playerLoseMoney(money*coeff);
                    System.out.printf("\nИзгубихте %.2f от инвестиция",money * coeff);

                }
                break;
            case "(4)":
                num = ran.nextInt(32)-18;
                if(num>0) {
                    player.playerGetsMoney(money * coeff);
                    System.out.printf("\nСпечелихте %.2f от инвестиция",money * coeff);
                }
                else {
                    player.playerLoseMoney(money*coeff);
                    System.out.printf("\nИзгубихте %.2f от инвестиция",money * coeff);

                }
                break;
            case "(5)":
                num = ran.nextInt(75)-25;
                if(num>0) {
                    player.playerGetsMoney(money * coeff);
                    System.out.printf("\nСпечелихте %.2f от инвестиция",money * coeff);
                }
                else {
                    player.playerLoseMoney(money*coeff);
                    System.out.printf("\nИзгубихте %.2f от инвестиция",money * coeff);

                }
                break;
            case "(6)":
                num = ran.nextInt(-10)-20;
                if(num>0)
                {
                    player.playerGetsMoney(money*coeff);
                    System.out.printf("\nСпечелихте %.2f от инвестиция",money * coeff);
                }
                else {
                    player.playerLoseMoney(money * coeff);
                    System.out.printf("\nИзгубихте %.2f от инвестиция",money * coeff);

                }
                break;
        }
    }
    public static void isPositionInvest(int position, Player player){
        if(board[position]=="I") {
            System.out.printf("\n стъпи на поле Invest",player.getName());
            if(player.getSteal().getType()==2 && player.getSteal().getActive()){
                player.playerGetsMoney(100);
            }
            for (int i = 0; i < 3; i++) {
                Invest invest = new Invest();
                invest = pickInvest();
                if (invest == null) {
                    System.out.printf("\n(N): Не ми се инвестира!");
                } else
                    System.out.printf("\n%s: %s| min: |%.2f risk/reward: %.2f", invest.getIndex(), invest.getName(),invest.getMinSum(), invest.getCoefficient());
            }
            System.out.println("\nИзбери една фирма, в която да инвестираш :");
            //sc.nextLine();
            String answer = sc.nextLine();
            Invest choseInvest = new Invest();
            choseInvest = chosenInvest(answer);
            if(choseInvest == null)
                System.out.println("Ти избра да не инвестираш!");
            else {
                System.out.printf("\nТи избра да инвестираш в %s\n", choseInvest.getName());
                System.out.println("посочи сумата, която си склонен да отделиш");
                double sum = sc.nextDouble();
                calculateInvest(answer, sum, player, choseInvest.getCoefficient());
            }
        }
    }

    public static void isPositionPartyHard(int position, Player player){
        player.playerLoseMoney(25);
    }
}
