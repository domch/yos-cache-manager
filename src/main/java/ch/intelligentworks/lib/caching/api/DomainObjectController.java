package ch.intelligentworks.lib.caching.api;

import ch.intelligentworks.lib.caching.model.DomainObject;
import ch.intelligentworks.lib.caching.service.DomainObjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class DomainObjectController {

    @Autowired
    private DomainObjectService domainObjectService;
   

    @GetMapping("/objects")
    public @ResponseBody
    List<DomainObject> getDomainObjects() {
        return domainObjectService.getDomainObjects();
    }

    @GetMapping("/objects/DomainObject/{DomainObjectName}")
    public @ResponseBody
    DomainObject getDomainObjectByName(@PathVariable("DomainObjectName") String DomainObjectName) {
        return domainObjectService.getDomainObjectsByName(DomainObjectName);
    }

    @PostMapping("/objects")
    public @ResponseBody
    DomainObject addDomainObject(@RequestBody DomainObject incomingDomainObject) {
        return domainObjectService.addDomainObject(incomingDomainObject);
    }

    @PutMapping("/objects")
    public @ResponseBody
    DomainObject updateDomainObject(@RequestBody DomainObject incomingDomainObject) {
        return domainObjectService.updateDomainObject(incomingDomainObject);
    }

    @DeleteMapping("/objects/{id}")
    public @ResponseBody
    void deleteDomainObject(@PathVariable("id") Long id) {
        domainObjectService.deleteDomainObject(new DomainObject(id));
    }
}
