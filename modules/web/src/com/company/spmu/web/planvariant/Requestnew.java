package com.company.spmu.web.planvariant;

import com.company.spmu.web.RequestNewCloseAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

@UiController("spmu_Requestnew")
@UiDescriptor("RequestNew.xml")
public class Requestnew extends Screen {
    @Inject
    private LookupField requestTypeField;

    @Subscribe
    private void onInit(InitEvent event) {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Заявка на доходы", 1);
        map.put("Заявка на расходы", 2);
        map.put("Заявка на мероприятия", 3);
        this.requestTypeField.setOptionsMap(map);
    }

    @Subscribe("ok")
    private void onOk(Action.ActionPerformedEvent event) {
        Integer selected = (Integer) this.requestTypeField.getValue();
        if (selected != null) {
            this.close(new RequestNewCloseAction(selected));
        } else {
            this.closeWithDefaultAction();
        }
    }

    @Subscribe("cancel")
    private void onCancel(Action.ActionPerformedEvent event) {
        this.closeWithDefaultAction();
    }

}
