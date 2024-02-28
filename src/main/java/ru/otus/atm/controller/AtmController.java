package ru.otus.atm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.atm.exceptions.WrongAmountException;
import ru.otus.atm.exceptions.WrongDenominationException;
import ru.otus.atm.exceptions.WrongPinCodeException;
import ru.otus.atm.services.AtmCommandServiceImpl;

@RestController
public class AtmController {

    private final AtmCommandServiceImpl commandService;

    public AtmController(AtmCommandServiceImpl commandService) {
        this.commandService = commandService;
    }

    @GetMapping(path = "getCash")
    public ResponseEntity<String> getCash(
            @RequestParam String pinCode,
            @RequestParam Long amount) {

        String returnMessage;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            returnMessage = commandService.getMoney(pinCode, amount);
        } catch (WrongPinCodeException e) {
            returnMessage = e.getMessage();
            httpStatus = HttpStatus.FORBIDDEN;
        } catch (WrongAmountException e) {
            returnMessage = e.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(returnMessage, httpStatus);

    }

    @GetMapping(path = "depositCash")
    public ResponseEntity<String> depositCash(
            @RequestParam String pinCode,
            @RequestParam Long denomination,
            @RequestParam Long amount) {

        String returnMessage;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            returnMessage = commandService.depositCash(pinCode, denomination, amount);
        } catch (WrongPinCodeException e) {
            returnMessage = e.getMessage();
            httpStatus = HttpStatus.FORBIDDEN;
        } catch (WrongDenominationException e) {
            returnMessage = e.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(returnMessage, httpStatus);
    }

    @GetMapping(path = "getBalance/{pinCode}")
    public ResponseEntity<String> getBalance(
            @PathVariable String pinCode) {

        String returnMessage;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            returnMessage = commandService.getBalance(pinCode);
        } catch (WrongPinCodeException e) {
            returnMessage = e.getMessage();
            httpStatus = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(returnMessage, httpStatus);

    }

    @GetMapping(path = "changePin")
    public ResponseEntity<String> changePin(
            @RequestParam String pinCode,
            @RequestParam String newPinCode) {

        String returnMessage;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            returnMessage = commandService.changePin(pinCode, newPinCode);
        } catch (WrongPinCodeException e) {
            returnMessage = e.getMessage();
            httpStatus = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(returnMessage, httpStatus);

    }
}
