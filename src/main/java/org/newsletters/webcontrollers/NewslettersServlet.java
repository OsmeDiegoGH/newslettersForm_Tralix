package org.newsletters.webcontrollers;

import org.newsletters.repositories.IndustryRepository;
import org.newsletters.repositories.StateRepository;
import org.newsletters.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/newsletters")
public class NewslettersServlet {
    
    private final StateRepository stateRepository;
    private final IndustryRepository industryRepository;
    
    public NewslettersServlet(){
        this(new StateRepository(), new IndustryRepository());
    }
    public NewslettersServlet(StateRepository stateRepository, IndustryRepository industryRepository){
        this.stateRepository = stateRepository;
        this.industryRepository = industryRepository;
    }
    
    @RequestMapping(value="/form", method = RequestMethod.GET)
    public ModelAndView viewForm() {
        ModelAndView view = new ModelAndView("/modules/newslettersForm/view");
        
        String stateListStr = "[]";
        try {
            stateListStr = JSONUtils.INSTANCE.ObjectToJSON(stateRepository.get().toArray());
        } catch (Exception ex) {
            System.err.println("Error al cargar la lista de estados - [" + ex.toString() + "]");
        }
        view.addObject("states", stateListStr);
        
        String industriesListStr = "[]";
        try {
            industriesListStr = JSONUtils.INSTANCE.ObjectToJSON(industryRepository.get().toArray());
        } catch (Exception ex) {
            System.err.println("Error al cargar la lista de industrias - [" + ex.toString() + "]");
        }        
        view.addObject("industries", industriesListStr);
        
        return view;
    }
    
}
