package com.company.spmu.web.screens;

import com.company.spmu.entity.*;
import com.company.spmu.entity.Target;
import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.Timer;
import com.haulmont.cuba.gui.components.mainwindow.*;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.web.app.main.MainScreen;
import com.haulmont.cuba.web.gui.components.mainwindow.WebUserIndicator;

import javax.inject.Inject;

import java.util.Optional;


@UiController("topMenuMainScreen")
@UiDescriptor("ext-main-screen.xml")
public class ExtMainScreen extends MainScreen {
    private static final String BRANDING_CIRCLE_PNG = "branding/circle.png";

    @Inject
    private UiComponents uiComponents;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private Screens screens;
    @Inject
    private AppWorkArea workArea;
    @Inject
    private AppMenu mainMenu;
    @Inject
    private SideMenu sideMenu;
    @Inject
    private TimeSource timeSource;
    @Inject
    protected DatatypeFormatter formatter;

    @Override
    protected void initUserIndicator() {
        super.initUserIndicator();
        WebUserIndicator userIndicator = (WebUserIndicator) getUserIndicator();
        userIndicator.getComponent().addLayoutClickListener(event -> screenBuilders.editor(User.class, this)
                .editEntity(this.userSessionSource.getUserSession().getUser())
                .build()
                .show());
    }

    @Install(to = "userIndicator", subject = "userNameFormatter")
    private String userIndicatorUserNameFormatter(User user) {
        Employee employee = ((SpmuUser) user).getEmployee();
        return employee.getFirstname().substring(0, 1) + "." + employee.getLastname().substring(0, 1) + "." + employee.getSecondname();
    }
        


    @Override
    protected void initComponents(InitEvent e) {
        super.initComponents(e);
        initTimeZoneIndicator();

    }
    public void refreshTime(Timer source) {
        Label currentDateTime = getCurrentDateTime();
        currentDateTime.setValue(formatter.formatDateTime(timeSource.currentTimestamp()));
    }

    private void initTimeZoneIndicator() {
        Label currentDateTime = getCurrentDateTime();
        currentDateTime.setValue(formatter.formatDateTime(timeSource.currentTimestamp()));

    }

    public Label getCurrentDateTime() {
        return (Label) getWindow().getComponent("currentDateTime");
    }

    /**
     * Добавить пункт меню в боковое меню
     * @param index
     * @param screenId
     * @param caption
     * @param icon
     */
    private void addSideMenu(int index, String screenId, String caption, String icon)
    {
        SideMenu.MenuItem item = this.sideMenu.createMenuItem("menu_item" + index, caption, icon, menuItem -> activateScreen(screenId));
        this.sideMenu.addMenuItem(item, index);
    }

    /**
     * Добавить пункт меню в боковое меню (экран браузера)
     */
    private void addSideMenuItemBrowser(int index, Class entityClass, String screenId, String caption, String icon)
    {
        SideMenu.MenuItem item = this.sideMenu.createMenuItem("menu_item" + index, caption, icon, menuItem -> activateScreenBrowser(entityClass, screenId));
        this.sideMenu.addMenuItem(item, index);
    }

    private void activateScreenBrowser(Class entityClass, String screenId) {
        if (screenId.isEmpty()){
            return;
        }
        Optional<Screen> first = screens.getOpenedScreens().getAll().stream().
                filter(x -> screenId.equals(x.getId())).findFirst();

        if (first.isPresent()) {
            screens.showFromNavigation(first.get());
        } else {
            this.screenBuilders.lookup(entityClass, this)
                    .withLaunchMode(OpenMode.NEW_WINDOW)
                    .build()
                    .show();
        }

    }

    /**
     * Устанавливает активный экран.
     * Если экран уже открыт, то делает его активным. Если не открыт, то создает новый и открывает в новом окне.
     *
     * @param screenId {@link String}
     */
    private void activateScreen(String screenId) {
        if (screenId.isEmpty()){
            return;
        }
        Optional<Screen> first = screens.getOpenedScreens().getAll().stream().
                filter(x -> screenId.equals(x.getId())).findFirst();

        if (first.isPresent()) {
            screens.showFromNavigation(first.get());
        } else {
            screens.create(screenId, OpenMode.NEW_TAB).show();
        }
    }

