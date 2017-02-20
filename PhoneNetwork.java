//Radu Molea(100992298)
//Assignment5

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class PhoneNetwork {
  
  private ArrayList<Customer> customers; //represents customers that will make phonecalls
  private HashMap<String, ArrayList<Call>>  outgoingCalls;  //key is a phone sumber
  private HashMap<String, ArrayList<Call>> incomingCalls;   //maps to list of calls made from that #
  
  //Constructor
  public PhoneNetwork(){    
    customers = new ArrayList<Customer>();
    outgoingCalls = new HashMap<String, ArrayList<Call>>();
    incomingCalls = new HashMap<String, ArrayList<Call>>();
  }
  
  //Get methods
  public ArrayList<Customer> getCustomers(){ 
    return customers;
  }
  public HashMap<String, ArrayList<Call>> getOutgoingCalls() {
    return outgoingCalls;
  }
  public HashMap<String, ArrayList<Call>> getIncomingCalls() {
    return incomingCalls;
  }
  
  //Registers a new customer into the network
  public void register(String name, String number, int minutes){
    Customer customer;
    PhonePlan phonePlan;
    phonePlan = new PhonePlan(minutes);
    customer = new Customer(name, number, phonePlan); //Creates the customer with info from parameter
    customers.add(customer);    //inserts the customer into the arraylist of all the customers
  }
  
  //make call from one phone number to another of specified duration
  public void makeCall(String from, String to, int seconds){
    Call call;
    Customer fromC = new Customer(null, null, null); //from which customer call is made
    Customer toC = new Customer(null, null, null);   //to which customer call is made
    int minutes;
    
    for(int i=0; i<customers.size(); i++) {
      if(customers.get(i).getNumber().equals(from)) 
        fromC = customers.get(i);               //finds fromCustomer associated with phone #
      else if (customers.get(i).getNumber().equals(to))
        toC = customers.get(i);                //same but for toCustomer
    }
    fromC.getPlan().setMinutesUsed(secToMin(seconds)); //registers length of call
    toC.getPlan().setMinutesUsed(secToMin(seconds));   //using custom method converts seconds to minutes
    call = new Call(fromC, toC, new Date(), seconds);  //makes apropriate call object
    addToList(outgoingCalls, fromC.getNumber(), call); //registers the call as outgoing
    addToList(incomingCalls, toC.getNumber(), call); //or incoming, using custom method
  }
  
  //method that adds a call to specific arraylist in out/incoming hashmap
  public void addToList(HashMap<String, ArrayList<Call>> hMap,
                        String key, Call call){
    ArrayList<Call> callList = hMap.get(key); //finds arraylist associated with phone# key
    
    if(callList == null){         //special case 
      callList = new ArrayList<Call>();  //to avoid nullpoint exception
      callList.add(call);             //adds the call to arraylist
      hMap.put(key, callList);       //inserts the modified arraylist back into hashmap
    }
    else{                   //general case
      callList.add(call);        
      hMap.put(key, callList);
    }
  }
  
  //method that converts seconds to minutes according to company policy
  public static int secToMin(int seconds){
    int minutes;
    minutes = seconds/60;   
    if (seconds%60 > 0) 
      minutes++;        //$++
    return minutes;   
  }
  
  //displays stats about each registered customer
  public void displayStats(){
    
    System.out.println("PHONE NUMBER    NAME                 OUT  IN" +
                       "     PLAN     USED     OVER      BA" +
                       "SE     EXTRA       HST     TOTAL");
                         
    for(int i=0; i<customers.size(); i++){     //scans through the list of customers
      String phoneNum = customers.get(i).getNumber();
      String name = customers.get(i).getName();
      int outNum = 0;
      if (outgoingCalls.get(phoneNum) == null) //avoids null exception
        outNum = 0;
      else
        outNum = outgoingCalls.get(phoneNum).size();
      int inNum = 0;
      if (incomingCalls.get(phoneNum) == null)
        inNum = 0;
      else
        inNum = incomingCalls.get(phoneNum).size();
      int planMinutes = customers.get(i).getPlan().getMinutesAllowed();
      int minUsed = customers.get(i).getPlan().getMinutesUsed();
      int minOver = 0;
      if (planMinutes-minUsed < 0)
        minOver = minUsed - planMinutes;
      else if(planMinutes-minUsed >= 0)
        minOver = 0;
      float basePrice = 20 + 5*((planMinutes - 100)/100);    
      float extra = minOver * .15f; 
      float tax = (basePrice+extra)*.13f;
      float total = basePrice + extra + tax;
      
      System.out.println(String.format("%-16s%-23s%-4s%-7s%-9s%-9s%-9s%-10.2f%-11.2f%-8.2f$%.2f", 
                                       phoneNum, name, outNum, inNum, planMinutes, minUsed, minOver,
                                       basePrice, extra, tax, total));
    }
  }
  
  //returns customer with most calls outgoing calls
  public Customer customerMakingMostCalls(){
    int maxNum = 0;
    int outNum = 0;
    int index = 0;
    for(int i=0; i<customers.size(); i++){            
      String phoneNum = customers.get(i).getNumber(); //scans through customers' phone numbers
      if (outgoingCalls.get(phoneNum) == null)       //looks in hashmap using phone number key
        outNum = 0;                                 //special case where no calls were made
      else                                           //in cases where calls were made:
        outNum = outgoingCalls.get(phoneNum).size(); //registers the amount of calls
      if(outNum > maxNum){                         //compares # of calls of this customer
        maxNum = outNum;                           //to the highest so far
        index = i;                                //if higher it will be registered
      }
    }
    return customers.get(index);      
  }
  
  //returns customer with most incoming calls
  public Customer customerReceivingMostCalls(){
    int maxNum = 0;
    int inNum = 0;
    int index = 0;
    for(int i=0; i<customers.size(); i++){
      String phoneNum = customers.get(i).getNumber();
      if (incomingCalls.get(phoneNum) == null)
        inNum = 0;
      else
        inNum = incomingCalls.get(phoneNum).size();
      if (inNum > maxNum){
        maxNum = inNum;
        index = i;
      }
    }
    return customers.get(index);     
  }
  
  //returns a boolean indicating whether or not a call was made from phone# n1 to n2
  public boolean wasCallMade(String n1, String n2){
    Customer n2C = new Customer(null, null, null); //will be customer associated with phone# n2
   
    for(int i=0; i<customers.size(); i++) {
      if (customers.get(i).getNumber().equals(n2))
        n2C = customers.get(i);                  //finds customer with phone# n2
    }
    if (outgoingCalls.get(n1) != null){         //avoid nullpointer error
      for(int i=0; i<outgoingCalls.get(n1).size(); i++){ //scan through outgoing calls made by phone# n1
        if(outgoingCalls.get(n1).get(i).getMadeTo() == n2C) //if phone# n1 made any calls to n2Customer
          return true;                                  
      }
    }
    return false;
  }    
}