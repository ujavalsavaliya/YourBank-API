package com.example.demo.Services;

import com.example.demo.Entity.DepositEntity;
import com.example.demo.Entity.TransferEntity;
import com.example.demo.Entity.TrasitionEntity;
import com.example.demo.Entity.WithdrawEntity;
import com.example.demo.Repositry.DepositRepo;
import com.example.demo.Repositry.TransferRepo;
import com.example.demo.Repositry.WithdrawRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrasitionService {

    @Autowired
    private DepositRepo depositRepo;

    @Autowired
    private WithdrawRepo withdrawRepo;

    @Autowired
    private TransferRepo transferRepo;

    public ResponseEntity<?> getDeposit(String username){
        List<DepositEntity> all_deposit = depositRepo.findByUsername(username);
        List<DepositEntity> sorted = all_deposit.stream()
                .filter(deposit -> deposit.getDate() != null) // filter out null dates
                .sorted(Comparator.comparing(DepositEntity::getDate).reversed())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(sorted);
    }

    public ResponseEntity<?> getWithdraw(String username){
        List<WithdrawEntity> all_withdraw = withdrawRepo.findByUsername(username);
        List<WithdrawEntity> sortedWithdraw = all_withdraw.stream()
                .filter(withdraw -> withdraw.getDate() != null)
                .sorted(Comparator.comparing(WithdrawEntity::getDate).reversed())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(sortedWithdraw);
    }

    public ResponseEntity<?> getTransfer(String username){
        List<TransferEntity> all_transfer = transferRepo.findByUsername(username);
        List<TransferEntity> sortedTransfer = all_transfer.stream()
                .filter(withdraw -> withdraw.getDate() != null)
                .sorted(Comparator.comparing(TransferEntity::getDate).reversed())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(sortedTransfer);
    }

    public ResponseEntity<?> getTrasition(String username){
            List<TrasitionEntity> allTransactions =
                    Stream.concat(
                                    depositRepo.findByUsername(username).stream()
                                            .map(d -> new TrasitionEntity("DEPOSIT", d.getAmount(), d.getDate() , d.getDepositType())),
                                    Stream.concat(
                                            withdrawRepo.findByUsername(username).stream()
                                                    .map(w -> new TrasitionEntity("WITHDRAW", w.getAmount(), w.getDate() , w.getWithdrawType())),
                                            transferRepo.findByUsername(username).stream()
                                                    .map(t -> new TrasitionEntity("TRANSFER", t.getAmount(), t.getDate() , t.getAccno()))
                                    )
                            )
                            .sorted(Comparator.comparing(
                                    TrasitionEntity::getDate,
                                    Comparator.nullsLast(Comparator.naturalOrder())
                            ).reversed())

                            .toList();

            return ResponseEntity.ok(allTransactions);
    }
}
