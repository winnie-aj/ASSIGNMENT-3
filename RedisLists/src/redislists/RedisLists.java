/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redislists;

import redislists.stats;
import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 *
 * @author USER
 */
public class RedisLists {
    static HashMap<Double, String> redisData = new HashMap<Double, String>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
       stats faac = new stats();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> value = new ArrayList<String>();
       // HashMap<Double, String> redisData = new HashMap<Double, String>();
        
             if (jedis.llen("state") == 0 && jedis.llen("number") == 0)  {
             //jedis.zadd("faac", (Map) Info.map);
             for(Map.Entry m: stats.map.entrySet()){
                 
                 jedis.lpush("state",(String)m.getValue());
                 jedis.lpush("number",m.getKey().toString());
                 
             }
         } else {
         }
        for(String s: jedis.lrange("state", 0, 100)){
            names.add(s);
                       
          }
         for(String r: jedis.lrange("number", 0, 100)){
               value.add(r);
            }
         for(int i =0; i < names.size(); i++){
             redisData.put(Double.parseDouble(value.get(i)), names.get(i));
         }
//         for(Tuple t: jedis.zrangeByScoreWithScores("faac", 0, 100)){
//        System.out.println(t.getScore());
//        redisData.put(t.getScore(), t.getElement());
//        }
         
        ArrayList<String> states = new ArrayList<>();
        redisData.entrySet().stream().forEach((m) -> {
            states.add((String)m.getValue());
        });
         
         String[] statesArray = new String[states.size()];
         states.toArray(statesArray);

        

        JComboBox<String> stateList = new JComboBox<>(statesArray);
        stateList.addItemListener(new Handler());
       // stateList.addItemListener(null);

// add to the parent container (e.g. a JFrame):
        JFrame jframe = new JFrame();
        JLabel item1 = new JLabel("OUT OF SCHOOL CHILDREN 2013-2014");
        //item1.setToolTipText("By Ajiniran winifred");
        jframe.add(item1);
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(new FlowLayout());
        jframe.setSize(250,180);
        jframe.setVisible(true);
        
        jframe.add(stateList);
        
        

// get the selected item:
       // String selectedBook = (String) stateList.getSelectedItem();
       

        // check whether the server is running or not
        System.out.println("Server is running: " + jedis.ping());
        //getting the percentage for each state
       

        // storing the data into redis database
      
        
        for (Map.Entry m : stats.map.entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue());

  
        }
    }
    private static class Handler implements ItemListener{
//

//           JOptionPane.showMessageDialog(null, String.format("%s", e.getActionCommand()));
//        }

        @Override
        public void itemStateChanged(ItemEvent e) {
             for (Map.Entry m : redisData.entrySet()) {
if(e.getItem().toString() == m.getValue()&& e.getStateChange() == 1){
                     
                     JOptionPane.showMessageDialog(null, m.getKey(), "VALUE IN THOUSANDS", 1);
                     //JOptionPane.showMessageDialog(null, m.getKey());
                     System.out.println(m.getKey());
                     break;
                     
                 }
          //  System.out.println(m.getKey() + " " + m.getValue());

            //jedis.llen("faac", M)
        }
       //     System.out.println(e.getItem().toString());
        }
        
    }

}
