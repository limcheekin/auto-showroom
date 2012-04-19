package com.springroo.autoshowroom.jsf;

import com.springroo.autoshowroom.domain.Car;
import com.springroo.autoshowroom.domain.Customer;
import com.springroo.autoshowroom.domain.Model;
import com.springroo.autoshowroom.jsf.converter.CustomerConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.validator.LongRangeValidator;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;
import org.primefaces.component.spinner.Spinner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@Configurable
@ManagedBean(name = "carBean")
@SessionScoped
@RooSerializable
@RooJsfManagedBean(entity = Car.class, beanName = "carBean")
public class CarBean implements Serializable {

	private String name = "Cars";

	private Car car;

	private List<Car> allCars;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("modelName");
        columns.add("price");
        columns.add("dateOfProduction");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Car> getAllCars() {
        return allCars;
    }

	public void setAllCars(List<Car> allCars) {
        this.allCars = allCars;
    }

	public String findAllCars() {
        allCars = Car.findAllCars();
        dataVisible = !allCars.isEmpty();
        return null;
    }

	public boolean isDataVisible() {
        return dataVisible;
    }

	public void setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }

	public HtmlPanelGrid getCreatePanelGrid() {
        if (createPanelGrid == null) {
            createPanelGrid = populateCreatePanel();
        }
        return createPanelGrid;
    }

	public void setCreatePanelGrid(HtmlPanelGrid createPanelGrid) {
        this.createPanelGrid = createPanelGrid;
    }

	public HtmlPanelGrid getEditPanelGrid() {
        if (editPanelGrid == null) {
            editPanelGrid = populateEditPanel();
        }
        return editPanelGrid;
    }

	public void setEditPanelGrid(HtmlPanelGrid editPanelGrid) {
        this.editPanelGrid = editPanelGrid;
    }

	public HtmlPanelGrid getViewPanelGrid() {
        if (viewPanelGrid == null) {
            viewPanelGrid = populateViewPanel();
        }
        return viewPanelGrid;
    }

	public void setViewPanelGrid(HtmlPanelGrid viewPanelGrid) {
        this.viewPanelGrid = viewPanelGrid;
    }

	public HtmlPanelGrid populateCreatePanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText modelNameCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        modelNameCreateOutput.setId("modelNameCreateOutput");
        modelNameCreateOutput.setValue("Model Name: * ");
        htmlPanelGrid.getChildren().add(modelNameCreateOutput);
        
        InputText modelNameCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        modelNameCreateInput.setId("modelNameCreateInput");
        modelNameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.modelName}", String.class));
        htmlPanelGrid.getChildren().add(modelNameCreateInput);
        
        Message modelNameCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        modelNameCreateInputMessage.setId("modelNameCreateInputMessage");
        modelNameCreateInputMessage.setFor("modelNameCreateInput");
        modelNameCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(modelNameCreateInputMessage);
        
        HtmlOutputText modelCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        modelCreateOutput.setId("modelCreateOutput");
        modelCreateOutput.setValue("Model: * ");
        htmlPanelGrid.getChildren().add(modelCreateOutput);
        
        AutoComplete modelCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        modelCreateInput.setId("modelCreateInput");
        modelCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.model}", Model.class));
        modelCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{carBean.completeModel}", List.class, new Class[] { String.class }));
        modelCreateInput.setDropdown(true);
        modelCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(modelCreateInput);
        
        Message modelCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        modelCreateInputMessage.setId("modelCreateInputMessage");
        modelCreateInputMessage.setFor("modelCreateInput");
        modelCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(modelCreateInputMessage);
        
        HtmlOutputText priceCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        priceCreateOutput.setId("priceCreateOutput");
        priceCreateOutput.setValue("Price: * ");
        htmlPanelGrid.getChildren().add(priceCreateOutput);
        
        Spinner priceCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        priceCreateInput.setId("priceCreateInput");
        priceCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.price}", Integer.class));
        priceCreateInput.setRequired(true);
        priceCreateInput.setMin(10.0);
        LongRangeValidator priceCreateInputValidator = new LongRangeValidator();
        priceCreateInputValidator.setMinimum(10);
        priceCreateInput.addValidator(priceCreateInputValidator);
        
        htmlPanelGrid.getChildren().add(priceCreateInput);
        
        Message priceCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        priceCreateInputMessage.setId("priceCreateInputMessage");
        priceCreateInputMessage.setFor("priceCreateInput");
        priceCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(priceCreateInputMessage);
        
        HtmlOutputText dateOfProductionCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dateOfProductionCreateOutput.setId("dateOfProductionCreateOutput");
        dateOfProductionCreateOutput.setValue("Date Of Production:   ");
        htmlPanelGrid.getChildren().add(dateOfProductionCreateOutput);
        
        Calendar dateOfProductionCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        dateOfProductionCreateInput.setId("dateOfProductionCreateInput");
        dateOfProductionCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.dateOfProduction}", Date.class));
        dateOfProductionCreateInput.setNavigator(true);
        dateOfProductionCreateInput.setEffect("slideDown");
        dateOfProductionCreateInput.setPattern("dd/MM/yyyy");
        dateOfProductionCreateInput.setRequired(false);
        dateOfProductionCreateInput.setMaxdate(new Date());
        htmlPanelGrid.getChildren().add(dateOfProductionCreateInput);
        
        Message dateOfProductionCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        dateOfProductionCreateInputMessage.setId("dateOfProductionCreateInputMessage");
        dateOfProductionCreateInputMessage.setFor("dateOfProductionCreateInput");
        dateOfProductionCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(dateOfProductionCreateInputMessage);
        
        HtmlOutputText customerCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        customerCreateOutput.setId("customerCreateOutput");
        customerCreateOutput.setValue("Customer: * ");
        htmlPanelGrid.getChildren().add(customerCreateOutput);
        
        AutoComplete customerCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        customerCreateInput.setId("customerCreateInput");
        customerCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.customer}", Customer.class));
        customerCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{carBean.completeCustomer}", List.class, new Class[] { String.class }));
        customerCreateInput.setDropdown(true);
        customerCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "customer", String.class));
        customerCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{customer.name} #{customer.address} #{customer.email}", String.class));
        customerCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{customer}", Customer.class));
        customerCreateInput.setConverter(new CustomerConverter());
        customerCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(customerCreateInput);
        
        Message customerCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        customerCreateInputMessage.setId("customerCreateInputMessage");
        customerCreateInputMessage.setFor("customerCreateInput");
        customerCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(customerCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText modelNameEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        modelNameEditOutput.setId("modelNameEditOutput");
        modelNameEditOutput.setValue("Model Name: * ");
        htmlPanelGrid.getChildren().add(modelNameEditOutput);
        
        InputText modelNameEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        modelNameEditInput.setId("modelNameEditInput");
        modelNameEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.modelName}", String.class));
        htmlPanelGrid.getChildren().add(modelNameEditInput);
        
        Message modelNameEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        modelNameEditInputMessage.setId("modelNameEditInputMessage");
        modelNameEditInputMessage.setFor("modelNameEditInput");
        modelNameEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(modelNameEditInputMessage);
        
        HtmlOutputText modelEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        modelEditOutput.setId("modelEditOutput");
        modelEditOutput.setValue("Model: * ");
        htmlPanelGrid.getChildren().add(modelEditOutput);
        
        AutoComplete modelEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        modelEditInput.setId("modelEditInput");
        modelEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.model}", Model.class));
        modelEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{carBean.completeModel}", List.class, new Class[] { String.class }));
        modelEditInput.setDropdown(true);
        modelEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(modelEditInput);
        
        Message modelEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        modelEditInputMessage.setId("modelEditInputMessage");
        modelEditInputMessage.setFor("modelEditInput");
        modelEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(modelEditInputMessage);
        
        HtmlOutputText priceEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        priceEditOutput.setId("priceEditOutput");
        priceEditOutput.setValue("Price: * ");
        htmlPanelGrid.getChildren().add(priceEditOutput);
        
        Spinner priceEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        priceEditInput.setId("priceEditInput");
        priceEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.price}", Integer.class));
        priceEditInput.setRequired(true);
        priceEditInput.setMin(10.0);
        LongRangeValidator priceEditInputValidator = new LongRangeValidator();
        priceEditInputValidator.setMinimum(10);
        priceEditInput.addValidator(priceEditInputValidator);
        
        htmlPanelGrid.getChildren().add(priceEditInput);
        
        Message priceEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        priceEditInputMessage.setId("priceEditInputMessage");
        priceEditInputMessage.setFor("priceEditInput");
        priceEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(priceEditInputMessage);
        
        HtmlOutputText dateOfProductionEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dateOfProductionEditOutput.setId("dateOfProductionEditOutput");
        dateOfProductionEditOutput.setValue("Date Of Production:   ");
        htmlPanelGrid.getChildren().add(dateOfProductionEditOutput);
        
        Calendar dateOfProductionEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        dateOfProductionEditInput.setId("dateOfProductionEditInput");
        dateOfProductionEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.dateOfProduction}", Date.class));
        dateOfProductionEditInput.setNavigator(true);
        dateOfProductionEditInput.setEffect("slideDown");
        dateOfProductionEditInput.setPattern("dd/MM/yyyy");
        dateOfProductionEditInput.setRequired(false);
        dateOfProductionEditInput.setMaxdate(new Date());
        htmlPanelGrid.getChildren().add(dateOfProductionEditInput);
        
        Message dateOfProductionEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        dateOfProductionEditInputMessage.setId("dateOfProductionEditInputMessage");
        dateOfProductionEditInputMessage.setFor("dateOfProductionEditInput");
        dateOfProductionEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(dateOfProductionEditInputMessage);
        
        HtmlOutputText customerEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        customerEditOutput.setId("customerEditOutput");
        customerEditOutput.setValue("Customer: * ");
        htmlPanelGrid.getChildren().add(customerEditOutput);
        
        AutoComplete customerEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        customerEditInput.setId("customerEditInput");
        customerEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.customer}", Customer.class));
        customerEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{carBean.completeCustomer}", List.class, new Class[] { String.class }));
        customerEditInput.setDropdown(true);
        customerEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "customer", String.class));
        customerEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{customer.name} #{customer.address} #{customer.email}", String.class));
        customerEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{customer}", Customer.class));
        customerEditInput.setConverter(new CustomerConverter());
        customerEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(customerEditInput);
        
        Message customerEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        customerEditInputMessage.setId("customerEditInputMessage");
        customerEditInputMessage.setFor("customerEditInput");
        customerEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(customerEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText modelNameLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        modelNameLabel.setId("modelNameLabel");
        modelNameLabel.setValue("Model Name:   ");
        htmlPanelGrid.getChildren().add(modelNameLabel);
        
        HtmlOutputText modelNameValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        modelNameValue.setId("modelNameValue");
        modelNameValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.modelName}", String.class));
        htmlPanelGrid.getChildren().add(modelNameValue);
        
        HtmlOutputText modelLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        modelLabel.setId("modelLabel");
        modelLabel.setValue("Model:   ");
        htmlPanelGrid.getChildren().add(modelLabel);
        
        HtmlOutputText modelValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        modelValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.model}", String.class));
        htmlPanelGrid.getChildren().add(modelValue);
        
        HtmlOutputText priceLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        priceLabel.setId("priceLabel");
        priceLabel.setValue("Price:   ");
        htmlPanelGrid.getChildren().add(priceLabel);
        
        HtmlOutputText priceValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        priceValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.price}", String.class));
        htmlPanelGrid.getChildren().add(priceValue);
        
        HtmlOutputText dateOfProductionLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dateOfProductionLabel.setId("dateOfProductionLabel");
        dateOfProductionLabel.setValue("Date Of Production:   ");
        htmlPanelGrid.getChildren().add(dateOfProductionLabel);
        
        HtmlOutputText dateOfProductionValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dateOfProductionValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.dateOfProduction}", Date.class));
        DateTimeConverter dateOfProductionValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        dateOfProductionValueConverter.setPattern("dd/MM/yyyy");
        dateOfProductionValue.setConverter(dateOfProductionValueConverter);
        htmlPanelGrid.getChildren().add(dateOfProductionValue);
        
        HtmlOutputText customerLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        customerLabel.setId("customerLabel");
        customerLabel.setValue("Customer:   ");
        htmlPanelGrid.getChildren().add(customerLabel);
        
        HtmlOutputText customerValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        customerValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{carBean.car.customer}", Customer.class));
        customerValue.setConverter(new CustomerConverter());
        htmlPanelGrid.getChildren().add(customerValue);
        
        return htmlPanelGrid;
    }

	public Car getCar() {
        if (car == null) {
            car = new Car();
        }
        return car;
    }

	public void setCar(Car car) {
        this.car = car;
    }

	public List<Model> completeModel(String query) {
        List<Model> suggestions = new ArrayList<Model>();
        for (Model model : Model.values()) {
            if (model.name().toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(model);
            }
        }
        return suggestions;
    }

	public List<Customer> completeCustomer(String query) {
        List<Customer> suggestions = new ArrayList<Customer>();
        for (Customer customer : Customer.findAllCustomers()) {
            String customerStr = String.valueOf(customer.getName() +  " "  + customer.getAddress() +  " "  + customer.getEmail());
            if (customerStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(customer);
            }
        }
        return suggestions;
    }

	public String onEdit() {
        return null;
    }

	public boolean isCreateDialogVisible() {
        return createDialogVisible;
    }

	public void setCreateDialogVisible(boolean createDialogVisible) {
        this.createDialogVisible = createDialogVisible;
    }

	public String displayList() {
        createDialogVisible = false;
        findAllCars();
        return "car";
    }

	public String displayCreateDialog() {
        car = new Car();
        createDialogVisible = true;
        return "car";
    }

	public String persist() {
        String message = "";
        if (car.getId() != null) {
            car.merge();
            message = "Successfully updated";
        } else {
            car.persist();
            message = "Successfully created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialog.hide()");
        context.execute("editDialog.hide()");
        
        FacesMessage facesMessage = new FacesMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCars();
    }

	public String delete() {
        car.remove();
        FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCars();
    }

	public void reset() {
        car = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	private static final long serialVersionUID = 1L;
}
