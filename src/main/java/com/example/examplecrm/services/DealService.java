package com.example.examplecrm.services;

import com.example.examplecrm.importers.DealExcelImporter;
import com.example.examplecrm.models.Client;
import com.example.examplecrm.models.Deal;
import com.example.examplecrm.models.User;
import com.example.examplecrm.models.enums.Role;
import com.example.examplecrm.repos.DealRepo;
import com.example.examplecrm.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {

    private final DealRepo dealRepo;
    private final UserRepo userRepo;
    private final EmailService emailService;

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

    public List<Deal> getListForExport(Principal principal) {

        return dealRepo.findByStatusAndUser(getUserByPrincipal(principal));
    }

    public List<Deal> getListAllForExport() {

        return dealRepo.findByStatus();
    }

    public void sendClientOffer(Deal deal, Principal principal) {

        if (getUserByPrincipal(principal).getId() == deal.getClient().getCreateUser().getId()) {
             try {
                 this.createDeal(principal, deal);
                 emailService.sendClientOffer(deal);
             } catch (Exception err) {
                 log.info(err.getMessage());
             }
        } else {
            log.info("It`s now your client");
        }
    }

    public void setDealStatus(List<Deal> dealList) {
        for (Deal deal : dealList) {
            deal.setStatus("IN_PROCESS");
            dealRepo.save(deal);
        }
    }

    public void updateDealStatus(MultipartFile file, Principal principal) {
        try {

            List<Deal> dealList = DealExcelImporter.excelToDeals(file.getInputStream());
            for (Deal d : dealList) {

                Deal deal = dealRepo.findById(d.getId()).orElse(null);
                if (deal != null) {
                    if (deal.getCreateUser() == getUserByPrincipal(principal) | getUserByPrincipal(principal).getAuthorities().contains(Role.MANAGER)) {
                        deal.setStatus(d.getStatus());
                        dealRepo.save(deal);
                    } else {
                        log.info("It`s now your deal");
                    }
                } else {
                    log.info("Deal with id {} doesnt exist",d.getId().toString());
                }
            }
        } catch (IOException ioe) {
            throw new RuntimeException("fail to store excel data: " + ioe.getMessage());
        }
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) { return new User(); }
        return userRepo.findByLogin(principal.getName());
    }
}
