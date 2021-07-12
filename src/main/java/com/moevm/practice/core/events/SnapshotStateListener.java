package com.moevm.practice.core.events;


/**
 * Следит за состоянием snapshotPointer
 */
public class SnapshotStateListener implements EventListener{

    @Override
    public String update(String eventType) {
        return eventType;
    }
}
