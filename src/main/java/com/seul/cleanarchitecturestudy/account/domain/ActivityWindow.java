package com.seul.cleanarchitecturestudy.account.domain;

import java.util.ArrayList;
import java.util.List;

public class ActivityWindow {

    private List<Activity> activities = new ArrayList<>();

    public Money calculateBalance(AccountId id) {
        return null;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }
}
