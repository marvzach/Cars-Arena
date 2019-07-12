package com.marvin.cararenaa.util;

import com.marvin.cararenaa.models.Carzarena;

import java.util.ArrayList;

public interface OnRestaurantSelectedListener {
    public void onRestaurantSelected(Integer position, ArrayList<Carzarena> restaurants, String source);
}
