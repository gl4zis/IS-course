package ru.itmo.is.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.dto.request.bid.BidRequest;
import ru.itmo.is.dto.request.bid.DepartureRequest;
import ru.itmo.is.dto.request.bid.OccupationRequest;
import ru.itmo.is.dto.request.bid.RoomChangeRequest;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.security.RolesAllowed;
import ru.itmo.is.service.BidService;

@RestController
@RequestMapping("/bid")
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @RolesAllowed(User.Role.NON_RESIDENT)
    @PostMapping("/occupation")
    public void createOccupationBid(@RequestBody @Valid OccupationRequest req) {
        bidService.createOccupationBid(req);
    }

    @RolesAllowed(User.Role.RESIDENT)
    @PostMapping("/eviction")
    public void createEvictionBid(@RequestBody @Valid BidRequest req) {
        bidService.createEvictionBid(req);
    }

    @RolesAllowed(User.Role.RESIDENT)
    @PostMapping("/departure")
    public void createDepartureBid(@RequestBody @Valid DepartureRequest req) {
        bidService.createDepartureBid(req);
    }

    @RolesAllowed(User.Role.RESIDENT)
    @PostMapping("/room-change")
    public void createRoomChangeBid(@RequestBody @Valid RoomChangeRequest req) {
        bidService.createRoomChangeBid(req);
    }

    @RolesAllowed(User.Role.MANAGER)
    @PostMapping("/{id}/accept")
    public void acceptBid(@PathVariable long id) {

    }
}
