package br.com.opsocial.server.locale;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageByLocaleServiceImpl {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String id) {
    	
        Locale locale = LocaleContextHolder.getLocale();
        
        return messageSource.getMessage(id, null, locale);
    }
    
    public String getMessage(String id, Object[] args) {
    	
        Locale locale = LocaleContextHolder.getLocale();
        
        return messageSource.getMessage(id, args, locale);
    }
    
    public String getMessage(String id, Object[] args, Locale locale) {
        return messageSource.getMessage(id, args, locale);
    }
    
    public String getMessage(String id, Locale locale) {
        return messageSource.getMessage(id, null, locale);
    }
}
