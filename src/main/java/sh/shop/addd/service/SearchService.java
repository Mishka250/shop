package sh.shop.addd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.shop.addd.entity.Furniture;
import sh.shop.addd.entity.Iron;
import sh.shop.addd.entity.SewingMachine;
import sh.shop.addd.entity.config.IronConfig;
import sh.shop.addd.repository.FurnitureRepository;
import sh.shop.addd.repository.IronRepository;
import sh.shop.addd.repository.ProductRepository;
import sh.shop.addd.repository.SewingMachineRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private FurnitureRepository furnitureRepository;
    @Autowired
    private IronRepository ironRepository;
    @Autowired
    private SewingMachineRepository sewingMachineRepository;
    @Autowired
    private ProductRepository productRepository;
    public Iterable<Iron> getIrons(){
        return ironRepository.findAll();
    }
    public Iterable<Furniture> getFurnitures(){
        return furnitureRepository.findAll();
    }

    public Iron getIronBy(Integer id){
        return ironRepository.findOne(id);
    }
    public Iterable<SewingMachine> getMachines(){
        return sewingMachineRepository.findAll();
    }
    public Iterable<Iron> getIrons(IronConfig config) {
        List<Iron> ironsToReturn = new ArrayList<>();

        if(config == null){
            ironRepository.findAll().forEach(ironsToReturn::add);
        }
        if(config.getPower()!=null){
            List<Iron> allByPower = ironRepository.getAllByPower(config.getPower());
            ironsToReturn.clear();
            ironsToReturn.addAll(allByPower);
        }
        if(config.isVapourSelected()){
            List<Iron> allByIsVapor = ironRepository.getAllByIsVapor(config.isVapourValue());
            List<Iron> filtered = ironsToReturn.stream().filter(iron -> allByIsVapor.contains(iron)).collect(Collectors.toList());
            ironsToReturn.clear();
            ironsToReturn.addAll(filtered);
        }
        return ironsToReturn;
    }
}
