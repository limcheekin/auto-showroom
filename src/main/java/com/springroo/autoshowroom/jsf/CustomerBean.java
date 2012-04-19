package com.springroo.autoshowroom.jsf;

import com.springroo.autoshowroom.domain.Car;
import com.springroo.autoshowroom.domain.Customer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
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
import javax.faces.validator.LengthValidator;
import javax.faces.validator.RegexValidator;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@ManagedBean(name = "customerBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = Customer.class, beanName = "customerBean")
public class CustomerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Customers";

	private Customer customer;

	private List<Customer> allCustomers;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Car> selectedCars;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("name");
        columns.add("address");
        columns.add("email");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Customer> getAllCustomers() {
        return allCustomers;
    }

	public void setAllCustomers(List<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

	public String findAllCustomers() {
        allCustomers = Customer.findAllCustomers();
        dataVisible = !allCustomers.isEmpty();
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
        
        HtmlOutputText nameCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nameCreateOutput.setId("nameCreateOutput");
        nameCreateOutput.setValue("Name: * ");
        htmlPanelGrid.getChildren().add(nameCreateOutput);
        
        InputText nameCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nameCreateInput.setId("nameCreateInput");
        nameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{customerBean.customer.name}", String.class));
        LengthValidator nameCreateInputValidator = new LengthValidator();
        nameCreateInputValidator.setMaximum(20);
        nameCreateInput.addValidator(nameCreateInputValidator);
        nameCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nameCreateInput);
        
        Message nameCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nameCreateInputMessage.setId("nameCreateInputMessage");
        nameCreateInputMessage.setFor("nameCreateInput");
        nameCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nameCreateInputMessage);
        
        HtmlOutputText addressCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        addressCreateOutput.setId("addressCreateOutput");
        addressCreateOutput.setValue("Address: * ");
        htmlPanelGrid.getChildren().add(addressCreateOutput);
        
        InputTextarea addressCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        addressCreateInput.setId("addressCreateInput");
        addressCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{customerBean.customer.address}", String.class));
        LengthValidator addressCreateInputValidator = new LengthValidator();
        addressCreateInputValidator.setMaximum(50);
        addressCreateInput.addValidator(addressCreateInputValidator);
        addressCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(addressCreateInput);
        
        Message addressCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        addressCreateInputMessage.setId("addressCreateInputMessage");
        addressCreateInputMessage.setFor("addressCreateInput");
        addressCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(addressCreateInputMessage);
        
        HtmlOutputText emailCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        emailCreateOutput.setId("emailCreateOutput");
        emailCreateOutput.setValue("Email:   ");
        htmlPanelGrid.getChildren().add(emailCreateOutput);
        
        InputText emailCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        emailCreateInput.setId("emailCreateInput");
        emailCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{customerBean.customer.email}", String.class));
        RegexValidator emailCreateInputRegexValidator = new RegexValidator();
        emailCreateInputRegexValidator.setPattern("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
        emailCreateInput.addValidator(emailCreateInputRegexValidator);
        htmlPanelGrid.getChildren().add(emailCreateInput);
        
        Message emailCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        emailCreateInputMessage.setId("emailCreateInputMessage");
        emailCreateInputMessage.setFor("emailCreateInput");
        emailCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(emailCreateInputMessage);
        
        HtmlOutputText carsCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        carsCreateOutput.setId("carsCreateOutput");
        carsCreateOutput.setValue("Cars:   ");
        htmlPanelGrid.getChildren().add(carsCreateOutput);
        
        HtmlOutputText carsCreateInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        carsCreateInput.setId("carsCreateInput");
        carsCreateInput.setValue("This relationship is managed from the Car side");
        htmlPanelGrid.getChildren().add(carsCreateInput);
        
        Message carsCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        carsCreateInputMessage.setId("carsCreateInputMessage");
        carsCreateInputMessage.setFor("carsCreateInput");
        carsCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(carsCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText nameEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nameEditOutput.setId("nameEditOutput");
        nameEditOutput.setValue("Name: * ");
        htmlPanelGrid.getChildren().add(nameEditOutput);
        
        InputText nameEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nameEditInput.setId("nameEditInput");
        nameEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{customerBean.customer.name}", String.class));
        LengthValidator nameEditInputValidator = new LengthValidator();
        nameEditInputValidator.setMaximum(20);
        nameEditInput.addValidator(nameEditInputValidator);
        nameEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nameEditInput);
        
        Message nameEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nameEditInputMessage.setId("nameEditInputMessage");
        nameEditInputMessage.setFor("nameEditInput");
        nameEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nameEditInputMessage);
        
        HtmlOutputText addressEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        addressEditOutput.setId("addressEditOutput");
        addressEditOutput.setValue("Address: * ");
        htmlPanelGrid.getChildren().add(addressEditOutput);
        
        InputTextarea addressEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        addressEditInput.setId("addressEditInput");
        addressEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{customerBean.customer.address}", String.class));
        LengthValidator addressEditInputValidator = new LengthValidator();
        addressEditInputValidator.setMaximum(50);
        addressEditInput.addValidator(addressEditInputValidator);
        addressEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(addressEditInput);
        
        Message addressEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        addressEditInputMessage.setId("addressEditInputMessage");
        addressEditInputMessage.setFor("addressEditInput");
        addressEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(addressEditInputMessage);
        
        HtmlOutputText emailEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        emailEditOutput.setId("emailEditOutput");
        emailEditOutput.setValue("Email:   ");
        htmlPanelGrid.getChildren().add(emailEditOutput);
        
        InputText emailEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        emailEditInput.setId("emailEditInput");
        emailEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{customerBean.customer.email}", String.class));
        RegexValidator emailEditInputRegexValidator = new RegexValidator();
        emailEditInputRegexValidator.setPattern("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
        emailEditInput.addValidator(emailEditInputRegexValidator);
        htmlPanelGrid.getChildren().add(emailEditInput);
        
        Message emailEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        emailEditInputMessage.setId("emailEditInputMessage");
        emailEditInputMessage.setFor("emailEditInput");
        emailEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(emailEditInputMessage);
        
        HtmlOutputText carsEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        carsEditOutput.setId("carsEditOutput");
        carsEditOutput.setValue("Cars:   ");
        htmlPanelGrid.getChildren().add(carsEditOutput);
        
        HtmlOutputText carsEditInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        carsEditInput.setId("carsEditInput");
        carsEditInput.setValue("This relationship is managed from the Car side");
        htmlPanelGrid.getChildren().add(carsEditInput);
        
        Message carsEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        carsEditInputMessage.setId("carsEditInputMessage");
        carsEditInputMessage.setFor("carsEditInput");
        carsEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(carsEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText nameLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nameLabel.setId("nameLabel");
        nameLabel.setValue("Name:   ");
        htmlPanelGrid.getChildren().add(nameLabel);
        
        HtmlOutputText nameValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nameValue.setId("nameValue");
        nameValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{customerBean.customer.name}", String.class));
        htmlPanelGrid.getChildren().add(nameValue);
        
        HtmlOutputText addressLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        addressLabel.setId("addressLabel");
        addressLabel.setValue("Address:   ");
        htmlPanelGrid.getChildren().add(addressLabel);
        
        InputTextarea addressValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        addressValue.setId("addressValue");
        addressValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{customerBean.customer.address}", String.class));
        addressValue.setReadonly(true);
        addressValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(addressValue);
        
        HtmlOutputText emailLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        emailLabel.setId("emailLabel");
        emailLabel.setValue("Email:   ");
        htmlPanelGrid.getChildren().add(emailLabel);
        
        HtmlOutputText emailValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        emailValue.setId("emailValue");
        emailValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{customerBean.customer.email}", String.class));
        htmlPanelGrid.getChildren().add(emailValue);
        
        HtmlOutputText carsLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        carsLabel.setId("carsLabel");
        carsLabel.setValue("Cars:   ");
        htmlPanelGrid.getChildren().add(carsLabel);
        
        HtmlOutputText carsValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        carsValue.setId("carsValue");
        carsValue.setValue("This relationship is managed from the Car side");
        htmlPanelGrid.getChildren().add(carsValue);
        
        return htmlPanelGrid;
    }

	public Customer getCustomer() {
        if (customer == null) {
            customer = new Customer();
        }
        return customer;
    }

	public void setCustomer(Customer customer) {
        this.customer = customer;
    }

	public List<Car> getSelectedCars() {
        return selectedCars;
    }

	public void setSelectedCars(List<Car> selectedCars) {
        if (selectedCars != null) {
            customer.setCars(new HashSet<Car>(selectedCars));
        }
        this.selectedCars = selectedCars;
    }

	public String onEdit() {
        if (customer != null && customer.getCars() != null) {
            selectedCars = new ArrayList<Car>(customer.getCars());
        }
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
        findAllCustomers();
        return "customer";
    }

	public String displayCreateDialog() {
        customer = new Customer();
        createDialogVisible = true;
        return "customer";
    }

	public String persist() {
        String message = "";
        if (customer.getId() != null) {
            customer.merge();
            message = "Successfully updated";
        } else {
            customer.persist();
            message = "Successfully created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialog.hide()");
        context.execute("editDialog.hide()");
        
        FacesMessage facesMessage = new FacesMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCustomers();
    }

	public String delete() {
        customer.remove();
        FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCustomers();
    }

	public void reset() {
        customer = null;
        selectedCars = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
