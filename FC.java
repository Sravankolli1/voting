package votingsystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Region{
	String name;
    Map<String,Integer> analysis = new HashMap<String,Integer>();
    int invalid=0;
	
}

public class FC {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<Integer> voters = new ArrayList<Integer>();
		Map<String,Integer> total_results = new HashMap<String,Integer>();
		List<Region> regions = new ArrayList<Region>();
		String file = "/Users/sravankolli/Desktop/java_tests/javaprograms/src/votingsystem/voting.dat";
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String initial ="vizag/ABC";
			Region currentRegion = null;
			while((initial = br.readLine())!=null) {
				
				if(initial.equals("//")) {
					//continue;
					
					while(!(initial =br.readLine()).equals("&&")){

						if(initial.equals("//")) {
							continue;
						}
						else if(Character.isDigit(initial.charAt(0))) {
							String[] vt = initial.split(" ");
							String[] slicedArray = Arrays.copyOfRange(vt, 1, vt.length);
					
							//is invalid vote?
							Integer voterId = Integer.valueOf(vt[0]);
							if(voters.contains(voterId)) {
								//if invalid
								currentRegion.invalid+=1;
								
							}
							else if(vt.length>4 || vt.length<2) {
								//if invalid
								currentRegion.invalid+=1;
							}
							else if(!FC.allKeysPresent(currentRegion,slicedArray)) {
								//invalid
								currentRegion.invalid+=1;
							}
							else {
								voters.add(voterId);
								for(int i=0;i<slicedArray.length;i++) {
									String votedFor = slicedArray[i];
									Integer currentVotes = currentRegion.analysis.get(votedFor);
									currentVotes+= i*(-1)+3;
									currentRegion.analysis.put(votedFor, currentVotes);
									Integer totalCurrentVotes = total_results.get(votedFor);
									totalCurrentVotes+=i*(-1) +3;
									total_results.put(votedFor, totalCurrentVotes);

									
								}
							}
							//System.out.println(initial);

							
						}
						else {
							//System.out.println(initial+"done");
							for(Region regn:regions) {
								if(regn.name.equals(initial)) {
									currentRegion = regn;
								}
							}
						}
					}
				}
				else if(initial.contains("/")) {
						
					
						String[] tmp = initial.split("/");
						char[] cnts = tmp[1].toCharArray();
						//System.out.println(cnts);
						 Region r = new Region();
						 //Initializing region r fields
						 r.name = tmp[0];
						 
						 for(char cntst:cnts) {
							 r.analysis.put(String.valueOf(cntst), 0);
							 if(!(total_results.containsKey(String.valueOf(cntst)))) {
								 total_results.put(String.valueOf(cntst), 0);
							 }
							 
						 }
						 //AAdding region object to regions list (verify before proceeding)
						 regions.add(r);
						
				}
				
				if(initial.equals("&&")) {
					break;
				}
				
				
				
			}//END WHILE
			
			
			FC.ElectionResults(regions,total_results);
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private static void ElectionResults(List<Region> regions,Map<String,Integer> total_results) {
		// TODO Auto-generated method stub
		
		for(Region r:regions) {
			String wonAtRegion = "";
			Integer currentHigh =0;
			System.out.println(r.analysis.toString());
			if(r.analysis.keySet().size()>=1) {
				for(String candidate:r.analysis.keySet()) {
					if(r.analysis.get(candidate)>currentHigh) {
						currentHigh = r.analysis.get(candidate);
						wonAtRegion = candidate;
						//System.out.println(currentHigh);
					}
				}
				System.out.println(r.name+" REGIONAL HEAD is "+wonAtRegion+"  with score:"+currentHigh+" And No of invalid votes in this region is: "+ r.invalid);
			}
			
		}
		String ChiefOfficer = "";
		Integer currentHigh = 0;
		for(String cd:total_results.keySet()) {
			
			if(total_results.get(cd)>currentHigh) {
				ChiefOfficer = cd;
				currentHigh = total_results.get(cd);
			}
			
		}
		System.out.println(total_results.toString());
		System.out.println("Chief Officer is: "+ChiefOfficer +"  with total votes: "+ currentHigh);
	}

	private static boolean allKeysPresent(Region currentRegion,String[] slicedarray) {
		boolean allKeysPresent = true;
		HashMap<String,Integer> hm =new HashMap<String,Integer>();
		// TODO Auto-generated method stub
		for(String i:slicedarray) {
			if(!currentRegion.analysis.containsKey(i)) {
				allKeysPresent=false;
				
		    }
		}
		return allKeysPresent;
	}

}