    /**
     * Создаём боковое меню
     */
    private void initSideMenu()
    {
        this.sideMenu.removeAllMenuItems();

        // Добавляем в боковое меню пункты >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        int index = 0;
        int index2 = 0;

        // Заголовок
        SideMenu.MenuItem item = this.sideMenu.createMenuItem("menu_topheader");
        SpmuUser user = (SpmuUser) this.userSessionSource.getUserSession().getUser();
        if (user != null) {
            Affilate aff = user.getAffiliate();
            if (aff != null) {
                item.setCaption(aff.getName());
            }
        }
        item.addStyleName("topheader");
        this.sideMenu.addMenuItem(item, index++);

        SideMenu.MenuItem item2 = this.addMenuItemWithChilds(index++, "Мои заявки", BRANDING_CIRCLE_PNG);
        this.addChildItem(item2, index, index2++, "", "Проекты", BRANDING_CIRCLE_PNG);
        this.addChildItem(item2, index, index2++, "", "На согласовании", BRANDING_CIRCLE_PNG);
        this.addChildItem(item2, index, index2++, "", "Утверждены", BRANDING_CIRCLE_PNG);
        this.addChildItem(item2, index, index2++, "", "В плане", BRANDING_CIRCLE_PNG);

        index2 = 0;
        item2 = this.addMenuItemWithChilds(index++, "Заявки и мероприятия", BRANDING_CIRCLE_PNG);
        this.addChildMenuItemBrowser(item2, index, index2++, ApplicationIncome.class, "spmu_ApplicationIncome.browse", "Заявки на доходы", BRANDING_CIRCLE_PNG);
        this.addChildMenuItemBrowser(item2, index, index2++, ApplicationExpense.class, "spmu_ApplicationExpense.browse", "Текущие расходы", BRANDING_CIRCLE_PNG);
        this.addChildMenuItemBrowser(item2, index, index2++, ApplicationEvent.class, "spmu_ApplicationEvent.browse", "Заявки на мероприятия", BRANDING_CIRCLE_PNG);

        index2 = 0;
        item2 = this.addMenuItemWithChilds(index++, "Планы филиалов (ПДФ)", BRANDING_CIRCLE_PNG);
        this.addChildMenuItemBrowser(item2, index, index2++, PlanVariant.class, "spmu_PlanVariant.browse", "Все планы", BRANDING_CIRCLE_PNG);

        index2 = 0;
        item2 = this.addMenuItemWithChilds(index++, "Планы предприятия (ПДП)", BRANDING_CIRCLE_PNG);
        this.addChildItem(item2, index, index2++, "", "Централизованные планы мероприятий", BRANDING_CIRCLE_PNG);

        // Добавляем в боковое меню пункты <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    }

    /**
     * Инициализация верхнего меню
     */
    private void initMainMenu()
    {
        AppMenu.MenuItem item = mainMenu.createMenuItem("mainwm", "АИС СПМУФЦП");
        item.setCommand(menuItem -> {
            //showNotification("Экран", NotificationType.HUMANIZED);
        });
        mainMenu.addMenuItem(item, 0);

        item = mainMenu.createMenuItem("sidemenu_hide", "");
        item.setIcon(CubaIcon.ALIGN_JUSTIFY.source());
        item.setCommand(menuItem -> {
            this.sideMenu.setVisible(!this.sideMenu.isVisible());
        });
        mainMenu.addMenuItem(item, 1);
    }

    private SideMenu.MenuItem addMenuItemWithChilds(int index, String caption, String icon) {
        SideMenu.MenuItem item = this.sideMenu.createMenuItem("menu_item" + index, caption, icon, menuItem -> {});
        this.sideMenu.addMenuItem(item, index);
        return item;
    }

    private void addChildItem(SideMenu.MenuItem parent, int indexParent, int index, String screenId, String caption, String icon) {
        SideMenu.MenuItem item = this.sideMenu.createMenuItem("menu_item" + indexParent + index, caption, icon, menuItem -> activateScreen(screenId));
        parent.addChildItem(item);
    }

    /**
     * Добавить пункт меню в боковое меню (экран браузера)
     */
    private void addChildMenuItemBrowser(SideMenu.MenuItem parent, int indexParent, int index, Class entityClass, String screenId, String caption, String icon)
    {
        SideMenu.MenuItem item = this.sideMenu.createMenuItem("menu_item" + indexParent + index, caption, icon, menuItem -> activateScreenBrowser(entityClass, screenId));
        parent.addChildItem(item);
    }

    @Override
    protected void initMenu() {
        super.initMenu();
        this.initMainMenu();
        this.initSideMenu();
    }
}
