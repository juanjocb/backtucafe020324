package com.backtucafe.service;

import com.backtucafe.model.Business;
import com.backtucafe.model.Client;
import com.backtucafe.model.Reservation;
import com.backtucafe.model.request.ReservationRequest;
import com.backtucafe.repository.BusinessRepository;
import com.backtucafe.repository.ClientRepository;
import com.backtucafe.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BusinessRepository businessRepository;

    public String reservation(Client client, ReservationRequest request) {

        Business business = businessRepository.findByName(request.getName());
        if (business == null) {
            throw new RuntimeException("Establecimiento no encontrado");
        }


        Reservation reservation = Reservation.builder()
                .id_client(client)
                .id_business(business)
                .date(request.getHour_reservation())
                .description(request.getDescription())
                .build();


        reservationRepository.save(reservation);

        return "Reserva realizada con éxito";
    }
}