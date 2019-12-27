package com.company.spmu.custom;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.global.FetchMode;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.sys.AbstractViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom implementation of cuba platform @{link {@link com.haulmont.cuba.core.sys.ViewRepositoryImpl}}.
 * Generates views for entity snapshots and caches it.
 */
public class CustomViewRepositoryImpl extends AbstractViewRepository implements CustomViewRepository {
    private static final Logger log = LoggerFactory.getLogger(CustomViewRepositoryImpl.class);

    @Override
    public void storeView(MetaClass metaClass, View view) {
        super.storeView(metaClass, view);
    }

    @Override
    public void createAndStoreView(Class className, String name) {

        MetaClass classNN = metadata.getClassNN(className);
        if (findView(classNN, name) != null) {
            return;
        }
        super.storeView(classNN, createView(classNN, name));
    }

    protected View createView(MetaClass meta, String name) {
        View view = new View(meta.getJavaClass(), name, true);
        for (MetaProperty metaProperty : meta.getProperties()) {
            switch (metaProperty.getType()) {
                case DATATYPE:
                case ENUM:
                    view.addProperty(metaProperty.getName());
                    break;
                case ASSOCIATION:
                case COMPOSITION:
                    MetaClass metaPropertyClass = metaProperty.getRange().asClass();

                    if (metadata.getTools().isEmbedded(metaProperty)) {
                        View embeddedViewWithRelations = createEmbeddedView(metaPropertyClass);
                        view.addProperty(metaProperty.getName(), embeddedViewWithRelations);
                    } else {
                        String viewName;
                        if (metaProperty.getRange().getCardinality().isMany()) {
                            viewName = View.LOCAL;
                        } else {
                            viewName = View.MINIMAL;
                        }
                        View propView = getView(metaPropertyClass, viewName);
                        view.addProperty(metaProperty.getName(),
                                new View(propView, viewName, true), FetchMode.BATCH);
                    }
                    break;
                default:
                    throw new IllegalStateException("unknown property type");
            }
        }
        return view;
    }

    protected View createEmbeddedView(MetaClass metaPropertyClass) {
        View propView = getView(metaPropertyClass, View.BASE);
        View embeddedViewWithRelations = new View(propView, metaPropertyClass.getName(), true);

        for (MetaProperty embeddedNestedProperty : metaPropertyClass.getProperties()) {
            if (embeddedNestedProperty.getRange().isClass() &&
                    !embeddedNestedProperty.getRange().getCardinality().isMany()) {
                View embeddedRelationView = getView(
                        embeddedNestedProperty.getRange().asClass(), View.MINIMAL);

                embeddedViewWithRelations.addProperty(embeddedNestedProperty.getName(), embeddedRelationView);
            }
        }
        return embeddedViewWithRelations;
    }
}
