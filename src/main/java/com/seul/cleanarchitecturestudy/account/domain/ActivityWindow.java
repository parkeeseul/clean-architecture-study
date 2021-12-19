package com.seul.cleanarchitecturestudy.account.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ActivityWindow {

    @Getter private List<Activity> activities = new ArrayList<>();

    public ActivityWindow(@NonNull List<Activity> activities) {
        this.activities = activities;
    }

    public Money calculateBalance(AccountId id) {
        return null;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }
}
