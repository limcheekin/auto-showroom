package com.springroo.autoshowroom.jsf.converter;

import com.springroo.autoshowroom.domain.Car;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@Configurable
@FacesConverter("com.springroo.autoshowroom.jsf.converter.CarConverter")
@RooJsfConverter(entity = Car.class)
public class CarConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Car.findCar(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Car ? ((Car) value).getId().toString() : "";
    }
}
