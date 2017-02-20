//Radu Molea(100992298)
//Assignment5

public class Customer 
{
    private String  name;
    private String  number;
    private PhonePlan plan;
    
    /*public Customer(){
      name = "???";
      number = "???";
      plan = new PhonePlan(0);
    }*/
    
    
    public Customer(String n, String num, PhonePlan p) 
    {
        name = n;
        number = num;
        plan = p;
    }
    
    public String    getName() { return name; }
    public String    getNumber() { return number; }
    public PhonePlan getPlan() { return plan; }
    public void    setPlan(PhonePlan p) { plan = p; }
    
    public String toString() 
    {
        return name + " [" + number + "] on a " + plan.toString();
    }
}