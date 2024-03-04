package com.backtucafe.controller;

import com.backtucafe.model.request.ReservationRequest;
import com.backtucafe.service.ReservationService;
import com.backtucafe.model.Client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tuCafe/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping(value = "creation_reservation")
    public ResponseEntity<String> reservation(@AuthenticationPrincipal Client client, @RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.reservation(client, request));
    }
}
