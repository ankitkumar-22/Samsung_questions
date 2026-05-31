/*
Exact same question to the tallest billboard
  https://leetcode.com/problems/tallest-billboard/

*/


class Solution {
    public int tallestBillboard(int[] rods) {
        HashMap<Integer,Integer> map = new HashMap<>();
        //map contains diff and largest pole length
        map.put(0,0);
        //acts as base 0 diff with max height of pole 0
        for(int rod : rods){
            //try adding each pole to the prev taller, smaller section or skip 
            HashMap<Integer,Integer> temp = new HashMap<>(map);
            for(int diff : temp.keySet()){
                int taller = temp.get(diff);
                int smaller = taller-diff;

                //add to the taller section
                int newTaller = taller + rod;
                int newDiff = newTaller-smaller;
                map.put(newDiff,Math.max(map.getOrDefault(newDiff,0),newTaller));

                //add to the smaller section
                int newSmaller = smaller+rod;
                newTaller = Math.max(newSmaller,taller);
                newSmaller= Math.min(newSmaller,taller);
                newDiff = newTaller-newSmaller;
                map.put(newDiff,Math.max(map.getOrDefault(newDiff,0),newTaller));

                //to skip this rod means adding the same thing again to the map 
                //so we add nothing

            }
        }
        return map.get(0);//get the taller section with diff 0
    }
}
