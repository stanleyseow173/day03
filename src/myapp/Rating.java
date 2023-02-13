package myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Rating {

    public static void main(String[] args) throws IOException{
        Path p = Paths.get(args[0]);
        File f = p.toFile();

        Map<String, Float> catAvgRatings = new HashMap<>();
        Map<String, Integer> catCount = new HashMap<>();
        Map<String, Float> catMinRating = new HashMap<>();
        Map<String, Float> catMaxRating = new HashMap<>();
        
        //open a file as input stream
        InputStream is = new FileInputStream(f);
        //convert the input stream to a reader byte -> char (2 byte)
        InputStreamReader isr = new InputStreamReader(is);
        //read whole lines
        BufferedReader br = new BufferedReader(isr); //just need to memorise this process

        String line;
        int numWords = 0;
        while ((line = br.readLine()) != null){
            //System.out.printf("> %s\n", line);
            String[] words = line.split(",");
            numWords += words.length;
            //Iterate all the words in the line
            int j = 0;
            
            boolean canPut = true;
            try{
                Float testFloat = Float.parseFloat(words[1].trim());
                canPut = false;
            }
            catch(Exception e){}
            finally{
                if (canPut){
                    if (!catAvgRatings.containsKey(words[1])){
                        try{
                            Float rating = Float.parseFloat(words[2].trim());
                            if (!Float.isNaN(rating)){
                                //System.out.printf("> %s --> %s --------------> ",words[1],words[2]);
                                //System.out.printf("> %s --> %f\n ",words[1],rating);
                                catAvgRatings.put(words[1],rating);
                                catCount.put(words[1],1);
                                catMinRating.put(words[1],rating);
                                catMaxRating.put(words[1],rating);
                            }
                            
                        }
                        catch (Exception e){
                        // System.out.println("Something went wrong");
                        }
                        finally {
                        }
                    } else{
                        try{
                            Float rating = Float.parseFloat(words[2].trim());
                            if (!Float.isNaN(rating)){
                                //System.out.printf("> %s --> %s --------------> ",words[1],words[2]);
                                //System.out.printf("> %s --> %f\n ",words[1],rating);
                                Float prevRating = catAvgRatings.get(words[1]); 
                                Integer prevCount = catCount.get(words[1]);
                                Float sumRatings = prevCount*prevRating + rating;
                                //System.out.printf(">> %f\n", sumRatings);
                                catCount.put(words[1],prevCount+1);
                                catAvgRatings.put(words[1],sumRatings/(prevCount+1));

                                if (rating > prevRating){
                                    catMaxRating.put(words[1],rating);
                                }
                                if (rating < prevRating){
                                    catMinRating.put(words[1],rating);
                                }
                                //System.out.printf("> %s --> %f\n ",words[1],rating);
                            }
                        }
                        catch(Exception e){
                        }
                        finally{}
                    }
                }
            }
            /*
            for (String w: words){
                if (j==1){
                    String t = w.trim().toLowerCase();
                    if (!catAvgRatings.containsKey(t)){
                        catAvgRatings.put(t,)
                    }
                    System.out.printf("> %s\n", t);
                }
                j++;
                
                
                if (t.length()<=0){
                    continue;
                }
                if (!wordFreq.containsKey(t)){
                    //if word is not in map, initalize the word with frequency of 1
                    wordFreq.put(t,1);
                } else{
                    //if word is in map, then increment the count
                    int c = wordFreq.get(t);
                    wordFreq.put(t,c+1);
                }
                
            }*/
        }

        //Get a list of all the words
        
        Set<String> words = catAvgRatings.keySet();
        for (String w: words){
            float avgRating = catAvgRatings.get(w);
            System.out.printf(": %s ===> Average Rating is %f, Max Rating is %f, Min Rating is %f \n",w,avgRating,catMaxRating.get(w),catMinRating.get(w));
        }

        //System.out.printf("Number of words: %d\n", numWords);
        //System.out.printf("Number of unique words: %d\n", words.size());
        //System.out.printf("Percentage of unique words: %f\n", words.size()/(float)numWords);

        br.close();
        isr.close();
        is.close();
    }
}
