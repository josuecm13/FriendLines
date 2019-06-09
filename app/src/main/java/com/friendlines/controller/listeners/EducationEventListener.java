package com.friendlines.controller.listeners;

import com.friendlines.model.Education;

public interface EducationEventListener {
    void onEducationAdded(Education education);
    void onEducationChanged(Education education);
    void onEducationDeleted(Education education);
}
