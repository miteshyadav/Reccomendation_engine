
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


public class Vito_Recom {
	
	
	int maxRow(int a,int b[][])
	{
		  int n = b.length;
          int temp = 0;
         
          return b[a][n-1];
	}
	public static void main(String[] args) throws IOException
	{
		
		
		String dir ="/media/mitesh/Acer/hadoop/thinkanalyt/implement/inputn";
		File dirObj = new File(dir);
		int c=0;
		int c1=0;
		int c2=0;
	
		Vector<String> v1= new Vector<String>(10000000);
		Vector<Integer> v2= new Vector<Integer>(1000000);
		Vector<Integer> v3= new Vector<Integer>(1000000);
		
		
		
		File[] files = dirObj.listFiles();
		for(File file : files)
		{
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			
			String line = br.readLine();
			if(c==0)
			{
				line=br.readLine();
			}
			
		
			//processing each line and adding unique values of customer, offers and categories to a Vector
			while(line!=null)
			{
				if(c!=0)
				{
				
				String[] tokens = line.split(",");
				
				int temp=Integer.parseInt(tokens[2]);
				int temp2=Integer.parseInt(tokens[3]);
				if(tokens[0].equals("OfferViewed")||tokens[0].equals("OfferSaved")||tokens[0].equals("OfferShared"))
				{
					if(!(v1.contains(tokens[1])) )
					{
					v1.add(tokens[1]);
					
					}
					 if(!(v2.contains(temp)))
					{
						
						v2.add(temp);
					}
					 if(!(v3.contains(temp2)))
					{
					
						v3.add(temp2);
					}
					
					
				}
				}
				c++;
			
				
				line= br.readLine();
				
			}
			
		}
	

		File dirObj2 = new File(dir);
		File[] files1 = dirObj.listFiles();
		// for creating a 2-dimensional matrix of
		//user-offer relation, user-category relation and category-offer relation
		int offer[][]=new int [v1.size()][v2.size()];
		int categ[][]=new int [v1.size()][v3.size()];
		int ofcat[][]=new int[v3.size()][v2.size()];
		
		
		
		for(File file : files1)
		{
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			
			String line = br.readLine();
			
			int cnt=0;
			int t1=0;
			int t2=0;
			int t3=0;
			int temp;
			int temp2;
			while(line!=null)
			{
				if(cnt!=0)
				{
				
				String[] tokens = line.split(",");
			
				 temp=Integer.parseInt(tokens[2]);
				 temp2=Integer.parseInt(tokens[3]);
				t1=v1.indexOf(tokens[1]);
				t2=v2.indexOf(temp);
				t3=v3.indexOf(temp2);
			//assigning rating as 1 if its an "offerViewed" for a user
				
					if(tokens[0].equals("OfferViewed"))
					{
						offer[t1][t2]=1;
						categ[t1][t3]=1;
						ofcat[t3][t2]=1;
					
					}
			//assigning rating as 1 if its an "Shared" for a user
					else if(tokens[0].equals("OfferShared"))
					{
						offer[t1][t2]=2;
						categ[t1][t3]=2;
						ofcat[t3][t2]=1;
						
					}
			//assigning rating as 1 if its an "offerSaved" for a user
					else if(tokens[0].equals("OfferSaved"))
					{
						offer[t1][t2]=3;
						categ[t1][t3]=3;
						ofcat[t3][t2]=1;
					
					}
				
				}
				cnt++;
			
			
				line= br.readLine();
			}
			
		
			
		
		}
		
	
		//summation of offer values based on the ratings given by all users
		int n1=v2.size();
		
		int s1=0;
		int summation[]= new int[n1];;
		
		for(int i=0;i<v2.size();i++)
		{
			s1=0;
			for(int j=0;j<v1.size();j++)
			{
				s1+=offer[j][i];
			}
			summation[i]=s1;
		
		}
		
		
		
		
		Scanner sc=new Scanner(System.in);
		System.out.println("enter user name:");
		String us=sc.nextLine();
		int user=v1.indexOf(us.trim());
		int tem[][]=categ;
		int n=v3.size();
		int max=0;
		
		for(int i=1;i<v3.size();i++)
		{
			if(tem[user][i]>tem[user][max])
			{
				max=i;
			}
		}
		
		System.out.println("recomended category for user:"+v3.get(max));
		
		
		//Adding offer_ids(indexes of offer) belonging to that category into a result vector
		
    
        Vector<Integer> result= new Vector<Integer>(1000000);
        for(int i=0;i<v2.size();i++)
        {
        	if(ofcat[max][i]==1)
        	{
        		result.add(i); 
        	}
        }
     
        //Creating a HashMap data structure where key will be the index of the offer and value will be the summation of that particular 
        //offer located in that index
        
        
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i=0;i<result.size();i++)
        {
        	map.put(result.get(i), summation[result.get(i)]);
        }
        //putting the offer values and it's corresponding summation value into a list and then sorting
       
        Set<Entry<Integer, Integer>> set = map.entrySet();
        List<Entry<Integer, Integer>> list = new ArrayList<Entry<Integer, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<Integer, Integer>>()
        {
            public int compare( Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        int cz=0;
        //reccomending top 5 offers based on highest summation values for that respective category
       label1: for(Map.Entry<Integer, Integer> entry:list)
       {
        	if(cz<5)
        	{
            System.out.println("final recommended offer:+"+v2.get(entry.getKey())+" ==== "+entry.getValue());
        	}
        	else
        	{
        		break label1;
        	}
        	cz++;
       }
   
		
	
	}
	
}			
		
	
		
		
		
		
	


			
		
	