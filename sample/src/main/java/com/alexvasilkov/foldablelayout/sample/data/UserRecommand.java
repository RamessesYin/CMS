package com.alexvasilkov.foldablelayout.sample.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class UserRecommand {

    private static boolean hasCard(User user, long id) {
        if (user == null || user.cards == null)
            return false;
        if (user.id == id)
            return true;
        for (Card card : user.cards) {
            if (card.owner == id)
                return true;
        }
        return false;
    }

    public static List<Long> recommandUser(User user, int top_n) {


        List<Map.Entry<Long, Double>> commonFriends = getNeighbourScores(user);
        List<Map.Entry<Long, Double>> commonTags = getTagScores(user);

        List<Long> ids = new ArrayList<>();
        for (int i = 0, n = 0; n < top_n && i < commonFriends.size(); i++) {
            long id = commonFriends.get(i).getKey();
            if (!hasCard(user, id)) {
                ids.add(id);
                n++;
            }
        }

        for (int i = 0, n = 0; n < top_n && i < commonTags.size(); i++) {
            long id = commonTags.get(i).getKey();
            if (!hasCard(user, id)) {
                ids.add(id);
                n++;
            }

        }

        Log.d("UserRecommand", new ArrayList(ids).toString());
        return ids;
    }

    private static List<Map.Entry<Long, Double>> getNeighbourScores(User user) {

        HashMap<Long, Double> scores = new HashMap<Long, Double>();
        if (user == null || user.cards == null)
            return new ArrayList<>();
        for (Card card : user.cards) {
            double score = 1.0 / Math.log(card.getSharedto().size());
            for (long id : card.getSharedto()) {
                if (scores.containsKey(id))
                    scores.put(id, scores.get(id) + score);
                else
                    scores.put(id, score);
            }
        }
        List<Map.Entry<Long, Double>> list = new ArrayList<>(scores.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list;
    }


    private static List<Map.Entry<Long, Double>> getTagScores(User user) {
        HashMap<Long, Double> scores = new HashMap<Long, Double>();
        double totalCnt = 0;
        for (Tag tag : user.tags)
            totalCnt += tag.count;

        if (user == null || user.tags == null)
            return new ArrayList<>();
        for (Tag tag : user.tags) {
            double score = ((double) tag.count) / (totalCnt * Math.log(tag.getTaggedto().size()));
            for (long id : tag.getTaggedto()) {
                if (scores.containsKey(id))
                    scores.put(id, scores.get(id) + score);
                else
                    scores.put(id, score);
            }
        }

        List<Map.Entry<Long, Double>> list = new ArrayList<>(scores.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list;
    }
}
