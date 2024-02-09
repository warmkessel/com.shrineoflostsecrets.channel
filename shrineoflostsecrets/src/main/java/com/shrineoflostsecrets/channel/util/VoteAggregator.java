package com.shrineoflostsecrets.channel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shrineoflostsecrets.channel.database.entity.ShrineVote;

public class VoteAggregator {

  
    // The method to sum up the scores for each unique ID and sort them
    public List<Map.Entry<Long, Long>> sumAndSortVotes(List<ShrineVote> votes) {
        // Using a map to aggregate scores by ID
        Map<Long, Long> scoreMap = new HashMap<>();

        for (ShrineVote vote : votes) {
            scoreMap.merge(vote.getShrineChannelEventId(), vote.getAmount(), Long::sum);
        }

        // Convert the entry set of the map to a list and sort it by values in descending order
        List<Map.Entry<Long, Long>> sortedEntries = new ArrayList<>(scoreMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        return sortedEntries;
    }
}
