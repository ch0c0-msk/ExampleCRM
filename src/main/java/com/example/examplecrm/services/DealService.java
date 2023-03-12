package com.example.examplecrm.services;

import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.Deal;
import com.example.examplecrm.models.User;
import com.example.examplecrm.models.enums.Role;
import com.example.examplecrm.repos.DealRepo;
import com.example.examplecrm.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {

    private final DealRepo dealRepo;
    private final UserRepo userRepo;

    public boolean createDeal(Principal principal, Deal deal) {
        deal.setCreateUser(getUserByPrincipal(principal));
        dealRepo.save(deal);
        log.info("Saving new deal with attributes: {}",deal.toString());
        return true;
    }

    public boolean modifyDeal(Principal principal, Deal deal) {
        if (dealRepo.findById(deal.getId()) == null) {
            log.info("Deal with id: {} doesnt exist", deal.getId());
            return false;
        } else {
            if (getUserByPrincipal(principal).getId() == deal.getCreateUser().getId() | getUserByPrincipal(principal).getAuthorities().contains(Role.MANAGER)) {
                deal.setUpdateUser(getUserByPrincipal(principal));
                dealRepo.save(deal);
                log.info("Modify deal with new attributes: {}", deal.toString());
                return true;
            } else {
                log.info("It`s now your deal");
                return false;
            }
        }
    }

    public boolean deleteDeal(Principal principal, Long id) {

        Deal deal = dealRepo.findById(id).orElse(null);
        if (deal == null) {
            log.info("Deal with id: {} doesnt exist", id);
            return false;
        } else {
            if (getUserByPrincipal(principal).getId() == deal.getCreateUser().getId() | getUserByPrincipal(principal).getAuthorities().contains(Role.MANAGER)) {
                dealRepo.delete(deal);
                log.info("Deal with attributes: {} was deleted", deal.toString());
                return true;
            } else {
                log.info("It`s now your deal");
                return false;
            }
        }
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) { return new User(); }
        return userRepo.findByLogin(principal.getName());
    }
}
