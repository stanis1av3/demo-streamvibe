package com.example.demo.ussd.util;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ListPageObject {

    List<List<String>> pageable;
    Integer lastShownIndex;
    String data;
    Integer size;

    final String prevPage = "5 <<";
    final String nextPage = "6 >>";
    final String quitPage = "0 quit";


    public ListPageObject(List<String> list) {
        int[] index = {1};
        list = list.stream().map(s -> {
            s = index[0]++ +" "+s;
            if (index[0] > 5) {
                index[0] = 0;
            }
            return s;

        }).collect(Collectors.toList());

        this.size = list.size();
        this.pageable = new ArrayList<List<String>>();
        for (List<String> l : Lists.partition(list, 4)) {
            pageable.add(l);
        }


    }

    public List<String> getCurrent() {
        if (lastShownIndex == null) {
            lastShownIndex = 0;
        }
        List<String> elements = new ArrayList<>(pageable.get(lastShownIndex));
        elements.addAll(getControls());
        return elements;
    }

    public List<String> getPrev() {

        if (lastShownIndex > 0) {
            lastShownIndex--;
        }
        List<String> elements = new ArrayList<>(pageable.get(lastShownIndex));
        elements.addAll(getControls());
        return elements;
    }

    public List<String> getNext() {
        if (lastShownIndex < pageable.size() - 1) {
            lastShownIndex++;
        }
        List<String> elements = new ArrayList<>(pageable.get(lastShownIndex));
        elements.addAll(getControls());
        return elements;
    }

    private List<String> getControls() {
        List<String> controls = new ArrayList<>();
        if (!isFirst() && !isLast()) {
            controls.add(prevPage);
            controls.add(nextPage);
        }
        if (isFirst() && !isLast()) {
            controls.add(nextPage);
            controls.add(quitPage);
        }
        if (!isFirst() && isLast()) {
            controls.add(prevPage);
            controls.add(quitPage);
        }
        if (isFirst() && isLast()) {

        }
        return controls;
    }

    private boolean isFirst() {
        if (lastShownIndex == 0) {
            return true;
        }
        return false;
    }

    private boolean isLast() {
        if (lastShownIndex + 1 == pageable.size()) {
            return true;
        }
        return false;
    }

}