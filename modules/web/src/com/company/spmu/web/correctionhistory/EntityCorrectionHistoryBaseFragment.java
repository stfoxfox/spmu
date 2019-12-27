package com.company.spmu.web.correctionhistory;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.app.core.entitydiff.EntityDiffViewer;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiDescriptor("entity-history-correction-browse.xml")
public abstract class EntityCorrectionHistoryBaseFragment<T extends StandardEntity> extends Screen {
    @Inject
    protected DatatypeFormatter formatter;
    @Inject
    protected Messages messages;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected EntityDiffViewer diffFrame;

    @Inject
    protected Label titleLabel;

    protected T entity;

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }

    @Subscribe
    protected void onInit(BeforeShowEvent event) {
        this.titleLabel.setValue(resolveTitleValue());
        diffFrame.loadVersions(entity);
    }

    protected String resolveTitleValue() {
        Object[] messageParams = getMessageParams();
        if (messageParams.length == 0) {
            return messages.getMessage(messageBundle.getMessagesPack(), resolveMessageKey());
        }
        return messages.formatMessage(messageBundle.getMessagesPack(), resolveMessageKey(), messageParams);
    }

    protected String resolveMessageKey() {
        return "browse.title." + entity.getClass().getSimpleName();
    }

    protected abstract Object[] getMessageParams(String... params);
}
