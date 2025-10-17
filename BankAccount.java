

public class BankAccount {
    //Constants
    private  static  final double CURRENT_ACCT_MIN_BALANCE=50.0;
    private  static  final double SAVINGS_ACCT_MIN_BALANCE=150.0;
    private static final double SAVINGS_ACCT_INTEREST_RATE=0.60;
    private static final double  CURRENT_ACCT_MAINTENANCE_FEE=10.0;
    private static final int SAVINGS_WITHDRAWAL_LIMIT=2;

    //Data fields
    AccountType acctType;
    String acctID;
    double balance;
    int numWithdrawals;
    boolean inTheRed;
    double minBalance;
    double interestRate;
    double maintenanceFee;
    int withdrawalLimit;//Note that if there is no withdrawal limit, we set the withdrawalLimit member variable to -1.
    double openingBalance;
    double addAmount;

    //Constructors

    public BankAccount(AccountType acctType, String acctID) {
        this.acctType = acctType;
        this.acctID = acctID;
        balance=0;
        numWithdrawals=0;//sth about for the current month.
        if(acctType==AccountType.CURRENT){
            minBalance=CURRENT_ACCT_MIN_BALANCE;
            interestRate=0;
            maintenanceFee=CURRENT_ACCT_MAINTENANCE_FEE;
            withdrawalLimit=-1;//no limit
        }
        else{minBalance=SAVINGS_ACCT_MIN_BALANCE;
        interestRate=SAVINGS_ACCT_INTEREST_RATE;
        maintenanceFee=0;
        withdrawalLimit=SAVINGS_WITHDRAWAL_LIMIT;

            }
        inTheRed = balance < minBalance;
    }

    public BankAccount(AccountType acctType, String acctID, double openingBalance) {
        this.acctType = acctType;
        this.acctID = acctID;
        this.openingBalance = openingBalance;
        balance= openingBalance;
        numWithdrawals=0;
        if(acctType==AccountType.CURRENT){
            minBalance=CURRENT_ACCT_MIN_BALANCE;
            interestRate=0;
            maintenanceFee=CURRENT_ACCT_MAINTENANCE_FEE;
        }
        else{minBalance=SAVINGS_ACCT_MIN_BALANCE;
            interestRate=SAVINGS_ACCT_INTEREST_RATE;
            maintenanceFee=0;

        }
        inTheRed = balance < minBalance;
    }
    //GETTERS
    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType(){
        return acctType;
    }

    public String getAccountID(){
        return acctID;
    }

    public double getMinBalance(){
        return minBalance;
    }



     //METHODS
    public boolean withdraw(double amount){

        //Check withdrawal limit
        if(withdrawalLimit!=-1 && numWithdrawals>=withdrawalLimit) {
            System.out.println("Sorry, could not perform withdrawal: Withdrawal limit exceeded.");
            return false;}


        //Check if it is in the red
         if(inTheRed){
            System.out.println("Sorry, could not perform withdrawal: Account is in the red.");
            return false;}


         //Check for insufficient balance
        if( balance-amount>minBalance){
            System.out.println("Sorry, could not perform withdrawal: Insufficient balance.");
            return false;}

        balance=balance-amount;
        numWithdrawals++;
        inTheRed = balance < minBalance;
        return true;
    }


    public void deposit(double amount){//assuming amount will always be a positive non-zero number
        addAmount=balance + amount;
        inTheRed = balance < minBalance;

    }

    public void performMonthlyMaintenance(){
        double earnedInterest=balance*(interestRate/12);
        balance+=earnedInterest;

        balance-=earnedInterest;
        inTheRed = balance < minBalance;
        numWithdrawals = 0;

        System.out.printf("Earned interest: %.2f\n", earnedInterest);
        System.out.printf("Maintenance fee: %.2f\n", maintenanceFee);
        System.out.printf("Updated balance: %.2f\n", balance);

        if(inTheRed){
            System.out.println("WARNING: This account is in the red!");
        }
    }




    public boolean transfer(boolean transferTo, BankAccount otherAccount,  double amount){//returns whether or not the transaction was successful.
      if(transferTo){ if (this.withdraw(amount)) {
          otherAccount.deposit(amount);
          return true;
      } else return false;
      } else {
          if (otherAccount.withdraw(amount)) {
              this.deposit(amount);
              return true;
          } else return false;
      }
    }

      }




