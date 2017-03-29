package com.alex.jap;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 1/10/2017.
 */
public class AllurePublisher extends TestWatcher {
    private final boolean onFail;
    private final boolean onSuccess;
    private List<AllureAttachment> attachers = new ArrayList<>();

    private AllurePublisher(boolean onFail, boolean onSuccess) {
        this.onFail = onFail;
        this.onSuccess = onSuccess;
    }

    public static AllurePublisher onFail() {
        return new AllurePublisher(true, false);
    }

    public static AllurePublisher onSuccess() {
        return new AllurePublisher(false, true);
    }

    public static AllurePublisher always() {
        return new AllurePublisher(true, true);
    }

    public AllurePublisher publish(AllureAttachment attachment) {
        if (attachment == null)
            throw new IllegalArgumentException("Attachment cannot be null!");
        this.attachers.add(attachment);
        return this;
    }

    public AllurePublisher and(AllureAttachment attachment) {
        return publish(attachment);
    }

    @Override
    protected void succeeded(Description test) {
        if (onSuccess) {
            for (AllureAttachment attachment : attachers) {
                attachment.publish();
            }
        }
    }

    @Override
    protected void failed(Throwable e, Description description) {
        if (onFail) {
            for (AllureAttachment attachment : attachers) {
                attachment.publish();
            }
        }
    }
}
